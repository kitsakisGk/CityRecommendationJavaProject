import com.fasterxml.jackson.core.JsonProcessingException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.*;

/**
 * The main class runs the environment
 * that you can get a preferable city.
 *
 * @author Kitsaros
 * @since 2021-12-10
 */
public class Main {

    static CityCollector cc = new CityCollector();
    static Logger logger = Logger.getLogger("CityRecommendationsApp");
    static FileHandler fh;

    public static void main(String[] args) throws Exception {

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("D:/repositories/JavaProjectCityRecommendation");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame f = new JFrame("City Recommendations");
        f.addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent e){
                System.out.println("closing window");
                try {
                    cc.storeCollection();
                    logger.info("Storing city collection to json file.");
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Welcome label
        JLabel welcomeLabel = new JLabel("CITY RECOMMENDATIONS");
        welcomeLabel.setBounds(380, 30, 400, 50);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 26));
        welcomeLabel.setForeground(new Color(60,100,250));
        f.add(welcomeLabel);

        // City input
        JTextField tf = new JTextField();
        tf.setBounds(30,100, 200,30);
        f.add(tf);


        // Displays the results of adding a new City
        JLabel cityInfoLabel = new JLabel();
        cityInfoLabel.setBounds(430, 100, 300, 40);
        cityInfoLabel.setForeground(new Color(250,250,250));
        f.add(cityInfoLabel);
        cityInfoLabel.setVisible(true);

        // Add city to collection Button
        JButton addCityButton = new JButton("Add City");
        addCityButton.setBounds(250,100,150, 30);
        addCityButton.setVisible(true);
        f.add(addCityButton);

        addCityButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String cityInput = tf.getText();
                try {
                    List<Object> addedCityInfo = cc.addCity(cityInput);
                    System.out.println("City added");
                    City addedCity = (City) addedCityInfo.get(0);
                    Boolean isNew = (Boolean) addedCityInfo.get(1);
                    if (isNew){
                        cityInfoLabel.setText("City " + addedCity.getName() + " added.");
                        //tf.setText(null);
                    } else {
                        cityInfoLabel.setText("City has already been added at: " + addedCity.getTimestamp());
                        //tf.setText(null);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Traveller info label
        JLabel travellerInfoLabel = new JLabel("Add traveller info");
        travellerInfoLabel.setBounds(30, 200, 300, 30);
        travellerInfoLabel.setForeground(new Color(250,250,250));
        travellerInfoLabel.setVisible(true);
        f.add(travellerInfoLabel);

        // Traveller name label
        JLabel travellerNameLabel = new JLabel("Name:");
        travellerNameLabel.setBounds(30, 250, 50, 30);
        travellerNameLabel.setForeground(new Color(250,250,250));
        travellerNameLabel.setVisible(true);
        f.add(travellerNameLabel);

        // Traveller name input
        JTextField travellerNameInput = new JTextField();
        travellerNameInput.setBounds(80,250, 200,30);
        travellerNameInput.setVisible(true);
        f.add(travellerNameInput);

        // Traveller age label
        JLabel travellerAgeLabel = new JLabel("Age:");
        travellerAgeLabel.setBounds(30, 300, 50, 30);
        travellerAgeLabel.setForeground(new Color(250,250,250));
        travellerAgeLabel.setVisible(true);
        f.add(travellerAgeLabel);

        // Traveller age input
        JTextField travellerAgeInput = new JTextField();
        travellerAgeInput.setBounds(80,300, 200,30);
        travellerAgeInput.setVisible(true);
        f.add(travellerAgeInput);

        // Add city to collection Button
        JButton recommend = new JButton("Recommend");
        recommend.setBounds(30,350,150, 30);
        recommend.setVisible(true);
        f.add(recommend);

        // City input
        JTextField jt = new JTextField();
        jt.setBounds(30,100, 200,30);
        f.add(jt);

        //add features selections via Spinners
        //left side

        JSpinner cafe = new JSpinner();
        cafe.setModel(new SpinnerNumberModel(5, 0, 10, 1));
        cafe.setBounds(800, 200, 45, 20);
        cafe.setVisible(true);
        f.add(cafe);

        JSpinner sea = new JSpinner();
        sea.setModel(new SpinnerNumberModel(5, 0, 10, 1));
        sea.setBounds(800, 250, 45, 20);
        sea.setVisible(true);
        f.add(sea);

        JSpinner museum = new JSpinner();
        museum.setModel(new SpinnerNumberModel(5, 0, 10, 1));
        museum.setBounds(800, 300, 45, 20);
        museum.setVisible(true);
        f.add(museum);

        JSpinner restaurant = new JSpinner();
        restaurant.setModel(new SpinnerNumberModel(5, 0, 10, 1));
        restaurant.setBounds(800, 350, 45, 20);
        restaurant.setVisible(true);
        f.add(restaurant);

        JSpinner stadium = new JSpinner();
        stadium.setModel(new SpinnerNumberModel(5, 0, 10, 1));
        stadium.setBounds(800, 400, 45, 20);
        stadium.setVisible(true);
        f.add(stadium);

        //right side
        JSpinner park = new JSpinner();
        park.setModel(new SpinnerNumberModel(5, 0, 10, 1));
        park.setBounds(1000, 200, 45, 20);
        park.setVisible(true);
        f.add(park);

        JSpinner gallery = new JSpinner();
        gallery.setModel(new SpinnerNumberModel(5, 0, 10, 1));
        gallery.setBounds(1000, 250, 45, 20);
        gallery.setVisible(true);
        f.add(gallery);

        JSpinner temperature = new JSpinner();
        temperature.setModel(new SpinnerNumberModel(5, 0, 10, 1));
        temperature.setBounds(1000, 300, 45, 20);
        temperature.setVisible(true);
        f.add(temperature);

        JSpinner clouds = new JSpinner();
        clouds.setModel(new SpinnerNumberModel(5, 0, 10, 1));
        clouds.setBounds(1000, 350, 45, 20);
        clouds.setVisible(true);
        f.add(clouds);

        JSpinner geoDist = new JSpinner();
        geoDist.setModel(new SpinnerNumberModel(5, 0, 10, 1));
        geoDist.setBounds(1000, 400, 45, 20);
        geoDist.setVisible(true);
        f.add(geoDist);

        //Features labels
        //left
        JLabel cafeLabel = new JLabel("Cafe:");
        cafeLabel.setBounds(750, 195, 50, 30);
        cafeLabel.setForeground(new Color(250,250,250));
        cafeLabel.setVisible(true);
        f.add(cafeLabel);

        JLabel seaLabel = new JLabel("Sea:");
        seaLabel.setBounds(752, 245, 50, 30);
        seaLabel.setForeground(new Color(250,250,250));
        seaLabel.setVisible(true);
        f.add(seaLabel);

        JLabel museumLabel = new JLabel("Museum:");
        museumLabel.setBounds(724, 295, 60, 30);
        museumLabel.setForeground(new Color(250,250,250));
        museumLabel.setVisible(true);
        f.add(museumLabel);

        JLabel restaurantLabel = new JLabel("Restaurant:");
        restaurantLabel.setBounds(710, 345, 80, 30);
        restaurantLabel.setForeground(new Color(250,250,250));
        restaurantLabel.setVisible(true);
        f.add(restaurantLabel);

        JLabel stadiumLabel = new JLabel("Stadium:");
        stadiumLabel.setBounds(730, 395, 50, 30);
        stadiumLabel.setForeground(new Color(250,250,250));
        stadiumLabel.setVisible(true);
        f.add(stadiumLabel);

        //right
        JLabel parkLabel = new JLabel("Park:");
        parkLabel.setBounds(950, 195, 50, 30);
        parkLabel.setForeground(new Color(250,250,250));
        parkLabel.setVisible(true);
        f.add(parkLabel);

        JLabel galleryLabel = new JLabel("Gallery:");
        galleryLabel.setBounds(935, 245, 50, 30);
        galleryLabel.setForeground(new Color(250,250,250));
        galleryLabel.setVisible(true);
        f.add(galleryLabel);

        JLabel temperatureLabel = new JLabel("Temperature:");
        temperatureLabel.setBounds(903, 295, 80, 30);
        temperatureLabel.setForeground(new Color(250,250,250));
        temperatureLabel.setVisible(true);
        f.add(temperatureLabel);

        JLabel cloudsLabel = new JLabel("Clouds:");
        cloudsLabel.setBounds(936, 345, 50, 30);
        cloudsLabel.setForeground(new Color(250,250,250));
        cloudsLabel.setVisible(true);
        f.add(cloudsLabel);

        JLabel geoDistLabel = new JLabel("GeodesicDistance:");
        geoDistLabel.setBounds(875, 395, 110, 30);
        geoDistLabel.setForeground(new Color(250,250,250));
        geoDistLabel.setVisible(true);
        f.add(geoDistLabel);

        // Checkbox to use custom features
        JCheckBox useCustomFeatures = new JCheckBox("Use custom preferences");
        useCustomFeatures.setBounds(730, 450, 300, 30);
//        useCustomFeatures.setBackground(new Color(7, 16, 30));
        useCustomFeatures.setForeground(new Color(250,250,250));
        useCustomFeatures.setOpaque(false);
        f.add(useCustomFeatures);

        recommend.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String travellerName = travellerNameInput.getText();
                String travellerAgeText = travellerAgeInput.getText();
                int travellerAge = Integer.parseInt(travellerAgeText);
                PerceptronTravellerBase pt;
                if (travellerAge >= 16 && travellerAge < 25) {
                    pt = new PerceptronYoungTraveller(travellerName, travellerAge);
                } else if (travellerAge >= 25 && travellerAge < 60) {
                    pt = new PerceptronMiddleTraveller(travellerName, travellerAge);
                } else {
                    pt = new PerceptronElderTraveller(travellerName, travellerAge);
                }
                try {
                    ArrayList<String> recommendedCities;
                    if (useCustomFeatures.isSelected()) {
                        System.out.println("Checkbox is checked");
                        Double cafeValue = (Double) cafe.getValue();
                        Double seaValue = (Double) sea.getValue();
                        Double museumValue = (Double) museum.getValue();
                        Double restaurantValue = (Double) restaurant.getValue();
                        Double stadiumValue = (Double) stadium.getValue();
                        Double parkValue = (Double) park.getValue();
                        Double galleryValue = (Double) gallery.getValue();
                        Double[] customFeatures = {
                                cafeValue, seaValue, museumValue, restaurantValue,
                                stadiumValue, parkValue, galleryValue
                        };
                        recommendedCities = pt.personalizedRecommend(cc.getCityCollection(), customFeatures);
//                        System.out.println(Arrays.toString(customFeatures));
                    } else {
                        recommendedCities = pt.recommend(cc.getCityCollection());
//                        System.out.println(sortedCities);
                    }
                    ArrayList<String> sortedCities = pt.sortRecommendations(recommendedCities, cc.getCityCollection());
                    jt.setText("Our recommendations for " + travellerName + " are: " + String.valueOf(sortedCities));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println(travellerAge);
            }
        });

        //GUI window info
        f.setSize(1100, 700);
        //image background
        ImageIcon img = new ImageIcon("earth_unsplash.jpg");
        JLabel background = new JLabel("", img, JLabel.CENTER);
        background.setBounds(0,0,1100, 700);
        f.add(background);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
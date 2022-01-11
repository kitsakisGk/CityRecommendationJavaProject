import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * <h1>Main</h1>
 * The main class runs the environment
 * that you can get a preferable city.
 *
 * @author Kitsaros
 * @version 1.0
 * @since 2021-12-10
 */

public class Main {

    static CityCollector cc = new CityCollector();

    public static void main(String[] args) throws Exception {

        JFrame f = new JFrame("City Recommendations");
        f.addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent e){
                System.out.println("closing window");
                try {
                    cc.storeCollection();
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // City input
        JTextField tf = new JTextField();
        tf.setBounds(50,100, 250,40);
        f.add(tf);

        // Displays the results of adding a new City
        JLabel cityInfoLabel = new JLabel();
        cityInfoLabel.setBounds(600, 100, 300, 40);
        f.add(cityInfoLabel);
        cityInfoLabel.setVisible(true);

        // Add city to collection Button
        JButton addCityButton = new JButton("Add City");
        addCityButton.setBounds(350,100,200, 40);
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
                    } else {
                        cityInfoLabel.setText("City has already been added at: " + addedCity.getTimestamp());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Traveller info label
        JLabel travellerInfoLabel = new JLabel("Add traveller info");
        travellerInfoLabel.setBounds(50, 200, 300, 40);
        travellerInfoLabel.setVisible(true);
        f.add(travellerInfoLabel);

        // Traveller name label
        JLabel travellerNameLabel = new JLabel("Name:");
        travellerNameLabel.setBounds(50, 250, 50, 40);
        travellerNameLabel.setVisible(true);
        f.add(travellerNameLabel);

        // Traveller name input
        JTextField travellerNameInput = new JTextField();
        travellerNameInput.setBounds(100,250, 250,40);
        travellerNameInput.setVisible(true);
        f.add(travellerNameInput);

        // Traveller age label
        JLabel travellerAgeLabel = new JLabel("Age:");
        travellerAgeLabel.setBounds(50, 300, 50, 40);
        travellerAgeLabel.setVisible(true);
        f.add(travellerAgeLabel);

        // Traveller age input
        JTextField travellerAgeInput = new JTextField();
        travellerAgeInput.setBounds(100,300, 250,40);
        travellerAgeInput.setVisible(true);
        f.add(travellerAgeInput);

        // Add city to collection Button
        JButton recommend = new JButton("Recommend");
        recommend.setBounds(50,350,200, 40);
        recommend.setVisible(true);
        f.add(recommend);

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
                    ArrayList<String> recommendedCities = pt.recommend(cc.getCityCollection());
                    System.out.println(recommendedCities);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println(travellerAge);
            }
        });

        f.setSize(800, 800);
        f.setLayout(null);
        f.setVisible(true);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Convert list of cities to hashmap for quick membership checks
//        HashMap<String, City> citiesMap = new HashMap<String, City>();
//        for (int i=0;i<cities.size();i++){
//            City city_obj = new City(cities.get(i));
//            // TODO: If same city is added in the collection return the timestamp
//            city_obj.getFeaturesVector();
//            citiesMap.put(cities.get(i), city_obj);
//            citiesList.add(city_obj);
//        }

        //TODO: https://www.twilio.com/blog/working-with-environment-variables-in-java
        /**
         * with this link make in windows the environment variable and then just comment out
         *  the code down below.
         */
//        System.out.println("Read Specific Enviornment Variable");
//        System.out.println("appId Value:- " + System.getenv(City.appId));
//
//        System.out.println("\nRead All Variables:-\n");
//
//        Map <String, String> map = System.getenv();
//        for (Map.Entry <String, String> entry: map.entrySet()) {
//            System.out.println("Variable Name:- " + entry.getKey() + " Value:- " + entry.getValue());
//        }
    }
}
import java.io.IOException;
import java.util.*;

/**
 * A class that makes the suggestion
 * for the travellers
 */

public interface PerceptronTraveller {
    /**
     * @param citiesMap
     * @return arrayList
     * @throws IOException
     */
    ArrayList<String> recommend(HashMap<String,City> citiesMap) throws IOException;
}

abstract class PerceptronTravellerBase implements PerceptronTraveller{
    /**
     * @param name
     * @param age
     * @param weightsBias : a number between [-1,1]
     */

    private final String name;
    private final int age;
    double[] weightsBias;

    //constructor
    public PerceptronTravellerBase(String name, int age){
        this.name = name;
        this.age = age;
    }

    //setters getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double[] getWeightsBias() {
        return weightsBias;
    }

    public void setWeightsBias(double[] weightsBias) {
        this.weightsBias = weightsBias;
    }

    public ArrayList<String> recommend(HashMap<String, City> citiesMap) throws IOException {
        ArrayList<String> recommendedCities = getCityRecommendations(citiesMap);
        return recommendedCities;
    }

    public ArrayList<String> recommend(HashMap<String, City> citiesMap, boolean toUpperCase) throws IOException {
        /**
         * @return recommended cities
         * either to UpperCase or LowerCase
         */
        ArrayList<String> recommendedCities = getCityRecommendations(citiesMap);
        if (toUpperCase == true) {
            for (int i=0; i<recommendedCities.size(); i++) {
                String city = recommendedCities.get(i).toUpperCase(Locale.ROOT);
                recommendedCities.set(i, city);
            }
        }
        return recommendedCities;
    }

    private Double[] getScaledCustomFeatures(Double[] customFeatures){
        Double[] scaledFeatures = new Double[7];
        for (int i=0; i<customFeatures.length; i++){
            scaledFeatures[i] = 2*customFeatures[i] - 1;
        }
        return scaledFeatures;
    }

    public ArrayList<String> personalizedRecommend(
            HashMap<String, City> citiesMap, Double[] customFeatures) throws IOException {
        Double[] scaledFeatures = getScaledCustomFeatures(customFeatures);
        for (int i=0; i<scaledFeatures.length; i++) {
            weightsBias[i] = scaledFeatures[i];
        }
        ArrayList<String> recommendedCities = recommend(citiesMap);
        return recommendedCities;
    }

    abstract ArrayList<String> sortRecommendations(
            ArrayList<String> recommendedCities, HashMap<String, City> cityCollection);

    private double calculateSum(double[] featuresVector) {
        // default method implementation
        double sum = 0.0;
        for (int i=0;i<featuresVector.length;i++) {
            sum += featuresVector[i]*weightsBias[i];
        }
        return sum;
    }

    private int calcHeavisideStep(double[] featuresVector) {
        //heaviside step implementation
        if (calculateSum(featuresVector)>0) {
            return 1;
        } else {
            return 0;
        }
    }

    private ArrayList<String> getCityRecommendations(HashMap<String, City> citiesMap) throws IOException {
        // map collection for objects Cities
        ArrayList<String> recommendedCities = new ArrayList<String>();
        for (Map.Entry<String, City> entry : citiesMap.entrySet()) {
            String cityName = entry.getKey();
            City cityObj = entry.getValue();
            double[] featuresVector = cityObj.getFeaturesVector();
            int result = calcHeavisideStep(featuresVector);
            if (result == 1) {
                recommendedCities.add(cityName);
            }
        }
        return recommendedCities;
    }
}


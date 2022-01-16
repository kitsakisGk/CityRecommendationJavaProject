import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class PerceptronElderTraveller extends PerceptronTravellerBase{

    public PerceptronElderTraveller(String name, int age) {
        super(name, age);
        this.weightsBias = new double[] {0.9, -0.1, -0.8, -0.3, 0.5, -0.3, 0.6, -0.5, -0.2, -0.8};
    }

    public ArrayList<String> sortRecommendations(ArrayList<String> recommendedCities, HashMap<String, City> cityCollection) {
        /*
         * @return an arraylist with sorted cities in a decreasing geodesic distance.
         */
        ArrayList<City> recommendedCityObj = new ArrayList<City>();
        for (int i=0; i<recommendedCities.size(); i++) {
            recommendedCityObj.add(cityCollection.get(recommendedCities.get(i)));
        }
        ReverseCityDistanceComparator rcdc = new ReverseCityDistanceComparator();
        Collections.sort(recommendedCityObj, rcdc);
        ArrayList<String> sortedCities = new ArrayList<String>();
        for (int i=0; i<recommendedCityObj.size(); i++) {
            sortedCities.add(recommendedCityObj.get(i).getName());
        }
        return sortedCities;
    }
}

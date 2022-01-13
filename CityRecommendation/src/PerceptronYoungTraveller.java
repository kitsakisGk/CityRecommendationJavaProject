import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class PerceptronYoungTraveller extends PerceptronTravellerBase{

    public PerceptronYoungTraveller(String name, int age) {
        super(name, age);
        this.weightsBias = new double[] {0.9, 0.8, -0.2, 0.3, 0.5, 0.7, -0.6, -0.5, 0, -0.5};//just a suggestion for now
    }

    public ArrayList<String> sortRecommendations(
            ArrayList<String> recommendedCities, HashMap<String, City> cityCollection
    ) {
        ArrayList<City> recommendedCityObj = new ArrayList<City>();
        for (int i=0; i<recommendedCities.size(); i++) {
            recommendedCityObj.add(cityCollection.get(recommendedCities.get(i)));
        }
        CityDistanceComparator cdc = new CityDistanceComparator();
        Collections.sort(recommendedCityObj, cdc);
        ArrayList<String> sortedCities = new ArrayList<String>();
        for (int i=0; i<recommendedCityObj.size(); i++) {
            sortedCities.add(recommendedCityObj.get(i).getName());
        }
        return sortedCities;
    }
}

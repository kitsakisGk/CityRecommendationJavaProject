import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class PerceptronMiddleTraveller extends PerceptronTravellerBase{

    public PerceptronMiddleTraveller(String name, int age) {
        super(name, age);
        this.weightsBias = new double[] {0.4, 0.5, -0.1, 0.3, -0.5, 0.7, 0.6, -0.7, 0, -0.8};//just a suggestion for now
    }

    public ArrayList<String> sortRecommendations(
            ArrayList<String> recommendedCities, HashMap<String, City> cityCollection
    ) {
        ArrayList<City> recommendedCityObj = new ArrayList<City>();
        for (int i=0; i<recommendedCities.size(); i++) {
            recommendedCityObj.add(cityCollection.get(recommendedCities.get(i)));
        }
        CityTimestampComparator ctc = new CityTimestampComparator();
        Collections.sort(recommendedCityObj, ctc);
        ArrayList<String> sortedCities = new ArrayList<String>();
        for (int i=0; i<recommendedCityObj.size(); i++) {
            sortedCities.add(recommendedCityObj.get(i).getName());
        }
        return sortedCities;
    }
}
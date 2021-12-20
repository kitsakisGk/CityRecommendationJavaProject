import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Hello World!\n");
        ArrayList<String> cities = new ArrayList<String>(
                Arrays.asList("Tokio", "Rio", "Berlin", "Denver", "Helsinki", "Nairobi", "Palermo", "Bogota")
        );

        HashMap<String, City> citiesMap = new HashMap<String, City>();
        for (int i=0;i<cities.size();i++){
            citiesMap.put(cities.get(i), new City(cities.get(i)));
        }

        PerceptronYoungTraveller pyt = new PerceptronYoungTraveller("George", 20);
        PerceptronMiddleTraveller pmt = new PerceptronMiddleTraveller("Kostas", 31);
        PerceptronElderTraveller pet = new PerceptronElderTraveller("John", 65);
        ArrayList<String> recommendationsYoung = pyt.recommend(citiesMap);
        ArrayList<String> recommendationsYoungUppercase = pyt.recommend(citiesMap, true);
        ArrayList<String> recommendationsMid = pmt.recommend(citiesMap);
        ArrayList<String> recommendationsElder = pet.recommend(citiesMap);

        System.out.println(recommendationsYoung);
        System.out.println(recommendationsYoungUppercase);
        System.out.println(recommendationsMid);
        System.out.println(recommendationsElder);

        System.out.println("Done");
    }
}

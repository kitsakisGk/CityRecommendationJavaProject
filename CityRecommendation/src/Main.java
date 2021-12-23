import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Hello World!\n");

        // Initialize json file to store/retrieve data from
        String currentPath = new java.io.File(".").getCanonicalPath();
        System.out.println("Current dir:" + currentPath);
        Path path = Paths.get(currentPath, "cities.json");
        File jsonFile = new File(path.toString());

        // Initialize sample cities list
        ArrayList<String> cities = new ArrayList<String>(
                Arrays.asList("Tokio", "Rio", "Berlin", "Denver", "Helsinki", "Nairobi", "Palermo", "Bogota")
        );
        List<City> citiesList = new ArrayList();

        // Convert list of cities to hashmap for quick membership checks
        HashMap<String, City> citiesMap = new HashMap<String, City>();
        for (int i=0;i<cities.size();i++){
            City city_obj = new City(cities.get(i));
            // TODO: If same city is added in the collection return the timestamp
            city_obj.getFeaturesVector();
            citiesMap.put(cities.get(i), city_obj);
            citiesList.add(city_obj);
        }

        // Write cities list to json file
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(citiesList);
        try (PrintStream out = new PrintStream(new FileOutputStream(jsonFile))){
            out.print(json);
        }

        // Parse json file as list of cities
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        List<City> parsedCities = objectMapper.readValue(jsonFile, new TypeReference<List<City>>(){});

        // Convert list of cities to hashmap for quick membership checks
        Map<String, City> citiesMap2 = new HashMap<>();
        for (City city: parsedCities) {
            citiesMap2.put(city.getName(), city);
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

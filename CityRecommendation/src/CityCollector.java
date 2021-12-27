import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityCollector {
    private HashMap<String, City> cityCollection;
    private static String currentDir;

    static {
        try {
            currentDir = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File citiesJson = new File(String.valueOf(Paths.get(currentDir, "cities.json")));

    public CityCollector(){
        this.cityCollection = retrieveCollection();
    }

    public HashMap<String, City> getCityCollection(){
        return this.cityCollection;
    }

    public void storeCollection() throws JsonProcessingException {
        // Stores current collection to the json file
        List<City> citiesList = new ArrayList();
        for (String key : this.cityCollection.keySet()) {
            citiesList.add(this.cityCollection.get(key));
        }

        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(citiesList);
        try (PrintStream out = new PrintStream(new FileOutputStream(citiesJson))){
            out.print(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, City> retrieveCollection() {
        // Reads json file and returns the hashmap with the stored cities
        List<City> parsedCities;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        try {
            parsedCities = objectMapper.readValue(citiesJson, new TypeReference<List<City>>(){});
        } catch (IOException e) {
            parsedCities = new ArrayList<City>();
        }

        Map<String, City> citiesMap = new HashMap<>();
        for (City city: parsedCities) {
            citiesMap.put(city.getName(), city);
        }
        return (HashMap<String, City>) citiesMap;
    }

    public City addCity(String cityNameToAdd) throws IOException {
        // Use this to add a new city to the collection
        if (this.cityCollection.get(cityNameToAdd) == null) {
            City newCity = new City(cityNameToAdd);
            this.cityCollection.put(cityNameToAdd, newCity);
            return newCity;
        } else {
            return this.cityCollection.get(cityNameToAdd);
        }
    }


}

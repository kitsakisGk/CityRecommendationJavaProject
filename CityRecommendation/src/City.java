import weather.OpenWeatherMap;
import wikipedia.MediaWiki;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class City {

    private MediaWiki wikiInfo;
    private OpenWeatherMap weatherInfo;
    private String name;
    private double[] featuresVector = new double[10];

    //constructors
    public City(String name) throws IOException {
        this.name = name;
        this.wikiInfo = null;
        this.weatherInfo = null;
    }

    //setters getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getFeaturesVector(String appId) throws IOException {
        calculateFeatureVector(appId);
        return featuresVector;
    }

    private int retrieveClouds() {
        return this.weatherInfo.getClouds().getAll();
    }

    private double retrieveTemperature() {
        return weatherInfo.getMain().getTemp();
    }

    private static int getTermCount(String cityArticle, String criterion) {
        cityArticle = cityArticle.toLowerCase();
        int index = cityArticle.indexOf(criterion);
        int count = 0;
        while (index != -1) {
            count++;
            cityArticle = cityArticle.substring(index + 1);
            index = cityArticle.indexOf(criterion);
        }
        return count;
    }

    private double normalizeTerm(int termCount) {
        if (termCount > 10) {
            termCount = 10;
        }
        return termCount / 10;
    }

    private double normalizeTemperature(double temperature) {
        return (temperature - 184) / (331-184);
    }

    private double normalizeCloudsValue(int cloudsValue) {
        return (double) cloudsValue / 100;
    }

    public static double geodesicDistance(double lat1, double lat2, double lon1, double lon2) {
        return(DistanceCalculator.distance(lat1, lon1, lat2, lon2, "K"));
    }

    private double normalizeDistance(double distance) {
        return distance / 15326.480138034301; // Distance between Athens and Sydney
    }

    private void calculateFeatureVector(String appId) throws IOException {
        String cleanedName = this.name.replace(' ', '+');
        this.wikiInfo = new ObjectMapper().readValue(
                new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" +
                        cleanedName + "&format=json&formatversion=2"), MediaWiki.class);
        this.weatherInfo = new ObjectMapper().readValue(
                new URL("http://api.openweathermap.org/data/2.5/weather?q=" +
                        cleanedName + "&APPID=" + appId + ""), OpenWeatherMap.class);
        this.featuresVector[0] = normalizeTerm(
                getTermCount(wikiInfo.getQuery().getPages().get(0).getExtract(), "cafe"));
        this.featuresVector[1] = normalizeTerm(
                getTermCount(wikiInfo.getQuery().getPages().get(0).getExtract(), "sea"));
        this.featuresVector[2] = normalizeTerm(
                getTermCount(wikiInfo.getQuery().getPages().get(0).getExtract(), "museum"));
        this.featuresVector[3] = normalizeTerm(
                getTermCount(wikiInfo.getQuery().getPages().get(0).getExtract(), "restaurant"));
        this.featuresVector[4] = normalizeTerm(
                getTermCount(wikiInfo.getQuery().getPages().get(0).getExtract(), "stadium"));
        this.featuresVector[5] = normalizeTerm(
                getTermCount(wikiInfo.getQuery().getPages().get(0).getExtract(), "park"));
        this.featuresVector[6] = normalizeTerm(
                getTermCount(wikiInfo.getQuery().getPages().get(0).getExtract(), "gallery"));
        this.featuresVector[7] = normalizeTemperature(retrieveTemperature());
        this.featuresVector[8] = normalizeCloudsValue(retrieveClouds());
        this.featuresVector[9] = normalizeDistance(geodesicDistance(
                weatherInfo.getCoord().getLat(), 37.9795,
                weatherInfo.getCoord().getLon(), 23.7162
        )); // Hard coded coords represent the coords of Athens
    }
}

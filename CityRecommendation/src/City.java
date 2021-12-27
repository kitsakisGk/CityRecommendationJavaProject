import weather.OpenWeatherMap;
import wikipedia.MediaWiki;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class has all the information
 * about one city. A traveller will
 * just pick some features.
 */

public class City {

    /**
     *       Cities parameters
     * @param wikiInfo : wiki data
     * @param weatherInfo : weather data
     * @param name : City name
     * @param featuresVector : array with cities terms
     * @param appId : key from openWeather site
     * @param formatter : date formatter for the timestamp
     * @param timestamp : timestamp
     */

    private MediaWiki wikiInfo;
    private OpenWeatherMap weatherInfo;
    private String name;
    private double[] featuresVector = new double[10];
    static final String appId = "a434c79e93cfeee860190e0489ff6715";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private String timestamp;

    //constructors
    public City(){

    }

    public City(String name) throws IOException {
        this.name = name;
        this.wikiInfo = null;
        this.weatherInfo = null;
        calculateFeatureVector();
    }

    public City(String name, MediaWiki wikiInfo, OpenWeatherMap weatherInfo, double[] featuresVector, String timestamp){
        this.name = name;
        this.wikiInfo = wikiInfo;
        this.weatherInfo = weatherInfo;
        this.featuresVector = featuresVector;
        this.timestamp = timestamp;
    }

    //setters getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getFeaturesVector() throws IOException {
        calculateFeatureVector();
        return featuresVector;
    }

    private int retrieveClouds() {
        return this.weatherInfo.getClouds().getAll();
    }

    private double retrieveTemperature() {
        return weatherInfo.getMain().getTemp();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setWikiInfo() throws IOException {
        String cleanedName = this.name.replace(' ', '+');
        this.wikiInfo = new ObjectMapper().readValue(
                new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + cleanedName + "&format=json&formatversion=2"), MediaWiki.class);
    }

    public MediaWiki getWikiInfo(){
        return this.wikiInfo;
    }

    public void setWeatherInfo() throws IOException {
        String cleanedName = this.name.replace(' ', '+');
        this.weatherInfo = new ObjectMapper().readValue(
                new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cleanedName + "&APPID=" + appId + ""), OpenWeatherMap.class);
    }

    public OpenWeatherMap getWeatherInfo(){
        return this.weatherInfo;
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
        return termCount / 10.0;
    }

    private double normalizeTemperature(double temperature) {
        /**
         * @return normalized temperature from min-max scaler
         * as it said
         */
        return (temperature - 184) / (331-184);
    }

    private double normalizeCloudsValue(int cloudsValue) {
        /**
         * @return percentage of clouds
         */
        return (double) cloudsValue / 100;
    }

    public static double geodesicDistance(double lat1, double lat2, double lon1, double lon2) {
        /**
         * @return geodesic distance based on
         * latitude and longitude from GeoDataSource
         */
        return(DistanceCalculator.distance(lat1, lon1, lat2, lon2, "K"));
    }

    private double normalizeDistance(double distance) {
        return distance / 15326.480138034301; // Distance between Athens and Sydney
    }

    private void calculateFeatureVector() throws IOException {
        /**
         * taking the terms that we want for the city and
         * setting them to one city
         */
        if (this.featuresVector == null) {
            this.featuresVector = new double[10];
            setWikiInfo();
            setWeatherInfo();
            Date date = new Date();
            this.timestamp = formatter.format(date);
            this.featuresVector[0] = normalizeTerm(
                    getTermCount(getWikiInfo().getQuery().getPages().get(0).getExtract(), "cafe"));
            this.featuresVector[1] = normalizeTerm(
                    getTermCount(getWikiInfo().getQuery().getPages().get(0).getExtract(), "sea"));
            this.featuresVector[2] = normalizeTerm(
                    getTermCount(getWikiInfo().getQuery().getPages().get(0).getExtract(), "museum"));
            this.featuresVector[3] = normalizeTerm(
                    getTermCount(getWikiInfo().getQuery().getPages().get(0).getExtract(), "restaurant"));
            this.featuresVector[4] = normalizeTerm(
                    getTermCount(getWikiInfo().getQuery().getPages().get(0).getExtract(), "stadium"));
            this.featuresVector[5] = normalizeTerm(
                    getTermCount(getWikiInfo().getQuery().getPages().get(0).getExtract(), "park"));
            this.featuresVector[6] = normalizeTerm(
                    getTermCount(getWikiInfo().getQuery().getPages().get(0).getExtract(), "gallery"));
            this.featuresVector[7] = normalizeTemperature(retrieveTemperature());
            this.featuresVector[8] = normalizeCloudsValue(retrieveClouds());
            this.featuresVector[9] = normalizeDistance(geodesicDistance(
                    getWeatherInfo().getCoord().getLat(), 37.9795, getWeatherInfo().getCoord().getLon(), 23.7162
            )); // Hard coded coords represent the coords of Athens
        }
    }
}

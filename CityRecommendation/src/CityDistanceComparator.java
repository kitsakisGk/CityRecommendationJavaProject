import java.util.Comparator;

public class CityDistanceComparator implements Comparator<City> {
    public int compare(City c1, City c2){
        if (c1.getNormalizedGeodesicDistance() < c2.getNormalizedGeodesicDistance()) return -1;
        if (c1.getNormalizedGeodesicDistance() > c2.getNormalizedGeodesicDistance()) return 1;
        else return 0;
    }
}
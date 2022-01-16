import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * returns an array of cities that are sorted with increased timestamp
 */

public class CityTimestampComparator implements Comparator<City> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public int compare(City c1, City c2){
        LocalDateTime c1TimeStamp = LocalDateTime.parse(c1.getTimestamp(), formatter);
        LocalDateTime c2TimeStamp = LocalDateTime.parse(c2.getTimestamp(), formatter);
        long c1TimeStampNumber = c1TimeStamp.toEpochSecond(ZoneOffset.UTC);
        long c2TimeStampNumber = c2TimeStamp.toEpochSecond(ZoneOffset.UTC);
        if (c1TimeStampNumber < c2TimeStampNumber) return -1;
        if (c1TimeStampNumber > c2TimeStampNumber) return 1;
        else return 0;
    }
}
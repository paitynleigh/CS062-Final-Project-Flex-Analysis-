import java.io.File;
import java.util.HashMap;

/**
 * This class stores the hours of each location in a HashMap where the key is the location name,
 * and the value is a Hashmap with keys being each day of the week, and values being an Hour
 * object containing hours of that day
 */
public class LocationHours {
    HashMap<String, HashMap<String, Hours>> locationHoursByDay;

    /**
     * Constructor to parse lines of location hour data
     * @param locationHourData
     */
    public LocationHours(File locationHourData){
        // while there are more locations in the file add them to the hashmap
        while (true) { 
            // addLocationHours for each line
        }
    }

    /**
     * 
     * @param locationData
     */
    public void addLocationHours(String locationData){
        // takes a line in the general form of the file and adds it to the HashMap
    }
}

import java.io.*;
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
        try{
            BufferedReader br = new BufferedReader(new FileReader(locationHourData));
            String singleLocationData = br.readLine();
            // while there are more locations in the file add them to the hashmap
            while (singleLocationData != null) { 
                addLocationHours(singleLocationData);
            }
        } catch (Exception e) {
            System.out.println("There was an error loading the location hour data. Please check to see that is formatted correctly");
        }
    }

    public void addLocationHours(String locationData){
        // takes a line in the general form of the file and adds it to the HashMap
        String name;
        HashMap<String, Hours> hoursByDay;
        name = "";
        hoursByDay = new HashMap<String, Hours>();
        locationHoursByDay.put(name, hoursByDay);
    }

    public void getHours(String location, String day){
        System.out.println("Opens at " + locationHoursByDay.get(location).get(day).getFirstOpeningTime().toString() + " and closes at " + locationHoursByDay.get(location).get(day).getLatestCloseTime().toString());
    }


    public static void main(String[] args) {
        // tester
        LocationHours lh = new LocationHours(new File("Data/LocationHoursData"));
    }
}

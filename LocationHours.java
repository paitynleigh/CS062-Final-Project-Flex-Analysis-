import java.io.*;
import java.util.HashMap;

public class LocationHours {
    HashMap<String, HashMap<String, Hours>> locationHoursByDay;


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

    private void addLocationHours(String singleLocationData){
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

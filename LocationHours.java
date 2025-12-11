import java.io.*;
import java.util.Arrays;
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
        locationHoursByDay = new HashMap<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(locationHourData));
            br.readLine();
            br.readLine();
            String singleLocationData = br.readLine();
            // while there are more locations in the file add them to the hashmap
            while (singleLocationData != null) { 
                System.out.println("Reading next line");
                addLocationHours(singleLocationData);
                singleLocationData = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("There was an error loading the location hour data. Please check to see that is formatted correctly");
        }
    }

    /**
     * Adds one hashmap entry to outer map by parsing a single line from the file
     * @param singleLocationData
     */
    private void addLocationHours(String singleLocationData){
        // get location name
        String name = singleLocationData.substring(0, singleLocationData.indexOf(":"));
        // save rest
        String remains = singleLocationData.substring(singleLocationData.indexOf(":") + 1);

        HashMap<String, Hours> hoursByDay = new HashMap<>();
        String[] dayEntries = remains.split(",");

        // for each day
        for (String dayEntry: dayEntries) {
            // check which day it is using if or switch cases
            String[] entries = dayEntry.substring(1).split(" ");

            String day = entries[0]; // get day (0 index)

            double[] times;
            
            // if closed all day leave times as null
            if (!entries[1].equals("Closed")) {
                times = new double[entries.length - 1];
                // add remaining entries to new array of doubles 
                for (int i = 1; i < entries.length; i++) {
                    times[i - 1] = Double.parseDouble(entries[i]);
                }
            } else {
                times = new double[0];
            }


            String newDay;
            // edit day to full Dayofweek String
            newDay = switch (day) {
                case "M" -> "Monday";
                case "Tu" -> "Tuesday";
                case "W" -> "Wednesday";
                case "Th" -> "Thursday";
                case "F" -> "Friday";
                case "Sa" -> "Saturday";
                case "Su" -> "Sunday";
                default -> "Invalid";
            };

            Hours hoursForDay = new Hours(times);
            hoursByDay.put(newDay, hoursForDay);
            
        }    

        locationHoursByDay.put(name, hoursByDay);

        System.out.println(singleLocationData);
 
    }

    public void getHours(String location, String day){
        System.out.println("Opens at " + locationHoursByDay.get(location).get(day).getFirstOpeningTime().toString() + " and closes at " + locationHoursByDay.get(location).get(day).getLatestCloseTime().toString());
    }


    public static void main(String[] args) {
        // tester
        LocationHours lh = new LocationHours(new File("Data/LocationHoursData"));
        // lh.getHours("Grove House", "Tuesday");
    }
}

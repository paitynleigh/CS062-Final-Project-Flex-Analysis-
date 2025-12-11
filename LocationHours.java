import java.io.*;
import java.time.LocalTime;
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
    public LocationHours(/*File locationHourData*/){
        locationHoursByDay = new HashMap<>();
    }

    /**
     * Adds one hashmap entry to outer map by parsing a single line from the file
     * @param singleLocationData
     */
    public void addLocationHours(String singleLocationData){
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
 
    }

    public boolean checkIfOpen(String location, String day, LocalTime time){
        Hours hoursForDay = locationHoursByDay.get(location).get(day);
        return hoursForDay.inRange(time);
    }

    public Hours getHours(String location, String day){
        return locationHoursByDay.get(location).get(day);
    }


    public static void main(String[] args) {
        // tester
        //LocationHours lh = new LocationHours(new File("Data/LocationHoursData"));
        // lh.getHours("Grove House", "Tuesday");
        Flex flex = new Flex();
        flex.loadLocationHours("Data/LocationHoursData");
        System.out.println("" + flex.getLocationHours().checkIfOpen("HMC - Jays Place", "Saturday", LocalTime.of(1,35)));
        //flex.getLocationHours().getHours("Shakedown", "Monday");
    }
}

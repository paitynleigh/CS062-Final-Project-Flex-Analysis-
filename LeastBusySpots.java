import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/***
 * Feature 1: Users will be able to enter the time of day in which they want to find a meal, 
 * and the program will provide them with the three locations likely to be the least busy at that time.
 */
public class LeastBusySpots {

    private static DayOfWeek day;
    private static LocalTime target;
    private static int limit;

    //TO-DO don't show locations that are closed

    /***
     * Prints the least busy locations based on user input of time
     * and number of locations
     * @param t time data passed from main method
     */
    public static String findLeastBusy(TimeData t, LocationHours locationHours, String inputDay, String inputHour, String inputMinute, int inputLimit) {
        // Get user input
        // Scanner scan = new Scanner(System.in);
        // System.out.println("Enter preferred visit day and time time (Day H:mm XM) or type 'now' to get current time data:");
        // String timeInput = scan.nextLine();


        // cast from string to time 

        // parse time input
        // TO-DO: paste AI conversation where we got this
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'2025' hh:mm a", Locale.ENGLISH);

        try {
            
            // if (timeInput.equalsIgnoreCase("now")) {
            //     // target = get current time (as LocalTime)
            //     target = LocalDateTime.now().toLocalTime();
            //     day = LocalDateTime.now().getDayOfWeek();
            // } else {
                // parse time
            // target = LocalTime.from(formatter.parse(inputTime.trim()));
            // day = DayOfWeek.from(formatter.parse(inputDay.trim()));
            int hour = Integer.parseInt(inputHour);
            int minute = Integer.parseInt(inputMinute);
            target = LocalTime.of(hour, minute);
                // get in LocalTime format
                // target = timeInput.. or manually
            //}
        } catch (DateTimeParseException e) {
            return "Invalid time format. Please enter time as Day H:mm XM (e.g., Monday 3:30 PM).";
        }
        
        day = DayOfWeek.valueOf(inputDay.toUpperCase());
        // System.out.println("Enter the number of least busy locations you would like to see: ");
        // limit = scan.nextInt();

        // get desired number of locations and print
        List<LocationCount> leastBusy = t.leastBusy(day, target, inputLimit, locationHours);

        int totalMinutes = target.getHour() * 60 + target.getMinute();
        int flooredIntervals = totalMinutes / 15; 
        int flooredMinutes = flooredIntervals * 15;
        LocalTime startInterval = LocalTime.of(flooredMinutes / 60, flooredMinutes % 60);
        LocalTime endInterval;
        if(startInterval.getMinute() + 15 >= 60){
            endInterval = LocalTime.of(startInterval.getHour() + 1, (startInterval.getMinute() + 15) % 60);
        } else {
            endInterval = LocalTime.of(startInterval.getHour(), startInterval.getMinute() + 15);
        }
        String message = "";
        for(int i = 0; i < leastBusy.size(); i++){
            message += ((i+1) + ". " + leastBusy.get(i).toString() + "\n");
        }
        if(leastBusy.isEmpty()){
            message = "No locations are open at this time";
        } else if (leastBusy.size() < inputLimit){
            if (leastBusy.size() == 1){
                message += "Only 1 location open";
            } else {
                message += "Only " + leastBusy.size() + " locations open";
            }
        }
        return message; // add day of week

        //scan.close();
    }

    


}
import java.util.List;
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
    public static void findLeastBusy(TimeData t) {
        // Get user input
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter preferred visit time (H:mm XM) or type 'now' to get current time data:");
        String timeInput = scan.nextLine();
        // cast from string to time 

        // parse time input
        // TO-DO: paste AI conversation where we got this
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");

        try {
            day = LocalDateTime.now().getDayOfWeek();

            if (timeInput.equalsIgnoreCase("now")) {
                // target = get current time (as LocalTime)
                target = LocalDateTime.now().toLocalTime();
            } else {
                // parse time
                target = LocalTime.parse(timeInput.trim(), formatter);
                // get in LocalTime format
                // target = timeInput.. or manually
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please enter time as H:mm XM (e.g., 3:30 PM).");
            return;
        }
        

        System.out.println("Enter the number of least busy locations you would like to see: ");
        limit = scan.nextInt();

        // get desired number of locations and print
        List<LocationCount> leastBusy = t.leastBusy(day, target, limit);

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
        System.out.println("Least busy (" + day.toString().toLowerCase() + " " + startInterval + " - " + endInterval + "): " + leastBusy); // add day of week

        scan.close();
    }


}
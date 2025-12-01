import java.util.List;
import java.util.Scanner;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

/***
 * Feature 1: Users will be able to enter the time of day in which they want to find a meal, 
 * and the program will provide them with the three locations likely to be the least busy at that time.
 */
public class LeastBusySpots {

    private static DayOfWeek day;
    private static LocalTime target;
    private static int limit;

    /***
     * Prints the least busy locations based on user input of time
     * and number of locations
     * @param t time data passed from main method
     */
    public static void findLeastBusy(TimeData t) {
        // Get user input
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter preferred visit time (H:mm AM) or type 'now' to get current time data:");
        String timeInput = scan.nextLine();
        // cast from string to time 

        // parse time input
        // TO-DO: day = get current day

        if (timeInput.contains("now")) {
            // target = get current time (as LocalTime)
        } else {
            // parse time
            // get in LocalTime format
            // target = timeInput.. or manually
        }

        System.out.println("Enter the number of least busy locations you would like to see: ");
        limit = scan.nextInt();

        // get desired number of locations and print
        List<LocationCount> leastBusy = t.leastBusy(day, target, limit);
        System.out.println("Least busy (" + timeInput + "): " + leastBusy); // add day of week

        scan.close();
    }


}
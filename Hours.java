
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Represents the hours during a 24-hour day that a vendor is open using an array of open and close times
 */
public class Hours {

    private LocalTime[] hours;

    public Hours(LocalTime[] hours){
        if(hours.length == 0){
            hours = new LocalTime[0];
        } else if(hours.length % 2 != 0){
            System.out.println("Ensure that each opening hour has a corresponding closing hour in the format [O, C, O, C, ...] for the array passed into Hours. These Hours were not set");
            LocalTime[] noHours = {LocalTime.MIN, LocalTime.MAX};
            this.hours = noHours;
            return;
        }
        this.hours = hours;
        Arrays.sort(this.hours);
    }

    public Hours(double[] hours){
        LocalTime[] timeHours = Hours.transformDoublestoTime(hours);
        if(hours.length == 0){
            hours = new double[0];
        } else if(timeHours.length % 2 != 0){
            System.out.println("Ensure that each opening hour has a corresponding closing hour in the format [O, C, O, C, ...] for the array passed into Hours. These Hours were not set");
            LocalTime[] noHours = {LocalTime.MIN, LocalTime.MAX};
            this.hours = noHours;
            return;
        }
        this.hours = timeHours;
        Arrays.sort(this.hours);
    }

    public static LocalTime[] transformDoublestoTime(double[] hours){
        LocalTime[] timeHours = new LocalTime[hours.length];
        for(int i = 0; i < hours.length; i++){
            int numHours = (int) hours[i];
            // get leftover fractional hours by subtracting the numHours from the rawhours double
            double fractionalHours = hours[i] - numHours;
            // convert to tenths of seconds 
            int totalSeconds = (int) Math.round(fractionalHours * 3600);
            // calculate number of minutes by dividing tenth of seconds by 600
            int minutes = totalSeconds/60;
            // get tenth of seconds left by subtracting equivalent amount of minutes
            int seconds = totalSeconds - 60 * minutes;
            if (hours[i] == 0.0) {
                timeHours[i] = LocalTime.MIN;
            } else if (hours[i] == 24.0){
                timeHours[i] = LocalTime.MAX;
            } else {
                timeHours[i] = LocalTime.of(numHours,minutes,seconds);
            }
        }
        return timeHours;
    }

    public LocalTime[] getHours(){
        return hours;
    }

    public boolean inRange(LocalTime time){
        for(int i = 0; i < hours.length; i += 2){
            if(hours[i].compareTo(time) < 0 && hours[i+1].compareTo(time) > 0){
                return true;
            }
        }
        return false;
    }

    // public LocalTime getFirstOpeningTime(){
    //     return hours[0];
    // }

    // public LocalTime getLatestCloseTime(){
    //     return hours[hours.length];
    // }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String message = hours[0].format(formatter) + " - " + hours[1].format(formatter);
        for(int i = 2; i < hours.length; i += 2){
            message += " and " + hours[i].format(formatter) + "-" + hours[i+1].format(formatter);
        }
        return message;
    }


    public static void main(String[] args) {
        // testing
        System.out.println(LocalTime.MIN.toString());
        double[] t1 = {1,2,3,4};
        Hours h1 = new Hours(t1);
        LocalTime[] t2 = {LocalTime.MIN, LocalTime.now()};
        Hours h2 = new Hours(t2);
        System.out.println(h1.toString());
        System.out.println(h2.toString());
    }
}

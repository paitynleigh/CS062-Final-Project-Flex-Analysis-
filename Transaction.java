import java.time.LocalDateTime;
import java.time.LocalTime;


public class Transaction {
    private LocalDateTime dateTime;
    private double amount;
    private String location;
    private String school;
    private String day; 
    private LocalTime time;
    private int dayIndex;


 
    public Transaction(LocalDateTime dateTime, double amount, String location, String school){
        this.dateTime = dateTime;
        this.amount = amount;
        this.location = location;
        this.school = school;

        //Extract Day of the week Name: Monday -> 1, Tuesday -> 2...
        this.day = dateTime.getDayOfWeek().toString();

        //Extract day of Week Index: Monday -> 1, Tuesday -> 2...
        this.dayIndex = dateTime.getDayOfWeek().getValue();

        //Extract Time only 
        this.time = dateTime.toLocalTime();
    }


    /*
    * Getter Methods for all Instance Variables in Class
     */
    public LocalDateTime getTimestamp(){ 
        return dateTime;
    }
    public double getAmount() { 
        return amount; 
    }
    public String getLocation() { 
        return location;
     }
    public String getSchool() {
         return school; 
    }
    public String getDayName(){
        return day;
    }
    public LocalTime getTime(){
        return time;
    }
    public int getDayIndex(){
        return dayIndex;
    }

    @Override
    public String toString(){
        return day + ", " + time + "," + location + ", " +amount + ", " + school;
    }

}

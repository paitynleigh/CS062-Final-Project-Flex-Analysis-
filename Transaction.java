import java.time.LocalDateTime;


public class Transaction {
    private LocalDateTime dateTime;
    private double amount;
    private String location;
    private String school;

 
    public Transaction(LocalDateTime dateTime, double amount, String location, String school){
        this.dateTime = dateTime;
        this.amount = amount;
        this.location = location;
        this.school = school;
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

    @Override
    public String toString(){
        return dateTime + ", " + location + ", " +amount + ", " + school;
    }

}

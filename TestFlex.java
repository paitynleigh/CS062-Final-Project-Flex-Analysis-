import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class TestFlex {
    public static void main(String[] args){
        Flex flex = new Flex();

        flex.loadCSV("Data/Stored_Value_Transaction_by_Customer__11_39_2025-10-17_11_40_52(Stored_Value_Transaction_by_Cus).csv");

        // Example: Print all transactions for Locations
        System.out.println("Frequency Data");
        System.out.println(flex.getFreqData());

        // Example: Print number of transactions per each time Interval at all 
        // locations and Days
        System.out.println("Time/Interval Data");
        System.out.println(flex.getTimeData());

        // Example: Top 3 least busy locations for Monday 12:00 - 12:15
        // Returns a List<LocationCount> (location name + summed count)
        List<LocationCount> leastBusy = flex.getTimeData()
                .leastBusy(DayOfWeek.MONDAY, LocalTime.of(12, 0), 3);
        System.out.println("Least busy (Mon 12:00 - 12:15): " + leastBusy);
    }
}   

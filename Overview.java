import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

/**
 * Prints general Information and Analysis from flex data set
 */

public class Overview {
    private TimeData td;

    public Overview(TimeData td){
        this.td = td;
    }
    

    /**
    *  prints out each location and the LEAST busy week day at that location
    */ 
    public void printLeastBusy(){
        // Average Transactions per week day
        Map<String, Map<DayOfWeek, Double>> averages = computeAveragePerWeekday();
        StringBuilder sb = new StringBuilder();

        // Create map with average transactions per day per location
        for(String loc : averages.keySet()){
            Map<DayOfWeek, Double> dayAverages = averages.get(loc);

            // initialize least busy day and value (start at greatest and search for lower)
            DayOfWeek leastBusy = null;
            double leastValue = Double.MAX_VALUE;

        for (DayOfWeek day : dayAverages.keySet()) {
            double avg = dayAverages.get(day);
            if (avg < leastValue) {    // Find the lowest avg value iterating through days
                leastValue = avg;
                leastBusy = day;
            }
        }

    sb.append(loc + ": " + leastBusy + " (avg. " + leastValue + " transactions) \n");
        }
        System.out.println("Least Busy Day on Average: \n" + sb);
    }

    /**
    *  prints out each location and the Most busy week day at that location
    */ 
    public void printMostBusy(){
        // Average Transactions per week day
        Map<String, Map<DayOfWeek, Double>> averages = computeAveragePerWeekday();
        StringBuilder sb = new StringBuilder();

        // Create map with average transactions per day per location
        for(String loc : averages.keySet()){
            Map<DayOfWeek, Double> dayAverages = averages.get(loc);

            // Same as leastBusy but now values starting at lowest 
            DayOfWeek mostBusy = null;
            double mostValue = Double.MIN_VALUE;

        for (DayOfWeek day : dayAverages.keySet()) {
            double avg = dayAverages.get(day);
            if (avg > mostValue) { // Find the Greatest avg value iterating through days
                mostValue = avg;
                mostBusy = day;
            }
        }

    sb.append(loc + ": " + mostBusy + " (avg. " + mostValue + " transactions) \n");
        }
        System.out.println("Most Busy Day on Average: \n" + sb);
    }

    /*
 * Computes the average number of transactions per weekday for each location.
 * @return map: location -> (DayOfWeek -> averageTransactions)
 */
private Map<String, Map<DayOfWeek, Double>> computeAveragePerWeekday() {
    Map<DayOfWeek, Map<String, Map<Integer, Integer>>> data = td.getData();
    Map<String, Map<DayOfWeek, Double>> averages = new HashMap<>();
    Map<String, Map<DayOfWeek, Integer>> dayCounts = new HashMap<>(); // counts number of occurrences of each weekday

    // Sum transactions per location per weekday, and count occurrences
    for (DayOfWeek day : data.keySet()) {
        Map<String, Map<Integer, Integer>> locationMap = data.get(day);

        for (String loc : locationMap.keySet()) {
            Map<Integer, Integer> intervalMap = locationMap.get(loc);

            int total = 0;
            for (int count : intervalMap.values()) {
                total += count;
            }

            // Sum totals
            averages.computeIfAbsent(loc, k -> new HashMap<>());
            averages.get(loc).merge(day, (double) total, Double::sum);

            // Count number of entries for each weekday 
            dayCounts.computeIfAbsent(loc, k -> new HashMap<>());
            dayCounts.get(loc).merge(day, 1, Integer::sum);
        }
    }

    // Divide sum by counts to get average
    for (String loc : averages.keySet()) {
        Map<DayOfWeek, Double> sumMap = averages.get(loc);
        Map<DayOfWeek, Integer> countMap = dayCounts.get(loc);

        for (DayOfWeek day : sumMap.keySet()) {
            double avg = sumMap.get(day) / countMap.get(day);
            sumMap.put(day, avg);
        }
    }

    return averages;
}


    // This method prints out the average dollar amount per transaction for each school
    public String printTransactionAmount(){
        //to-do
        return "";
    }

    // This method prints out the TOTAL dollar amount spent over the semester per school
    public String printTotalAmount(){
        //to-do
        return "";
    }

    // This method prints the total number of transactions per each location
    public String printNumTransactions(){
        //to-do
        return "";
    }

    public static void main(String[] args){
        Flex flex = new Flex();
        flex.loadCSV("Data/Stored_Value_Transaction_by_Customer__11_39_2025-10-17_11_40_52(Stored_Value_Transaction_by_Cus).csv");

        Overview o = new Overview(flex.getTimeData());

        o.printLeastBusy();
        o.printMostBusy();
    }
}

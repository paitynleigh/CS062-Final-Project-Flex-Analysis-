import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

/**
 * Feature 3: Prints general Information and Analysis using the flex data, including the least busy day on average,
most busy day on average, average transaction price, total retail over 6 weeks, as well as the average transaction price and total dollar
amount spent per school at a given location.
@authors Jalen DeLoney, Paityn Richardson, Maren Rusk, and Nate Wehner
 */

public class Overview {
    private TimeData td;
    private FrequencyData fd;

    public Overview(TimeData td, FrequencyData fd){
        this.td = td;
        this.fd = fd;
    }

    /**
    *  prints out each location and the LEAST busy week day at that location
    */ 
    public String getLeastBusy(){
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

    sb.append(loc).append(": ").append(leastBusy).append(" (avg. ").append(leastValue).append(" transactions) \n");
        }
        return "Least Busy Day on Average: \n" + sb;
    }


    /**
    *  prints out each location and the Most busy week day at that location
    */ 
    public String getMostBusy(){
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
        return "Most Busy Day on Average: \n" + sb;
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


    /*
    * prints out the average dollar amount per transaction for each school
    */
    public String getTransactionAmountbySchool(){
        Map<String, Map<String, Integer>> counts = fd.getData();
        Map<String, Map<String, Double>> amounts = fd.getTotalAmount();

        //initialize map for school -> total amounts 
        Map<String, Double> totalAmountPerSchool = new HashMap<>();
        //initialize map for school -> total num transactions
        Map<String, Integer> totalCountPerSchool = new HashMap<>();

        StringBuilder sb = new StringBuilder();

        for(String loc : counts.keySet()){
            Map<String, Integer> schoolCounts = counts.get(loc);
            Map<String, Double> schoolAmounts = amounts.get(loc);

            for(String school: schoolCounts.keySet()){
                //Skip null data for overview
                if(school.equals("{null}") || school.equals("CUC")) continue;

                //Total Dollar Amount of transactions per School
                totalAmountPerSchool.put(school, totalAmountPerSchool.getOrDefault(school, 0.0) + schoolAmounts.get(school));
                // Total Num of transactions per School
                totalCountPerSchool.put(school, totalCountPerSchool.getOrDefault(school, 0) + schoolCounts.get(school));
            }
        }

        // Construct Output
        for(String school : totalAmountPerSchool.keySet()){
            double totalAmount = totalAmountPerSchool.get(school);
            int totalCount = totalCountPerSchool.get(school);

            double average = Math.round((totalAmount / totalCount) * 100.0) / 100.0;

            sb.append(school + ": $" + average + "    ");
        }
        return "Average Transaction Price: \n" + sb;
    }

    /*
    * prints out the average dollar amount per transaction for each location
    */

    public String getTransactionAmountbyLocation(){
        Map<String, Map<String, Integer>> counts = fd.getData();
        Map<String, Map<String, Double>> amounts = fd.getTotalAmount();


        StringBuilder sb = new StringBuilder();

        for(String loc : counts.keySet()){
            Map<String, Integer> schoolCounts = counts.get(loc);
            Map<String, Double> schoolAmounts = amounts.get(loc);

            int totalCount = 0;
            double totalAmount = 0.0;

        // Sum over all schools at each location
        for (String school : schoolCounts.keySet()) {
            totalCount += schoolCounts.get(school);
            totalAmount += schoolAmounts.get(school);
        }
         double average = Math.round((totalAmount / totalCount) * 100.0) / 100.0;

         sb.append(loc + ": $" + average + "\n");
        }
        return "Average Transaction Price by Location: \n" + sb;
    }

    /** 
   *prints out the TOTAL dollar amount spent over the semester per school
   */
    public String getTotalAmountbySchool(){
         Map<String, Map<String, Double>> amounts = fd.getTotalAmount();

         //initialize map for school -> total amounts 
         Map<String, Double> totalAmountPerSchool = new HashMap<>();

         for(String loc : amounts.keySet()){
            Map<String, Double> schoolAmounts = amounts.get(loc);

            for(String school : schoolAmounts.keySet()){
                //Skip null data for overview
                if(school.equals("{null}") || school.equals("CUC")) continue;

                //Total Dollar Amount of transactions per School
                totalAmountPerSchool.put(school, totalAmountPerSchool.getOrDefault(school, 0.0) + schoolAmounts.get(school));
            }
         }

         //Construct Output 
         StringBuilder sb = new StringBuilder();
         for(String school : totalAmountPerSchool.keySet()){
            double totalAmount = totalAmountPerSchool.get(school);
            int roundAmount = (int) totalAmount;

            sb.append(school + ": $" + roundAmount + "    ");
         }
        return "Total Dollar Amount Spent per School: \n" + sb;
    }

    /** 
   *prints out the TOTAL dollar amount spent over the semester per location
   */
    public String getTotalAmountbyLocation(){
        Map<String, Map<String, Double>> amounts = fd.getTotalAmount();
        StringBuilder sb = new StringBuilder();

        for(String loc : amounts.keySet()){
            Map<String, Double> schoolAmounts = amounts.get(loc);

            double totalAmount = 0.0;

            for(String school : schoolAmounts.keySet()){
                totalAmount += schoolAmounts.get(school);
            }
            int roundAmount = (int) totalAmount;
            sb.append(loc + ": $" + roundAmount + "\n");
        }
        return "Total Dollar Amount Spent: \n" + sb;
    }

    public static void main(String[] args){
        Flex flex = new Flex();
        flex.loadCSV("Data/Stored_Value_Transaction_by_Customer__11_39_2025-10-17_11_40_52(Stored_Value_Transaction_by_Cus).csv");

        Overview o = new Overview(flex.getTimeData(), flex.getFreqData());

        System.out.println(o.getLeastBusy());
        System.out.println(o.getMostBusy());

        System.out.println(o.getTransactionAmountbySchool());
        System.out.println(o.getTransactionAmountbyLocation());

        System.out.println(o.getTotalAmountbySchool());
        System.out.println(o.getTotalAmountbyLocation());

    }
}

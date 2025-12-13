import java.util.HashMap;
import java.util.Map;

/**
 * Class storing frequency data for locations
 * @authors Jalen DeLoney, Paityn Richardson, Maren Rusk, and Nate Wehner
 */
public class FrequencyData {
    //location -> school -> transaction count
    private Map<String, Map<String, Integer>> data;

    // location -> school -> total transaction amount
    private Map<String, Map<String, Double>> totalAmount;

    /**
     * Constructor
     * initializes instance variables
     */
    public FrequencyData(){
        data = new HashMap<>();
        totalAmount = new HashMap<>();
    }

    /**
     * adds a transaction to our data given a Transaction
     * @param t given transaction
     */
    public void add(Transaction t){
        String location = t.getLocation();
        String school = t.getSchool();
        Double amount = t.getAmount();

        // If the location is not present, create a new map for it
        if (!data.containsKey(location)) {
            data.put(location, new HashMap<>());
            totalAmount.put(location, new HashMap<>());
        }

        Map<String, Integer> schoolMap = data.get(location);
        // Increment count for this school at this location
        schoolMap.put(school, schoolMap.getOrDefault(school, 0) + 1);

         Map<String, Double> amountMap = totalAmount.get(location);
         // calculate total amount spent by location by school 
        amountMap.put(school, amountMap.getOrDefault(school, 0.0) + amount);
    }

    // Getter for counts
    public Map<String, Map<String, Integer>> getData() {
        return data;
    }

    // Getter for total amounts
    public Map<String, Map<String, Double>> getTotalAmount() {
        return totalAmount;
    }

    /**
     * Gives location with its frequency data
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String loc : data.keySet()) {
            sb.append("Location: ").append(loc).append("\n");

            Map<String, Integer> schoolMap = data.get(loc);
            boolean first = true;

            for (Map.Entry<String, Integer> entry : schoolMap.entrySet()) {
                if (!first) sb.append(", ");
                sb.append(entry.getKey()).append(": ").append(entry.getValue());
                first = false;
            }

            sb.append("\n");
        }

        return sb.toString();
}

}

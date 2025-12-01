import java.util.HashMap;
import java.util.Map;

public class FrequencyData {
    //location -> school -> transaction count
    private Map<String, Map<String, Integer>> data;

    public FrequencyData(){
        data = new HashMap<>();
    }

    public void add(Transaction t){
        String location = t.getLocation();
        String school = t.getSchool();

        // If the location is not present, create a new map for it
        if (!data.containsKey(location)) {
            data.put(location, new HashMap<>());
        }

        Map<String, Integer> schoolMap = data.get(location);
        // Increment count for this school at this location
        schoolMap.put(school, schoolMap.getOrDefault(school, 0) + 1);
    }

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

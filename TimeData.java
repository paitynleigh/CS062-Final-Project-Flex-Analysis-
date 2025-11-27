import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

public class TimeData {
    //day of week -> location -> interval -> number of transactions
    private Map<DayOfWeek, Map<String, Map<Integer, Integer>>> data;

    public TimeData() {
        data = new HashMap<>();
    }

    public void add(Transaction t) {
        LocalDateTime dt = t.getTimestamp();
        DayOfWeek day = dt.getDayOfWeek();
        String location = t.getLocation();
        int interval = dt.getHour() * 4 + dt.getMinute() / 15; // 15-min intervals

        //outer map if day not present
        if (!data.containsKey(day)) {
            data.put(day, new HashMap<>());
        }
        Map<String, Map<Integer, Integer>> locationMap = data.get(day);

        // if location not present
        if (!locationMap.containsKey(location)) {
            // Set Initial Count to 0
            Map<Integer, Integer> intervalMap = new HashMap<>();
            for (int i = 0; i <= 96; i++) {
                intervalMap.put(i, 0);
            }
            locationMap.put(location, intervalMap);
        }
        Map<Integer, Integer> intervalMap = locationMap.get(location);

        // Increment count for specific interval
        intervalMap.put(interval, intervalMap.get(interval) + 1);
}


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DayOfWeek day : data.keySet()) {
            sb.append(day).append(":\n");
            Map<String, Map<Integer, Integer>> locationMap = data.get(day);

            for (String loc : locationMap.keySet()) {
                sb.append("  Location: ").append(loc).append("\n");
                Map<Integer, Integer> intervalMap = locationMap.get(loc);

                for (Integer interval : intervalMap.keySet()) {
                    sb.append(interval).append(":").append(intervalMap.get(interval)).append("\n ");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}

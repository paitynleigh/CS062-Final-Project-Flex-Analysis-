import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class TimeData {
    //day of week -> location -> interval -> number of transactions
    private Map<DayOfWeek, Map<String, Map<Integer, Integer>>> data;

    public TimeData() {
        // Build maps as we go
        data = new HashMap<>();
    }

    public void add(Transaction t) {
        // Grab day, location, and 15-min bucket
        LocalDateTime dt = t.getTimestamp();
        DayOfWeek day = dt.getDayOfWeek();
        String location = t.getLocation();
        // this truncates, can we round to nearest?
        //int interval = dt.getHour() * 4 + dt.getMinute() / 15; // 15-min intervals
        int totalMinutes = dt.getHour() * 60 + dt.getMinute();
        int interval = Math.round(totalMinutes / 15.0f); // nearest 15 min interval

        // Get or create the map for this day
        Map<String, Map<Integer, Integer>> locationMap = data.get(day);
        if (locationMap == null) {
            locationMap = new HashMap<>();
            data.put(day, locationMap);
        }

        // Get or create the map for this location
        Map<Integer, Integer> intervalMap = locationMap.get(location);
        if (intervalMap == null) {
            intervalMap = new HashMap<>();
            locationMap.put(location, intervalMap);
        }

        // Bump this bucket
        int current = intervalMap.getOrDefault(interval, 0);
        intervalMap.put(interval, current + 1);
    }

    /**
     * Find least busy locations in a 15-minute interval
     */
    public List<LocationCount> leastBusy(DayOfWeek day, LocalTime target, int limit) {
        if (target == null || limit <= 0) return Collections.emptyList();

        Map<String, Map<Integer, Integer>> locationMap = data.get(day);
        if (locationMap == null) return Collections.emptyList();

        int interval = target.getHour() * 4 + target.getMinute() / 15;
        List<LocationCount> results = new ArrayList<>();

        for (Map.Entry<String, Map<Integer, Integer>> entry : locationMap.entrySet()) {
            int count = entry.getValue().getOrDefault(interval, 0);
            results.add(new LocationCount(entry.getKey(), count));
        }

        // Lowest count first; tie-break by name
        results.sort((a, b) -> {
            int cmp = Integer.compare(a.getCount(), b.getCount());
            return (cmp != 0) ? cmp : a.getLocation().compareTo(b.getLocation());
        });

        // Return only the specified number of least busy locations
        List<LocationCount> results_limited = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, results.size()); i++) {
            results_limited.add(results.get(i));
        }
        return results_limited;
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


                // Print only intervals with transactions.
                // Time intervals now display as HH:mm instead of number 0-95.
                for (int interval = 0; interval <= 95; interval++) {
                    int count = intervalMap.getOrDefault(interval, 0);
                    if (count == 0) continue;

                    int hour = interval / 4;
                    int minute = (interval % 4) * 15;
                    String label = String.format("%02d:%02d", hour, minute);

                    sb.append(label).append(":").append(count).append("\n ");

                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}

public class LocationCount {
    // One location name plus its count for a query window
    private final String location;
    private final int count;

    public LocationCount(String location, int count) {
        this.location = location;
        this.count = count;
    }

    public String getLocation() {
        return location;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        // Prints like "Some Hall: 7"
        return location + ": " + count;
    }
}

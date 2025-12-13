/**
 * Class storing frequency counts for locations
 * @authors Jalen DeLoney, Paityn Richardson, Maren Rusk, and Nate Wehner
 */
public class LocationCount {
    // One location name plus its count for a query window
    private final String location;
    private final int count;

    /**
     * Constructor
     * @param location location name
     * @param count frequency count for that location
     */
    public LocationCount(String location, int count) {
        this.location = location;
        this.count = count;
    }

    /**
     * Getters for instance variables
     */
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

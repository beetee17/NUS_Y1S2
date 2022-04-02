import java.util.ArrayList;
import java.util.List;

/**
 * A database of {@link Location Locations}
 */
class LocationDatabase {

    private final List<Location> listOfLocations = new ArrayList<>();

    /**
     * Gets a location given its name
     * @param locationName The name of the location
     * @return             the resulting location
     */
    Location getLocation(String locationName) {
        // If any of the locations have the same name as locationName,
        // return it.
        for (Location l : listOfLocations) {
            if (l.toString().equals(locationName)) {
                return l;
            }
        }

        // Otherwise, create the location and return it.
        Location l = new Location(locationName);
        this.listOfLocations.add(l);
        return l;
    }

}


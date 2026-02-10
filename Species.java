public class Species {
    protected Location location;

    public Species(Location location) {
        this.location = location;
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Set the animal's location.
     * @param location The new location.
     */
    protected void setLocation(Location location)
    {
        this.location = location;
    }
}

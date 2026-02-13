public class Species {
    // The animal's position.
    private Location location;
    // Whether the animal is alive or not.
    private boolean alive;

    public Species(Location location) {
        this.location = location;
        this.alive = true;
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     */
    protected void setDead()
    {
        alive = false;
        setLocation(null);
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

    /**
     * Return the energy value gained by eating this species.
     * By default, non-edible species return zero.
     */
    public int getEnergyValue()
    {
        return 0;
    }
}

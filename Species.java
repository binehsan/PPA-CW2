/**
 * Base class for all species in the simulation, tracking location, life
 * state, and infection status.
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 1.0
 */
public class Species {
    // The animal's position.
    private Location location;
    // Whether the animal is alive or not.
    private boolean alive;
    private boolean infected;

    /**
     * Create a species instance at the given location.
     *
     * @param location The species' location.
     */
    public Species(Location location) {
        this.location = location;
        this.alive = true;
        this.infected = false;
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
     * Overridden in each species
     */
    public int getEnergyValue()
    {
        return 0;
    }

    /**
     * @return true if the species is infected.
     */
    public boolean isInfected() {
        return infected;
    }

    /**
     * Set whether the species is infected.
     *
     * @param infected true to mark infected.
     */
    public void setInfected(boolean infected) {
        this.infected = infected;
    }
}


/**
 * The bush plant in the simulation. This is one of the types of plants
 * that are found in the desert ecosystem.
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 1.0
 */
public class Bush extends Plant {

    /**
     * Create a bush at the given location.
     *
     * @param location The bush's location.
     */
    public Bush(Location location) {
        super(SimulationConfig.BUSH_HARVESTS, location);
    }

    /**
     * @return true if this plant blocks movement.
     */
    @Override
    public boolean blocksMovement()
    {
        return false;
    }
}

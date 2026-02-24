/**
 * The Nakhla plant in the simulation. This is a desert palm that can be
 * harvested for energy by herbivores.
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 7.0
 */
public class Nakhla extends Plant {
    /**
     * Create a Nakhla plant at the given location.
     *
     * @param location The plant's location.
     */
    public Nakhla(Location location) {
        super(SimulationConfig.NAKHLA_HARVESTS, location);
    }

    /**
     * @return true if this plant blocks movement.
     */
    @Override
    public boolean blocksMovement()
    {
        return false; //CHANGE THIS TO TRUE IF YOU WANT NAKHLAS TO BLOCK MOVEMENT
    }
}

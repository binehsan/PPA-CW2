
/**
 * The bush plant in the simulation. This is one of the types of plants
 * that are found in the desert ecosystem.
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 7.0
 */
public class Bush extends Plant {

    public Bush(Location location) {
        super(SimulationConfig.BUSH_HARVESTS, location);
    }

    @Override
    public boolean blocksMovement()
    {
        return false;
    }
}

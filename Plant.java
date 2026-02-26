/**
 * Base class for plants in the simulation. Plants provide energy when
 * harvested and may optionally block movement.
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 1.0
 */
public abstract class Plant extends Species {
    private int harvests;
    private int energyValue;

    /**
     * Create a plant with a fixed number of harvests.
     *
     * @param harvests The number of times the plant can be harvested.
     * @param location The plant's location.
     */
    public Plant(int harvests, Location location) {
        super(location);
        this.harvests = harvests;
        this.energyValue = SimulationConfig.PLANT_ENERGY;
    }

    /**
     * Harvest the plant, reducing its remaining harvests.
     */
    public void harvest() {
        harvests--;
        if (harvests == 0) {
            // die
            setDead();
        }
    }

    /**
     * @return The energy gained by eating this plant.
     */
    @Override
    public int getEnergyValue()
    {
        return energyValue;
    }

    /**
     * Whether this plant blocks movement for animals.
     */
    public boolean blocksMovement()
    {
        return true;
    }


}

public abstract class Plant extends Species {
    private int harvests;
    private final int energyValue;

    public Plant(int harvests, Location location) {
        super(location);
        this.harvests = harvests;
        this.energyValue = SimulationConfig.PLANT_ENERGY;
    }

    public void harvest() {
        harvests--;
        if (harvests == 0) {
            // die
            setDead();
        }
    }

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

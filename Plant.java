public abstract class Plant extends Species {
    private int harvests;
    private int energyValue;

    public Plant(int harvests, Location location) {
        super(location);
        this.harvests = harvests;
        this.energyValue = SimulationConfig.PLANT_ENERGY;
    }

    /**
     *
     */
    public void harvest() {
        harvests--;
        if (harvests == 0) {
            // die
            setDead();
        }
    }

    /**
     * @return energyValue (the energy value of this plant when harvested).
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

    // removed infect.
}

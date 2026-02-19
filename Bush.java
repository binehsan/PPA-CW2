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

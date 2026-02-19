public class Nakhla extends Plant {
    public Nakhla(Location location) {
        super(SimulationConfig.NAKHLA_HARVESTS, location);
    }

    @Override
    public boolean blocksMovement()
    {
        return false; //CHANGE THIS TO TRUE IF YOU WANT NAKHLAS TO BLOCK MOVEMENT
    }
}

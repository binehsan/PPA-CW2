public abstract class Plant extends Species {
    private int harvests;

    public Plant(int harvests, Location location) {
        super(location);
        this.harvests = harvests;
    }

    public void harvest() {
        harvests--;
        if (harvests == 0) {
            // die
            setDead();
        }
    }
}

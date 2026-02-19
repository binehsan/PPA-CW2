public final class SimulationConfig {
    private SimulationConfig() {
    }

    // Spawn probabilities
    public static final double FALCON_SPAWN = 0.02;
    public static final double SNAKE_SPAWN = 0.03;
    public static final double CAMEL_SPAWN = 0.02;
    public static final double LIZARD_SPAWN = 0.10;
    public static final double JERBOA_SPAWN = 0.10;
    public static final double BUSH_SPAWN = 0.30;
    public static final double NAKHLA_SPAWN = 0.15;

    // Species configs by role
    // SpeciesConfig(breedingAge, maxAge, breedingProbability, maxOffspring,
    // visibility, maxEnergy, restThreshold, breedingThreshold)
    // IMPORTANT: restThreshold < breedingThreshold < maxEnergy
    // LOW energy = [0, restThreshold) -> desperate hunt / rest
    // MED energy = [restThreshold, breedTh) -> hunt to build reserves
    // HIGH energy = [breedTh, maxEnergy] -> breed, then hunt

        public static final SpeciesConfig FALCON = new SpeciesConfig(
                        10, 100, 0.22, 2,
                        2, 26, 6, 13);

        public static final SpeciesConfig SNAKE = new SpeciesConfig(
                        8, 170, 0.16, 3,
                        3, 24, 5, 12);

        public static final SpeciesConfig LIZARD = new SpeciesConfig(
                        4, 110, 0.40, 5,
                        2, 24, 4, 10);

        public static final SpeciesConfig JERBOA = new SpeciesConfig(
                        4, 90, 0.32, 5,
                        2, 24, 4, 10);

        public static final SpeciesConfig CAMEL = new SpeciesConfig(
                        15, 120, 1, 1,
                        4, 30, 7, 28);

        // Plants
        public static final int PLANT_ENERGY = 6;
        public static final int BUSH_HARVESTS = 10;
        public static final int NAKHLA_HARVESTS = 12;
        public static final double BUSH_REGROW_CHANCE = 0.006;
        public static final double NAKHLA_REGROW_CHANCE = 0.003;
}

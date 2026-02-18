public final class SimulationConfig {
    private SimulationConfig() {
    }

    // Spawn probabilities
                public static final double FALCON_SPAWN = 0.02;
                public static final double SNAKE_SPAWN = 0.03;
                public static final double CAMEL_SPAWN = 0.04;
                public static final double LIZARD_SPAWN = 0.14;
                public static final double JERBOA_SPAWN = 0.14;
                public static final double BUSH_SPAWN = 0.25;
                public static final double NAKHLA_SPAWN = 0.15;

    // Species configs by role
    // SpeciesConfig(breedingAge, maxAge, breedingProbability, maxOffspring,
    //               visibility, maxEnergy, restThreshold, breedingThreshold)
    // IMPORTANT: restThreshold < breedingThreshold < maxEnergy
    //   LOW energy  = [0, restThreshold)       -> desperate hunt / rest
    //   MED energy  = [restThreshold, breedTh) -> hunt to build reserves
    //   HIGH energy = [breedTh, maxEnergy]     -> breed, then hunt

    public static final SpeciesConfig FALCON = new SpeciesConfig(
            10, 200, 0.20, 2,
            3, 26, 6, 14
    );

    public static final SpeciesConfig SNAKE = new SpeciesConfig(
            8, 170, 0.14, 3,
            3, 24, 5, 12
    );

    public static final SpeciesConfig LIZARD = new SpeciesConfig(
            4, 110, 0.35, 4,
            2, 22, 4, 10
    );

    public static final SpeciesConfig JERBOA = new SpeciesConfig(
            4, 90, 0.27, 5,
            2, 22, 4, 10
    );

    public static final SpeciesConfig CAMEL = new SpeciesConfig(
            8, 240, 0.20, 3,
            3, 36, 7, 16
    );

    // Plants
        public static final int PLANT_ENERGY = 6;
        public static final int BUSH_HARVESTS = 8;
        public static final int NAKHLA_HARVESTS = 10;
        public static final double BUSH_REGROW_CHANCE = 0.002;
        public static final double NAKHLA_REGROW_CHANCE = 0.001;
}

public final class SimulationConfig {
    private SimulationConfig() {
    }

    // Spawn probabilities
                public static final double FALCON_SPAWN = 0.03;
                public static final double SNAKE_SPAWN = 0.04;
                public static final double CAMEL_SPAWN = 0.03;
                public static final double LIZARD_SPAWN = 0.10;
                public static final double JERBOA_SPAWN = 0.12;
                public static final double BUSH_SPAWN = 0.20;
                public static final double NAKHLA_SPAWN = 0.12;

    // Species configs by role
    public static final SpeciesConfig FALCON = new SpeciesConfig(
            24, 200, 0.06, 2,
            3, 26, 6, 12
    );

    public static final SpeciesConfig SNAKE = new SpeciesConfig(
            16, 170, 0.08, 3,
            3, 24, 6, 10
    );

    public static final SpeciesConfig LIZARD = new SpeciesConfig(
            8, 110, 0.18, 3,
            2, 18, 4, 7
    );

    public static final SpeciesConfig JERBOA = new SpeciesConfig(
            6, 90, 0.2, 4,
            2, 18, 3, 7
    );

    public static final SpeciesConfig CAMEL = new SpeciesConfig(
            14, 240, 0.07, 2,
            3, 34, 7, 13
    );

    // Plants
        public static final int PLANT_ENERGY = 5;
        public static final int BUSH_HARVESTS = 5;
        public static final int NAKHLA_HARVESTS = 7;
}

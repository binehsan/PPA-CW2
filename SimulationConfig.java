public final class SimulationConfig {
    private SimulationConfig() {
    }

    // Spawn probabilities
                public static final double FALCON_SPAWN = 0.02;
                public static final double SNAKE_SPAWN = 0.025;
                public static final double CAMEL_SPAWN = 0.04;
                public static final double LIZARD_SPAWN = 0.12;
                public static final double JERBOA_SPAWN = 0.12;
                public static final double BUSH_SPAWN = 0.22;
                public static final double NAKHLA_SPAWN = 0.14;

    // Species configs by role
    public static final SpeciesConfig FALCON = new SpeciesConfig(
            24, 200, 0.04, 2,
            3, 26, 6, 12
    );

    public static final SpeciesConfig SNAKE = new SpeciesConfig(
            16, 170, 0.06, 3,
            3, 24, 6, 10
    );

    public static final SpeciesConfig LIZARD = new SpeciesConfig(
            8, 110, 0.22, 3,
            2, 22, 4, 7
    );

    public static final SpeciesConfig JERBOA = new SpeciesConfig(
            6, 90, 0.25, 4,
            2, 22, 3, 7
    );

    public static final SpeciesConfig CAMEL = new SpeciesConfig(
            14, 240, 0.1, 2,
            3, 36, 7, 13
    );

    // Plants
        public static final int PLANT_ENERGY = 8;
        public static final int BUSH_HARVESTS = 7;
        public static final int NAKHLA_HARVESTS = 9;
                public static final double BUSH_REGROW_CHANCE = 0.0008;
                public static final double NAKHLA_REGROW_CHANCE = 0.0006;
}

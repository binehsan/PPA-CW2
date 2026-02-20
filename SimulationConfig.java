
public final class SimulationConfig {
    public static final double DISEASE_OUTBREAK_CHANCE = 0.4;

    private SimulationConfig() {
    }

    public static final double FALCON_SPAWN = 0.02;
    public static final double SNAKE_SPAWN = 0.04;
    public static final double CAMEL_SPAWN = 0.02;
    public static final double LIZARD_SPAWN = 0.20;
    public static final double JERBOA_SPAWN = 0.20;
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
            15, 80, 0.10, 2,
            2, 25, 5, 14);

    public static final SpeciesConfig SNAKE = new SpeciesConfig(
            10, 120, 0.12, 2,
            3, 22, 5, 12);

    public static final SpeciesConfig LIZARD = new SpeciesConfig(
            3, 110, 0.50, 6,
            2, 26, 4, 10);

    public static final SpeciesConfig JERBOA = new SpeciesConfig(
            3, 100, 0.55, 7,
            2, 26, 4, 10);

    public static final SpeciesConfig CAMEL = new SpeciesConfig(
            15, 120, 1, 1,
            4, 30, 7, 28);

    public static final int PLANT_ENERGY = 6;
    public static final int BUSH_HARVESTS = 10;
    public static final int NAKHLA_HARVESTS = 12;
    public static final double BUSH_REGROW_CHANCE = 0.006;
    public static final double NAKHLA_REGROW_CHANCE = 0.003;
}
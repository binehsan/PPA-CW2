import java.util.Random;

/**
 * A model of a Falcon in the desert simulation
 * 
 * This animal feeds on the Snake, Jerboa, and Lizard and has no predators
 *
 * @author Muhammad bin Ehsan & Faisal Al-Khalifa
 * @version 1.0
 */
public class Falcon extends Animal
{
    private static final Class<?>[] preys = {Snake.class, Jerboa.class, Lizard.class};

    private static final Class<?>[] predators = {};

    private static final TimePeriod[] restingPeriods = {TimePeriod.NIGHT, TimePeriod.EVENING};

    private static final SpeciesConfig CONFIG = SimulationConfig.FALCON;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    public static final int visibility = CONFIG.visibility();
    public static final int MAX_ENERGY = CONFIG.maxEnergy();

    public static final int REST_THRESHOLD = CONFIG.restThreshold();
    public static final int BREEDING_THRESHOLD = CONFIG.breedingThreshold();


    // Individual characteristics (instance fields).

    /**
     * Create a falcon. A falcon can be created as a new born (age zero)
     * or with a random age.
     *
     * @param randomAge If true, the falcon will have a random age.
     * @param location The location within the field.
     */
    public Falcon(boolean randomAge, Location location)
    {
        super(location, randomAge, MAX_ENERGY);
    }

    /**
     * @return The breeding age for falcons.
     */
    public int getBreedingAge() {
        return CONFIG.breedingAge();
    }

    /**
     * @return The visibility range for falcons.
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * @return The predator classes for falcons.
     */
    public Class<?>[] getPredators(){
        return predators;
    }

    /**
     * @return The time periods in which falcons rest.
     */
    public TimePeriod[] getRestingPeriods(){
        return restingPeriods;
    }

    /**
     * @return The energy threshold below which falcons rest.
     */
    public int getRestThreshold(){
        return REST_THRESHOLD;
    }

    /**
     * @return The energy threshold above which falcons can breed.
     */
    public int getBreedThreshold(){
        return BREEDING_THRESHOLD;
    }

    /**
     * @return The maximum age for falcons.
     */
    public int getMaxAge() {
        return CONFIG.maxAge();
    }

    /**
     * @return The maximum offspring count for falcons.
     */
    public int getMaxOffspring() {
        return CONFIG.maxOffspring();
    }

    /**
     * @return The maximum energy level for falcons.
     */
    public int getMaxEnergyLevel() {
        return CONFIG.maxEnergy();
    }

    /**
     * @return The probability of breeding for falcons.
     */
    public double getBreedingProbability() {
        return CONFIG.breedingProbability();
    }

    /**
     * @return The prey classes for falcons.
     */
    public Class<?>[] getPrey(){
        return preys;
    }

    /**
     * @return The shared random generator for falcons.
     */
    public Random getRand() {
        return rand;
    }
}

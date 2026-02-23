import java.util.Random;

/**
 * A model of a Jerboa (desert mouse) in the desert simulation
 * 
 * This animal feeds on the Bush and is preyed upon by the Falcon and Snake
 *
 * @author Muhammad bin Ehsan & Faisal Al-Khalifa
 * @version 1.0
 */
public class Jerboa extends Animal
{
    private static final Class<?>[] preys = {Bush.class};

    private static final Class<?>[] predators = {Falcon.class, Snake.class};

    private static final TimePeriod[] restingPeriods = {TimePeriod.MORNING, TimePeriod.AFTERNOON};

    // Characteristics shared by all foxes (class variables).
    private static final SpeciesConfig CONFIG = SimulationConfig.JERBOA;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    public static final int visibility = CONFIG.visibility();
    public static final int MAX_ENERGY = CONFIG.maxEnergy();

    public static final int REST_THRESHOLD = CONFIG.restThreshold();
    public static final int BREEDING_THRESHOLD = CONFIG.breedingThreshold();


    // Individual characteristics (instance fields).

    /**
     * Create a jerboa. A jerboa can be created as a new born (age zero)
     * or with a random age.
     *
     * @param randomAge If true, the jerboa will have a random age.
     * @param location The location within the field.
     */
    public Jerboa(boolean randomAge, Location location)
    {
        super(location, randomAge, MAX_ENERGY);
    }

    /**
     * @return The breeding age for jerboas.
     */
    public int getBreedingAge() {
        return CONFIG.breedingAge();
    }

    /**
     * @return The visibility range for jerboas.
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * @return The predator classes for jerboas.
     */
    public Class<?>[] getPredators(){
        return predators;
    }

    /**
     * @return The time periods in which jerboas rest.
     */
    public TimePeriod[] getRestingPeriods(){
        return restingPeriods;
    }

    /**
     * @return The energy threshold below which jerboas rest.
     */
    public int getRestThreshold(){
        return REST_THRESHOLD;
    }

    /**
     * @return The energy threshold above which jerboas can breed.
     */
    public int getBreedThreshold(){
        return BREEDING_THRESHOLD;
    }

    /**
     * @return The maximum age for jerboas.
     */
    public int getMaxAge() {
        return CONFIG.maxAge();
    }

    /**
     * @return The maximum offspring count for jerboas.
     */
    public int getMaxOffspring() {
        return CONFIG.maxOffspring();
    }

    /**
     * @return The maximum energy level for jerboas.
     */
    public int getMaxEnergyLevel() {
        return CONFIG.maxEnergy();
    }

    /**
     * @return The probability of breeding for jerboas.
     */
    public double getBreedingProbability() {
        return CONFIG.breedingProbability();
    }

    /**
     * @return The prey classes for jerboas.
     */
    public Class<?>[] getPrey(){
        return preys;
    }

    /**
     * @return The shared random generator for jerboas.
     */
    public Random getRand() {
        return rand;
    }
}

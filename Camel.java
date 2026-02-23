import java.util.Random;

/**
 * A model of a Camel in the desert simulation
 * 
 * This animal feeds on the Nakhla and the Bush and has no predators
 *
 * @author Muhammad bin Ehsan & Faisal Al-Khalifa
 * @version 1.0
 */
public class Camel extends Animal
{
    private static final Class<?>[] preys = {Nakhla.class, Bush.class};

    private static final Class<?>[] predators = {};

    private static final TimePeriod[] restingPeriods = {TimePeriod.NIGHT};

    // Characteristics shared by all foxes (class variables).
    private static final SpeciesConfig CONFIG = SimulationConfig.CAMEL;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    public static final int visibility = CONFIG.visibility();
    public static final int MAX_ENERGY = CONFIG.maxEnergy();

    public static final int REST_THRESHOLD = CONFIG.restThreshold();
    public static final int BREEDING_THRESHOLD = CONFIG.breedingThreshold();


    // Individual characteristics (instance fields).

    /**
     * Create a camel. A camel can be created as a new born (age zero)
     * or with a random age.
     *
     * @param randomAge If true, the camel will have a random age.
     * @param location The location within the field.
     */
    public Camel(boolean randomAge, Location location)
    {
        super(location, randomAge, MAX_ENERGY);
    }

    /**
     * @return The breeding age for camels.
     */
    public int getBreedingAge() {
        return CONFIG.breedingAge();
    }

    /**
     * @return The visibility range for camels.
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * @return The predator classes for camels.
     */
    public Class<?>[] getPredators(){
        return predators;
    }

    /**
     * @return The energy threshold below which camels rest.
     */
    public int getRestThreshold(){
        return REST_THRESHOLD;
    }

    /**
     * @return The energy threshold above which camels can breed.
     */
    public int getBreedThreshold(){
        return BREEDING_THRESHOLD;
    }

    /**
     * @return The maximum age for camels.
     */
    public int getMaxAge() {
        return CONFIG.maxAge();
    }

    /**
     * @return The maximum offspring count for camels.
     */
    public int getMaxOffspring() {
        return CONFIG.maxOffspring();
    }

    /**
     * @return The maximum energy level for camels.
     */
    public int getMaxEnergyLevel() {
        return CONFIG.maxEnergy();
    }

    /**
     * @return The probability of breeding for camels.
     */
    public double getBreedingProbability() {
        return CONFIG.breedingProbability();
    }

    /**
     * @return The prey classes for camels.
     */
    public Class<?>[] getPrey(){
        return preys;
    }

    /**
     * @return The time periods in which camels rest.
     */
    public TimePeriod[] getRestingPeriods(){
        return restingPeriods;
    }

    /**
     * @return The shared random generator for camels.
     */
    public Random getRand() {
        return rand;
    }
}

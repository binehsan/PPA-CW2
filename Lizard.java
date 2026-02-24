import java.util.Random;

/**
 * A model of a Lizard in the desert simulation
 * 
 * This animal feeds on the Bush and is preyed upon by the Falcon and Snake
 *
 * @author Muhammad bin Ehsan & Faisal Al-Khalifa
 * @version 1.0
 */
public class Lizard extends Animal
{
    private static final Class<?>[] preys = {Bush.class};

    private static final Class<?>[] predators = {Falcon.class, Snake.class};

    private static final TimePeriod[] restingPeriods = {TimePeriod.MORNING, TimePeriod.NIGHT};

    // Characteristics shared by all foxes (class variables).
    private static final SpeciesConfig CONFIG = SimulationConfig.LIZARD;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    public static final int visibility = CONFIG.visibility();
    public static final int MAX_ENERGY = CONFIG.maxEnergy();

    public static final int REST_THRESHOLD = CONFIG.restThreshold();
    public static final int BREEDING_THRESHOLD = CONFIG.breedingThreshold();


    // Individual characteristics (instance fields).

    /**
     * Create a lizard. A lizard can be created as a new born (age zero)
     * or with a random age.
     *
     * @param randomAge If true, the lizard will have a random age.
     * @param location The location within the field.
     */
    public Lizard(boolean randomAge, Location location)
    {
        super(location, randomAge, MAX_ENERGY);
    }

    /**
     * @return The breeding age for lizards.
     */
    public int getBreedingAge() {
        return CONFIG.breedingAge();
    }

    /**
     * @return The visibility range for lizards.
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * @return The predator classes for lizards.
     */
    public Class<?>[] getPredators(){
        return predators;
    }

    /**
     * @return The time periods in which lizards rest.
     */
    public TimePeriod[] getRestingPeriods(){
        return restingPeriods;
    }

    /**
     * @return The energy threshold below which lizards rest.
     */
    public int getRestThreshold(){
        return REST_THRESHOLD;
    }

    /**
     * @return The energy threshold above which lizards can breed.
     */
    public int getBreedThreshold(){
        return BREEDING_THRESHOLD;
    }

    /**
     * @return The maximum age for lizards.
     */
    public int getMaxAge() {
        return CONFIG.maxAge();
    }

    /**
     * @return The maximum offspring count for lizards.
     */
    public int getMaxOffspring() {
        return CONFIG.maxOffspring();
    }

    /**
     * @return The maximum energy level for lizards.
     */
    public int getMaxEnergyLevel() {
        return CONFIG.maxEnergy();
    }

    /**
     * @return The probability of breeding for lizards.
     */
    public double getBreedingProbability() {
        return CONFIG.breedingProbability();
    }

    /**
     * @return The prey classes for lizards.
     */
    public Class<?>[] getPrey(){
        return preys;
    }

    /**
     * @return The shared random generator for lizards.
     */
    public Random getRand() {
        return rand;
    }
}

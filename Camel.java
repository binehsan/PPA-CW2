import java.util.List;
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
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param location The location within the field.
     */
    public Camel(boolean randomAge, Location location)
    {
        super(location, randomAge, MAX_ENERGY);
    }

    public int getBreedingAge() {
        return CONFIG.breedingAge();
    }

    public int getVisibility() {
        return visibility;
    }

    public Class<?>[] getPredators(){
        return predators;
    }

    public int getRestThreshold(){
        return REST_THRESHOLD;
    }

    public int getBreedThreshold(){
        return BREEDING_THRESHOLD;
    }

    public int getMaxAge() {
        return CONFIG.maxAge();
    }

    public int getMaxOffspring() {
        return CONFIG.maxOffspring();
    }

    public int getMaxEnergyLevel() {
        return CONFIG.maxEnergy();
    }

    public double getBreedingProbability() {
        return CONFIG.breedingProbability();
    }

    public Class<?>[] getPrey(){
        return preys;
    }

    public TimePeriod[] getRestingPeriods(){
        return restingPeriods;
    }

    public Random getRand() {
        return rand;
    }
}

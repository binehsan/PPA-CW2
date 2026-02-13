import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 7.1
 */
public class Jerboa extends Animal
{
    //COME UP WITH SOLUTION
    private static final Class<?>[] preys = {Bush.class};

    private static final Class<?>[] predators = {Snake.class};

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
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param location The location within the field.
     */
    public Jerboa(boolean randomAge, Location location)
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

    public double getBreedingProbability() {
        return CONFIG.breedingProbability();
    }

    public Class<?>[] getPrey(){
        return preys;
    }

    public Random getRand() {
        return rand;
    }
}

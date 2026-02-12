import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author Muhammad A Ehsan & Faisal AlKhalifa
 * @version 1
 */
public class Falcon extends Animal
{
    //COME UP WITH SOLUTION
    private static final Class<?>[] preys = {Snake.class, Lizard.class};

    private static final Class<?>[] predators = {};

    // Characteristics shared by all foxes (class variables).
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_OFFSPRINGS = 2;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    public static final int visibility = 2;
    public static final int MAX_ENERGY = 20;

    public static final int REST_THRESHOLD = 5;
    public static final int BREEDING_THRESHOLD = 10;


    // Individual characteristics (instance fields).

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param location The location within the field.
     */
    public Falcon(boolean randomAge, Location location, int gender)
    {
        super(location, gender, randomAge, MAX_ENERGY);
    }

    public int getBreedingAge() {
        return BREEDING_AGE;
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

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    public int getMaxOffspring() {
        return MAX_OFFSPRINGS;
    }

    public double getBreedingProbability() {
        return MAX_OFFSPRINGS;
    }

    public Class<?>[] getPrey(){
        return preys;
    }

    public Random getRand() {
        return rand;
    }


//    @Override
//    public String toString() {
//        return "Fox{" +
//                "age=" + age +
//                ", alive=" + isAlive() +
//                ", location=" + getLocation() +
//                ", foodLevel=" + energyLevel +
//                '}';
//    }
}

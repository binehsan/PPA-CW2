import java.util.*;

/**
 * Holds the main behaviours of all animals in the simulation.
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 1.0
 */
public abstract class Animal extends Species {

    // Male = 0, Female = 1
    private final int gender;

    private int energyLevel;
    private int age;


    /**
     * Constructor for objects of class Animal.
     * 
     * @param location The animal's location.
     * @param randomAge Whether the animal should be created with a random age (true) or start at age 0 (false).
     * @param MAX_ENERGY_LEVEL The maximum energy level for this animal, used to initialize the energy level.
     */
    public Animal(Location location, boolean randomAge, int MAX_ENERGY_LEVEL) {
        super(location);

        if (randomAge) {
            int maxInitialAge = Math.max(1, this.getMaxAge() / 2);
            setAge(this.getRand().nextInt(maxInitialAge));
        } else {
            setAge(0);
        }

        energyLevel = MAX_ENERGY_LEVEL;
        this.gender = this.getRand().nextInt(2);
    }

    /**
     * @return the animals current energy level
     */
    public int getEnergyLevel() {
        return energyLevel;
    }


    /**
     * @param energyLevel the level to set this animal's energy to
     */
    protected void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    /**
     * @return the animal's gender
     */
    public int getGender() {
        return gender;
    }

    /**
     * @return the animal's age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the new age of this animal
     */
    protected void setAge(int age) {
        this.age = age;
    }

    /**
     * Increase the age. This could result in the fox's death.
     */
    private void incrementAge(int MAX_AGE) {
        age++;
        if (age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger() {
        energyLevel--;
        if (energyLevel <= 0) {
            setDead();
        }
    }

    // Abstract methods defined for every animal.
    // This ensures that every animal has these fields in
    // order to move the act class into the Animal class.

    abstract public int getVisibility();

    abstract public Class<?>[] getPredators();

    abstract public Class<?>[] getPrey();

    abstract public int getRestThreshold();

    abstract public int getBreedingAge();

    abstract public int getBreedThreshold();

    abstract public int getMaxAge();

    abstract public int getMaxOffspring();

    abstract public int getMaxEnergyLevel();

    abstract public TimePeriod[] getRestingPeriods();

    abstract public double getBreedingProbability();

    abstract public Random getRand();


    /**
     * This method encompasses the animals' behavioural logic.
     * It outlines the flow of when to flee, breed, hunt,
     * rest, and wander.
     *
     * @param currentField The current field of animals/plants
     * @param nextFieldState The field for the next step in the simulation
     * @param currentTime The time period for this step
     * @param currentWeather The weather for this step
     */
    public void act(Field currentField, Field nextFieldState, TimePeriod currentTime, Weather currentWeather) {

        incrementAge(this.getMaxAge());
        incrementHunger();

        if (!isAlive()) {
            return;
        }

        boolean acted = false;

        //Flee if predator is adjacent
        acted = AnimalFlee.tryFlee(this, currentField, nextFieldState, currentWeather);

        if (!acted) {
            if (energyLevel < getRestThreshold()) {
                // LOW energy: survival mode — hunt or rest
                acted = AnimalHunt.tryHunt(this, currentField, nextFieldState, currentWeather);
                if (!acted) {
                    boolean isRestingTime = false;
                    for (TimePeriod time : getRestingPeriods()) {
                        if (time == currentTime) {
                            isRestingTime = true;
                            break;
                        }
                    }
                    if (isRestingTime) {
                        acted = AnimalRest.tryRest(this);
                    }
                }
            } else if (energyLevel < getBreedThreshold()) {
                // MEDIUM energy: build reserves — hunt or wander
                acted = AnimalHunt.tryHunt(this, currentField, nextFieldState, currentWeather);
            } else {
                // HIGH energy: breed first, then hunt to maintain
                acted = AnimalBreed.tryBreed(this, currentField, nextFieldState);
                if (!acted) {
                    acted = AnimalHunt.tryHunt(this, currentField, nextFieldState, currentWeather);
                }
            }
        }

        // Fallback: wander
        if (!acted) {
            AnimalWander.tryWander(this, nextFieldState);
        }

        if (isAlive()) {
            nextFieldState.placeAnimal(this, getLocation());
        }
    }

    /**
     * Makes this animal infected.
     */
    public void infect() {
        if (this.isInfected()) {
            return;
        }
        this.setEnergyLevel(this.getEnergyLevel() / 2);
        this.setInfected(true);
    }

}

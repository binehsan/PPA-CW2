import java.util.*;

/**
 * Holds the main behaviours of all animals in the simulation.
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 7.0
 */
public abstract class Animal extends Species {

    private int energyLevel;
    private int age;
    // Male = 0, Female = 1
    private int gender;

    /**
     * Constructor for objects of class Animal.
     * 
     * @param location The animal's location.
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

    public int getEnergyLevel() {
        return energyLevel;
    }

    @Override
    public int getEnergyValue() {
        return energyLevel;
    }

    protected void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public int getGender() {
        return gender;
    }

    protected void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

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

    public void act(Field currentField, Field nextFieldState, TimePeriod currentTime, Weather currentWeather) {

        incrementAge(this.getMaxAge());
        incrementHunger();

        if (!isAlive()) {
            return;
        }

        boolean acted = false;

        // 1. Flee if predator is adjacent
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

    public void infect() {
        this.setEnergyLevel(this.getEnergyLevel() / 2);
        this.setInfected(true);
    }

}

import java.util.*;
/**
 * Common elements of foxes and rabbits.
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 7.0
 */
public abstract class Animal extends Species
{

    private int energyLevel;
    private int age;
    // Male = 0, Female = 1
    private int gender;

    /**
     * Constructor for objects of class Animal.
     * @param location The animal's location.
     */
    public Animal(Location location, boolean randomAge, int MAX_ENERGY_LEVEL)
    {
        super(location);

        if(randomAge) {
            int maxInitialAge = Math.max(1, this.getMaxAge() / 2);
            setAge(this.getRand().nextInt(maxInitialAge));
        }
        else {
            setAge(0);
        }

        energyLevel = MAX_ENERGY_LEVEL;
        this.gender = this.getRand().nextInt(2);
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    @Override
    public int getEnergyValue()
    {
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
    private void incrementAge(int MAX_AGE)
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        energyLevel--;
        if(energyLevel <= 0) {
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

    abstract public double getBreedingProbability();

    abstract public Random getRand();



    /**
     * @param location
     * @param currentField
     * @return
     */
    public boolean tryFlee(Location location, Field currentField){
        List<Location> adjacentSpaces = currentField.getAdjacentLocations(location, this.getVisibility());
        for (Location adjacent : adjacentSpaces) {
            Animal tempAnimal = currentField.getAnimalAt(adjacent);
            for (Class<?> predator : this.getPredators()) {
                if (predator.isInstance(tempAnimal)) {
                    List<Location> freeLocations =
                            currentField.getFreeAdjacentLocations(getLocation(), 1);
                    Location nextLocation = null;
                    if(! freeLocations.isEmpty()) {
                        // No food found - try to move to a free location.
                        nextLocation = freeLocations.removeFirst();
                    }
                    // See if it was possible to move.
                    if(nextLocation != null) {
                        setLocation(nextLocation);
                        currentField.placeAnimal(this, nextLocation);
                    }
                    else {
                        // Overcrowding.
                        setDead();
                    }
                    return true;
                }
            }
        }

        return false;
    }

    public boolean tryRest(Location location, Field currentField){
        // checks if energy level below its threshold, if so, stays put.

        if (this.getEnergyLevel() < this.getRestThreshold()){
            // set as an enum
            this.setEnergyLevel(this.getEnergyLevel() + 1);
            return true;
        }
        return false;
    }

    public boolean tryHunt(Location location, Field currentField){
        List<Location> adjacentSpaces = currentField.getAdjacentLocations(location, this.getVisibility());
        for (Location adjacent : adjacentSpaces) {
            Species target = currentField.getSpeciesAt(adjacent);
            for (Class<?> prey : this.getPrey()) {
                if (prey.isInstance(target)) {
                    // take energy level, move to its location.
                     this.setEnergyLevel(this.getEnergyLevel() + target.getEnergyValue());
                     if (target instanceof Plant plant) {
                         plant.harvest();
                     } else if (target instanceof Animal animal) {
                         animal.setDead();
                     }
                     this.setLocation(target.getLocation());
                     currentField.placeAnimal(this, target.getLocation());

                    return true;
                }
            }
        }

        return false;
    }


    public boolean tryBreed(Location location, Field currentField, Field nextFieldState){

        if (this.getAge() < this.getBreedingAge()) return false;

        if (this.getGender() == 1) return false;    // skip if female

        if (energyLevel < this.getBreedThreshold()) return false;

        List<Location> adjacentSpaces = currentField.getAdjacentLocations(location, this.getVisibility());
        for (Location adjacent : adjacentSpaces) {
            Animal tempAnimal = currentField.getAnimalAt(adjacent);

            if (tempAnimal == null) continue;
            if (tempAnimal.getAge() < tempAnimal.getBreedingAge()) continue;

            if (tempAnimal.getClass().equals(this.getClass()) && tempAnimal.getGender() == 1) {
                List<Location> freeSpaces = currentField.getFreeAdjacentLocations(location, this.getVisibility());

                //number of offsprings
                for (int i=0; i < Math.min(freeSpaces.size(), this.getMaxOffspring()); i++) {
                    //breed at freeSpaces.get(i)
                    if (this.getRand().nextDouble() > this.getBreedingProbability()) continue;

                    //Change to use BiFunction functional interface

                    try {
                        Class<?> tempClass = this.getClass();
                        Animal offspring = (Animal) tempClass
                                .getDeclaredConstructor(boolean.class, Location.class)
                                .newInstance(false, freeSpaces.get(i));

                        nextFieldState.placeAnimal(offspring, freeSpaces.get(i));
                    } catch (Exception error) {
                        error.printStackTrace();
                        return false;
                    }
                }

                return true;

            }
        }

        return false;
    }


    public boolean tryWander(Location location, Field currentField){
        List<Location> freeLocations =
                currentField.getFreeAdjacentLocations(getLocation(), 1);
        Location nextLocation = null;
        if(! freeLocations.isEmpty()) {
            // No food found - try to move to a free location.
            nextLocation = freeLocations.removeFirst(); //FLAG
        }
        // See if it was possible to move.
        if(nextLocation != null) {
            setLocation(nextLocation);
            currentField.placeAnimal(this, nextLocation);
        } else {
            // Overcrowding.
            setDead();
        }
        return true;
    }

    public void act(Field currentField, Field nextFieldState)
    {
        
        incrementAge(this.getMaxAge());
        incrementHunger();
        if(! isAlive()) {
            return;
        }

        // Fix this
        if (!tryFlee(this.getLocation(), currentField)) {
            if (!tryRest(this.getLocation(), currentField)) {
                if (!tryHunt(this.getLocation(), currentField)) {
                    if (!tryBreed(this.getLocation(), currentField, nextFieldState)) {
                        tryWander(this.getLocation(), currentField);
                    }
                }
            }
        }

        if (isAlive()) {
            nextFieldState.placeAnimal(this, getLocation());
        }
    }


}



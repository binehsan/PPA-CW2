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
    public Animal(Location location, int gender, boolean randomAge, int MAX_ENERGY_LEVEL)
    {
        super(location);
        //removed age logic

        energyLevel = MAX_ENERGY_LEVEL;
        this.gender = gender;
    }

    public int getEnergyLevel() {
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


    abstract public int getVisibility();
    abstract public Class<?>[] getPredators();
    abstract public Class<?>[] getPrey();

    abstract public int getRestThreshold();

    abstract public void incrementAge();
    abstract public void incrementHunger();


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
                        nextLocation = freeLocations.remove(0);
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
            Animal tempAnimal = currentField.getAnimalAt(adjacent);
            for (Class<?> prey : this.getPrey()) {
                if (prey.isInstance(tempAnimal)) {
                    // take energy level, move to its location.
                     this.setEnergyLevel(this.getEnergyLevel() + tempAnimal.getEnergyLevel());
                     tempAnimal.setDead();
                     this.setLocation(tempAnimal.getLocation());
                     currentField.placeAnimal(this, tempAnimal.getLocation());

                    return true;
                }
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
        }
        else {
            // Overcrowding.
            setDead();
        }
        return true;
    }

    public void act(Field currentField, Field nextFieldState)
    {
        incrementAge();
        incrementHunger();
        if(! isAlive()) {
            return;
        }

        if (tryFlee(this.getLocation(), currentField)) {
            return;
        } else if (tryRest(this.getLocation(), currentField)) {
            return;
        } else if (tryHunt(this.getLocation(), currentField)) {
            return;
        } else {
            tryWander(this.getLocation(), currentField);
        }

    }


}



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
    abstract public int getMaxEnergyLevel();

    abstract public double getBreedingProbability();

    abstract public Random getRand();



    /**
     * @param location
     * @param currentField
     * @return
     */
    public boolean tryFlee(Location location, Field currentField, Field nextFieldState){
        List<Location> adjacentSpaces = currentField.getAdjacentLocations(location, 1);
        for (Location adjacent : adjacentSpaces) {
            Animal tempAnimal = currentField.getAnimalAt(adjacent);
            for (Class<?> predator : this.getPredators()) {
                if (predator.isInstance(tempAnimal)) {
                    List<Location> freeLocations =
                            nextFieldState.getFreeAdjacentLocations(getLocation(), 1);
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
                    // If no space to flee, stay put and risk predation later.
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
            this.setEnergyLevel(Math.min(this.getEnergyLevel() + 1, this.getMaxEnergyLevel()));
            return true;
        }
        return false;
    }

    public boolean tryHunt(Location location, Field currentField, Field nextFieldState){
        List<Location> visibleSpaces = currentField.getAdjacentLocations(location, this.getVisibility());
        Location closestPreyLocation = null;
        Species closestPrey = null;
        int closestDistance = Integer.MAX_VALUE;

        for (Location visible : visibleSpaces) {
            Animal animalTarget = currentField.getAnimalAt(visible);
            if (animalTarget != null && animalTarget.isAlive()) {
                for (Class<?> prey : this.getPrey()) {
                    if (prey.isInstance(animalTarget)) {
                        int distance = Math.abs(visible.row() - location.row())
                                + Math.abs(visible.col() - location.col());
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestPreyLocation = visible;
                            closestPrey = animalTarget;
                        }
                    }
                }
            }

            Plant plantTarget = currentField.getPlantAt(visible);
            if (plantTarget != null && plantTarget.isAlive()) {
                for (Class<?> prey : this.getPrey()) {
                    if (prey.isInstance(plantTarget)) {
                        int distance = Math.abs(visible.row() - location.row())
                                + Math.abs(visible.col() - location.col());
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestPreyLocation = visible;
                            closestPrey = plantTarget;
                        }
                    }
                }
            }
        }

        if (closestPreyLocation == null || closestPrey == null) {
            return false;
        }

        if (closestDistance == 1) {
            this.setEnergyLevel(Math.min(this.getEnergyLevel() + closestPrey.getEnergyValue(), this.getMaxEnergyLevel()));
            if (closestPrey instanceof Plant plant) {
                if (nextFieldState.getPlantAt(closestPreyLocation) == null) {
                    plant.harvest();
                    if (plant.isAlive()) {
                        nextFieldState.placePlant(plant, closestPreyLocation);
                    }
                }
                if (!plant.blocksMovement()) {
                    this.setLocation(closestPreyLocation);
                }
            } else if (closestPrey instanceof Animal animal) {
                animal.setDead();
                this.setLocation(closestPreyLocation);
            }
            return true;
        }

        int rowStep = Integer.compare(closestPreyLocation.row(), location.row());
        int colStep = Integer.compare(closestPreyLocation.col(), location.col());
        Location stepLocation = new Location(location.row() + rowStep, location.col() + colStep);
        Plant stepPlant = nextFieldState.getPlantAt(stepLocation);
        boolean stepBlocked = stepPlant != null && stepPlant.isAlive() && stepPlant.blocksMovement();
        if (nextFieldState.getAnimalAt(stepLocation) == null && !stepBlocked) {
            this.setLocation(stepLocation);
            return true;
        }

        return false;
    }


    public boolean tryBreed(Location location, Field currentField, Field nextFieldState){
        if (this.getAge() < this.getBreedingAge()) return false;

        if (this.getGender() == 1) return false;    // skip if female

        if (energyLevel < this.getBreedThreshold()) return false;

//        if (!hasPreyInRange(location, currentField)) return false;

        List<Location> adjacentSpaces = currentField.getAdjacentLocations(location, this.getVisibility());
        for (Location adjacent : adjacentSpaces) {
            Animal tempAnimal = currentField.getAnimalAt(adjacent);

            if (tempAnimal == null) continue;
            if (tempAnimal.getAge() < tempAnimal.getBreedingAge()) continue;

            if (tempAnimal.getClass().equals(this.getClass()) && tempAnimal.getGender() == 1) {
                List<Location> freeSpaces = nextFieldState.getFreeAdjacentLocations(location, this.getVisibility());

                // number of offsprings
                for (int i = 0; i < Math.min(freeSpaces.size(), this.getMaxOffspring()); i++) {
                    // breed at freeSpaces.get(i)
                    if (this.getRand().nextDouble() > this.getBreedingProbability()) continue;

                    // Change to use BiFunction functional interface

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

    private boolean hasPreyInRange(Location location, Field currentField) {
        List<Location> adjacentSpaces = currentField.getAdjacentLocations(location, this.getVisibility());
        for (Location adjacent : adjacentSpaces) {
            Animal animalTarget = currentField.getAnimalAt(adjacent);
            if (animalTarget != null && animalTarget.isAlive()) {
                for (Class<?> prey : this.getPrey()) {
                    if (prey.isInstance(animalTarget)) {
                        return true;
                    }
                }
            }

            Plant plantTarget = currentField.getPlantAt(adjacent);
            if (plantTarget != null && plantTarget.isAlive()) {
                for (Class<?> prey : this.getPrey()) {
                    if (prey.isInstance(plantTarget)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean tryWander(Location location, Field currentField, Field nextFieldState){
        List<Location> freeLocations =
                nextFieldState.getFreeAdjacentLocations(getLocation(), 1);
        Location nextLocation = null;
        if(! freeLocations.isEmpty()) {
            // No food found - try to move to a free location.
            nextLocation = freeLocations.removeFirst(); // FLAG
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

    public void act(Field currentField, Field nextFieldState, TimePeriod currentTime)
    {

        incrementAge(this.getMaxAge());
        incrementHunger();

        if(! isAlive()) {
            return;
        }


        boolean acted = tryFlee(this.getLocation(), currentField, nextFieldState);

        if (!acted && this.getEnergyLevel() < this.getRestThreshold()) {
            double prob = getRand().nextDouble();
            if (currentTime == TimePeriod.NIGHT) {
                if (prob < 0.7) {
                    acted = tryRest(this.getLocation(), currentField);
                    if (!acted) {
                        acted = tryHunt(this.getLocation(), currentField, nextFieldState);
                    }
                } else {
                    acted = tryHunt(this.getLocation(), currentField, nextFieldState);
                    if (!acted) {
                        acted = tryRest(this.getLocation(), currentField);
                    }
                }
            } else {
                if (prob < 0.7) {
                    acted = tryHunt(this.getLocation(), currentField, nextFieldState);
                    if (!acted) {
                        acted = tryRest(this.getLocation(), currentField);
                    }
                } else {
                    acted = tryRest(this.getLocation(), currentField);
                    if (!acted) {
                        acted = tryHunt(this.getLocation(), currentField, nextFieldState);
                    }
                }
            }
        }

        if (!acted) {
            if (!tryBreed(this.getLocation(), currentField, nextFieldState)) {
                tryWander(this.getLocation(), currentField, nextFieldState);
            }
        }

        if (isAlive()) {
            nextFieldState.placeAnimal(this, getLocation());
        }
    }
}


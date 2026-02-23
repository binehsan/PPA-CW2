import java.util.List;

/**
 * Deals with the animals' ability to breed
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 7.0
 */
public class AnimalBreed {
    public static boolean tryBreed(Animal animal, Field currentField, Field nextFieldState) {
        Location location = animal.getLocation();
        
        if (animal.getAge() < animal.getBreedingAge())
            return false;


        if (animal.getEnergyLevel() < animal.getBreedThreshold())
            return false;

        // Probability check gates the whole breeding attempt
        if (animal.getRand().nextDouble() > animal.getBreedingProbability())
            return false;

        List<Location> adjacentSpaces = currentField.getAdjacentLocations(location, animal.getVisibility());
        for (Location adjacent : adjacentSpaces) {
            Animal tempAnimal = currentField.getAnimalAt(adjacent);

            if (tempAnimal == null)
                continue;
            if (tempAnimal.getAge() < tempAnimal.getBreedingAge())
                continue;

            if (tempAnimal.getClass().equals(animal.getClass()) && tempAnimal.getGender() != animal.getGender()) {
                // Offspring ONLY in adjacent cells (radius 1)
                List<Location> freeSpaces = nextFieldState.getFreeAdjacentLocations(location, 1);

                int numOffspring = animal.getRand().nextInt(animal.getMaxOffspring()) + 1;
                for (int i = 0; i < Math.min(freeSpaces.size(), numOffspring); i++) {
                    try {
                        Class<?> tempClass = animal.getClass();
                        Animal offspring = (Animal) tempClass
                                .getDeclaredConstructor(boolean.class, Location.class)
                                .newInstance(false, freeSpaces.get(i));

                        if (animal.isInfected() || tempAnimal.isInfected()) {
                            tempAnimal.infect();
                            animal.infect();
                            offspring.infect();
                        }

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
}

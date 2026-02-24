import java.util.List;

/**
 * Deals with the animals' ability to flee
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 7.0
 */
public class AnimalFlee {
    /**
     * Attempt to flee from nearby predators, moving the animal if possible.
     *
     * @param animal The animal attempting to flee.
     * @param currentField The field containing current positions.
     * @param nextFieldState The field for the next simulation step.
     * @param currentWeather The current weather conditions.
     * @return true if the animal attempted to flee.
     */
    public static boolean tryFlee(Animal animal, Field currentField, Field nextFieldState, Weather currentWeather) {
        Location location = animal.getLocation();
        int effectiveVisibility = (int) (animal.getVisibility() * currentWeather.visibilityMultiplier());
        List<Location> adjacentSpaces = currentField.getAdjacentLocations(location, effectiveVisibility);
        for (Location adjacent : adjacentSpaces) {
            Animal tempAnimal = currentField.getAnimalAt(adjacent);
            if (tempAnimal == null)
                continue;
            for (Class<?> predator : animal.getPredators()) {
                if (predator.isInstance(tempAnimal)) {
                    List<Location> freeLocations = nextFieldState.getFreeAdjacentLocations(animal.getLocation(), 1);
                    if (!freeLocations.isEmpty()) {
                        animal.setLocation(freeLocations.removeFirst());
                    }
                    // If no space to flee, stay put
                    return true;
                }
            }
        }
        return false;
    }
}

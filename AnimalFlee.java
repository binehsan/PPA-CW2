import java.util.List;

public class AnimalFlee {
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
                        nextFieldState.placeAnimal(animal, location);
                    }
                    // If no space to flee, stay put
                    return true;
                }
            }
        }
        return false;
    }
}

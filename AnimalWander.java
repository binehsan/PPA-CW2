import java.util.List;

/**
 * Deals with the animals' ability to wander
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 7.0
 */
public class AnimalWander {
    public static boolean tryWander(Animal animal, Field nextFieldState) {
        List<Location> freeLocations = nextFieldState.getFreeAdjacentLocations(animal.getLocation(), 1);
        if (!freeLocations.isEmpty()) {
            animal.setLocation(freeLocations.removeFirst());
        }
        // If no space, stay put — do NOT kill
        return true;
    }
}

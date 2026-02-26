import java.util.List;

/**
 * Deals with the animals' ability to wander
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 1.0
 */
public class AnimalWander {
    /**
     * Attempt to move the animal to a random adjacent free space.
     *
     * @param animal The animal that is wandering.
     * @param nextFieldState The field for the next simulation step.
     * @return true after attempting to move.
     */
    public static boolean tryWander(Animal animal, Field nextFieldState) {
        List<Location> freeLocations = nextFieldState.getFreeAdjacentLocations(animal.getLocation(), 1);
        if (!freeLocations.isEmpty()) {
            animal.setLocation(freeLocations.removeFirst());
        }
        // If no space, stay put — do NOT kill to move
        return true;
    }
}

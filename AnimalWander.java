import java.util.List;

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

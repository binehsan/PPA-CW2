
/**
 * Deals with the animals' ability to rest
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 7.0
 */
public class AnimalRest {
    public static boolean tryRest(Animal animal) {
        // Recover energy while resting (up to restThreshold)
        int recovery = Math.max(1, animal.getRestThreshold() / 3);
        animal.setEnergyLevel(Math.min(animal.getEnergyLevel() + recovery, animal.getMaxEnergyLevel()));
        return true;
    }
}

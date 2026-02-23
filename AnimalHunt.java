import java.util.List;

/**
 * Deals with the animals' ability to hunt
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 7.0
 */
public class AnimalHunt {
    /**
     * Attempt to hunt the closest visible prey (animal or plant).
     *
     * @param animal The hunting animal.
     * @param currentField The field containing current positions.
     * @param nextFieldState The field for the next simulation step.
     * @param currentWeather The current weather conditions.
     * @return true if the animal hunted or moved to prey.
     */
    public static boolean tryHunt(Animal animal, Field currentField, Field nextFieldState, Weather currentWeather) {
        Location location = animal.getLocation();
        int effectiveVisibility = (int) (animal.getVisibility() * currentWeather.visibilityMultiplier());
        List<Location> visibleSpaces = currentField.getAdjacentLocations(location, effectiveVisibility);
        Location closestPreyLocation = null;
        Species closestPrey = null;
        int closestDistance = Integer.MAX_VALUE;

        for (Location visible : visibleSpaces) {
            Animal animalTarget = currentField.getAnimalAt(visible);
            if (animalTarget != null && animalTarget.isAlive()) {
                for (Class<?> prey : animal.getPrey()) {
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
                for (Class<?> prey : animal.getPrey()) {
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
            if (closestPrey instanceof Plant plant) {
                int gainedEnergy = plant.getEnergyValue();
                animal.setEnergyLevel(
                        Math.min(animal.getEnergyLevel() + gainedEnergy, animal.getMaxEnergyLevel()));
                plant.harvest();

                // potential if block for setting field location To null.
                if (!plant.blocksMovement()) {
                    animal.setLocation(closestPreyLocation);
                }

            } else if (closestPrey instanceof Animal prey) {
                int gainedEnergy = Math.max(1, (int) Math.round(prey.getMaxEnergyLevel() * 0.10));
                animal.setEnergyLevel(
                        Math.min(animal.getEnergyLevel() + gainedEnergy, animal.getMaxEnergyLevel()));
                prey.setDead();
                animal.setLocation(closestPreyLocation);
            }
            return true;
        }
        return false;
    }
}

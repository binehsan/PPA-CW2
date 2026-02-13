import java.util.*;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single animal/object.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 7.0
 */
public class Field {
    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();

    // The dimensions of the field.
    private final int depth, width;
    // Species mapped by location.
    private final Map<Location, Species> field = new HashMap<>();
    // The animals.
    private final List<Animal> animals = new ArrayList<>();

    /**
     * Represent a field of the given dimensions.
     * 
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width) {
        this.depth = depth;
        this.width = width;
    }

    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * 
     * @param anAnimal The animal to be placed.
     * @param location Where to place the animal.
     */
    public void placeAnimal(Animal anAnimal, Location location) {
        assert location != null;
        Species other = field.get(location);
        if (other instanceof Animal otherAnimal) {
            animals.remove(otherAnimal);
        }
        field.put(location, anAnimal);
        animals.add(anAnimal);
    }

    /**
     * Place a plant at the given location.
     * @param plant The plant to place.
     * @param location Where to place the plant.
     */
    public void placePlant(Plant plant, Location location) {
        assert location != null;
        Species other = field.get(location);
        if (other instanceof Animal otherAnimal) {
            animals.remove(otherAnimal);
        }
        field.put(location, plant);
    }

    /**
     * Return the animal at the given location, if any.
     * 
     * @param location Where in the field.
     * @return The animal at the given location, or null if there is none.
     */
    public Animal getAnimalAt(Location location) {
        Species occupant = field.get(location);
        if (occupant instanceof Animal animal) {
            return animal;
        }
        return null;
    }

    /**
     * Return the species at the given location, if any.
     * @param location Where in the field.
     * @return The species at the location, or null if there is none.
     */
    public Species getSpeciesAt(Location location) {
        return field.get(location);
    }

    /**
     * Get a shuffled list of the free adjacent locations.
     * 
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location, int visibility) {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = getAdjacentLocations(location, visibility);
        for (Location next : adjacent) {
            Species occupant = field.get(next);
            if (occupant == null) {
                free.add(next);
            } else if (!occupant.isAlive()) {
                free.add(next);
            }
        }
        return free;
    }

    /**
     * Return a shuffled list of locations within a given visibility to the given
     * location.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * 
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> getAdjacentLocations(Location location, int visiblity) {
        // The list of locations to be returned.
        List<Location> locations = new ArrayList<>();
        if (location != null) {
            int row = location.row();
            int col = location.col();
            for (int roffset = -visiblity; roffset <= visiblity; roffset++) {
                int nextRow = row + roffset;
                if (nextRow >= 0 && nextRow < depth) {
                    for (int coffset = -visiblity; coffset <= visiblity; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if (nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }

            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Print out the number of foxes and rabbits in the field.
     */
    public void fieldStats(int step) {
        int numFalcons = 0,
                numSnakes = 0,
                numJerboas = 0,
                numLizards = 0,
                numCamels = 0;
        int numBushes = 0,
                numNakhlas = 0;
        for (Species occupant : field.values()) {
            if (occupant instanceof Falcon falcon) {
                if (falcon.isAlive()) {
                    numFalcons++;
                }
            } else if (occupant instanceof Snake snake) {
                if (snake.isAlive()) {
                    numSnakes++;
                }
            } else if (occupant instanceof Lizard lizard) {
                if (lizard.isAlive()) {
                    numLizards++;
                }
            } else if (occupant instanceof Jerboa jerboa) {
                if (jerboa.isAlive()) {
                    numJerboas++;
                }
            } else if (occupant instanceof Camel camel) {
                if (camel.isAlive()) {
                    numCamels++;
                }
            } else if (occupant instanceof Bush bush) {
                if (bush.isAlive()) {
                    numBushes++;
                }
            } else if (occupant instanceof Nakhla nakhla) {
                if (nakhla.isAlive()) {
                    numNakhlas++;
                }
            }
        }

    System.out.printf(
        "{\"step\":%d,\"falcon\":%d,\"snake\":%d,\"lizard\":%d,\"jerboa\":%d,\"camel\":%d,\"bush\":%d,\"nakhla\":%d}%n",
        step,
        numFalcons,
        numSnakes,
        numLizards,
        numJerboas,
        numCamels,
        numBushes,
        numNakhlas);
    }

    /**
     * Empty the field.
     */
    public void clear() {
        field.clear();
        animals.clear();
    }

    /**
     * Return whether there is at least one rabbit and one fox in the field.
     * 
     * @return true if there is at least one rabbit and one fox in the field.
     */
    public boolean isViable() {
        for (Animal anAnimal : animals) {
            if (anAnimal.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the list of animals.
     */
    public List<Animal> getAnimals() {
        return animals;
    }

    /**
     * Get all species (animals + plants) in the field.
     */
    public Collection<Species> getSpecies() {
        return field.values();
    }

    /**
     * Return the depth of the field.
     * 
     * @return The depth of the field.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Return the width of the field.
     * 
     * @return The width of the field.
     */
    public int getWidth() {
        return width;
    }
}

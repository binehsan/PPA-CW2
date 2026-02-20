import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A simple predator-prey simulator, based on a rectangular field containing 
 * rabbits and foxes.
 * 
 * @author Muhammad Amen Ehsan & Faisal AlKhalifa
 * @version 7.1
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    private static final int MINUTES_PER_STEP = 4;

    private static final double FALCON_CREATION_PROBABILITY = SimulationConfig.FALCON_SPAWN;
    private static final double SNAKE_CREATION_PROBABILITY = SimulationConfig.SNAKE_SPAWN;
    private static final double CAMEL_CREATION_PROBABILITY = SimulationConfig.CAMEL_SPAWN;
    private static final double LIZARD_CREATION_PROBABILITY = SimulationConfig.LIZARD_SPAWN;
    private static final double JERBOA_CREATION_PROBABILITY = SimulationConfig.JERBOA_SPAWN;
    private static final double BUSH_CREATION_PROBABILITY = SimulationConfig.BUSH_SPAWN;
    private static final double NAKHLA_CREATION_PROBABILITY = SimulationConfig.NAKHLA_SPAWN;


    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private final SimulatorView view;

    private Weather currentWeather;


    public static void main(String[] args) {
        Simulator s = new Simulator();
        s.runLongSimulation();
    }

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be >= zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        field = new Field(depth, width);
        view = new SimulatorView(depth, width);

        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long 
     * period (4000 steps).
     */
    public void runLongSimulation()
    {

        simulate(360);
    }
    
    /**
     * Run the simulation for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        currentWeather = Weather.NORMAL;
        reportStats();
        for(int n = 1; n <= numSteps && field.isViable(); n++) {
            boolean hour = (step*MINUTES_PER_STEP) % 60 == 0;
            simulateOneStep(hour);
            delay(50);         // adjust this to change execution speed
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep(boolean hour)
    {
        step++;
        TimePeriod currentTime = getTimePeriod();

        double weatherRoll = Randomizer.getRandom().nextDouble();
        if (hour && weatherRoll < 0.5)
            updateWeather();

        double diseaseRoll = Randomizer.getRandom().nextDouble();
        if (hour && diseaseRoll < SimulationConfig.DISEASE_OUTBREAK_CHANCE) {
            Collection<Location> infectedLocations = field.getRandomRegion();
            for (Location location : infectedLocations) {
                Animal animal = field.getAnimalAt(location);
                if (animal != null) {
                    animal.infect();
                    System.out.println("Disease outbreak at " + location + "Animal:");
                    break;
                }

                Plant plant = field.getPlantAt(location);
                if (plant != null) {
                    plant.infect();
                    System.out.println("Disease outbreak at " + location + "Plant:");
                    break;
                }
            }
        }

        // Use a separate Field to store the starting state of
        // the next step.
        Field nextFieldState = new Field(field.getDepth(), field.getWidth());

        List<Animal> animals = new ArrayList<>(field.getAnimals());
        for (Animal anAnimal : animals) {
            anAnimal.act(field, nextFieldState, currentTime, currentWeather);
        }

        for (Plant plant : field.getPlants()) {
            if (plant.isAlive()) {
                Location location = plant.getLocation();
                if (!plant.blocksMovement() || nextFieldState.getAnimalAt(location) == null) {
                    nextFieldState.placePlant(plant, location);
                }
            }
        }

        Random rand = Randomizer.getRandom();
        double weatherMultiplier = switch(currentWeather) {
            case RAIN -> 1.5;
            case SANDSTORM -> 0.5;
            default -> 1.0;
        };
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                if (nextFieldState.getPlantAt(location) == null) {
                    if (rand.nextDouble() <= SimulationConfig.BUSH_REGROW_CHANCE*weatherMultiplier) {
                        nextFieldState.placePlant(new Bush(location), location);
                    } else if (nextFieldState.getAnimalAt(location) == null
                            && rand.nextDouble() <= SimulationConfig.NAKHLA_REGROW_CHANCE*weatherMultiplier) {
                        nextFieldState.placePlant(new Nakhla(location), location);
                    }
                }
            }
        }
        
        // Replace the old state with the new one.
        field = nextFieldState;

        reportStats();
        view.showStatus(getDisplayTime(), field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        populate();
        view.showStatus(getDisplayTime(), field);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                double roll = rand.nextDouble();
                Location location = new Location(row, col);
                if (roll <= FALCON_CREATION_PROBABILITY) {
                    Falcon falcon = new Falcon(true, location);
                    field.placeAnimal(falcon, location);
                } else if (roll <= FALCON_CREATION_PROBABILITY
                        + SNAKE_CREATION_PROBABILITY) {
                    Snake snake = new Snake(true, location);
                    field.placeAnimal(snake, location);
                } else if (roll <= FALCON_CREATION_PROBABILITY
                        + SNAKE_CREATION_PROBABILITY
                        + LIZARD_CREATION_PROBABILITY) {
                    Lizard lizard = new Lizard(true, location);
                    field.placeAnimal(lizard, location);
                } else if (roll <= FALCON_CREATION_PROBABILITY
                        + SNAKE_CREATION_PROBABILITY
                        + LIZARD_CREATION_PROBABILITY
                        + JERBOA_CREATION_PROBABILITY) {
                    Jerboa jerboa = new Jerboa(true, location);
                    field.placeAnimal(jerboa, location);
                } else if (roll <= FALCON_CREATION_PROBABILITY
                        + SNAKE_CREATION_PROBABILITY
                        + LIZARD_CREATION_PROBABILITY
                        + JERBOA_CREATION_PROBABILITY
                        + CAMEL_CREATION_PROBABILITY) {
                    Camel camel = new Camel(true, location);
                    field.placeAnimal(camel, location);
                } else if (roll <= FALCON_CREATION_PROBABILITY
                        + SNAKE_CREATION_PROBABILITY
                        + LIZARD_CREATION_PROBABILITY
                        + JERBOA_CREATION_PROBABILITY
                        + CAMEL_CREATION_PROBABILITY
                        + BUSH_CREATION_PROBABILITY) {
                    Bush bush = new Bush(location);
                    field.placePlant(bush, location);
                } else if (roll <= FALCON_CREATION_PROBABILITY
                        + SNAKE_CREATION_PROBABILITY
                        + LIZARD_CREATION_PROBABILITY
                        + JERBOA_CREATION_PROBABILITY
                        + CAMEL_CREATION_PROBABILITY
                        + BUSH_CREATION_PROBABILITY
                        + NAKHLA_CREATION_PROBABILITY) {
                    Nakhla nakhla = new Nakhla(location);
                    field.placePlant(nakhla, location);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Report on the number of each type of animal in the field.
     */
    public void reportStats()
    {
        //System.out.print("Step: " + step + " ");
        field.fieldStats(step);
    }
    
    /**
     * Pause for a given time.
     * @param milliseconds The time to pause for, in milliseconds
     */
    private void delay(int milliseconds)
    {
        try {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e) {
            // ignore
        }
    }

    public TimePeriod getTimePeriod() {
        int hour = (step*MINUTES_PER_STEP / 60) % 24;
        if (hour >= 6 && hour < 12) {
            return TimePeriod.MORNING;
        } else if (hour >= 12 && hour < 18) {
            return TimePeriod.AFTERNOON;
        } else if (hour >= 18 && hour < 24) {
            return TimePeriod.EVENING;
        } else {
            return TimePeriod.NIGHT;
        }
    }

    public String getDisplayTime(){
        return String.format("%02d:%02d", (step*MINUTES_PER_STEP / 60) % 24, step*MINUTES_PER_STEP % 60) + " (" + getTimePeriod() + ")" + " - Weather: " + currentWeather;
    }

    public void updateWeather() {
        Random rand = Randomizer.getRandom();
        int weatherRoll = rand.nextInt(100);
        if (weatherRoll < 20) {
            currentWeather = Weather.RAIN;
        } else if (weatherRoll < 40) {
            currentWeather = Weather.SANDSTORM;
        } else {
            currentWeather = Weather.NORMAL;
        }
    }




}

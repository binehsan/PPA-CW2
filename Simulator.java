import java.util.*;

/**
 * A simple predator-prey simulator, based on a rectangular field containing 
 * rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 7.1
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

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
        simulate(700);
    }
    
    /**
     * Run the simulation for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        reportStats();
        for(int n = 1; n <= numSteps && field.isViable(); n++) {
            simulateOneStep();
            delay(50);         // adjust this to change execution speed
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        // Use a separate Field to store the starting state of
        // the next step.
        Field nextFieldState = new Field(field.getDepth(), field.getWidth());

        List<Animal> animals = new ArrayList<>(field.getAnimals());
        for (Animal anAnimal : animals) {
            anAnimal.act(field, nextFieldState);
        }

        for (Species species : field.getSpecies()) {
            if (species instanceof Plant plant && plant.isAlive()) {
                nextFieldState.placePlant(plant, plant.getLocation());
            }
        }
        
        // Replace the old state with the new one.
        field = nextFieldState;

        reportStats();
        view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        populate();
        view.showStatus(step, field);
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
                if(rand.nextDouble() <= FALCON_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Falcon falcon = new Falcon(true, location);
                    field.placeAnimal(falcon, location);
                }
                else if(rand.nextDouble() <= SNAKE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Snake snake = new Snake(true, location);
                    field.placeAnimal(snake, location);
                } else if(rand.nextDouble() <= LIZARD_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Lizard lizard = new Lizard(true, location);
                    field.placeAnimal(lizard, location);
                } else if(rand.nextDouble() <= JERBOA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Jerboa jerboa = new Jerboa(true, location);
                    field.placeAnimal(jerboa, location);
                } else if(rand.nextDouble() <= CAMEL_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Camel camel = new Camel(true, location);
                    field.placeAnimal(camel, location);
                } else if(rand.nextDouble() <= BUSH_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Bush bush = new Bush(location);
                    field.placePlant(bush, location);
                } else if(rand.nextDouble() <= NAKHLA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
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
}

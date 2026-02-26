import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author Muhammad Amen Ehsan & Faisal AlKhalifa
 * @version 1.0
 */
public class SimulatorView extends JFrame {
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String TIME_PREFIX = "Time: ";
    private final String POPULATION_PREFIX = "Population: ";
    private final JLabel timeLabel;
    private final JLabel population;
    private final FieldView fieldView;
    private final JLabel statisticsLabel;
    private final JPanel legendPanel;
    private final JPanel statsPanel;

    // A map for storing colors for participants in the simulation
    private final Map<Class<?>, Color> colors;
    // A statistics object computing and storing simulation information
    private final FieldStats stats;

    /**
     * Create a view of the given width and height.
     * 
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width) {
        stats = new FieldStats();
        colors = new LinkedHashMap<>();

        setColor(Falcon.class, Color.red);
        setColor(Snake.class, Color.green);
        setColor(Jerboa.class, Color.orange);
        setColor(Lizard.class, Color.blue);
        setColor(Camel.class, Color.magenta);
        setColor(Bush.class, new Color(34, 139, 34));
        setColor(Nakhla.class, new Color(85, 107, 47));

        setTitle("Desert Ecosystem Simulation");
        timeLabel = new JLabel(TIME_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        legendPanel = new JPanel();
        statisticsLabel = new JLabel("", JLabel.CENTER);

        population.setAlignmentX(Component.CENTER_ALIGNMENT);
        statisticsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        legendPanel.setBorder(new EmptyBorder(6, 6, 6, 6));
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.add(createLegendEntry("Falcon", getColor(Falcon.class)));
        legendPanel.add(createLegendEntry("Snake", getColor(Snake.class)));
        legendPanel.add(createLegendEntry("Jerboa", getColor(Jerboa.class)));
        legendPanel.add(createLegendEntry("Lizard", getColor(Lizard.class)));
        legendPanel.add(createLegendEntry("Camel", getColor(Camel.class)));
        legendPanel.add(createLegendEntry("Bush", getColor(Bush.class)));
        legendPanel.add(createLegendEntry("Nakhla", getColor(Nakhla.class)));
        legendPanel.add(createLegendEntry("Infected", Color.black));

        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.add(population);
        statsPanel.add(statisticsLabel);

        setLocation(100, 50);

        fieldView = new FieldView(height, width);

        Container contents = getContentPane();
        contents.add(timeLabel, BorderLayout.NORTH);
        contents.add(legendPanel, BorderLayout.WEST);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(statsPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    private JPanel createLegendEntry(String label, Color color) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setBorder(new EmptyBorder(2, 2, 2, 2));

        JPanel swatch = new JPanel();
        swatch.setBackground(color);
        swatch.setPreferredSize(new Dimension(12, 12));
        swatch.setMaximumSize(new Dimension(12, 12));
        swatch.setMinimumSize(new Dimension(12, 12));
        swatch.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel textLabel = new JLabel(" " + label);
        row.add(swatch);
        row.add(Box.createRigidArea(new Dimension(6, 0)));
        row.add(textLabel);
        return row;
    }

    /**
     * Define a color to be used for a given class of animal.
     * 
     * @param animalClass The animal's Class object.
     * @param color       The color to be used for the given class.
     */
    public void setColor(Class<?> animalClass, Color color) {
        colors.put(animalClass, color);
    }

    /**
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class<?> animalClass) {
        Color col = colors.get(animalClass);
        if (col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        } else {
            return col;
        }
    }

    public FieldStats getFieldStats() {
        return stats;
    }

    /**
     * Show the current status of the field.
     * 
     * @param time  The formatted time string for display.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(String time, Field field, PopulationHistory populationHistory) {
        if (!isVisible()) {
            setVisible(true);
        }

        timeLabel.setText(TIME_PREFIX + time);
        stats.reset();

        fieldView.preparePaint();

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Species occupant = field.getSpeciesAt(new Location(row, col));
                if (occupant != null) {
                    stats.incrementCount(occupant.getClass());
                    // use black if infected, otherwise the class color
                    Color drawColor;
                    if (occupant.isInfected()) {
                        drawColor = Color.black;
                    } else {
                        drawColor = getColor(occupant.getClass());
                    }
                    fieldView.drawMark(col, row, drawColor);
                } else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        statisticsLabel.setText(populationHistory.getAvgString());
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * 
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field) {
        return stats.isViable(field);
    }

    /**
     * Provide a graphical view of a rectangular field. This is
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this
     * for your project if you like.
     */
    private class FieldView extends JPanel {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private final int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width) {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                    gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint() {
            if (!size.equals(getSize())) { // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if (xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if (yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }

        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color) {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale - 1, yScale - 1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (fieldImage != null) {
                Dimension currentSize = getSize();
                if (size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                } else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}

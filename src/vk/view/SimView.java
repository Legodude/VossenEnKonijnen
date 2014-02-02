package vk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import vk.model.Field;
import vk.model.FieldStats;

public class SimView extends JPanel 
{
    /**
	 * stom nummertje om hem maar uniek te laten zijn
	 */
	private static final long serialVersionUID = 1959304529004351545L;
	private JLabel stepLabel, population;
    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    public static FieldView fieldView;
    private FieldStats stats;
    // A map for storing colors for participants in the simulation
    private Map<Class<?>, Color> colors;
	// Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;
	public SimView(int height, int width)
	{
		colors = new LinkedHashMap<Class<?>, Color>();
		stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
	    population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
	    stats = new FieldStats();
	    fieldView = new FieldView(height, width);
        this.setLayout(new BorderLayout(0, 0));
        this.add(stepLabel, BorderLayout.NORTH);
        this.add(fieldView, BorderLayout.CENTER);
        this.add(population, BorderLayout.SOUTH);
	}
	/**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, Field field)
    {
        if(!isVisible()) {
            setVisible(true);
        }
            
        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();
        
        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class<?> animalClass, Color color)
    {
        colors.put(animalClass, color);
    }
    /**
     * @return The color to be used for a given class of animal.
     */
    public Color getColor(Class<?> animalClass)
    {
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }
}

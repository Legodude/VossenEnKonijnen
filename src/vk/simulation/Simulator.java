package vk.simulation;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

import vk.actor.*;
import vk.model.*;
import vk.view.*;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits, foxes and hunters.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */


public class Simulator 
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 100;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.10;    
    // The probability that a hunter will be created in any given grid position.
    private static final double HUNTER_CREATION_PROBABILITY = 0.015;    
    // The probability that an alligator will be created in any given grid position.
    private static final double ALLIGATOR_CREATION_PROBABILITY = 0.01;
    // List of animals in the field.
    private static List<Actor> actors;
    // The current state of the field.
    private static Field field;
    // The current step of the simulation.
    private static int step;
    // A graphical view of the simulation.
    private static SimulatorView view;
    
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
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        actors = new ArrayList<Actor>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.getSim().setColor(Rabbit.class, Color.orange);
        view.getSim().setColor(Fox.class, Color.blue);
        view.getSim().setColor(Hunter.class, Color.red);
        view.getSim().setColor(Alligator.class, new Color(52,151,52));
        view.getSim().setColor(ZombieRabbit.class, new Color(170,255,170));
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     */
    public void runLongSimulation()
    {
        simulate(500);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public static void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.getSim().isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public static void simulateOneStep()
    {
        step++;

        // Provide space for newborn actors.
        List<Actor> newActors = new ArrayList<Actor>();        
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            actor.act(newActors);
            if(! actor.isAlive()) {
                it.remove();
            }

        }
        actors.addAll(newActors);

        view.getSim().showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public static void reset()
    {
        step = 0;
        actors.clear();
        populate();
        
        // Show the starting state in the view.
        view.getSim().showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private static void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(false, field, location);
                    actors.add(fox);
                }
                else if(rand.nextDouble() <= HUNTER_CREATION_PROBABILITY)
                {
                	Location location = new Location (row, col);
                	Hunter hunter = new Hunter(false, field, location);
                	actors.add(hunter);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(false, field, location);
                    actors.add(rabbit);
                }
                else if(rand.nextDouble() <= ALLIGATOR_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Alligator alligator = new Alligator(false, field, location);
                    actors.add(alligator);
                }
                // else leave the location empty.
            }
        }
    }
}
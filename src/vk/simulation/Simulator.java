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
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */


public class Simulator extends AbstractModel implements Runnable
{	
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 100;
    private static int CUSTOM_WIDTH = 0;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;
    private static int CUSTOM_DEPTH = 0;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.1;    
    // The probability that a hunter will be created in any given grid position.
    private static final double HUNTER_CREATION_PROBABILITY = 0.015;    
    // The probability that an alligator will be created in any given grid position.
    private static final double ALLIGATOR_CREATION_PROBABILITY = 0.015;
    // The probability that an Grass will be created in any given grid position.
    private static final double GRASS_CREATION_PROBABILITY = 0.030;
    // List of animals in the field.
    private static List<Actor> actors;
    // The current state of the field.
    private static Field field;
    // The current step of the simulation.
    private static int step;
    // A graphical view of the simulation.
    private static SimulatorView view;
    
    //A set of thread-related variables
    private static boolean running = false;
    private static boolean run = false;
    private static boolean suspendFlag;
    public static Thread thread;
    private static final Object lock = new Object();
    
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
        thread = new Thread(this);
        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.getSim().setColor(Rabbit.class, Color.orange);
        view.getSim().setColor(Fox.class, Color.blue);
        view.getSim().setColor(Hunter.class, Color.red);
        view.getSim().setColor(Alligator.class, new Color(52,151,52));
        view.getSim().setColor(ZombieRabbit.class, Color.pink );
        view.getSim().setColor(Grass.class, new Color(170,255,170));
        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a single step.
     * The simulation stops if there is only one kind of animal left.
     */
    public static void simulateOneStep()
    {
    	if(!view.getSim().isViable(field)){
    		suspendFlag = true;
    	}
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
        if(CUSTOM_DEPTH>0 && CUSTOM_WIDTH>0 && CUSTOM_DEPTH <=100 && CUSTOM_WIDTH <=100) {
        	field.setField(CUSTOM_DEPTH, CUSTOM_WIDTH);
        	view.getSim().getView().changeView(CUSTOM_DEPTH,CUSTOM_WIDTH);
        	view.repack();
        }
        // Show the starting state in the view.
        view.getSim().showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with animals.
     */
    public static void populate()
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
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY)
                {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(false, field, location);
                    actors.add(rabbit);
                }
                else if(rand.nextDouble() <= ALLIGATOR_CREATION_PROBABILITY)
                {
                    Location location = new Location(row, col);
                    Alligator alligator = new Alligator(false, field, location);
                    actors.add(alligator);
                }
                else if (rand.nextDouble() <= GRASS_CREATION_PROBABILITY)
                {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(field, location);
                    actors.add(grass);
                }
                // else leave the location empty.
            }
        }
    }
    
    /**
     * The method that starts a new thread or restarts an already started one
     */
    public static void start(){
    	if(thread.getState()==Thread.State.NEW) {
    		thread.start();
    	}
    	else {
    		suspendFlag = false;
        	synchronized (lock) {
        		lock.notifyAll();
        	}
    	}
    }

    /**
     * This method contains what a thread does in its lifetime
     */
    public void run(){
        if(running == true)
            return;
        run = true;
        try {
		    while(run){
		    	running = true;
		        simulateOneStep();
		        running = false;
		        try {
		        Thread.sleep(50);
		    	} catch (InterruptedException ex) {}
		        synchronized (lock) {
		        	while(suspendFlag) {
		        		lock.wait();
		        	}
		        }
		    }
		} catch (InterruptedException e) {}
    }
        
    /**
     * This method pauses the thread.
     */
    public static void stop() {
        suspendFlag = true;
    }
    
    /**
     * Changes the field's dimensions
     * @param width
     * @param depth
     */
    public static void setField(int width, int depth) {
    	CUSTOM_WIDTH = width;
    	CUSTOM_DEPTH = depth;
    }
    
    public Simulator getThis() {
    	return this;
    }
}
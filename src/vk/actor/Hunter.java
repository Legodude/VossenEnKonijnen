package vk.actor;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import vk.model.Field;
import vk.model.Location;
import vk.simulation.Randomizer;

public class Hunter extends Animal {

    // Characteristics shared by all foxes (static fields).
    
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 16;//16 and pregnant :D
    // The age to which a fox can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.90;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 10;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
	private static final int FOX_FOOD_VALUE = 5;
	
    // Individual characteristics (instance fields).
    // The fox's age.
    private int age;
    // The hunter's sex.
    public char sex;
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a hunter can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        sex = chooseSex();
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the hunter most of the time: it hunts for
     * rabbits and foxes. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newHunter A list to add newly born hunters to.
     */
    public void act(List<Actor> newHunter)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newHunter);            
            // Move towards a source of food if found.
            Location location = getLocation();
            Location newLocation = findFood(location);
            if(newLocation == null) { 
                // No food found - try to move to a partner.
                newLocation = closestMate();
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    /**
     * The getter for the sex of the hunter.
     * @return a 'm' or 'f'
     */
    public char getSex()
    {
    	return sex;
    }

    /**
     * Increase the age. This could result in the hunters death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Make this hunter more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Tell the hunter to look for rabbits and foxes adjacent to its current location.
     * Only the first live rabbit is eaten or fox.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    
    
    private Location findFood(Location location)
    {
        Field field = getField();
        List<Location> adjacent = closestPrey(field.adjacentLocations(getLocation(), 2));
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) { 
                    fox.setDead();
                    foodLevel = FOX_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Return the list of the closest prey. 1 block or 2 blocks away.
     * @param location
     * @return List<Location> of closest prey
     */
    private List<Location> closestPrey(List<Location> location)
    {
    	Iterator<Location> it = location.iterator();
    	List<Location> closer = new LinkedList<Location>();
    	while(it.hasNext()) {
    		Location where = it.next();
    		if(where.getCol()==getLocation().getCol()-1 || where.getCol()==getLocation().getCol()+1
    		|| where.getRow()==getLocation().getRow()-1 || where.getRow()==getLocation().getRow()+1) {
    			closer.add(where);
    		}
    	}
    	if (closer.isEmpty()) {
    		return location;
    	}
    	return closer;
    }
    
    /**
     * Returns the best spot to go to to get to a mate
     * @return Location (towards mate)
     */
    private Location closestMate()
    {	
    	Location route;
    	int movementcol;
    	int movementrow;
    	int closest = 999999;
    	int current = this.getLocation().getCol() + this.getLocation().getRow();
    	Location mate = null;
    	Field field = getField();
    	if(field.getAllCompatibleActors(this)!=null && mate() == false && canBreed() == true) {
    		List<Location> all = field.getAllCompatibleActors(this);
	    	for(int a = 0; a < all.size(); a++) {
	    		int comparable = all.get(a).getRow() + all.get(a).getCol();
	    		if(current-comparable<closest) {
	    			closest = comparable;
	    			mate = all.get(a);
	    		}
	    	}
	    	if(mate.getCol()>this.getLocation().getCol()) {
	    		movementcol = this.getLocation().getCol()+1;
	    	}
	    	else {
	    		movementcol = mate.getCol()+1;
	    	}
	    	if(mate.getRow()>this.getLocation().getRow()) {
	    		movementrow = this.getLocation().getRow()+1;
	    	}
	    	else {
	    		movementrow = mate.getRow()+1;
	    	}
	    	if(mate.getCol()==this.getLocation().getCol()) {
	    		route = new Location(movementrow,mate.getCol());
	    	}
	    	else if(mate.getRow()==this.getLocation().getRow()) {
	    		route = new Location(mate.getRow(), movementcol);
	    	}
	    	else {
	    		route = new Location(movementrow, movementcol);
	    	}
    	}
    	else {
    		return field.freeAdjacentLocation(this.getLocation());
    	}
    	return route;
    }
    
    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to add newly born foxes to.
     */
    private void giveBirth(List<Actor> newHunter)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        if(mate()==true) {
	        for(int b = 0; b < births && free.size() > 0; b++) {
	            Location loc = free.remove(0);
	            Hunter young = new Hunter(false, field, loc);
	            newHunter.add(young);
	        }
        }
    }
    
    /**
     * Check if the hunter is standing next to a member of the opposite sex
     * @return true/false
     */
    private boolean mate()
    {
    	Field field = getField();
    	List<Actor> animals = field.getAnimalsAdjacentLocations(getLocation());
    	for(int a = 0; a < animals.size(); a++ ) {
    		if(this.getClass().equals(animals.get(a).getClass())) {
    			if(this.getSex()!=animals.get(a).getSex()) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A hunter can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
package vk.actor;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import vk.model.Field;
import vk.model.Location;
import vk.simulation.Randomizer;

/**
 * A simple model of an alligator.
 * Alligators age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Alligator extends Animal
{
    // Characteristics shared by all alligators (static fields).

    // The age at which an alligator can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which an alligator can live.
    private static final int MAX_AGE = 50;
    // The likelihood of an alligator breeding.
    private static final double BREEDING_PROBABILITY = 0.45;
    // The food value of a single rabbit, fox and hunter. In effect, this is the
    // number of steps an alligator can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 7;
    private static final int FOX_FOOD_VALUE = 5;
    private static final int HUNTER_FOOD_VALUE = 12;
    // The maximum number of births.    
    private static final int MAX_LITTER_SIZE = 6;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The alligator's age.
    private int age;
    // The alligator's sex.
    public char sex;
    // The alligator's food level, which is increased by eating rabbits, foxes and hunters.
    public int foodLevel;

    /**
     * Create a new alligator. An alligator may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the alligator will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Alligator(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        sex = chooseSex();
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(HUNTER_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = HUNTER_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the alligator does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newAlligators A list to add newly born alligators to.
     */
    public void act(List<Actor> newAlligators)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newAlligators);            
            // Move towards a source of food if found.
            Location location = getLocation();
            Location newLocation = findFood(location);
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(location);
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
    
    private Location findFood(Location location)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation(), 1);
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
            if(animal instanceof Hunter){
            	Hunter hunter = (Hunter) animal;
            	if(hunter.isAlive()){
            		hunter.setDead();
            		foodLevel = HUNTER_FOOD_VALUE;
            		return where;
            	}
            }
            if(animal instanceof Grass){
            	Grass grass = (Grass) animal;
            	if(grass.isAlive()){
            		grass.setDead();
            		return where;
            	}
            }
        }
        return null;
    }
    
    /**
     * Make this alligator more hungry. This could result in the alligator's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * The getter for the sex of the alligator.
     * @return a 'm' or 'f'
     */
    public char getSex()
    {
    	return sex;
    }
    /**
     * Increase the age.
     * This could result in the alligator's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check whether or not this alligator is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAlligators A list to add newly born alligators to.
     */
    private void giveBirth(List<Actor> newAlligators)
    {
        // New alligators are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        if(mate()==true) {
	        for(int b = 0; b < births && free.size() > 0; b++) {
	            Location loc = free.remove(0);
	            Alligator young = new Alligator(false, field, loc);
	            newAlligators.add(young);
	        }
        }
    }
    
    /**
     * Check if the alligator is standing next to a member of the opposite sex
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
     * An alligator can breed if it has reached the breeding age.
     * @return true if the alligator can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}

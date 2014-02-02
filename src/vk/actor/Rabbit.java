package vk.actor;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import vk.model.Field;
import vk.model.Location;
import vk.simulation.Randomizer;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (static fields).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 20;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.30;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 8;
    // number of steps an rabbit can go before it has to eat again
    private static final int GRASS_FOOD_VALUE = 30;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Random infection chance
    private static final int INFECTION_CHANCE = 10000;

    // Individual characteristics (instance fields).
    // The rabbit's age.
    private int age;
    // The rabbit's sex.
    public char sex;
 // The rabbits food level, which is increased by eating grass
    public int foodLevel;
    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        sex = chooseSex();
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(GRASS_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = GRASS_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to add newly born rabbits to.
     */
    public void act(List<Actor> newRabbits)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            infectionChance(newRabbits);
        }
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Move towards a source of food if found.
            Location location = getLocation();
            Location newLocation = findFood(location);
            if(avoidZombies()!=null) {
            	newLocation = avoidZombies();
            }
            else {
            	newLocation = getField().freeAdjacentLocation(getLocation());	
            }
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
    
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
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
            if(animal instanceof Grass) {
                Grass grass = (Grass) animal;
                if(grass.isAlive()) { 
                    grass.setDead();
                    foodLevel = GRASS_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
            else if(animal instanceof PoisonGrass) {
                PoisonGrass poisongrass = (PoisonGrass) animal;
                if(poisongrass.isAlive()) { 
                    poisongrass.setDead();
                    this.setDead();
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * The getter for the sex of the rabbit.
     * @return a 'm' or 'f'
     */
    public char getSex()
    {
    	return sex;
    }
    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * This method gives the rabbit a chance to get sick and turn into a zombie rabbit
     */
    private void infectionChance(List<Actor>newRabbits)
    {
    	Field field = getField();
    	int random = rand.nextInt(INFECTION_CHANCE+1);
    	if(random==INFECTION_CHANCE) {
    		Location location = getLocation();
    		setDead();
            ZombieRabbit rabbit = new ZombieRabbit(true, field, location);
            newRabbits.add(rabbit);
    	}
	}
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to add newly born rabbits to.
     */
    private void giveBirth(List<Actor> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        if(mate()==true) {
	        for(int b = 0; b < births && free.size() > 0; b++) {
	            Location loc = free.remove(0);
	            Rabbit young = new Rabbit(false, field, loc);
	            newRabbits.add(young);
	        }
        }
    }
    
    /**
     * This method returns a new location, away from the closeby zombie rabbit!
     * @return the new Location!
     */
    private Location avoidZombies() {
    	Field field = getField();
    	Location current = getLocation();
    	List<Location> locations = field.adjacentLocations(getLocation(), 2);
    	if(locations.isEmpty()) {
    		for(int a = 0; a < locations.size(); a++) {
    			Location comparable = locations.get(a);
    			if(field.getObjectAt(comparable.getRow(), comparable.getCol()) instanceof ZombieRabbit) {
    				int row = (comparable.getRow()-current.getRow()) *-1;
    				int col = (comparable.getCol()-current.getRow()) *-1;
    				return new Location(getLocation().getRow()+row, getLocation().getCol()+col);
    			}
    		}
    	}
    	return null;
    }
    
    /**
     * Check if the rabbit is standing next to a member of the opposite sex
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
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    /**
     * get the age of the rabbit
     * @return age
     */
    public int getAge()
    {
    	return age;
    }
}

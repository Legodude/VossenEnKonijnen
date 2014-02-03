package vk.actor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import vk.model.Field;
import vk.model.Location;
import vk.simulation.Randomizer;

public class ZombieRabbit extends Animal{

	private int age;
	private char sex;
	private static final int MAX_AGE = 4;
	private static final double INFECTION_CHANCE = 0.4;
	private static final double MOVEMENT_CHANCE = 0.2;
	
	public ZombieRabbit(boolean alive, Field field, Location location){
		super(field, location);
	}
	
	public ZombieRabbit(boolean alive, Field field, Location location, int age) {
		super(field, location);
	}
	
	/**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to add newly born rabbits to.
     */
    public void act(List<Actor> newZombieRabbits)
    {
        incrementAge();
        if(isAlive()) {
        	Location location = getLocation();
        	infectRabbit(location,newZombieRabbits);
        	Location newLocation = getField().freeAdjacentLocation(getLocation());
        	if(newLocation != null) {
        		if(movement() == true) {
        			setLocation(newLocation);
        		}
        	}
        	else {
        		setDead();
        	}
        }
    }
    
    /**
     * The chance that the zombie rabbit moves
     * @return true or false
     */
    private boolean movement() {
    	Random rand = Randomizer.getRandom();
    	double random = rand.nextDouble();
    	if(random < MOVEMENT_CHANCE) {
    		return true;
    	}
    	return false;
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
     * Tell the hunter to look for rabbits and foxes adjacent to its current location.
     * Only the first live rabbit is eaten or fox.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    
    private boolean infectRabbit(Location location,List<Actor> newZombieRabbits)
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
            		if(Randomizer.getRandom().nextDouble()>INFECTION_CHANCE)
            		{
            			rabbit.setDead();
            			List<Location> free = field.getFreeAdjacentLocations(getLocation());
    		            Location loc = free.remove(0);
            			ZombieRabbit zombieRabbit = new ZombieRabbit(true, field, loc, rabbit.getAge());
            			newZombieRabbits.add(zombieRabbit);
            			return true;
            		}
            		else
            		{
            			rabbit.setDead();
            			return true;
            		}
                }
            	setLocation(where);
            }
        }
        return false;
    }
}

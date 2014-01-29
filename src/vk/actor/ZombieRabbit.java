package vk.actor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import vk.model.Field;
import vk.model.Location;
import vk.simulation.Randomizer;

public class ZombieRabbit extends Animal{

	private int age;
	private char sex;
	private static final int MAX_AGE = 40;
	private static final double INFECTION_CHANCE = 0.5;
	
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
        }
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
        List<Location> adjacent = closestPrey(field.adjacentLocations(getLocation(), 1));
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
}

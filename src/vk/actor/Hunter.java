package vk.actor;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import vk.model.Field;
import vk.model.Location;
import vk.simulation.Randomizer;

public class Hunter implements Actor {
    
    private int age;
    
    private int foodLevel;
    
    private boolean alive;
    
    private Field field;
    
    private static final Random rand = Randomizer.getRandom();
    
    private final int MAX_AGE = 100;
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
        this.alive=true;
        this.field=field;
        setLocation(location);
        if(randomAge==true)
        {
        	this.age=rand.nextInt(MAX_AGE);
        }
        else
        {
        	this.age=0;
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
        if(isAlive()) {        
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
    
    /**
     * Tell the hunter to look for rabbits and foxes adjacent to its current location.
     * Only the first live rabbit is eaten or fox.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    
    
    //Vos moet er nog bij komen die wordt "opgegeten door de jager(hunter)"
    private Location findFood(Location location)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) { 
                    fox.setDead();
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
    
    public void incrementAge()
    {
    	if(age<MAX_AGE)
		{
    		age++;
		}
    	else
    	{
    		setDead();
    	}
    }

	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDead() {
		// TODO Auto-generated method stub
		
	}

	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public Field getField() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLocation(Location location) {
		// TODO Auto-generated method stub
		
	}
}
package vk.actor;
import java.util.List;

import java.util.Random;

import vk.model.Field;
import vk.model.Location;
import vk.simulation.Randomizer;

public class Grass extends Animal {
	
    // The age at which a grass can start to breed.
    private static final int BREEDING_AGE = 0;
    // The age to which a grass can live.
    private static final int MAX_AGE = 35;
    // The likelihood of a grass breeding.
    private static final double BREEDING_PROBABILITY = 0.10;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // Random generator to controll breeding
    private static final Random rand = Randomizer.getRandom();
	// Set the age of the Grass
    private int age;
    
	public Grass(Field field, Location location)
	{
		super(field, location);
		age = 0;
	}
	
    public void act(List<Actor> newGrass)
    {
        incrementAge();
        if(isAlive()) 
        {
            giveBirth(newGrass);            
        }
        else 
        {
        	 setDead();
        }
     }
    
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    private void giveBirth(List<Actor> newGrass)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
	    for(int b = 0; b < births && free.size() > 0; b++) 
	    {
		    Location loc = free.remove(0);
		    Grass young = new Grass(field, loc);
		    newGrass.add(young);
	    }
     }
    
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

	public char getSex() {
		return 0;
	} 
}

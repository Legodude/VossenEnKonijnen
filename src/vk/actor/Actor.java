package vk.actor;

import java.util.List;

import vk.model.Field;
import vk.model.Location;

public interface Actor {
	
	//All actors have at least these variables
	public Field field = null;
	public Location location = null;
	public boolean alive=true;
	public String name = null;
	/**
	 * Function to check if Actor is Alive
	 * @return boolean alive
	 */
	public abstract boolean isAlive();
	
	/**
	 * Kill the actor
	*/
	public abstract void setDead();
	
	/**
	 * function to give the name of the Actor (e.g Chuck Norris)
	 * @return actorname
	 */
	public abstract String getActorName();
	
	/**
	 * Let the Actor act out what he does
	 * @param newactors
	 */
	public abstract void act(List<Actor> newactors);
	
	/**
	 * Return the sex of the Actor
	 */
	public char getSex();
	
}

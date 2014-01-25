package vk.actor;

import java.util.List;

import vk.model.Field;
import vk.model.Location;

public interface Actor {
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
	 * kill the Actor
	*/
	public abstract void setDead();
	/**
	 * function to give the name of the Actor (e.g Chuck Norris)
	 * @return actorname
	 */
	public abstract String getActorName();
	
	/**
	 * perform the operations on the grid
	 * @param newactors
	 */
	
	public abstract void act(List<Actor> newactors);
}

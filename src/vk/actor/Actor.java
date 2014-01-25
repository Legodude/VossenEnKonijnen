package vk.actor;

import java.util.List;

import vk.model.Field;
import vk.model.Location;

public interface Actor {
	public boolean alive = true;
	
	public Field field = null;
	
	public Location location = null;
	
	public boolean isAlive();
	
	public void setDead();
	
	public Location getLocation();
	
	public Field getField();
	
	public void setLocation(Location location);
	
	public void act(List<Actor> newActors);
}

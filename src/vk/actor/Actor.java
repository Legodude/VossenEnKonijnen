package vk.actor;

import java.util.List;

import vk.model.Field;
import vk.model.Location;

public interface Actor {
	public Field field = null;
	
	public Location location = null;
	
	public boolean alive=true;
	
	public abstract boolean isAlive();
	
	public abstract void setDead();
	
	public abstract void act(List<Actor> newactors);
}

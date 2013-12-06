package taz40.lifeless.entity.spawner;

import taz40.lifeless.entity.Entity;
import taz40.lifeless.entity.particle.Particle;
import taz40.lifeless.level.Level;

public abstract class Spawner extends Entity {
	
	public enum Type{
		PARTICLE, MOB
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level){
		this.x = x;
		this.y = y;
		this.type = type;
		this.level = level;
	}
	
}

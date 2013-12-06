package taz40.lifeless.Weapons;

import taz40.lifeless.entity.Entity;
import taz40.lifeless.graphics.Screen;
import taz40.lifeless.level.Level;

public abstract class Weapon {

	public int FIRERATE;
	public int firerate;
	public Level level;
	public Entity e;
	public int dmg = 0;
	
	public Weapon(){
	}
	
	public void init(Level level, Entity e){
		this.level = level;
		this.e = e;
	}
	
	public void update(){
		if(firerate > 0) firerate--;
	}
	
	public void render(Screen screen){
		
	}
	
	public abstract void shoot(double x, double y, double dir);
	
}

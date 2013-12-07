package taz40.lifeless.Weapons;

import taz40.lifeless.entity.Entity;
import taz40.lifeless.level.Level;

public class Sword extends Knife {
	
	public void init(Level level, Entity e){
		super.init(level, e);
		FIRERATE = 60;
		firerate = FIRERATE;
		dmg = 10;
	}

}

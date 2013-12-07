package taz40.lifeless.Weapons;

import java.util.List;

import taz40.lifeless.entity.Entity;
import taz40.lifeless.entity.mob.Mob;
import taz40.lifeless.entity.spawner.BloodSpawner;
import taz40.lifeless.level.Level;

public class Knife extends Weapon {

	public void init(Level level, Entity e){
		super.init(level, e);
		FIRERATE = 15;
		firerate = FIRERATE;
		dmg = 2;
	}
	

	public void shoot(double x, double y, double dir) {
		if(firerate <= 0){
			List<Mob> mobs = level.getMobs(e, 40);
			if(mobs.size() > 0){
				for(int i = 0; i < mobs.size(); i++){
					mobs.get(i).damage(dmg);
					level.add(new BloodSpawner((int)mobs.get(i).x, (int)mobs.get(i).y, 70, level));
				}
			}
			firerate = FIRERATE;
		}
	}

	
	
}

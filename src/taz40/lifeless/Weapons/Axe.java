package taz40.lifeless.Weapons;

import taz40.lifeless.entity.projectile.AxeProjectile;
import taz40.lifeless.entity.projectile.Projectile;
import taz40.lifeless.entity.projectile.WizardProjectile;

public class Axe extends Weapon {

	public Axe(){
		FIRERATE = 60*30;
	}
	
	@Override
	public void shoot(double x, double y, double dir) {
		// TODO Auto-generated method stub
		if(firerate <= 0){
			Projectile p = new AxeProjectile(x, y, dir);
			level.add(p);
			firerate = FIRERATE;
		}
	}

	
	
}

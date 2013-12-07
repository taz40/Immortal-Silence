package taz40.lifeless.entity.projectile;

import java.util.Random;

import taz40.lifeless.entity.spawner.BloodSpawner;
import taz40.lifeless.entity.spawner.ParticleSpawner;
import taz40.lifeless.entity.spawner.Spawner;
import taz40.lifeless.graphics.Screen;
import taz40.lifeless.graphics.Sprite;

public class WizardProjectile extends Projectile {

	protected final Random random = new Random();
	protected double velx, vely;
	public WizardProjectile(double x, double y, double dir) {
		super(x, y, dir);
		range = 200;
		dmg = 20;
		speed = 4;
		sprite = Sprite.projectile_wizard;
		velx = speed * Math.cos(angle);
		vely = speed * Math.sin(angle);
	}
	
	public void update(){
		xa = velx;
		ya = vely;
		if(level.tileCollision((int)(x+xa), (int)(y+ya), 8, 4, 4)){
			level.add(new ParticleSpawner((int)x, (int)y, 44, 50, level));
			remove();
		}
		if(collition(20)){
			level.add(new BloodSpawner((int)x, (int)y, 50, level));
			remove();
		}

		move();
	}

	public void render(Screen screen){
		screen.renderProjectile((int) x, (int) y, this);
	}

	
	
}
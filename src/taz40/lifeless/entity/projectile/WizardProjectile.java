package taz40.lifeless.entity.projectile;

import java.util.Random;

import taz40.lifeless.entity.spawner.ParticleSpawner;
import taz40.lifeless.entity.spawner.Spawner;
import taz40.lifeless.graphics.Screen;
import taz40.lifeless.graphics.Sprite;

public class WizardProjectile extends Projectile {

	protected final Random random = new Random();
	
	public WizardProjectile(double x, double y, double dir) {
		super(x, y, dir);
		range = 200;
		damage = 20;
		speed = 4;
		sprite = Sprite.projectile_wizard;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update(){
		if(level.tileCollision((int)(x+nx), (int)(y+ny), 8, 4, 4)){
			level.add(new ParticleSpawner((int)x, (int)y, 44, 50, level));
			remove();
		}
		move();
	}
	
	protected void move(){
		x += nx;
		y += ny;
		if(distance() > range){
			remove();
		}
	}
	
	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x)*(xOrigin - x) + (yOrigin - y)*(yOrigin - y)));
		return dist;
	}

	public void render(Screen screen){
		screen.renderProjectile((int) x, (int) y, this);
	}

	
	
}

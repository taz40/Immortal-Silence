package taz40.lifeless.entity.projectile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import taz40.lifeless.entity.mob.Mob;
import taz40.lifeless.entity.spawner.BloodSpawner;
import taz40.lifeless.entity.spawner.ParticleSpawner;
import taz40.lifeless.graphics.AnimatedSprite;
import taz40.lifeless.graphics.Screen;
import taz40.lifeless.graphics.Sprite;
import taz40.lifeless.graphics.SpriteSheet;

public class AxeProjectile extends Projectile {

	public double velx, vely;
	public AnimatedSprite Anim = new AnimatedSprite(SpriteSheet.AxeAim, 16, 16, 4, 7);
	public List<Mob> alreadyhit = new ArrayList<Mob>();
	
	public AxeProjectile(double x, double y, double dir) {
		super(x, y, dir);

		range = 200;
		dmg = 50;
		speed = 4;
		sprite = Sprite.projectile_wizard;
		velx = speed * Math.cos(angle);
		vely = speed * Math.sin(angle);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		xa = velx;
		ya = vely;
		if(level.tileCollision((int)(x+xa), (int)(y+ya), 8, 4, 4)){
			level.add(new ParticleSpawner((int)x, (int)y, 44, 50, level));
			remove();
		}
		if(collition(20, alreadyhit)){
			level.add(new BloodSpawner((int)x, (int)y, 50, level));
			if(new Random().nextInt(4) == 0){
				
			}else{
				remove();
			}
		}
		Anim.update();
		move();
	}
	
	public void render(Screen screen){
		sprite = Anim.getSprite();
		screen.renderProjectile((int) x, (int) y, this);
	}

	
	
}

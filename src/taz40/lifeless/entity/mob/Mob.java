package taz40.lifeless.entity.mob;

import taz40.lifeless.Weapons.Weapon;
import taz40.lifeless.entity.Entity;
import taz40.lifeless.entity.particle.Particle;
import taz40.lifeless.entity.projectile.Projectile;
import taz40.lifeless.entity.projectile.WizardProjectile;
import taz40.lifeless.graphics.Screen;
import taz40.lifeless.graphics.Sprite;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
	public int flip = 0;
	public Weapon weapon;
	
	public void move(double xa, double ya){
		if(xa != 0 && ya != 0){
			move(0, ya);
			move(xa, 0);
			return;
		}
		
		if(ya > 0) dir = 2;
		if(ya < 0) dir = 0;
		if(xa > 0) dir = 1;
		if(xa < 0) dir = 3;
		
		while(xa != 0){
			if(Math.abs(xa) > 1){
				if(!collision(abs(xa), ya)){
					this.x += abs(xa);
				}
				xa -= abs(xa);
			}else{
				if(!collision(abs(xa), ya)){
					this.x += xa;
				}
				xa = 0;
			}
		}
		
		while(ya != 0){
			if(Math.abs(ya) > 1){
				if(!collision(xa, abs(ya))){
					this.y += abs(ya);
				}
				ya -= abs(ya);
			}else{
				if(!collision(xa, abs(ya))){
					this.y += ya;
				}
				ya = 0;
			}
		}
	}
	
	private int abs(double value){
		if(value < 0 ) return -1;
		return 1;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public abstract void update();
	
	private boolean collision(double xa, double ya){
		boolean solid = false;
		for(int c = 0; c < 4; c++){
			double xt = ((x+xa) - c % 2 * 16) / 16;
			double yt = ((y+ya) - c / 2 * 16) /16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if(c % 2 == 0) ix = (int) Math.floor(xt);
			if(c / 2 == 0) iy = (int) Math.floor(yt);
			if(level.getTile(ix, iy).solid()) solid = true;
		}

		return solid;
	}
	
	public abstract void render(Screen screen);
	
}

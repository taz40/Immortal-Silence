package taz40.lifeless.entity.projectile;

import java.util.List;

import taz40.lifeless.entity.Entity;
import taz40.lifeless.entity.mob.Mob;
import taz40.lifeless.entity.spawner.BloodSpawner;
import taz40.lifeless.graphics.Sprite;

public abstract class Projectile extends Entity {

	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double xa, ya;
	protected double distance;
	protected double speed, range, dmg;
	
	public Projectile(double x, double y, double dir){
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	protected boolean collition(int size){
		List<Mob> mobs = level.getMobs(this, size);
		if(mobs.size() > 0){
			for(int i = 0; i < mobs.size(); i++){
				mobs.get(i).damage((int)dmg);
			}
			return true;
		}
		return false;
	}
	
	protected boolean collition(int size, List<Mob> alreadyhit){
		List<Mob> mobs = level.getMobs(this, size);
		boolean result = false;
		if(mobs.size() > 0){
			for(int i = 0; i < mobs.size(); i++){
				mobs.get(i).damage((int)dmg);
				if(!alreadyhit.contains(mobs.get(i))){
					result = true;
					alreadyhit.add(mobs.get(i));
				}
			}
		}
		return result;
	}
	
	protected void move(){
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
		if(distance() > range){
			remove();
		}
	}
	
	protected double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x)*(xOrigin - x) + (yOrigin - y)*(yOrigin - y)));
		return dist;
	}
	
	protected int abs(double value){
		if(value < 0 ) return -1;
		return 1;
	}
	
	protected boolean collision(double xa, double ya){
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
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getSpriteSize(){
		return sprite.SIZE;
	}
	
}

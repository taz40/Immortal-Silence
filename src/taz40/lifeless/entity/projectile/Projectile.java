package taz40.lifeless.entity.projectile;

import taz40.lifeless.entity.Entity;
import taz40.lifeless.graphics.Sprite;

public abstract class Projectile extends Entity {

	protected final double xOrigin, yOrigin;
	protected double x, y;
	protected double angle;
	protected Sprite sprite;
	protected double nx, ny;
	protected double distance;
	protected double speed, range, damage;
	
	public Projectile(double x, double y, double dir){
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	protected void move(){
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getSpriteSize(){
		return sprite.SIZE;
	}
	
}

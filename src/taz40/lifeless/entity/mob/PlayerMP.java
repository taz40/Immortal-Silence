package taz40.lifeless.entity.mob;

import taz40.lifeless.Game;
import taz40.lifeless.Weapons.Axe;
import taz40.lifeless.Weapons.Sword;
import taz40.lifeless.entity.projectile.Projectile;
import taz40.lifeless.graphics.AnimatedSprite;
import taz40.lifeless.graphics.Screen;
import taz40.lifeless.graphics.SpriteSheet;
import taz40.lifeless.input.Keyboard;
import taz40.lifeless.input.Mouse;
import taz40.lifeless.level.Level;

public class PlayerMP extends Player {

	private int anim = 0;
	private boolean walking = false;
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3, 7);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3, 7);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3, 7);
	public int ID;
	
	private AnimatedSprite animSprite = null;

	public PlayerMP(){
		initiate();
	}
	
	public void init(Level level){
		super.init(level);
		primary.init(level, this);
		secondary.init(level, this);
	}
	
	public PlayerMP(int x, int y){
		this.x = x;
		this.y = y;
		initiate();
	}
	
	private void initiate(){ 
		animSprite = down;
		primary = new Sword();
		secondary = new Axe();
	}
	
	public void update(){
		primary.update();
		secondary.update();
		if(walking) animSprite.update();
		else animSprite.setFrame(0);
		double xa=0,ya=0;
		double speed = 0.7;
		if(xa != 0 || ya != 0){
			move(xa, ya);
			walking = true;
		}else{
			walking = false;
		}
		clear();
	}
	
	private void clear() {
		// TODO Auto-generated method stub
		for(int i = 0; i < level.projectiles.size(); i++){
			Projectile p = level.projectiles.get(i);
			if(p.isRemoved()) level.projectiles.remove(i);
		}
	}

	public void render(Screen screen){
		int flip = 0;
		if(dir == 0) {
			animSprite = up;
		}
		if(dir == 1){
			animSprite = right;
		}
		if(dir == 2){
			animSprite = down;
		}
		if(dir == 3){
			flip = 1;
			animSprite = right;
		}
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x - 16),(int) (y - 16), sprite, flip);
		primary.render(screen);
		secondary.render(screen);
	}
}

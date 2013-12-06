package taz40.lifeless.entity.mob;

import taz40.lifeless.graphics.AnimatedSprite;
import taz40.lifeless.graphics.Screen;
import taz40.lifeless.graphics.Sprite;
import taz40.lifeless.graphics.SpriteSheet;

public class Dummy extends Mob {

	private boolean walking = false;
	private int xa = 0, ya = 0;
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3, 7);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3, 7);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3, 7);
	
	private AnimatedSprite animSprite = null;
	
	private int time = 0;
	
	public Dummy(int x, int y){
		this.x = x;
		this.y = y;
		animSprite = up;
		animSprite.setFrame(0);
		sprite = animSprite.getSprite();
	}
	
	public void render(Screen screen){
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
		screen.renderMob((int)(x - 16),(int) (y - 16), this);
	}
	
	private void updateAI(){
		time++;
		if(time % (random.nextInt(50) + 30) == 0){
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if(random.nextInt(4) == 0){
				xa = 0;
				ya = 0;
			}
		}
	}
	
	public void update() {
		updateAI();
		if(walking) animSprite.update();
		else animSprite.setFrame(0);
		if(xa != 0 || ya != 0){
			move(xa, ya);
			walking = true;
		}else{
			walking = false;
		}
	}

}

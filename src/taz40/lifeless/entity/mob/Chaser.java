package taz40.lifeless.entity.mob;

import java.util.List;

import taz40.lifeless.graphics.AnimatedSprite;
import taz40.lifeless.graphics.Screen;
import taz40.lifeless.graphics.SpriteSheet;

public class Chaser extends Mob {

	private boolean walking = false;
	private double xa = 0, ya = 0;
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3, 7);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3, 7);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3, 7);
	double speed = 0.8;
	private AnimatedSprite animSprite = null;

	private int time = 0;

	public Chaser(int x, int y) {
		this.x = x;
		this.y = y;
		sprite = up.getSprite();
	}

	public void render(Screen screen) {
		flip = 0;
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

	private void updateAI() {
		xa = 0;
		ya = 0;

		List<Player> players = level.getPlayers(this, 50);
		if (players.size() > 0) {
			Player player = players.get(0);
			if ((int)x < (int)player.x) xa += speed;
			if ((int)x > (int)player.x) xa -= speed;
			if ((int)y < (int)player.y) ya += speed;
			if ((int)y > (int)player.y) ya -= speed;
		}

	}

	public void update() {
		updateAI();
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
	}

}

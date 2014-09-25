package io.brace.lightsoutgaming.immortalSilence.entities;

import io.brace.lightsoutgaming.engine.graphics.Screen;
import io.brace.lightsoutgaming.immortalSilence.Weapon;

public class PistolBullet extends bullet {
	
	public static final int PistolSpeed = 10;
	
	@Override
	public void render(Screen s) {
		// TODO Auto-generated method stub
		s.renderSprite(x, y, Weapon.pistol_round.rotate(rot-90), true);
	}

}

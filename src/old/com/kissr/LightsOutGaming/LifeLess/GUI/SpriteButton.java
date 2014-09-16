package com.kissr.LightsOutGaming.LifeLess.GUI;

import java.awt.FontMetrics;

import com.kissr.LightsOutGaming.LifeLess.Game;
import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;
import com.kissr.LightsOutGaming.LifeLess.Graphics.Sprite;
import com.kissr.LightsOutGaming.LifeLess.input.Mouse;
import com.kissr.LightsOutGaming.LifeLess.utill.function;

public class SpriteButton extends Button{
	
	Sprite s;

	public SpriteButton(Sprite s, Game g, int x, int y, function f) {
		super("", g, x, y, f);
		this.s = s;
		width = s.width;
		height = s.height;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Screen screen, int x1, int y1) {
		// TODO Auto-generated method stub
		screen.renderSprite(x+x1, y+y1, s, false);
	}
	
	public void update(int x1, int y1) {
		// TODO Auto-generated method stuff
		float scalex = ((float)game.frame.getWidth() / (float)game.width);
		float scaley = ((float)game.frame.getHeight() / (float)game.height);
		if(Mouse.x > (x+x1)*scalex && Mouse.x < (x+width+x1)*scalex && Mouse.y > (y+y1)*scaley && Mouse.y < (y+width+y1)*scaley){
			if(Mouse.clicked){
				Mouse.clicked = false;
				f.run(this);
			}
		}
	}
	
	

}

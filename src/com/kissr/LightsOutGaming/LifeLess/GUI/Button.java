package com.kissr.LightsOutGaming.LifeLess.GUI;

import java.awt.FontMetrics;

import com.kissr.LightsOutGaming.LifeLess.Game;
import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;
import com.kissr.LightsOutGaming.LifeLess.input.Mouse;
import com.kissr.LightsOutGaming.LifeLess.utill.function;

public class Button extends MenuItem {
	
	boolean hover = false;
	int height;
	int width;
	int rawwidth, rawheight;
	Game game;
	public String text;
	function f;
	
	public Button(String text, Game g, int x, int y, function f){
		this.f = f;
		this.text = text;
		game = g;
		FontMetrics fm = game.getFontMetrics(game.getFont());
		rawheight = fm.getHeight();
		rawwidth = fm.stringWidth(text);
		height = (fm.getHeight()*game.scale);
		width = (fm.stringWidth(text)*game.scale);
		this.x = x;
		this.y = y;
	}

	@Override
	public void update(int x1, int y1) {
		// TODO Auto-generated method stub
		float scale = (game.frame.getWidth() / game.width);
		System.out.println(scale);
		float collx = (x+x1);
		float colly = (y+y1);
		float collwidth = rawwidth;
		float collheight = rawheight;
		if(!(Mouse.x/scale < collx || Mouse.x/scale >= collx+collwidth || Mouse.y/scale < colly || Mouse.y/scale >= colly+collheight)){
			hover = true;
			if(Mouse.clicked){
				Mouse.clicked = false;
				f.run(this);
			}
		}else{
			hover = false;
		}
	}

	@Override
	public void render(Screen screen, int x1, int y1) {
		// TODO Auto-generated method stub
		int col = 0xff000000;
		if(hover) col = 0xff505050;
		screen.renderString(x+x1, y+y1, text, true, col);
	}

}

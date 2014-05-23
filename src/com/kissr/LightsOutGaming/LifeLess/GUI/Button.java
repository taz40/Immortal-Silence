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
	public Game game;
	public String text;
	function f;
	
	public Button(String text, Game g, int x, int y, function f){
		super(x, y);
		this.f = f;
		this.text = text;
		game = g;
		FontMetrics fm = game.getFontMetrics(game.getFont());
		rawheight = fm.getHeight();
		rawwidth = fm.stringWidth(text);
		height = (fm.getHeight()*game.scale);
		width = (fm.stringWidth(text)*game.scale);
	}

	@Override
	public void update(int x1, int y1) {
		// TODO Auto-generated method stub
		FontMetrics fm = game.getFontMetrics(game.getFont());
		rawheight = fm.getHeight();
		rawwidth = fm.stringWidth(text);
		height = (fm.getHeight()*game.scale);
		width = (fm.stringWidth(text)*game.scale);
		float scalex = ((float)game.frame.getWidth() / (float)game.width);
		float scaley = ((float)game.frame.getHeight() / (float)game.height);
		float collx = (x+x1) * scalex;
		float colly = (y+y1) * scaley;
		float collwidth = (rawwidth) * scalex;
		float collheight = rawheight * scaley;
		if(!(Mouse.x < collx || Mouse.x >= collx+collwidth || Mouse.y < colly || Mouse.y >= colly+collheight)){
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
		int col = 0xffd0d0d0;
		if(hover) col = 0xff505050;
		screen.renderString(x+x1, y+y1, text, false, col);
	}

}

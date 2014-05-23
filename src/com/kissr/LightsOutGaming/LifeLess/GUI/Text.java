package com.kissr.LightsOutGaming.LifeLess.GUI;

import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;

public class Text extends MenuItem {
	
	public String text;
	public int color;
	
	public Text(String text, int x, int y, int col){
		super(x, y);
		this.text = text;
		color = col;
	}
	
	public Text(String text, int x, int y){
		this(text, x, y, 0xff000000);
	}

	@Override
	public void update(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Screen screen, int x, int y) {
		// TODO Auto-generated method stub
		screen.renderString(this.x+x, this.y+y, text, false, color);
	}

}

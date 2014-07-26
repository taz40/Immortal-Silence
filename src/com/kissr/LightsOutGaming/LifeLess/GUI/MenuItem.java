package com.kissr.LightsOutGaming.LifeLess.GUI;

import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;

public abstract class MenuItem {
	int x, y;
	
	public MenuItem(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public abstract void refresh();
	
	public abstract void update(int x, int y);
	public abstract void render(Screen screen, int x, int y);
}

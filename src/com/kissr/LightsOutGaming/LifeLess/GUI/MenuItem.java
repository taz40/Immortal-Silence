package com.kissr.LightsOutGaming.LifeLess.GUI;

import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;

public abstract class MenuItem {
	int x, y;
	
	public abstract void update(int x, int y);
	public abstract void render(Screen screen, int x, int y);
}

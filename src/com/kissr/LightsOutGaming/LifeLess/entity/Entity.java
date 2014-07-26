package com.kissr.LightsOutGaming.LifeLess.entity;

import java.util.ArrayList;

import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;
import com.kissr.LightsOutGaming.LifeLess.Graphics.Sprite;

public abstract class Entity {

	public int x, y;
	public Sprite sprite;
	public boolean defaultrender;
	public boolean active = true;
	public boolean networked = false;
	public int flip = 0;
	
	public Entity(int x, int y, Sprite sprite, boolean defaultrender){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.defaultrender = defaultrender;
	}
	
	public void update(){
		if(active && !networked){
			onUpdate();
		}
	}
	
	protected abstract void onUpdate();
	
	public void render(Screen screen){
		if(active){
			if(defaultrender){
				screen.renderMob(x, y, sprite, true, flip);
			}
			onDraw(screen);
		}
	}
	
	protected abstract void onDraw(Screen screen);
	
}

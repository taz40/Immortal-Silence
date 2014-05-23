package com.kissr.LightsOutGaming.LifeLess.entity;

import java.awt.event.KeyEvent;

import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;
import com.kissr.LightsOutGaming.LifeLess.Graphics.Sprite;
import com.kissr.LightsOutGaming.LifeLess.input.Keyboard;

public class Player extends Entity{
	
	private Keyboard key;

	public Player(int x, int y, Sprite sprite, Keyboard key) {
		super(x, y, sprite, true);
		this.key = key;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onUpdate() {
		// TODO Auto-generated method stub
		int mx = 0;
		int my = 0;
		flip = 0;
		if(key.getKey(KeyEvent.VK_W)){
			my--;
			sprite = Sprite.player_up;
		}
		if(key.getKey(KeyEvent.VK_S)){
			my++;
			sprite = Sprite.player_down;
		}
		if(key.getKey(KeyEvent.VK_A)){
			mx--;
			sprite = Sprite.player_right;
			flip = 1;
		}
		if(key.getKey(KeyEvent.VK_D)){
			mx++;
			sprite = Sprite.player_right;
		}
		x += mx;
		y += my;
	}

	@Override
	protected void onDraw(Screen screen) {
		// TODO Auto-generated method stub
		
	}

}

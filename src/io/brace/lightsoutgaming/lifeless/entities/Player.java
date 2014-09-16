package io.brace.lightsoutgaming.lifeless.entities;

import java.awt.event.KeyEvent;

import io.brace.lightsoutgaming.engine.Network.Networked;
import io.brace.lightsoutgaming.engine.graphics.Screen;
import io.brace.lightsoutgaming.engine.graphics.Sprite;
import io.brace.lightsoutgaming.engine.input.Keyboard;
import io.brace.lightsoutgaming.lifeless.Main;

public class Player extends Networked {
	
	Sprite s = Main.player_up;

	@Override
	public void update() {
		// TODO Auto-generated method stub
		int mx = 0, my = 0;
		if(Keyboard.getKey(KeyEvent.VK_W)){
			my--;
		}
		if(Keyboard.getKey(KeyEvent.VK_S)){
			my++;
		}
		if(Keyboard.getKey(KeyEvent.VK_A)){
			mx--;
		}
		if(Keyboard.getKey(KeyEvent.VK_D)){
			mx++;
		}
		
		if(my < 0){
			s = Main.player_up;
		}else if(my > 0){
			s = Main.player_down;
		}else if(mx < 0){
			s = Main.player_left;
		}else if(mx > 0){
			s = Main.player_right;
		}
		x += mx;
		y += my;
	}

	@Override
	public void render(Screen s) {
		// TODO Auto-generated method stub
		s.renderSprite(376, 276, this.s, false);
	}

	@Override
	public String[] send() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recv(String[] data) {
		// TODO Auto-generated method stub
		
	}

}

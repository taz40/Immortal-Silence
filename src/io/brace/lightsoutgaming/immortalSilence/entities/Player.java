package io.brace.lightsoutgaming.immortalSilence.entities;

import java.awt.event.KeyEvent;

import io.brace.lightsoutgaming.engine.Network.Networked;
import io.brace.lightsoutgaming.engine.graphics.Screen;
import io.brace.lightsoutgaming.engine.graphics.Sprite;
import io.brace.lightsoutgaming.engine.input.Keyboard;
import io.brace.lightsoutgaming.immortalSilence.Main;

public class Player extends Networked {
	
	Sprite s = Main.player_up;
	int dir = 0;
	float speed = 2;

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
			dir = 0;
		}else if(my > 0){
			dir = 1;
		}else if(mx < 0){
			dir = 2;
		}else if(mx > 0){
			dir = 3;
		}
		x += (mx)*speed;
		y += (my)*speed;
	}

	@Override
	public void render(Screen sc) {
		// TODO Auto-generated method stub
		if(dir == 0){
			s = Main.player_up;
		}else if(dir == 1){
			s = Main.player_down;
		}else if(dir == 2){
			s = Main.player_left;
		}else if(dir == 3){
			s = Main.player_right;
		}
		if(ismine){
			sc.renderSprite(376, 276, this.s, false);
		}else{
			sc.renderSprite(x, y, this.s, true);
		}
		
	}

	@Override
	public String[] send() {
		// TODO Auto-generated method stub
		String[] result = new String[3];
		result[0] = x+"";
		result[1] = y+"";
		result[2] = dir+"";
		return result;
	}

	@Override
	public void recv(String[] data) {
		// TODO Auto-generated method stub
		x = Integer.parseInt(data[0]);
		y = Integer.parseInt(data[1]);
		dir = Integer.parseInt(data[2]);
	}

}

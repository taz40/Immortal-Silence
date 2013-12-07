package taz40.lifeless.graphics.GUI;

import java.awt.Color;
import java.awt.Graphics;

import taz40.lifeless.input.Mouse;

public class Button {
	
	public String text;
	public int x, y, width, height;
	public Color color = Color.gray;
	
	public void update(){
		if(Mouse.getX() < x+width && Mouse.getX() > x){
			if(Mouse.getY() < y+height && Mouse.getY() > y){
				color = Color.LIGHT_GRAY;
			}
		}
	}
	
	public void render(Graphics g){
		g.setColor(color);
		g.drawString(text, x, y);
	}

}

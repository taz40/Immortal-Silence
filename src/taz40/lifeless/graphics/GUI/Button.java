package taz40.lifeless.graphics.GUI;

import java.awt.Color;
import java.awt.Graphics;

import taz40.lifeless.Util.Function;
import taz40.lifeless.input.Mouse;

public class Button {
	
	public String text;
	public int x, y, width, height, textOffsetx, textOffsety, scale;
	public Color color = Color.gray;
	public Function func;
	
	public Button(String text, int x, int y, int width, int height, int textOffsetx, int textOffsety, int scale, Function func){
		this.text = text;
		this.scale = scale;
		this.x = x*scale;
		this.y = y*scale;
		this.width = width*scale;
		this.height = height*scale;
		this.textOffsetx = textOffsetx*scale;
		this.textOffsety = (textOffsety*scale) + this.height;
		this.func = func;
	}
	
	public void update(){
		if(Mouse.getX() < x+width && Mouse.getX() > x){
			if(Mouse.getY() < y+height && Mouse.getY() > y){
				if(Mouse.getButton() == 1){
					Thread t = new Thread(func);
					t.start();
				}else{
					color = Color.LIGHT_GRAY;
				}
			}else{
				color = Color.gray;
			}
		}else{
			color = Color.gray;
		}
	}
	
	public void render(Graphics g){
		g.setColor(color);
		g.drawString(text, x+textOffsetx, y+textOffsety);
	}

}

package com.kissr.LightsOutGaming.LifeLess.Graphics;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kissr.LightsOutGaming.LifeLess.Game;
import com.kissr.LightsOutGaming.LifeLess.Level.Level;

public class Screen {

	public int[] pixels;
	public int width, height;
	public int clearcolor;
	public int xOffset, yOffset;
	Game game;
	
	public Screen(int width, int height, Game g){
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		game = g;
	}
	
	public void clear(){
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = clearcolor;
		}
	}
	
	public void renderSpriteSheet(int xp, int yp, SpriteSheet sheet, boolean fixed){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for(int y = 0; y < sheet.height; y++){
			for(int x = 0; x < sheet.width; x++){
				int xa = x+xp;
				int ya = y+yp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col =  sheet.pixels[x+y*sheet.width];
				if(col != 0xffff00ff) pixels[xa+ya*width] = col;
			}
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for(int y = 0; y < sprite.height; y++){
			int ys = y;
			for(int x = 0; x < sprite.width; x++){
				int xa = x+xp;
				int ya = y+yp;
				int xs = x;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x+y*sprite.width];
				if(col != 0xffff00ff) pixels[xa+ya*width] = col;
			}
		}
	}
	
	public void renderLevel(int xp, int yp, Level level, boolean fixed){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		int px = 0;
		for(int y = 0; y < level.height; y++){
			for(int x = 0; x < level.width; x++){
				int xa = x+xp;
				int ya = y+yp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = level.pixels[x+y*level.width];
				if(col != 0xffff00ff){
					pixels[xa+ya*width] = col;
					px++;
				}
			}
		}
	}
	
	public void renderMob(int xp, int yp, Sprite sprite, boolean fixed, int flip){
			xp -= xOffset;
			yp -= yOffset;
		
		for(int y = 0; y < sprite.height; y++){
			int ys = y;
			if(flip == 2 || flip == 3) ys = 31 - y;
			for(int x = 0; x < sprite.width; x++){
				int xa = x+xp;
				int ya = y+yp;
				int xs = x;
				if(flip == 1 || flip == 3) xs = 31 - x;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[xs+ys*sprite.width];
				if(col != 0xffff00ff) pixels[xa+ya*width] = col;
			}
		}
	}
	
	public void renderString(int xp, int yp, String text, boolean fixed, int col1){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		
		FontMetrics fm = game.getFontMetrics(game.getFont());
		int stringheight = fm.getHeight();
		int stringwidth = fm.stringWidth(text);
		BufferedImage image = new BufferedImage(stringwidth, stringheight, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		if(col1 == 0xff000000) col1 = 0xff010101;
		g.setColor(new Color(col1));
		g.drawString(text, 0, stringheight-5);
		g.dispose();
		
		int[] textpixels = new int[stringheight*stringwidth];
		image.getRGB(0, 0, stringwidth, stringheight, textpixels, 0, stringwidth);
		
		for(int y = 0; y < stringheight; y++){
			for(int x = 0; x < stringwidth; x++){
				int xa = x+xp;
				int ya = y+yp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = textpixels[x+y*stringwidth];
				if(col == 0xff000000) col = 0xffff00ff;
				if(col != 0xffff00ff) pixels[xa+ya*width] = col;
			}
		}
		
	}
	
	public void renderRect(int xp, int yp, int w, int h, int col, boolean fixed){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				int xa = x+xp;
				int ya = y+yp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				if(col != 0xffff00ff) pixels[xa+ya*width] = col;
			}
		}
	}
	
}

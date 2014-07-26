package com.kissr.LightsOutGaming.LifeLess.Graphics;

import java.awt.image.BufferedImage;

public class Sprite {

	public int width, height;
	public int[] pixels;
	public SpriteSheet sheet;
	
	public static Sprite clear = new Sprite(16, 16, 0xffff00ff);
	public static Sprite grass = new Sprite(0, 0, 16, 16, SpriteSheet.LevelSprites);
	public static Sprite floor = new Sprite(1, 1, 16, 16, SpriteSheet.LevelSprites);
	public static Sprite hedge = new Sprite(1, 0, 16, 16, SpriteSheet.LevelSprites);
	public static Sprite player_red = new Sprite(1, 2, 16, 16, SpriteSheet.MainSheet);
	public static Sprite player_blue = new Sprite(6, 1, 16, 16, SpriteSheet.MainSheet);
	public static Sprite player_green = new Sprite(9, 2, 16, 16, SpriteSheet.MainSheet);
	public static Sprite zombie_1_idle = new Sprite(0, 1, 16, 16, SpriteSheet.MainSheet);
	public static Sprite zombie_1_attack = new Sprite(0, 0, 16, 16, SpriteSheet.MainSheet);
	
	public Sprite(int x, int y, int width, int height, SpriteSheet sheet){
		this.width = width;
		this.height = height;
		int xoff = x * width;
		int yoff = y * height;
		pixels = new int[width*height];
		for(int xp = 0; xp < width; xp++){
			for(int yp = 0; yp < height; yp++){
				int xa = xp + xoff;
				int ya = yp + yoff;
				pixels[xp+yp*width] = sheet.pixels[xa+ya*sheet.width];
			}
		}
	}
	
	public Sprite(int width, int height, int color){
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = color;
		}
	}

	public Sprite(BufferedImage image) {
		// TODO Auto-generated constructor stub
		this.width = image.getWidth();
		this.height = image.getHeight();
		pixels = new int[width*height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	}
	
}

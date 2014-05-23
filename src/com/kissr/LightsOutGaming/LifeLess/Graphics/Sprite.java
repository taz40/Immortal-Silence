package com.kissr.LightsOutGaming.LifeLess.Graphics;

public class Sprite {

	public int width, height;
	public int[] pixels;
	public SpriteSheet sheet;
	
	public static Sprite grass = new Sprite(0, 0, 16, 16, SpriteSheet.LevelSprites);
	public static Sprite player_down = new Sprite(0, 0, 32, 32, SpriteSheet.player1);
	public static Sprite player_right = new Sprite(1, 0, 32, 32, SpriteSheet.player1);
	public static Sprite player_up = new Sprite(2, 0, 32, 32, SpriteSheet.player1);
	
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
	
}

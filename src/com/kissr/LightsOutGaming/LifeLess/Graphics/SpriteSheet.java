package com.kissr.LightsOutGaming.LifeLess.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public int[] pixels;
	public String path;
	int width, height;
	
	public static SpriteSheet LevelSprites = new SpriteSheet("/Textures/spawn_level.png");
	public static SpriteSheet player1 = new SpriteSheet("/Textures/player_sheet.png");
	
	public SpriteSheet(String path){
		this.path = path;
		load();
	}
	
	public void load(){
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width*height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

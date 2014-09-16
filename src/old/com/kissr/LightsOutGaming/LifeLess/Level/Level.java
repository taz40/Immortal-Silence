package com.kissr.LightsOutGaming.LifeLess.Level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;
import com.kissr.LightsOutGaming.LifeLess.Graphics.Sprite;

public class Level {

	public int[] pixels;
	public int width;
	public int height;
	public String path;
	
	public static Level l1 = new Level("/Levels/Spawn_Level.png");
	
	public Level(String path){
		this.path = path;
		load();
	}
	
	private void load(){
		try {
			BufferedImage levelmap = ImageIO.read(Level.class.getResource(path));
			int[] levelmappixels = new int[levelmap.getWidth()*levelmap.getHeight()];
			levelmap.getRGB(0, 0, levelmap.getWidth(), levelmap.getHeight(), levelmappixels, 0, levelmap.getWidth());
			width = levelmap.getWidth() * 16;
			height = levelmap.getHeight() * 16;
			pixels = new int[width*height];
			for(int x = 0; x < levelmap.getWidth(); x++){
				for(int y = 0; y < levelmap.getHeight(); y++){
					Sprite sprite = Sprite.clear;
					int pixel = levelmappixels[x+y*levelmap.getWidth()];
					if(pixel == 0xff00ff00) sprite = Sprite.grass;
					if(pixel == 0xff808080) sprite = Sprite.hedge;
					if(pixel == 0xffAF541F) sprite = Sprite.floor;
					
					for(int x1 = 0; x1 < sprite.width; x1++){
						for(int y1 = 0; y1 < sprite.height; y1++){
							pixels[((x*16)+x1)+((y*16)+y1)*width] = sprite.pixels[x1+y1*sprite.width];
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(){
		
	}
	
	public void render(Screen screen){
		screen.renderLevel(0, 0, this, true);
	}
	
}

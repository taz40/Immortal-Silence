package taz40.lifeless.level;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import taz40.lifeless.entity.mob.Chaser;
import taz40.lifeless.entity.mob.Dummy;

public class SpawnLevel extends Level {
	
	public SpawnLevel(String path) {
		super(path);
		
		// TODO Auto-generated constructor stub
	}
	

	protected void loadLevel(String path){
		try{
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height =  image.getHeight();
			tiles = new int[w*h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Could not load level file!");
		}
		TileCoordinate dummyspawn = new TileCoordinate(20, 53);
			add(new Chaser(dummyspawn.x(), dummyspawn.y()));
	}
	

	protected void generateLevel() {
	}
	
	
	
}

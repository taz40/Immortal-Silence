package lightsoutgaming.games.lifeless.onejar.main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import taz40.lightsoutgamingengine.V1.Game;

public class Map {

	int zombiesatonetime;
	double zobiechance;
	int width, height;
	int background;
	
	public Map(String path, Game game){
		File file = new File(path+"\\info.txt");
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(fr);
		
		try {
			zombiesatonetime = Integer.parseInt(br.readLine());
			zobiechance = Double.parseDouble(br.readLine());
			width = Integer.parseInt(br.readLine());
			height = Integer.parseInt(br.readLine());
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(zombiesatonetime == -1 || zobiechance == -1 || width == -1 || height == -1){
			System.err.println("map null");
		}
		System.out.println("widht: "+width);
		System.out.println("height: "+height);
		background = game.texturerenderer.LoadTexture(path+"\\bg.png");
		
	}
	
}

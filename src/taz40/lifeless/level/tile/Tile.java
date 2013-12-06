package taz40.lifeless.level.tile;

import taz40.lifeless.graphics.Screen;
import taz40.lifeless.graphics.Sprite;
import taz40.lifeless.level.tile.SpawnLevel.SpawnFloorTile;
import taz40.lifeless.level.tile.SpawnLevel.SpawnGrassTile;
import taz40.lifeless.level.tile.SpawnLevel.SpawnHedgeTile;
import taz40.lifeless.level.tile.SpawnLevel.SpawnWallTile;
import taz40.lifeless.level.tile.SpawnLevel.SpawnWaterTile;

public class Tile {

	public int x, y;
	public Sprite sprite;
	
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	
	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_hedge = new SpawnHedgeTile(Sprite.spawn_hedge);
	public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_wall1 = new SpawnWallTile(Sprite.spawn_wall1);
	public static Tile spawn_wall2 = new SpawnWallTile(Sprite.spawn_wall2);
	public static Tile spawn_floor = new SpawnFloorTile(Sprite.spawn_floor);
	
	public static Tile voidTile = new voidTile(Sprite.voidSprite);
	
	public static final int col_spawn_grass = 0xFF1DFF00;
	public static final int col_spawn_hedge = 0; //unused
	public static final int col_spawn_water = 0; //unused
	public static final int col_spawn_wall1 = 0xFF808080;
	public static final int col_spawn_wall2 = 0xFF404040;
	public static final int col_spawn_floor = 0xFFAF541F;
	
	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen){
	}
	
	public boolean solid(){
		return false;
	}
	
}

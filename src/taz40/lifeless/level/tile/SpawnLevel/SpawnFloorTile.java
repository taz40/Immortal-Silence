package taz40.lifeless.level.tile.SpawnLevel;

import taz40.lifeless.graphics.Screen;
import taz40.lifeless.graphics.Sprite;
import taz40.lifeless.level.tile.Tile;

public class SpawnFloorTile extends Tile {

	public SpawnFloorTile(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void render(int x, int y, Screen screen){
		screen.renderTile(x << 4, y << 4, this);
	}

}

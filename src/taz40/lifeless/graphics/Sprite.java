package taz40.lifeless.graphics;

public class Sprite {

	public final int SIZE;
	public int width, height;
	private int x, y;
	public int[] pixels;
	protected SpriteSheet sheet;
	
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 0, 1, SpriteSheet.tiles);
	
	//Spawn Level Sprites
	
	public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_hedge = new Sprite(16, 1, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_water = new Sprite(16, 2, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_wall1 = new Sprite(16, 0, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_wall2 = new Sprite(16, 0, 2, SpriteSheet.spawn_level);
	public static Sprite spawn_floor = new Sprite(16, 1, 1, SpriteSheet.spawn_level);
	
	public static Sprite voidSprite = new Sprite(16, 0x7FFFF2);
		
	//Mob Sprites
	public static Sprite player_forward = new Sprite(32, 2, 0, SpriteSheet.player);
	
	//Projectile Sprites
	public static Sprite projectile_wizard = new Sprite(16, 0, 0, SpriteSheet.projectile_wizard);
	
	// Particles
	public static Sprite particle_normal = new Sprite(3, 0xAAAAAA);
	
	protected Sprite(SpriteSheet sheet, int width, int height){
		if(width == height) SIZE = width;
		else SIZE = -1;
		this.sheet = sheet;
		this.width = width;
		this.height = height;
	}
	
	public Sprite(int width, int height, int color){
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		setColor(color);
	}
	
	public Sprite(int size, int x, int y, SpriteSheet sheet){
		this.SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[size*size];
		this.x = x*size;
		this.y = y*size;
		this.sheet = sheet;
		load();
	}
	
	private void setColor(int color){
		for(int i = 0; i < width*height; i++){
			pixels[i] = color;
		}
	}
	
	public Sprite(int size, int color){
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[size*size];
		setColor(color);
	}
	
	public Sprite(int[] pixels, int width, int height) {
		if(width == height) SIZE = width;
		else SIZE = -1;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	private void load(){
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				pixels[x+y*SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
	
}

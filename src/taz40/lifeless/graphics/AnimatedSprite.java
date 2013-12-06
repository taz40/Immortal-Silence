package taz40.lifeless.graphics;

public class AnimatedSprite extends Sprite{

	private int frame = 0;
	private Sprite sprite;
	private int rate = 5;
	private int length = -1;
	private int time = 0;
	
	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length, int rate){
		super(sheet, width, height);
		this.length = length;
		sprite = sheet.getSprites()[0];
		if(length > sheet.getSprites().length) System.err.println("error! Lenght of anim is too long");
		this.rate = rate;
	}
	
	public void update(){
		time++;
		if(time % rate == 0){
			if(frame >= length - 1) frame = 0;
			else frame++;
			sprite = sheet.getSprites()[frame];
		}
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public void setFrameRate(int frames){
		rate = frames;
	}
	
	public void setFrame(int index){
		if(index > sheet.getSprites().length - 1){
			System.err.println("Error! index too high!");
			return;
		}
		frame = index;
		sprite = sheet.getSprites()[frame];
	}
	
}

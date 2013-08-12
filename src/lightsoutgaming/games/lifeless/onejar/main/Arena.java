package lightsoutgaming.games.lifeless.onejar.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import taz40.lightsoutgamingengine.V1.Entity;
import taz40.lightsoutgamingengine.V1.EntityLiving;
import taz40.lightsoutgamingengine.V1.Screen;
import taz40.lightsoutgamingengine.V1.ScreenFactory;

public class Arena extends Screen {

	public int numofzombies = 0;
	public int maxzombies = 5;
	public Camera camera = new Camera(this, 0, 0, screenfactory.getGame().getWindow().getWidth(), screenfactory.getGame().getWindow().getHeight(), 0);
	public int fps;
	Timer t = new Timer();
	public boolean istimergoing = false;
	public int frames;
	public boolean debug = false;
	public boolean pause = false;
	public Player player = new Player(this, PlayerT.Normal);
	
	public Arena(ScreenFactory screenfactory) {
		super(screenfactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCustomCreate() {
		// TODO Auto-generated method stub
		this.addEntity(player);
	}
	
	double distance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	public boolean isPlayerInRange(Player p, Zombie z){
		//sight
		if((p.sight + z.type.sightrange) < distance(p.x, p.y, z.x, z.y)){
			return true;
		//sound
		}else if((p.sound + z.type.hearingrange) < distance(p.x, p.y, z.x, z.y)){
			return true;
		//smell
		}else if((p.smell + z.type.smellrange) < distance(p.x, p.y, z.x, z.y)){
			return true;
		}else{
			return false;
		}
	}
	
	public Player getPlayerInRange(Zombie z){
		for(int i = 0; i < this.entities.size(); i++){
			if(entities.get(i) instanceof Player){
				if(isPlayerInRange((Player)entities.get(i), z)){
					return (Player) entities.get(i);
				}
			}
		}
		return null;
	}

	@Override
	public void onCustomDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, screenfactory.getGame().getWindow().getWidth(), screenfactory.getGame().getWindow().getHeight());
		g.setColor(Color.blue);
		if(debug){
			g.drawString("FPS: "+fps, 5, 200);
		}
	}

	@Override
	public void onCustomUpdate() {
		// TODO Auto-generated method stub\
		if(!istimergoing){
			t.schedule(new FPSTimer(this), 1000);
			istimergoing = true;
		}
		frames++;
		if(player != null){
		camera.x = player.x;
		camera.y = player.y;
		}else{
			System.out.println("player is null");
		}
		
		if(!(numofzombies >= maxzombies)){
		Random rand = new Random();
		int random = rand.nextInt(101)+1;
		if(random <= 1){
			int x = rand.nextInt(390-30);
			int y = rand.nextInt(230-30);
			Entity e = new Zombie(this, x, y, rand.nextInt(360), ZType.normal);
			while(detectCollision(x, y, 30, 30, e)){
				x = rand.nextInt(390-30);
				y = rand.nextInt(230-30);
				e = new Zombie(this, x, y, rand.nextInt(360), ZType.normal);
			}
			this.addEntity(e);
			
			this.numofzombies++;
			System.out.println("New Zombie");
			System.out.println("	X: " + x);
			System.out.println("	Y: " + y);
			System.out.println("Zombies: "+numofzombies);
		}
		}
		if(getScreenFactory().getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_ESCAPE)){
			getScreenFactory().showScreen(new MainMenu(getScreenFactory()));
		}
		if(getScreenFactory().getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_F3)){
			debug = !debug;
			getScreenFactory().getGame().getKeyboardListener().unpresskey(KeyEvent.VK_F3);
		}
		if(getScreenFactory().getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_SPACE)){
			pause = !pause;
			getScreenFactory().getGame().getKeyboardListener().unpresskey(KeyEvent.VK_SPACE);
		}
		
		
		
	}

	@Override
	public boolean detectCollision(float x, float y, int width, int height,
			Entity e) {
		// TODO Auto-generated method stub
		for(int i = 0; i < entities.size(); i++){
			if(e != entities.get(i)){
				if(entities.get(i) instanceof EntityLiving){
					EntityLiving ent = (EntityLiving) entities.get(i);
					if((x-camera.x)+width >= (ent.x)-camera.x && x-camera.x <= (ent.x-camera.x)+ent.width){
						if((y-camera.y)+height >= (ent.y)-camera.y && (y)-camera.y <= (ent.y-camera.y)+ent.height){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	

	
	
}

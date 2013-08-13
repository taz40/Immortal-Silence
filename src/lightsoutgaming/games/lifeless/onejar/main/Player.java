package lightsoutgaming.games.lifeless.onejar.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.SwingUtilities;

import taz40.lightsoutgamingengine.V1.EntityLiving;
import taz40.lightsoutgamingengine.V1.Screen;
import taz40.lightsoutgamingengine.V1.TextureRenderer;

public class Player extends EntityLiving {

	public Player(Screen screen, PlayerT type) {
		super(screen, 0, 0, 30, 30, 0);
		// TODO Auto-generated constructor stub
		this.type = type;
		sight = type.sightB;
		sound = type.soundB;
		smell = type.smellB;
	}
	


	
	int CamX = 0;
	int CamY = 0;
	Point image = new Point();
	boolean paused = false;
	Point mouse = new Point();
	Weapon CurWep = Weapon.Pistol;
	PlayerT type;
	int sight;
	int sound;
	int smell;

	@Override
	public void onCustomCreate() {
		// TODO Auto-generated method stub
		
	}
	
	public void drawCircle(Graphics2D cg, int xCenter, int yCenter, int r) {

		cg.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
	}


	@Override
	public void onCustomDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		if(CurWep == Weapon.Pistol){
			TextureRenderer.DrawSprite(Textures.redplayerPistol, Math.round(150), Math.round(100), 2, rotation, g);
		}else{
			TextureRenderer.DrawSprite(Textures.redplayeridle, Math.round(0), Math.round(0), 2, rotation, g);
		}
		if(((Arena) screen).debug){
			// sight
			g.setColor(Color.red);
			int xCent = (int) ((((150*2))+(width)));
			int yCent = (int) ((((100*2))+(height)));
			drawCircle(g, xCent, yCent, sight);
			// sound
			g.setColor(Color.cyan);
			drawCircle(g, xCent, yCent, sound);
			
			// smell
			g.setColor(Color.blue);
			drawCircle(g, xCent, yCent, smell);
		}
	}

	@Override
	public void onCustomUpdate() {
		// TODO Auto-generated method stub
		CamX = (int) (x-150);
		CamY = (int) (y-100);
		if(screen.getScreenFactory().getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_W)){
			float px = (float) ((1 * Math.cos((rotation-90) * Math.PI / 180.0))+(x));
			float py = (float) ((1 * Math.sin((rotation-90) * Math.PI / 180.0))+(y));
			
			if(!((Arena) screen).detectCollision(px, py, width, height, this)){
				x = (px);
				y = (py);
			}
			
		}
		if(screen.getScreenFactory().getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_S)){
			float px = (x+150)-(float) ((1 * Math.cos((rotation-90) * Math.PI / 180.0)));
			float py = (y+100)-(float) ((1 * Math.sin((rotation-90) * Math.PI / 180.0)));
			if(!((Arena) screen).detectCollision(px, py, width, height, this)){
				x = (px-150);
				y = (py-100);
			}
		}
		if(screen.getScreenFactory().getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_A)){
			rotation -= 2;
		}
		if(screen.getScreenFactory().getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_D)){
			rotation += 2;
		}
		if(screen.getScreenFactory().getGame().getMousePadListener().isMousePressed()){
			if(CurWep == Weapon.Pistol){
				screen.addEntity(new Shot(screen, x+8, y+8, rotation, Textures.pistolShot, this));
				screen.getScreenFactory().getGame().getMousePadListener().clickDone();
			}
		}
		if(screen.getScreenFactory().getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_J)){
			System.out.println("Player:");
			System.out.println("	X: " + x);
			System.out.println("	Y: " + y);
		}
		/*mouse.x = screen.getScreenFactory().getGame().getMousePadListener().getX();
		mouse.y = screen.getScreenFactory().getGame().getMousePadListener().getY();
		float xDistance = mouse.x - x;
		float yDistance = mouse.y - y;
		rotation = Math.toDegrees(Math.atan2(yDistance, xDistance))+90;*/
		
		
		
	}

	@Override
	public void OnCollide(EntityLiving arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCustomDestroy() {
		// TODO Auto-generated method stub
		
	}

}

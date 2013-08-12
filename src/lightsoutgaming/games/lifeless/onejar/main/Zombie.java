package lightsoutgaming.games.lifeless.onejar.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import taz40.lightsoutgamingengine.V1.EntityLiving;
import taz40.lightsoutgamingengine.V1.Screen;
import taz40.lightsoutgamingengine.V1.TextureRenderer;

public class Zombie extends EntityLiving {
	
	public Zombie(Screen screen, float X, float Y, double rot, ZType type) {
		super(screen, X, Y, 30, 30, rot);
		// TODO Auto-generated constructor stub
		this.type = type;
	}
	
	ZType type;
	
	Player targetPlayer = null;
	Point targetPos = new Point();
	ArrayList<Command> commandlist = new ArrayList<Command>();
	Arena arena = (Arena) screen;

	@Override
	public void onCustomCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCustomDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		EntityLiving camera = ((Arena)screen).camera;
		TextureRenderer.DrawSprite(Textures.zombie1, (int)(x-camera.x), (int)(y-camera.y), 2, rotation, g);
		if(((Arena) screen).debug){
			// sight
			g.setColor(Color.red);
			int xCent = (int) ((((x*2))+(width))-(camera.x*2));
			int yCent = (int) ((((y*2))+(height))-(2*camera.y));
			int xCent2 = (int) ((((targetPos.x*2))+(width))-(camera.x*2));
			int yCent2 = (int) ((((targetPos.y*2))+(height))-(2*camera.y));
			drawCircle(g, xCent, yCent, type.sightrange);
			// hearingrange
			g.setColor(Color.cyan);
			drawCircle(g, xCent, yCent, type.hearingrange);
			
			// smell
			g.setColor(Color.blue);
			drawCircle(g, xCent, yCent, type.smellrange);
			
			//targetLine
			if(targetPos != null){
				g.drawLine(xCent, yCent, xCent2, yCent2);
			}
		}
	}
	
	public void drawCircle(Graphics2D cg, int xCenter, int yCenter, int r) {

	cg.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);

	}
	
	public void routeToPoint(Point p){
		this.commandlist = new ArrayList<Command>();
		float xDistance = p.x - this.x;
	      float yDistance = p.y - this.y;
	      int targetrot = (int)(Math.toDegrees(Math.atan2(yDistance, xDistance)) + 90.0D);
	      double rotdiff = this.rotation - targetrot;
	      int numofrotationframes = (int)Math.abs(rotdiff / 2.0D);
	      for (int i = 0; i < numofrotationframes; i++) {
	        if (rotdiff < 0.0D)
	          this.commandlist.add(Command.rotatecounterclock);
	        else {
	          this.commandlist.add(Command.rotateclock);
	        }
	      }
	      int dist = (int)Math.sqrt((this.x - p.x) * (this.x - p.x) + (this.y - p.y) * (this.y - p.y));
	      double distperframe = Math.sqrt((0.0D - 1.0D * Math.cos((this.rotation - 90.0D) * 3.141592653589793D / 180.0D)) * (0.0D - 1.0D * Math.cos((this.rotation - 90.0D) * 3.141592653589793D / 180.0D)) + (0.0D - 1.0D * Math.sin((this.rotation - 90.0D) * 3.141592653589793D / 180.0D)) * (0.0D - 1.0D * Math.sin((this.rotation - 90.0D) * 3.141592653589793D / 180.0D)));
	      int numofmovementframes = (int)Math.abs(dist / distperframe);
	      for (int i = 0; i < numofmovementframes; i++){
	        this.commandlist.add(Command.moveforward);
	      }
	      return;
	      
	}

	@Override
	public void onCustomUpdate() {
		// TODO Auto-generated method stub
		if(!((Arena)screen).pause){
			if(rotation > 360){
				rotation = 0;
			}
			if(rotation < 0){
				rotation = 360;
			}
			
			if(targetPlayer != null){
				if(arena.isPlayerInRange(targetPlayer, this)){
					targetPos.x = (int) targetPlayer.x;
					targetPos.y = (int) targetPlayer.y;
					routeToPoint(targetPos);
				}else{
					targetPlayer = null;
					Player p = arena.getPlayerInRange(this);
					if(p != null){
						targetPlayer = p;
						targetPos.x = (int) targetPlayer.x;
						targetPos.y = (int) targetPlayer.y;
						routeToPoint(targetPos);
					}else{
						if(commandlist.size() <= 0){
							Random rand = new Random();
							targetPos.x = rand.nextInt((int) (x+60))-30;
							targetPos.y = rand.nextInt((int) (y+60))-30;
							routeToPoint(targetPos);
						}
					}
				}
			}else{
				Player p = arena.getPlayerInRange(this);
				if(p != null){
					targetPlayer = p;
					targetPos.x = (int) targetPlayer.x;
					targetPos.y = (int) targetPlayer.y;
					routeToPoint(targetPos);
				}else{
					if(commandlist.size() <= 0){
						Random rand = new Random();
						targetPos.x = (int) ((rand.nextInt((int) (60))-30)+x);
						targetPos.y = (int) ((rand.nextInt((int) (60))-30)+y);
						routeToPoint(targetPos);
					}
				}
			}
			
		}
		
		if(!arena.pause){
		 Command currCommand = (Command)this.commandlist.get(0);
	      this.commandlist.remove(0);
	      if (currCommand == Command.rotateclock) {
	        this.rotation += 2.0D;
	      } else if (currCommand == Command.rotatecounterclock) {
	        this.rotation -= 2.0D;
	      } else if (currCommand == Command.moveforward) {
	        float px = (float)(1.0D * Math.cos((this.rotation - 90.0D) * 3.141592653589793D / 180.0D) + this.x);
	        float py = (float)(1.0D * Math.sin((this.rotation - 90.0D) * 3.141592653589793D / 180.0D) + this.y);

	        if (!((Arena)this.screen).detectCollision(px, py, this.width, this.height, this)) {
	          this.x = px;
	          this.y = py;
	        }
	      }
		}
		
		EntityLiving camera = ((Arena)screen).camera;
		int lx = (int) (x-camera.x);
		int ly = (int) (y-camera.y);
		if(lx < 0 || lx > screen.getScreenFactory().getGame().getWindow().getWidth()){
			this.visible = false;
		}else{
			if(ly < 0 || ly > screen.getScreenFactory().getGame().getWindow().getHeight()){
				this.visible = false;
			}else{
				this.visible = true;
			}
		}
		
		
		
	}

	@Override
	public void OnCollide(EntityLiving e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void kill(){
		super.kill();
		((Arena) screen).numofzombies--;
		System.out.println("Zombie Killed");
	}

}

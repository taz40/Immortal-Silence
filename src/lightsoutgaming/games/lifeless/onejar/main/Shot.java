package lightsoutgaming.games.lifeless.onejar.main;

import java.awt.Graphics2D;

import taz40.lightsoutgamingengine.V1.EntityLiving;
import taz40.lightsoutgamingengine.V1.Screen;
import taz40.lightsoutgamingengine.V1.TextureRenderer;

public class Shot extends EntityLiving {

	int texture;
	EntityLiving owner;
	
	public Shot(Screen screen, float x, float y, double rot, int Texture, EntityLiving e) {
		super(screen, x, y, 2, 1, rot);
		// TODO Auto-generated constructor stub
		texture = Texture;
		owner = e;
	}

	@Override
	public void onCustomCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCustomDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		EntityLiving camera = ((Arena)screen).camera;
		TextureRenderer.DrawSprite(texture, (int)(x-camera.x), (int)(y-camera.y), 2, rotation, g);
		
	}

	@Override
	public void onCustomUpdate() {
		// TODO Auto-generated method stub
		x += (5 * Math.cos((rotation-90) * Math.PI / 180.0));
		y += (5 * Math.sin((rotation-90) * Math.PI / 180.0));
		EntityLiving camera = ((Arena)screen).camera;
		int lx = (int) (x-camera.x);
		int ly = (int) (y-camera.y);
		if(lx < 0 || lx > screen.getScreenFactory().getGame().getWindow().getWidth()){
			this.kill();
		}else{
			if(ly < 0 || ly > screen.getScreenFactory().getGame().getWindow().getHeight()){
				this.kill();
			}
		}
	}

	@Override
	public void OnCollide(EntityLiving e) {
		// TODO Auto-generated method stub
		if(e != owner){
			e.kill();
			this.kill();
			System.out.println("Shot Collision");
		}
	}

	@Override
	public void onCustomDestroy() {
		// TODO Auto-generated method stub
		
	}
	
}

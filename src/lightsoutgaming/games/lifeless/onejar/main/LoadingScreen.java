package lightsoutgaming.games.lifeless.onejar.main;

import java.awt.Graphics2D;

import lightsoutgaming.lifeless.textures.textureclass;

import taz40.lightsoutgamingengine.V1.Screen;
import taz40.lightsoutgamingengine.V1.ScreenFactory;
import taz40.lightsoutgamingengine.V1.TextureRenderer;

public class LoadingScreen extends Screen {
	
	private boolean isDrawn = false;
	private TextureRenderer texturerenderer = this.screenfactory.getGame().texturerenderer;

	public LoadingScreen(ScreenFactory screenfactory) {
		super(screenfactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCustomCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCustomDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		if(!isDrawn){
			g.scale(5, 5);
			g.drawString("Loading...", 380, 290);
			isDrawn = true;
		}
	}

	@Override
	public void onCustomUpdate() {
		// TODO Auto-generated method stub
		if(isDrawn){
			onLoad();
			this.screenfactory.showScreen(new MainMenu(this.screenfactory.getGame().getScreenFactory()));
		}
	}
	
	public void onLoad(){
		Textures.buttonpressed = texturerenderer.LoadTexture("Gui/Button_Pressed.png", textureclass.class);
		Textures.buttonunpressed = texturerenderer.LoadTexture("Gui/Button.png", textureclass.class);
		Textures.spritesheet = texturerenderer.LoadSpritesheet("arena/LifelessSpriteSheet.png", textureclass.class);
		Textures.zombie1 = texturerenderer.LoadSprite(Textures.spritesheet, 20, 0, 15, 15);
		Textures.redplayeridle = texturerenderer.LoadSprite(Textures.spritesheet, 115, 0, 15, 15);
		Textures.redplayerPistol = texturerenderer.LoadSprite(Textures.spritesheet, 115, 16, 15, 15);
		Textures.pistolShot = texturerenderer.LoadSprite(Textures.spritesheet, 188, 39, 2, 2);
	}

	@Override
	public void onCustomDestroy() {
		// TODO Auto-generated method stub
		
	}

	
	
}

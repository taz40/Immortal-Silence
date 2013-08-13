package lightsoutgaming.games.lifeless.onejar.main;

import java.awt.Graphics2D;

import taz40.lightsoutgamingengine.V1.Button;
import taz40.lightsoutgamingengine.V1.Entity;
import taz40.lightsoutgamingengine.V1.Function;
import taz40.lightsoutgamingengine.V1.Screen;
import taz40.lightsoutgamingengine.V1.ScreenFactory;

public class OptionsScreen extends Screen {

	public OptionsScreen(ScreenFactory screenfactory) {
		super(screenfactory);
		// TODO Auto-generated constructor stub
	}
	
	static boolean fullscreen = false;
	static String fst = "Off";
	
	Button FullScreenButton = Button.createXCenteredButton("Full Screen: " + fst , 160, 250, 50, 3, Textures.buttonpressed, Textures.buttonunpressed, new Function(this){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(fullscreen){
				((OptionsScreen) s).FullScreenButton.setTitle("Full Screen: Off");
				fullscreen = false;
				fst = "Off";
				screenfactory.getGame().setFullScreen(fullscreen);
			}else{
				((OptionsScreen) s).FullScreenButton.setTitle("Full Screen: On");
				fullscreen = true;
				fst = "On";
				screenfactory.getGame().setFullScreen(fullscreen);
			}
		}}, this);

	@Override
	public void onCustomCreate() {
		// TODO Auto-generated method stub
		this.addEntity(FullScreenButton);
		this.addEntity(Button.createXCenteredButton("ChangeLog", 100, 200, 50, 3, Textures.buttonpressed, Textures.buttonunpressed, new Function(this){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				screenfactory.showScreen(new ChangeLogScreen(screenfactory));
			}}, this));
		
		this.addEntity(Button.createXCenteredButton("Back", 400, 100, 50, 3, Textures.buttonpressed, Textures.buttonunpressed, new Function(this){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				screenfactory.showScreen(new MainMenu(screenfactory));
			}}, this));
		
	}

	@Override
	public void onCustomDraw(Graphics2D arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCustomUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCustomDestroy() {
		// TODO Auto-generated method stub
		
	}

}

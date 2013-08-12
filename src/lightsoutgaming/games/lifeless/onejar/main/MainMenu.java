package lightsoutgaming.games.lifeless.onejar.main;

import java.awt.Graphics2D;

import taz40.lightsoutgamingengine.V1.Button;
import taz40.lightsoutgamingengine.V1.Entity;
import taz40.lightsoutgamingengine.V1.Function;
import taz40.lightsoutgamingengine.V1.Screen;
import taz40.lightsoutgamingengine.V1.ScreenFactory;

public class MainMenu extends Screen {

	public MainMenu(ScreenFactory screenfactory) {
		super(screenfactory);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void onCustomCreate() {
		// TODO Auto-generated method stub
		this.addEntity(Button.createXCenteredButton("Start Game", 100, 200, 50, 3, Textures.buttonpressed, Textures.buttonunpressed, new Function() {

			@Override
			public void Do(Entity e) {
				// TODO Auto-generated method stub
				screenfactory.showScreen(new StartupScreen(screenfactory));
			}
			
		},this));
		this.addEntity(Button.createXCenteredButton("Options", 160, 200, 50, 3, Textures.buttonpressed, Textures.buttonunpressed, new Function() {

			@Override
			public void Do(Entity e) {
				// TODO Auto-generated method stub
				screenfactory.showScreen(new OptionsScreen(screenfactory));
			}
			
		}, this));
		this.addEntity(Button.createXCenteredButton("Help", 220, 200, 50, 3, Textures.buttonpressed, Textures.buttonunpressed, new Function() {

			@Override
			public void Do(Entity e) {
				// TODO Auto-generated method stub
				
			}
			
		},this));
		this.addEntity(Button.createXCenteredButton("Exit", 280, 200, 50, 3, Textures.buttonpressed, Textures.buttonunpressed, new Function() {
			
			@Override
			public void Do(Entity e) {
				// TODO Auto-generated method stub
				System.out.println("Exit Clicked");
				getScreenFactory().getGame().Exit();
				System.out.println("Closing");
			}
			
		},this));
	}

	@Override
	public void onCustomDraw(Graphics2D g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onCustomUpdate() {
		// TODO Auto-generated method stub
		
	}

}

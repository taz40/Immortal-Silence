package lightsoutgaming.games.lifeless.onejar.main;

import java.awt.Graphics2D;

import taz40.lightsoutgamingengine.V1.Button;
import taz40.lightsoutgamingengine.V1.Entity;
import taz40.lightsoutgamingengine.V1.Function;
import taz40.lightsoutgamingengine.V1.Screen;
import taz40.lightsoutgamingengine.V1.ScreenFactory;

public class ChangeLogScreen extends Screen {

	public ChangeLogScreen(ScreenFactory screenfactory) {
		super(screenfactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCustomCreate() {
		// TODO Auto-generated method stub
		this.addEntity(Button.createXCenteredButton("Back", 400, 100, 50, 3, Textures.buttonpressed, Textures.buttonunpressed, new Function(){

			@Override
			public void Do(Entity e) {
				// TODO Auto-generated method stub
				screenfactory.showScreen(new OptionsScreen(screenfactory));
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

}

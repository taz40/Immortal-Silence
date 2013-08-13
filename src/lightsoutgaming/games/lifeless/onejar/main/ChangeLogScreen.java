package lightsoutgaming.games.lifeless.onejar.main;

import java.awt.Graphics2D;

import taz40.lightsoutgamingengine.V1.Button;
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
		this.addEntity(Button.createXCenteredButton("Back", 400, 100, 50, 3, Textures.buttonpressed, Textures.buttonunpressed, new Function(this){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				screenfactory.showScreen(new OptionsScreen(screenfactory));
			}}, this));
		
		
	}

	@Override
	public void onCustomDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.scale(2, 2);
		g.drawString("8-12-2013 SNAPSHOT", 100, 100);
		g.drawString("-AI updates", 100, 120);
		g.drawString("-Made ChangeLog", 100, 140);
		
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

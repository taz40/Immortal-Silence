package taz40.lifeless.graphics.GUI;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public abstract class Menu {

	List<Button> buttons = new ArrayList<Button>();
	
	public void update(){
		for(Button b: buttons){
			b.update();
		}
	}
	
	public void render(Graphics g){
		for(Button b: buttons){
			b.render(g);
		}
	}
	
}

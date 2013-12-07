package taz40.lifeless.graphics.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Menu {

	List<Button> buttons = new ArrayList<Button>();
	public boolean notavalable = false;
	public int time;
	public String title;
	
	public Menu(String title){
		this.title = title;
	}
	
	public void add(Button b){
		buttons.add(b);
	}
	
	public void update(){
		if(notavalable){
			if(time >= 60*3){
				notavalable = false;
				time = 0;
			}else time++;
				
		}
		for(Button b: buttons){
			b.update();
		}
	}
	
	public void render(Graphics g){
		if(notavalable){
			g.setColor(Color.red);
			g.drawString("Not Avalable Yet!", 10*3, 86*3);
		}
		g.setColor(Color.gray);
		g.drawString(title, 100*3, 30*3);
		for(Button b: buttons){
			b.render(g);
		}
	}
	
}

package com.kissr.LightsOutGaming.LifeLess.GUI;

import java.util.ArrayList;

import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;

public class Menu {

	private ArrayList<MenuItem> menuitems = new ArrayList<MenuItem>();
	int x, y;
	public boolean active = false;
	
	public Menu(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void update(){
		if(!active) return;
		for(int i = 0; i < menuitems.size(); i++){
			menuitems.get(i).refresh();
		}
		for(int i = 0; i < menuitems.size(); i++){
			menuitems.get(i).update(x, y);
		}
	}
	
	public void render(Screen screen){
		if(!active) return;
		for(int i = 0; i < menuitems.size(); i++){
			menuitems.get(i).render(screen, x, y);
		}
	}
	
	public void add(MenuItem item){
		menuitems.add(item);
	}
	
	public void remove(MenuItem item){
		menuitems.remove(item);
	}
	
	public void remove(int item){
		menuitems.remove(item);
	}
	
	public MenuItem get(int item){
		return menuitems.get(item);
	}
	
}

package io.brace.lightsoutgaming.immortalSilence.entities;

import io.brace.lightsoutgaming.engine.Network.NetworkUtils;
import io.brace.lightsoutgaming.engine.Network.Networked;
import io.brace.lightsoutgaming.engine.Network.Server;
import io.brace.lightsoutgaming.engine.Network.ServerClient;
import io.brace.lightsoutgaming.engine.graphics.Screen;
import io.brace.lightsoutgaming.engine.graphics.Sprite;
import io.brace.lightsoutgaming.immortalSilence.Main;

import java.util.ArrayList;
import java.util.Random;

public class Zombie extends Networked{
	
	public double rotation = 0;
	int frame = 0;
	int hearing = 5*16;
	int smell = 5*16;
	ServerClient target;
	Server server;
	int targetx, targety;
	Sprite sprite = new Sprite(0, 0, 3, 16, Main.main);

	public Zombie() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] send() {
		// TODO Auto-generated method stub
		return new String[] {x+"", y+""};
	}

	@Override
	public void recv(String[] data) {
		// TODO Auto-generated method stub
		int x = Integer.parseInt(data[0]);
		int y = Integer.parseInt(data[1]);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		ArrayList<Networked> targets = new ArrayList<Networked>();
		for(int i = 0; i < NetworkUtils.myObjects.size(); i++){
			if(NetworkUtils.myObjects.get(i).classname.equals(Player.class.getName())){
				targets.add(NetworkUtils.myObjects.get(i));
			}
		}
		for(int i = 0; i < NetworkUtils.networkObjects.size(); i++){
			if(NetworkUtils.networkObjects.get(i).classname.equals(Player.class.getName())){
				targets.add(NetworkUtils.networkObjects.get(i));
			}
		}
		double closestDist = 9999999;
		Networked closestTarget = null;
		for(int i = 0; i < targets.size(); i++){
			if(Math.sqrt((targets.get(i).getX() - x)*(targets.get(i).getX() - x) + (targets.get(i).getY() - y)*(targets.get(i).getY() - y)) < closestDist){
				closestDist = Math.sqrt((targets.get(i).getX() - x)^2 + (targets.get(i).getY() - y)^2);
				closestTarget = targets.get(i);
			}
		}
		Random rand = new Random();
		if(closestTarget!= null){
		targetx = closestTarget.getX() + rand.nextInt(10);
		targety = closestTarget.getY() + rand.nextInt(10);
		if(targetx > x+20){
			x++;
			rotation = 90;
		}
		if(targetx < x-20){
			x--;
			rotation = 270;
		}
		if(targety < y-20){
			y--;
			rotation = 0;
		}
		if(targety > y+20){
			y++;
			rotation = 180;
		}
		}
	}

	@Override
	public void render(Screen s) {
		// TODO Auto-generated method stub
		s.renderSprite(x, y, sprite.rotate(rotation), true);
	}

}

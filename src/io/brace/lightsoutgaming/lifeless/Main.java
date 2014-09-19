package io.brace.lightsoutgaming.lifeless;

import static io.brace.lightsoutgaming.engine.Network.NetworkUtils.*;
import io.brace.lightsoutgaming.engine.LightsOut;
import io.brace.lightsoutgaming.engine.Network.Server;
import io.brace.lightsoutgaming.engine.graphics.Sprite;
import io.brace.lightsoutgaming.engine.graphics.SpriteSheet;
import io.brace.lightsoutgaming.lifeless.entities.Player;
import io.brace.lightsoutgaming.lifeless.entities.Zombie;

import java.net.DatagramSocket;

public class Main extends LightsOut {
	
	DatagramSocket socket;
	public static SpriteSheet main = new SpriteSheet("/Textures/MainSheet.png");
	public static Sprite player_up = new Sprite(1, 2, 3, 16, main);
	public static Sprite player_down = player_up.rotate(180);
	public static Sprite player_left = player_up.rotate(270);
	public static Sprite player_right = player_up.rotate(90);
	public static Sprite zombie = new Sprite(0, 0, 3, 16, main);
	boolean playerCreated = false;
	
	public static void main(String[] args){
		new Main().init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		createDisplay("Life Less", 800, 600);
		start();
		socket = NetInit();
		connect("localhost", 1010, "taz40", "LifeLess 0.1", socket, this);
		createObject(Player.class, serverIP, serverPort, socket);
		createObject(Zombie.class, serverIP, serverPort, socket);
		
	}

	@Override
	protected void render() {
		// TODO Auto-generated method stub
		screen.clear();
		screen.renderSprite(10, 10, zombie, true);
		for(int i = 0; i < myObjects.size(); i++){
			myObjects.get(i).render(screen);
		}
		for(int i = 0; i < networkObjects.size(); i++){
			networkObjects.get(i).render(screen);
		}
		if(playerCreated){
			screen.xOffset = (myObjects.get(0).getX())-((screen.getWidth()/2)-8*3);
			screen.yOffset = (myObjects.get(0).getY())-((screen.getHeight()/2)-8*3);
		}else if(myObjects.size() > 0){
			playerCreated = true;
		}
		show();
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		for(int i = 0; i < myObjects.size(); i++){
			myObjects.get(i).update();
		}
		for(int i = 0; i < myObjects.size(); i++){
			sendObject(myObjects.get(i), serverIP, serverPort, socket);
		}
		//screen.xOffset = myObjects.get(0).getX();
		//screen.yOffset = myObjects.get(0).getY();
	}
	
}

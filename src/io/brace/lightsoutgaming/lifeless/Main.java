package io.brace.lightsoutgaming.lifeless;

import static io.brace.lightsoutgaming.engine.Network.NetworkUtils.*;
import io.brace.lightsoutgaming.engine.LightsOut;
import io.brace.lightsoutgaming.engine.Network.Server;
import io.brace.lightsoutgaming.engine.graphics.Sprite;
import io.brace.lightsoutgaming.engine.graphics.SpriteSheet;
import io.brace.lightsoutgaming.lifeless.entities.Player;

import java.net.DatagramSocket;

public class Main extends LightsOut {
	
	DatagramSocket socket;
	static SpriteSheet main = new SpriteSheet("/Textures/MainSheet.png");
	public static Sprite player_up = new Sprite(1, 2, 3, 16, main);
	public static Sprite player_down = player_up.rotate(180);
	public static Sprite player_left = player_up.rotate(270);
	public static Sprite player_right = player_up.rotate(90);
	public static Sprite zombie = new Sprite(0, 0, 3, 16, main);
	
	public static void main(String[] args){
		new Main().init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		createDisplay("Life Less", 800, 600);
		start();
		Server s = new Server(false, 1010, "LifeLess 0.1");
		socket = NetInit();
		connect("localhost", 1010, "taz40", "LifeLess 0.1", socket);
		createObject(Player.class, serverIP, serverPort, socket);
		
	}

	@Override
	protected void render() {
		// TODO Auto-generated method stub
		screen.clear();
		screen.renderSprite(10, 10, zombie, true);
		for(int i = 0; i < myObjects.size(); i++){
			myObjects.get(i).render(screen);
		}
		show();
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		for(int i = 0; i < myObjects.size(); i++){
			myObjects.get(i).update();
		}
		//screen.xOffset = myObjects.get(0).getX();
		//screen.yOffset = myObjects.get(0).getY();
	}
	
}

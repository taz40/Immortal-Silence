package io.brace.lightsoutgaming.lifeless.entities;

import io.brace.lightsoutgaming.engine.Network.Networked;
import io.brace.lightsoutgaming.engine.Network.Server;
import io.brace.lightsoutgaming.engine.Network.ServerClient;
import io.brace.lightsoutgaming.engine.graphics.Screen;
import io.brace.lightsoutgaming.engine.graphics.Sprite;
import io.brace.lightsoutgaming.lifeless.Main;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
		x++;
		rotation = 90;
	}

	@Override
	public void render(Screen s) {
		// TODO Auto-generated method stub
		s.renderSprite(x, y, sprite.rotate(rotation), true);
	}

}

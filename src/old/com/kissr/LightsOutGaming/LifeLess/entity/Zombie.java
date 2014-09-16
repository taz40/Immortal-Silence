package com.kissr.LightsOutGaming.LifeLess.entity;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;
import com.kissr.LightsOutGaming.LifeLess.Graphics.Sprite;
import com.kissr.LightsOutGaming.LifeLess.Server.Server;
import com.kissr.LightsOutGaming.LifeLess.Server.ServerClient;

public class Zombie extends Entity{
	
	public double rotation = 0;
	int frame = 0;
	int hearing = 5*16;
	int smell = 5*16;
	ServerClient target;
	Server server;
	int targetx, targety;

	public Zombie(int x, int y, Sprite sprite, Server server) {
		super(x, y, sprite, false);
		this.server = server;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onUpdate() {
		// TODO Auto-generated method stub
		List<ServerClient> clients = new ArrayList<ServerClient>();
		for(int i = 0; i < server.clients.size(); i++){
			ServerClient client = server.clients.get(i);
			int soundtotal = client.sound + hearing;
			int scenttotal = client.scent + smell;
			double dist = Math.sqrt((client.x-x)*(client.x-x) + (client.y-y)*(client.y-y));
			if(soundtotal > dist || scenttotal > dist){
				clients.add(client);
			}
		}
		
		boolean found = false;
		ServerClient lowestdistclient = null;
		double lowestdistance = 999999999;
		for(int i = 0; i < clients.size(); i++){
			ServerClient client = clients.get(i);
			double dist = Math.sqrt((client.x-x)*(client.x-x) + (client.y-y)*(client.y-y));
			if(dist < lowestdistance){
				lowestdistance = dist;
				lowestdistclient = client;
				found = true;
			}
		}
		target = lowestdistclient;
		if(!found){
			target = null;
		}
		if(target == null){
		}else{
			targetx = target.x;
			targety = target.y;
		}
		
		if(x < targetx){
			x++;
			rotation = 90;
		}else if(x > targetx){
			x--;
			rotation = 270;
		}
		if(y < targety){
			y++;
			rotation = 180;
		}else if(y > targety){
			y--;
			rotation = 0;
		}
		
	}

	@Override
	protected void onDraw(Screen screen) {
		// TODO Auto-generated method stub
		BufferedImage image = new BufferedImage(this.sprite.width, this.sprite.height, BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, image.getWidth(), image.getHeight(), this.sprite.pixels, 0, image.getWidth());
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(rotation), image.getWidth() / 2, image.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		BufferedImage newimage = op.filter(image, null);
		Sprite rotsprite = new Sprite(newimage);
		screen.renderMob(x, y, rotsprite, true, 0);
	}

}

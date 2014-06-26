package com.kissr.LightsOutGaming.LifeLess.entity;

import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;
import com.kissr.LightsOutGaming.LifeLess.Graphics.Sprite;
import com.kissr.LightsOutGaming.LifeLess.input.Keyboard;
import com.kissr.LightsOutGaming.LifeLess.input.Mouse;

public class Player extends Entity{
	
	private Keyboard key;
	private double rotation;
	public String name;
	public int id;

	public Player(int x, int y, Sprite sprite, Keyboard key) {
		super(x, y, sprite, true);
		this.key = key;
		this.defaultrender = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onUpdate() {
		// TODO Auto-generated method stub
		int mx = 0;
		int my = 0;
		flip = 0;
		
		if(key.getKey(KeyEvent.VK_A)){
			mx--;
			rotation = 270;
		}
		if(key.getKey(KeyEvent.VK_D)){
			mx++;
			rotation = 90;
		}
		if(key.getKey(KeyEvent.VK_W)){
			my--;
			rotation = 0;
		}
		if(key.getKey(KeyEvent.VK_S)){
			my++;
			rotation = 180;
		}
		x += mx;
		y += my;
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

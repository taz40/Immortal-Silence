package io.brace.lightsoutgaming.immortalSilence;

import io.brace.lightsoutgaming.engine.Entity;
import io.brace.lightsoutgaming.engine.Network.NetworkUtils;
import io.brace.lightsoutgaming.engine.graphics.Screen;
import io.brace.lightsoutgaming.engine.graphics.Sprite;
import io.brace.lightsoutgaming.engine.input.Keyboard;
import io.brace.lightsoutgaming.immortalSilence.entities.PistolBullet;
import io.brace.lightsoutgaming.immortalSilence.entities.bullet;

import java.awt.event.KeyEvent;

public class Weapon extends Entity {

	float damage;
	float cooldown;
	int range;
	float cooldownt;
	long lasttime = -1;
	int ammo = 0;
	int ammoinclip = 0;
	int ammoPerClip;
	boolean beingHeld = true;
	float reloadTime;
	float reloadTimer;
	boolean reloading = false;
	
	public static Sprite pistol_round = new Sprite(66, 20, 3, 1, 2, Main.main);
	
	
	public Weapon(float damage, float cooldown, int range, int ammoPerClip, int ammo, float reloadTime){
		this.damage = damage;
		this.cooldown = cooldown;
		cooldownt = cooldown;
		this.range = range;
		this.ammo = ammo;
		this.ammoPerClip = ammoPerClip;
		ammoinclip = ammoPerClip;
		this.reloadTime = reloadTime;
		reloadTimer = reloadTime;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(cooldownt < cooldown){
			if(lasttime == -1){
				lasttime = System.currentTimeMillis();
			}else{
				long now = System.currentTimeMillis();
				cooldownt += (now - lasttime) / 1000.0;
				lasttime = now;
			}
		}
		if(Keyboard.getKey(KeyEvent.VK_R)){
			reloading = true;
			reloadTimer = 0;
			ammo += ammoinclip;
			ammoinclip = 0;
			lasttime = System.currentTimeMillis();
		}
		if(reloading){
			if(lasttime == -1){
				lasttime = System.currentTimeMillis();
			}else{
				long now = System.currentTimeMillis();
				reloadTimer += (now - lasttime) / 1000.0;
				lasttime = now;
			}
			if(reloadTimer >= reloadTime){
				reloading = false;
				if(ammo >= ammoPerClip){
					ammo -= ammoPerClip;
					ammoinclip = ammoPerClip;
				}else{
					ammoinclip = ammo;
					ammo = 0;
				}
			}
		}
	}
	@Override
	public void render(Screen s) {
		// TODO Auto-generated method stub
	}
	
	public void fire(double angle, int x, int y){
		if(cooldownt >= cooldown && ammoinclip > 0){
			int id = NetworkUtils.createObject(PistolBullet.class, NetworkUtils.serverIP, NetworkUtils.serverPort, Main.socket);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bullet b = (bullet)NetworkUtils.myObjects.get(id);
			b.x = x;
			b.y = y;
			b.rot = Math.toDegrees(angle);
			b.vx = PistolBullet.PistolSpeed * Math.cos(angle);
			b.vy = PistolBullet.PistolSpeed * Math.sin(angle);
			System.out.println("velocity, X: " + b.vx + ", Y: " + b.vy);
			ammoinclip--;
			System.out.println("bang!");
			cooldownt = 0;
			lasttime = System.currentTimeMillis();
		}
	}
	
	
	
}

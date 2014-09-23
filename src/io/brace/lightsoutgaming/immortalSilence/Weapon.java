package io.brace.lightsoutgaming.immortalSilence;

import io.brace.lightsoutgaming.engine.Entity;
import io.brace.lightsoutgaming.engine.graphics.Screen;

public class Weapon extends Entity {

	float damage;
	float cooldown;
	int range;
	float cooldownt;
	long lasttime = -1;
	
	public Weapon(float damage, float cooldown, int range){
		this.damage = damage;
		this.cooldown = cooldown;
		cooldownt = cooldown;
		this.range = range;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(cooldownt < cooldown){
			if(lasttime == -1){
				lasttime = System.currentTimeMillis();
			}else{
				long now = System.currentTimeMillis();
				cooldownt += (now - lasttime) / 1000;
			}
		}
		System.out.println(cooldownt + "/" + cooldown);
	}
	@Override
	public void render(Screen s) {
		// TODO Auto-generated method stub
		
	}
	
	public void fire(double angle){
		if(cooldownt >= cooldown){
			System.out.println("bang!");
			cooldownt = 0;
		}
	}
	
	
	
}

package io.brace.lightsoutgaming.immortalSilence.entities;

import io.brace.lightsoutgaming.engine.Network.Networked;
import io.brace.lightsoutgaming.engine.graphics.Screen;
import io.brace.lightsoutgaming.engine.graphics.Sprite;

public abstract class bullet extends Networked {
	
	public double vx, vy;
	public double rot;

	@Override
	public String[] send() {
		// TODO Auto-generated method stub
		return new String[]{""+x,""+y};
	}

	@Override
	public void recv(String[] data) {
		// TODO Auto-generated method stub
		x = Integer.parseInt(data[0]);
		y = Integer.parseInt(data[1]);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		x+=vx;
		y+=vy;
	}

}

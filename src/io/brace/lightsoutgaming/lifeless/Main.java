package io.brace.lightsoutgaming.lifeless;

import io.brace.lightsoutgaming.engine.LightsOut;

public class Main extends LightsOut {
	
	public static void main(String[] args){
		new Main().init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		createDisplay("Life Less", 800, 600);
		start();
		
	}

	@Override
	protected void render() {
		// TODO Auto-generated method stub
		screen.clear();
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		
	}
	
}

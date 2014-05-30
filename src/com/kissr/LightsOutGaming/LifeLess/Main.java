package com.kissr.LightsOutGaming.LifeLess;

public class Main {

	public static void main(String[] args){
		new Main();
	}
	
	public Main(){
		Thread main = new Thread("Main Thread"){
			public void run(){
				new Game();
			}
		};
		main.start();
	}
	
}

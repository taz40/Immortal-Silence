package io.brace.lightsoutgaming.lifeless;

import io.brace.lightsoutgaming.engine.Network.Server;

public class ServerMain {
	public static void main(String[] args){
		Server s = new Server(false, 1010, "LifeLess 0.1");
	}
}

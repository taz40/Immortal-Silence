package com.kissr.LightsOutGaming.LifeLess.Server;

import java.net.InetAddress;

public class ServerClient {

	public String name;
	public InetAddress address;
	public int port;
	private final int ID;
	public int attempt = 0;
	public int x;
	public int y = 0;
	public int playercolor = 0;
	public double rotation = 0;
	public int sound = 16;
	public int scent = 16;
	
	public ServerClient(String name, InetAddress address, int port, final int ID){
		this.name = name;
		this.address = address;
		this. port = port;
		this.ID = ID;
	}
	
	public int GetID(){
		return ID;
	}
	
	@Override
	public String toString(){
		return name + "/" + x + "/" + y;
	}

	
	
}

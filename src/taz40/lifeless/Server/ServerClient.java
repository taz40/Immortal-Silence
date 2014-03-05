package taz40.lifeless.Server;

import java.net.InetAddress;

public class ServerClient {

	public String name;
	public InetAddress address;
	public int port;
	private final int ID;
	public int attempt = 0;
	int x, y = 0;
	
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

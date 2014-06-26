package com.kissr.LightsOutGaming.LifeLess.Server;

public class MainServer {
	public static void main(String args[]){
		int port;
		if(args.length < 1){
			port = 2743;
		}else{
			
			try{
				port = Integer.parseInt(args[0]);
			}catch(Exception e){
				port = 2743;
			}
		}
		new Server(false, port);
	}
}

package com.kissr.LightsOutGaming.LifeLess.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.kissr.LightsOutGaming.LifeLess.Graphics.Sprite;
import com.kissr.LightsOutGaming.LifeLess.entity.Zombie;

public class Server implements Runnable {

	public static final int MAX_ATTEMPTS = 5;
	
	public boolean single = true;
	boolean firsttime = true;
	public int port;
	DatagramSocket socket;
	Thread run;
	boolean running = true;
	public List<ServerClient> clients = new ArrayList<ServerClient>();
	List<Integer> responses = new ArrayList<Integer>();
	List<Integer> updateresponses = new ArrayList<Integer>();
	List<Zombie> zombies = new ArrayList<Zombie>();
	
	public Server(boolean single, int port){
		this.single = single;
		this.port = port;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		zombies.add(new Zombie(10, 10, Sprite.zombie_1_idle, this));
		run = new Thread(this, "Server");
		run.start();
	}
	
	private String recv(){
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		
		try {
			socket.setSoTimeout(1000);
			socket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "false";
		}
		String message = new String(packet.getData());
		return message.split("/e/")[0];
	}
	
	private void send(final String s, final InetAddress ip, final int port){
		Thread send = new Thread("Send"){
		final String string = s + "/e/";
			public void run(){
				DatagramPacket packet = new DatagramPacket(string.getBytes(), string.getBytes().length, ip, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		send.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		running = true;
		System.out.println("server started on port "+port);
		manageClients();
		receive();
	}
	
	private void manageClients() {
		Thread timeout = new Thread("timeout"){
			public void run(){
				while(running){
					sendToAll("/t/");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int i = 0; i < clients.size(); i++){
						if(responses.contains(clients.get(i).GetID())){
							clients.get(i).attempt = 0;
							responses.remove(new Integer(clients.get(i).GetID()));
						}else{
							clients.get(i).attempt++;
							if(clients.get(i).attempt > MAX_ATTEMPTS){
								disconnect(clients.get(i).GetID(), false);
							}
						}
					}
				}
			}
		};
		timeout.start();
		
		Thread update = new Thread("timeout"){
			public void run(){
				long lastTime = System.nanoTime();
				final double ns = 1000000000.0 / 30.0;
				double delta = 0;
				final double nsu = 1000000000.0 / 30.0;
				double deltau = 0;
				while(running){
					long now = System.nanoTime();
					delta += (now-lastTime) / ns;
					deltau += (now-lastTime) / nsu;
					lastTime = now;
					while(delta >= 1){
						for(int i = 0; i < clients.size(); i++){
							send("/u/", clients.get(i).address, clients.get(i).port);
							//System.out.println("updated");
						}
						delta--;
					}
					
					while(deltau >= 1){
						for(int i = 0; i < zombies.size(); i++){
							zombies.get(i).update();
						}
						deltau--;
					}
				}
			}
		};
		update.start();
	}
	
	private void disconnect(int id, boolean clean){
		ServerClient client = null;
		for(int i = 0; i < clients.size(); i++){
			if(clients.get(i).GetID() == id){
				client = clients.get(i);
				break;
			}
		}
		if(client != null){
			clients.remove(client);
			if(clean){
				System.out.println(client.name + "(" + id + ")" + " @ " + client.address.toString() + ":"+ client.port+ " disconnected");
			}else{
				System.out.println(client.name + "(" + id + ")" + " @ " + client.address.toString() + ":"+ client.port+ " timed out");
			}
		}
	}
	
	private void sendToAll(String msg){
		for(int i = 0; i < clients.size(); i++){
			send(msg, clients.get(i).address, clients.get(i).port);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void receive() {
		Thread receive = new Thread("Receive"){
			public void run(){
				while(running){
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					process(packet);
				}
			}
		};
		receive.start();
	}
	
	private void process(DatagramPacket packet) {
		// TODO Auto-generated method stub
		String string = new String(packet.getData()).split("/e/")[0];
		if(string.startsWith("/c/") && (!single || firsttime)){
			int id = UniqueIdentifier.getIdentifier();
			System.out.println(id);
			clients.add(new ServerClient(string.substring(3, string.length()), packet.getAddress(), packet.getPort(), id));
			send("/c/"+id, clients.get(clients.size() - 1).address, clients.get(clients.size() - 1).port);
			if(single) firsttime = false;
		}else if(string.startsWith("/s/")){
			running = false;
			socket.close();
		}else if(string.startsWith("/u/")){
			String[] tokens = string.split("/u/|/");
			String name = tokens[1];
			int id = Integer.parseInt(tokens[2]);
			updateresponses.add(id);
			ServerClient client = getClientById(id);
			if(client == null){
				System.out.println("Client is null");
			}
			client.x = Integer.parseInt(tokens[3]);
			client.y = Integer.parseInt(tokens[4]);
			client.rotation = Double.parseDouble(tokens[5]);
			client.playercolor = Integer.parseInt(tokens[6]);
			client.sound = Integer.parseInt(tokens[7]);
			client.scent = Integer.parseInt(tokens[8]);
			String msg = "/i/" + (clients.get(0).name + "/" + clients.get(0).GetID() + "/" + Integer.toString(clients.get(0).x) + "/" + Integer.toString(clients.get(0).y) + "/" + clients.get(0).rotation + "/" + clients.get(0).playercolor);
			if(clients.size() == 1){
				//msg += "/p/";
			}
			for(int i = 1; i < clients.size(); i++){
				msg += "/p/" + (clients.get(i).name + "/" + clients.get(i).GetID() + "/" + clients.get(i).x + "/" + clients.get(i).y + "/" + clients.get(i).rotation + "/" + clients.get(i).playercolor);
			}
			msg += "/zi/";
			if(zombies.size() >= 1){
			msg += Integer.toString(zombies.get(0).x) + "/" + Integer.toString(zombies.get(0).y) + "/" + zombies.get(0).rotation;
			for(int i = 1; i < zombies.size(); i++){
				msg += "/z/" + Integer.toString(zombies.get(i).x) + "/" + Integer.toString(zombies.get(i).y) + "/" + zombies.get(i).rotation;
			}
			}
			send(msg, client.address, client.port);
		}else if(string.startsWith("/d/")){
			int id = Integer.parseInt(string.split("/d/")[1]);
			disconnect(id, true);
		}else if(string.startsWith("/r/")){
			String[] tokens = string.split("/r/");
			int id = Integer.parseInt(tokens[1]);
			responses.add(id);
			updateresponses.add(id);
		}
	}
	
	public ServerClient getClientByUsername(String username){
		for(int i = 0; i < clients.size(); i++){
			if(clients.get(i).name.equals(username)){
				return clients.get(i);
			}
		}
		return null;
	}
	
	public ServerClient getClientById(int id){
		for(int i = 0; i < clients.size(); i++){
			if(clients.get(i).GetID() == id){
				return clients.get(i);
			}
		}
		return null;
	}
	
	
	
}

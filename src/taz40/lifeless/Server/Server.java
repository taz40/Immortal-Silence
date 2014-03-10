package taz40.lifeless.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
	int port;
	InetAddress ip;
	DatagramSocket socket;
	Thread send;
	Thread run;
	Thread manage;
	Thread receive;
	boolean running;
	private int time = 0;
	private List<ServerClient> clients = new ArrayList<ServerClient>();
	private List<Integer> responses = new ArrayList<Integer>();
	private final int MAX_ATTEMPTS = 5;
	
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
		send = new Thread("Send"){
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
	
	public Server(int port){
		this.port = port;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		run = new Thread(this, "Server");
		run.start();
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
		manage = new Thread("manage"){
			public void run(){
				while(running){
					sendToAll("/u/");
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
		manage.start();
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
		}
	}
	
	private void receive() {
		receive = new Thread("Receive"){
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

			private void process(DatagramPacket packet) {
				// TODO Auto-generated method stub
				String string = new String(packet.getData()).split("/e/")[0];
				if(string.startsWith("/c/")){
					int id = UniqueIdentifier.getIdentifier();
					System.out.println(id);
					clients.add(new ServerClient(string.substring(3, string.length()), packet.getAddress(), packet.getPort(), id));
					send("/c/"+id, clients.get(clients.size() - 1).address, clients.get(clients.size() - 1).port);
				}else if(string.startsWith("/u/")){
					String[] tokens = string.split("/u/|/");
					String name = tokens[1];
					int id = Integer.parseInt(tokens[2]);
					ServerClient client = getClientByID(id);
					if(client == null){
						System.out.println("Client is null");
					}
					System.out.println("response from " + client.name + "(" + client.GetID() + ")");
					responses.add(id);
					client.x = Integer.parseInt(tokens[3]);
					client.x = Integer.parseInt(tokens[4]);
					String msg = "/i/" + (clients.get(0).GetID() + "/" + Integer.toString(clients.get(0).x) + "/" + Integer.toString(clients.get(0).y));
					for(int i = 1; i < clients.size(); i++){
						msg += "/p/" + (clients.get(i).GetID() + "/" + clients.get(i).x + "/" + clients.get(i).y);
					}
					send(msg, client.address, client.port);
				}else if(string.startsWith("/d/")){
					int id = Integer.parseInt(string.split("/d/")[1]);
					disconnect(id, true);
				}else{
					System.out.println(string);
				}
			}
		};
		receive.start();
	}
	
	public ServerClient getClientByID(int id){
		for(int i = 0; i < clients.size(); i++){
			if(clients.get(i).GetID() == id){
				return clients.get(i);
			}
		}
		return null;
	}
}

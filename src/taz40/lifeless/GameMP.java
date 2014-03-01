package taz40.lifeless;

import java.awt.Dimension;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFrame;

import taz40.lifeless.entity.mob.Player;
import taz40.lifeless.graphics.Screen;
import taz40.lifeless.input.Keyboard;
import taz40.lifeless.input.Mouse;
import taz40.lifeless.level.Level;
import taz40.lifeless.level.TileCoordinate;

public class GameMP extends Game {
	
	Thread send;
	private DatagramSocket socket;
	private InetAddress ip;
	private String name, address;
	private int port;
	
	public GameMP(String name1, String address1, int port1){
		super();
		name = name1;
		address = address1;
		port = port1;
		menu = false;
		if(!openConnection(address)){
			failed();
		}else{
			success();
		}
		Mouse.resetButton();
	}
	
	private void failed(){
		menu = true;
	}
	
	private void success(){
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		level.clear();
		TileCoordinate playerspawn = new TileCoordinate(20, 36);
		player = new Player(playerspawn.x(), playerspawn.y(), key);
		level.add(player);
		addKeyListener(key);
		
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	private boolean openConnection(String address){
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
			String msg = "/c/" + name;
			this.send(msg.getBytes());
			String data = receive();
			if( data == "false" ){
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private String receive(){
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
		return message;
	}
	
	private void send(final byte[] data){
		send = new Thread("Send"){
			public void run(){
				DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
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
	
}
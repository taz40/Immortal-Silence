package old.com.kissr.LightsOutGaming.LifeLess;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.kissr.LightsOutGaming.LifeLess.GUI.Button;
import com.kissr.LightsOutGaming.LifeLess.GUI.Menu;
import com.kissr.LightsOutGaming.LifeLess.GUI.SpriteButton;
import com.kissr.LightsOutGaming.LifeLess.GUI.Text;
import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;
import com.kissr.LightsOutGaming.LifeLess.Graphics.Sprite;
import com.kissr.LightsOutGaming.LifeLess.Level.Level;
import com.kissr.LightsOutGaming.LifeLess.Server.Server;
import com.kissr.LightsOutGaming.LifeLess.entity.Entity;
import com.kissr.LightsOutGaming.LifeLess.entity.Player;
import com.kissr.LightsOutGaming.LifeLess.entity.Zombie;
import com.kissr.LightsOutGaming.LifeLess.input.Keyboard;
import com.kissr.LightsOutGaming.LifeLess.input.Mouse;
import com.kissr.LightsOutGaming.LifeLess.utill.function;

public class Game extends Canvas implements Runnable{

	public int width = 300;
	public int height;
	public float aspectratio;
	public int scale = 3;
	public JFrame frame;
	public static final String TITLE = "Life Less 2D alpha";
	private Thread runningthread;
	private boolean running = false;
	Screen screen;
	public Keyboard keyboard;
	Menu MainMenu;
	Menu OptionsMenu;
	Menu PauseMenu;
	Menu PlayerChange;
	boolean fullscreen;
	public boolean single = true;
	Player p;
	boolean ingame = false;
	boolean pause = false;
	InetAddress ip;
	int port;
	DatagramSocket socket;
	Server spserver;
	
	private BufferedImage image;
	private int[] pixels;
	Level currentlevel;
	String name = "dude";
	int id;
	Clip menu;
	Clip peacefull;
	Game game = this;
	
	private ArrayList<Menu> menues = new ArrayList<Menu>();
	private ArrayList<Entity> gameentities = new ArrayList<Entity>();
	private ArrayList<Entity> menuentities = new ArrayList<Entity>();
	
	public Game(){
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Game.class.getResource("/TitleFull.wav"));
			menu = AudioSystem.getClip();
			menu.open(ais);
			menu.loop(Clip.LOOP_CONTINUOUSLY);
			AudioInputStream ais2 = AudioSystem.getAudioInputStream(Game.class.getResource("/Peaceful.wav"));
			peacefull = AudioSystem.getClip();
			peacefull.open(ais2);
			peacefull.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
		aspectratio = (float)frame.getHeight() / (float)frame.getWidth();
		
		frame.setVisible(false);
		frame.dispose();
		height = (int) (width * aspectratio);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		frame = new JFrame(TITLE);
		frame.setSize(width*scale, height*scale);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		keyboard = new Keyboard();
		
		MainMenu = new Menu(10, 10);
		MainMenu.active = true;
		MainMenu.add(new Button("Single Player", this, 10, 10, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				single = true;
				if(networksetup()){
					ingame = true;
					MainMenu.active = false;
				}
			}}));
		
		MainMenu.add(new Button("Multiplayer", this, 10, 30, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				single = false;
				String s = (String)JOptionPane.showInputDialog(
	                    frame,
	                    "Ip",
	                    "Ip Address",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "localhost");
				try {
					ip = InetAddress.getByName(s);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					ip = null;
				}
				s = (String)JOptionPane.showInputDialog(
	                    frame,
	                    "port",
	                    "Port",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "2743");
				
				try{
					port = Integer.parseInt(s);
				}catch(Exception e){
					port = 0;
				}
				if(networksetup()){
					ingame = true;
					MainMenu.active = false;
				}else{
					JOptionPane.showMessageDialog(frame, "Unnable to connect.");
				}
			}}));
		
		MainMenu.add(new Button("Options", this, 10, 50, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				MainMenu.active = false;
				OptionsMenu.active = true;
			}}));
		
		MainMenu.add(new Button("Quit", this, 10, 70, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				stop();
			}}));
		
		OptionsMenu = new Menu(10, 10);
		OptionsMenu.add(new Button("FullScreen: OFF", this, 10, 10, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				
				fullscreen = !fullscreen;
				if(fullscreen){
					b.text = "FullScreen: ON";
					frame.dispose();
					frame = new JFrame(TITLE);
					frame.setSize(width*scale, height*scale);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setResizable(false);
					frame.add(b.game);
					frame.setLocationRelativeTo(null);
					frame.setUndecorated(true);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);
				}else{
					b.text = "FullScreen: OFF";
					frame.dispose();
					frame = new JFrame(TITLE);
					frame.setSize(width*scale, height*scale);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					frame.add(b.game);
					frame.setVisible(true);
				}
				b.game.requestFocus();
			}}));
		OptionsMenu.add(new Text("Text Color", 150, 10));
		OptionsMenu.add(new Button("Red", this, 120, 30, 0xffFF0000, 0xff990000, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				Button.defaulthovercol = 0xff990000;
				Button.defaultcol = 0xffFF0000;
				Text.defaultcol = 0xffFF0000;
			}
			
		}));
		OptionsMenu.add(new Button("Green", this, 147, 30, 0xff33CC33, 0xff248F24, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				Button.defaulthovercol = 0xff248F24;
				Button.defaultcol = 0xff33CC33;
				Text.defaultcol = 0xff33CC33;
				
				
			}
			
		}));
		OptionsMenu.add(new Button("Blue", this, 185, 30,0xff0000FF, 0xff000099, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				Button.defaulthovercol = 0xff000099;
				Button.defaultcol = 0xff0000FF;
				Text.defaultcol = 0xff0000FF;
			}
			
		}));
		OptionsMenu.add(new Button("White", this, 215, 30, 0xffd0d0d0, 0xff505050, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				Button.defaulthovercol = 0xff505050;
				Button.defaultcol = 0xffd0d0d0;
				Text.defaultcol = 0xffd0d0d0;
			}
			
		}));
		OptionsMenu.add(new Button("Refresh Screen", this, 10, 30, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				new Main();
				stop();
			}}));
		OptionsMenu.add(new Button("Back", this, 10, 50, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				if(pause){
					OptionsMenu.active = false;
					PauseMenu.active = true;
				}else{
					MainMenu.active = true;
					OptionsMenu.active = false;
				}
			}}));
		PauseMenu = new Menu((width/2) - 50, (height/2) - 50);
		PauseMenu.add(new Text("Paused", 20, 0));
		PauseMenu.add(new Button("Resume Game", this, 0, 20, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				ingame = true;
				pause = false;
				PauseMenu.active = false;
			}
			
		}));
		PauseMenu.add(new Button("Options", this, 20, 40, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				PauseMenu.active = false;
				OptionsMenu.active = true;
			}
			
		}));
		PauseMenu.add(new Button("Change player", this, 0, 60, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				PauseMenu.active = false;
				PlayerChange.active = true;
				
			}
			
		}));
        PauseMenu.add(new Button("Quit", this, 30, 80, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				if(single){
					send("/s/");
				}else{
					send("/d/"+id);
				}
				pause = false;
				PauseMenu.active = false;
				MainMenu.active = true;
			}
			
		}));
		
        PlayerChange = new Menu(10, 10);
        PlayerChange.add(new SpriteButton(Sprite.player_red, this, 20, 10, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				p.sprite = Sprite.player_red;
				if(pause){
					PauseMenu.active = true;
				}
				PlayerChange.active = false;
			}
			
		}));
        
        PlayerChange.add(new SpriteButton(Sprite.player_blue, this, 40, 10, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				p.sprite = Sprite.player_blue;
				if(pause){
					PauseMenu.active = true;
				}
				PlayerChange.active = false;
			}
			
		}));
        
        PlayerChange.add(new SpriteButton(Sprite.player_green, this, 60, 10, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				p.sprite = Sprite.player_green;
				if(pause){
					PauseMenu.active = true;
				}
				PlayerChange.active = false;
			}
			
		}));
		
		
		//-------------------------------------------------------------------------
		
		menues.add(MainMenu);
		menues.add(OptionsMenu);
		menues.add(PauseMenu);
		menues.add(PlayerChange);
		gameentities.add(new Player(10, 10, Sprite.player_red, keyboard));
		p = (Player) gameentities.get(0);
		this.addKeyListener(keyboard);
		this.addMouseListener(new Mouse());
		this.addMouseMotionListener(new Mouse());
		frame.setVisible(true);
		scale = frame.getWidth() / width;
		System.out.println(width + ", "+height);
		System.out.println(width*height);
		screen = new Screen(width, height, this);
		screen.clearcolor = Color.black.getRGB();
		currentlevel = Level.l1;
		start();
	}
	
	protected boolean networksetup() {
		// TODO Auto-generated method stub
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(single){
			try {
				ip = InetAddress.getByName("localhost");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			port = 4025;
			spserver = new Server(true, 4025);
			PauseMenu.add(new Button("Open to lan", this, 100, 20, new function(){

				@Override
				public void run(Button b) {
					// TODO Auto-generated method stub
					spserver.single = false;
					JOptionPane.showMessageDialog(frame, "Port: " + port);
					PauseMenu.remove(b);
					PauseMenu.add(new Button("Get Port", b.game, 100, 20, new function(){

						@Override
						public void run(Button b) {
							// TODO Auto-generated method stub
							JOptionPane.showMessageDialog(frame, "Port: " + port);
						}}));
				}}));
		}
		if(port == 0) port = 2743;
		if(ip == null) try {
			ip = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		send("/c/" + name);
		String s = receive();
		if(s == "false"){
			return false;
		}
		s = s.substring(3);
		id = Integer.parseInt(s);
		recvthread();
		return true;
	}

	public synchronized void start(){
		runningthread = new Thread(this, "running thread");
		running = true;
		runningthread.start();
	}
	
	public synchronized void stop(){
		Thread stop = new Thread("stop"){
			public void run(){
				running = false;
				try {
					runningthread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				menu.stop();
				frame.dispose();
				menu.close();
				peacefull.close();
			}
		};
		stop.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double nsups = 1000000000.0 / 60.0;
		final double nsfps = 1000000000.0 / 30.0;
		double deltaups = 0;
		double deltafps = 0;
		int frames = 0;
		int updates = 0;
		this.requestFocus();
		while(running){
			if(!this.hasFocus()){
				new Thread("focus"){
					public void run(){
						running = false;
						
						try {
							runningthread.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						while(!game.hasFocus()){
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						start();
						
					}
				};
			}
			long now = System.nanoTime();
			deltaups += (now-lastTime) / nsups;
			deltafps += (now-lastTime) / nsfps;
			lastTime = now;
			while(deltaups >= 1){
				update();
				updates++;
				deltaups--;
			}
			while(deltafps >= 1){
				render();
				frames++;
				deltafps--;
			}
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				frame.setTitle(TITLE + " - " + updates + "ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
	}
	
	private void recvthread(){
		Thread recv = new Thread("recv thread"){
			public void run(){
				while(ingame || pause){
					try{
					String s = receive();
					if(s.startsWith("/u/")){
						int col;
						if(p.sprite == Sprite.player_red){
							col = 0;
						}else if(p.sprite == Sprite.player_blue){
							col = 1;
						}else{
							col = 2;
						}
						send("/u/" + name + "/" + id + "/" + p.x + "/" + p.y + "/" + p.rotation + "/" + col + "/" + p.sound + "/" + p.scent);
						String players = receive();
						ArrayList newentities = new ArrayList<Entity>();
						String[] info = players.split("/i/|/zi/");
						String[] playerarray = info[1].split("/p/");
						for(int i = 0; i < playerarray.length; i++){
							String[] playerinfo = playerarray[i].split("/");
							if(id != Integer.parseInt(playerinfo[1])){ 
								Player p = new Player(Integer.parseInt(playerinfo[2]), Integer.parseInt(playerinfo[3]), Sprite.player_blue, keyboard);
								p.networked = true;
								p.name = playerinfo[0];
								p.id = Integer.parseInt(playerinfo[1]);
								p.rotation = Double.parseDouble(playerinfo[4]);
								if(playerinfo[5].equals("0")){
									p.sprite = Sprite.player_red;
								}else if(playerinfo[5].equals("1")){
									p.sprite = Sprite.player_blue;
								}else if(playerinfo[5].equals("2")){
									p.sprite = Sprite.player_green;
								}
								newentities.add(p);
							}else{
								newentities.add(p);
							}
						}
						String[] zombiearray = info[2].split("/z/");
						for(int i = 0; i < zombiearray.length; i++){
							String[] zombieinfo = zombiearray[i].split("/");
							Zombie z = new Zombie(Integer.parseInt(zombieinfo[0]), Integer.parseInt(zombieinfo[1]), Sprite.zombie_1_idle, null);
							z.networked = true;
							z.rotation = Double.parseDouble(zombieinfo[2]);
							newentities.add(z);
						}
						gameentities = newentities;
					}else if(s.startsWith("/t/")){
						send("/r/" + id);
					}
					}catch(Exception e){
						
					}
				}
			}
		};
		recv.start();
	}
	
	private void update(){
		if(keyboard.getKey(KeyEvent.VK_ESCAPE)){
			if(ingame){
				ingame = false;
				PauseMenu.active = true;
				pause = true;
			}else{
				if(pause){
					if(OptionsMenu.active){
						OptionsMenu.active = false;
						PauseMenu.active = true;
					}else{
						ingame = true;
						PauseMenu.active = false;
						pause = false;
					}
				}
			}
			
		}
		
		if(keyboard.getKey(KeyEvent.VK_F11)){
			fullscreen = !fullscreen;
			if(fullscreen){
				((Button)OptionsMenu.get(0)).text = "FullScreen: ON";
				frame.dispose();
				frame = new JFrame(TITLE);
				frame.setSize(width*scale, height*scale);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.add(this);
				frame.setLocationRelativeTo(null);
				frame.setUndecorated(true);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setVisible(true);
			}else{
				((Button)OptionsMenu.get(0)).text = "FullScreen: OFF";
				frame.dispose();
				frame = new JFrame(TITLE);
				frame.setSize(width*scale, height*scale);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.add(this);
				frame.setVisible(true);
			}
			keyboard.resetKey(KeyEvent.VK_F11);
			this.requestFocus();
		}
		for(int i = 0; i < menues.size(); i++){
			menues.get(i).update();
		}
		if(!ingame){
			for(int i = 0; i < menuentities.size(); i++){
				menuentities.get(i).update();
			}
			if(pause){
				screen.xOffset = p.x - ((width/2)-16);
				screen.yOffset = p.y - ((height/2)-16);
			}else{
				screen.xOffset = 0;
				screen.yOffset = 0;
			}
		}
		
		if(ingame){
			for(int i = 0; i < gameentities.size(); i++){
				gameentities.get(i).update();
			}
			currentlevel.update();
			screen.xOffset = p.x - ((width/2)-16);
			screen.yOffset = p.y - ((height/2)-16);
		}
		keyboard.resetKey(KeyEvent.VK_ESCAPE);
		
		if(!pause && !ingame){
			peacefull.stop();
			menu.start();
			menu.loop(Clip.LOOP_CONTINUOUSLY);
		}
		if(pause || ingame){
			peacefull.start();
			menu.stop();
			peacefull.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		currentlevel.render(screen);
		
		if(ingame || pause){
			for(int i = 0; i < gameentities.size(); i++){
				gameentities.get(i).render(screen);
			}
		}
		
		if(!ingame){
			for(int i = 0; i < menuentities.size(); i++){
				menuentities.get(i).render(screen);
			}
		}
		
		for(int i = 0; i < menues.size(); i++){
			menues.get(i).render(screen);
		}
		
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
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
		return message.split("/e/")[0];
	}
	
	private void send(String s){
		final String string = s + "/e/";
		Thread send = new Thread("Send"){
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
	
}

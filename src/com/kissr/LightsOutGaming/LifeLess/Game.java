package com.kissr.LightsOutGaming.LifeLess;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.kissr.LightsOutGaming.LifeLess.GUI.Button;
import com.kissr.LightsOutGaming.LifeLess.GUI.Menu;
import com.kissr.LightsOutGaming.LifeLess.Graphics.Screen;
import com.kissr.LightsOutGaming.LifeLess.input.Keyboard;
import com.kissr.LightsOutGaming.LifeLess.input.Mouse;
import com.kissr.LightsOutGaming.LifeLess.utill.function;

public class Game extends Canvas implements Runnable{

	public int width = 300;
	public int height = width / 16*9;
	public int scale = 3;
	public JFrame frame;
	public static final String TITLE = "Life Less 2D alpha";
	private Thread runningthread;
	private boolean running = false;
	Screen screen;
	public Keyboard keyboard;
	Menu MainMenu;
	Menu OptionsMenu;
	boolean fullscreen;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	private ArrayList<Menu> menues = new ArrayList<Menu>();
	
	public Game(){
		frame = new JFrame(TITLE);
		frame.setSize(width*scale, height*scale);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		keyboard = new Keyboard();
		MainMenu = new Menu(10, 10);
		MainMenu.active = true;
		MainMenu.add(new Button("Start Game", this, 10, 10, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				System.out.println("Start Game");
			}}));
		
		MainMenu.add(new Button("Options", this, 10, 30, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				MainMenu.active = false;
				OptionsMenu.active = true;
			}}));
		
		MainMenu.add(new Button("Quit", this, 10, 50, new function(){

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
					frame.setVisible(false);
					//frame.setUndecorated(true);
					frame.setVisible(true);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				}else{
					b.text = "FullScreen: OFF";
					frame.setVisible(false);
					//frame.setUndecorated(false);
					frame.setVisible(true);
					frame.setExtendedState(JFrame.NORMAL);
				}
			}}));
		OptionsMenu.add(new Button("Back", this, 10, 30, new function(){

			@Override
			public void run(Button b) {
				// TODO Auto-generated method stub
				MainMenu.active = true;
				OptionsMenu.active = false;
			}}));
		
		menues.add(MainMenu);
		menues.add(OptionsMenu);
		this.addKeyListener(keyboard);
		this.addMouseListener(new Mouse());
		this.addMouseMotionListener(new Mouse());
		frame.setVisible(true);
		screen = new Screen(width, height, this);
		screen.clearcolor = Color.blue.getRGB();
		start();
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
				frame.dispose();
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
		final double nsfps = 1000000000.0 / 200.0;
		double deltaups = 0;
		double deltafps = 0;
		int frames = 0;
		int updates = 0;
		this.requestFocus();
		while(running){
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
	
	private void update(){
		if(keyboard.getKey(KeyEvent.VK_ESCAPE)){
			stop();
		}
		for(int i = 0; i < menues.size(); i++){
			menues.get(i).update();
		}
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		for(int i = 0; i < menues.size(); i++){
			menues.get(i).render(screen);
		}
		
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
}

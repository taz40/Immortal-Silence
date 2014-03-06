package taz40.lifeless;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Scanner;

import javax.swing.JFrame;

import taz40.lifeless.Util.Function;
import taz40.lifeless.entity.mob.Player;
import taz40.lifeless.graphics.Screen;
import taz40.lifeless.graphics.GUI.Button;
import taz40.lifeless.graphics.GUI.Menu;
import taz40.lifeless.input.Keyboard;
import taz40.lifeless.input.Mouse;
import taz40.lifeless.level.Level;
import taz40.lifeless.level.TileCoordinate;

public class Game extends Canvas implements Runnable {

	protected static final long serialVersionUID = 1L;
	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	protected boolean menu = true;
	private Menu MainMenu = new Menu("LifeLess");
	private Menu curMenu = MainMenu;
	protected Thread thread;
	protected JFrame frame;
	protected Keyboard key;
	protected Level level;
	protected Player player;
	protected boolean running = false;
	private String title = "LifeLess";

	protected Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	final String name;
	
	public Game() {
		name = new Scanner(System.in).nextLine();
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
		MainMenu.add(new Button("SinglePlayer", 0, 50, 300, 16, 185, 0, 3, new Function(){

			@Override
			public void run() {
				stop();
				frame.dispose();
				Game game = new Game();
				game.frame.setResizable(false);
				game.frame.setTitle(game.title);
				game.frame.add(game);
				game.frame.pack();
				game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				game.frame.setLocationRelativeTo(null);
				game.frame.setVisible(true);
				game.menu = false;
				game.start();
			}
			
		}));
		MainMenu.add(new Button("MultiPlayer", 0, 70, 300, 16, 185, 0, 3, new Function(){

			@Override
			public void run() {
				stop();
				frame.dispose();
				menu = false;
				Scanner s = new Scanner(System.in);
				String ip = s.nextLine();
				int port = s.nextInt();
				Game game = new GameMP(name, ip, port);
				game.frame.setResizable(false);
				game.frame.setTitle(game.title);
				game.frame.add(game);
				game.frame.pack();
				game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				game.frame.setLocationRelativeTo(null);
				game.frame.setVisible(true);

				game.start();
			}
			
		}));
		MainMenu.add(new Button("Exit", 0, 90, 300, 16, 185, 0, 3, new Function(){

			@Override
			public void run() {
				stop();
				frame.dispose();
			}
			
		}));
	}
	
	public void renderMenu(Graphics g){
		curMenu.render(g);
	}
	
	public void updateMenu(){
		curMenu.update();
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		final double ns2 = 1000000000.0 / 200.0;
		double delta = 0;
		double delta2 = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			delta2 += (now - lastTime) / ns2;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			while (delta2 >= 1) {
				render();
				frames++;
				delta2--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				//System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(title + " - " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
	}

	public void update() {
		if(menu){
			updateMenu();
		}else{
			key.update();
			level.update();
			if(key.isKeyPressed(KeyEvent.VK_ESCAPE)){
				stop();
				frame.dispose();
				Game game = new Game();
				game.frame.setResizable(false);
				game.frame.setTitle(game.title);
				game.frame.add(game);
				game.frame.pack();
				game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				game.frame.setLocationRelativeTo(null);
				game.frame.setVisible(true);
				game.menu = true;
				game.start();
			}
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		Graphics g = bs.getDrawGraphics();
		
		if(!menu){
		double xScroll = player.x - screen.width / 2;
		double yScroll = player.y - screen.height / 2;
		level.render((int) xScroll, (int) yScroll, screen);
		}
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.white);
		g.setFont(new Font("Verdana", 0, 50));
		if(menu){
			renderMenu(g);
		}
		//g.fillRect(Mouse.getX() - 32, Mouse.getY() - 32, 64, 64);
		//if(Mouse.getButton() != -1) g.drawString("Button: " + Mouse.getButton(), 80, 80);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}

}

package lightsoutgaming.games.lifeless.onejar.main;

import java.util.Arrays;

import taz40.lightsoutgamingengine.V1.Game;

public class LifeLessMain {
    
    public static void main(String args[]) {
        if (args == null)
            args = new String[0];
        System.out.println("LifeLess main entry point, args=" + Arrays.asList(args));
        new LifeLessMain().run();
    }
    
    // Bring up the application: only expected to exit when user interaction
    // indicates so.
    public void run() {
        System.out.println("LifeLess main is running");
        // Implement the functionality of the application. 
        Game game = new Game(800, 600, "Life Less", 130);
        game.getScreenFactory().showScreen(new LoadingScreen(game.getScreenFactory()));
        System.out.println("LifeLess OK.");
    }
    

}

package lightsoutgaming.games.lifeless.onejar.main;

import java.util.TimerTask;

public class FPSTimer extends TimerTask {

	Arena arena;
	public FPSTimer(Arena a){
		arena = a;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		arena.fps = arena.frames;
		arena.frames = 0;
		arena.istimergoing = false;
	}

}

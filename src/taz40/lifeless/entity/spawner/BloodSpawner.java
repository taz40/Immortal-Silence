package taz40.lifeless.entity.spawner;

import taz40.lifeless.entity.particle.BloodParticle;
import taz40.lifeless.entity.particle.Particle;
import taz40.lifeless.entity.spawner.Spawner.Type;
import taz40.lifeless.level.Level;

public class BloodSpawner extends Spawner {

	private int life;
	
	public BloodSpawner(int x, int y, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = 60;
		for(int i = 0; i < amount; i++){
				level.add(new BloodParticle(x, y, life));
		}

		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}

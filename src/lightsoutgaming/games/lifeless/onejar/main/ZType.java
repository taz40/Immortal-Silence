package lightsoutgaming.games.lifeless.onejar.main;

public enum ZType {
	normal(2, 1, 55, 80, 40);
	
	public int rotspeed, movementMulti, sightrange, hearingrange,smellrange;
	
	ZType(int rotspeed, int movementMulti, int sightrange, int hearingrange, int smellrange){
		this.rotspeed = rotspeed;
		this.movementMulti = movementMulti;
		this.sightrange = sightrange;
		this.hearingrange = hearingrange;
		this.smellrange = smellrange;
	}
}

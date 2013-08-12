package lightsoutgaming.games.lifeless.onejar.main;

public enum PlayerT {

	Normal(40, 30, 50);
	
	public int soundB, sightB, smellB;
	
	PlayerT(int soundB, int sightB, int smellB){
		this.soundB = soundB;
		this.sightB = sightB;
		this.smellB = smellB;
	}
	
}

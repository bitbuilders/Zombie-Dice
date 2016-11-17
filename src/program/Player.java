package program;

public class Player {
	
	private String name;
	private byte brainsEaten;
	private short survivorsCornered;
	private byte shotsFired;
	
	public void setName(String word) {
		name = word;
	}
	
	public String getName() {
		return name;
	}
	
	public void setBrains(byte numOfBrains) {
		brainsEaten = numOfBrains;
	}
	
	public byte getBrains() {
		return brainsEaten;
	}
	
	public void setSurvivorsCornered(short survivorCount) {
		survivorsCornered = survivorCount;
	}
	
	public short getSurvivorsCornered() {
		return survivorsCornered;
	}
	
	public void setShotsFired(byte shots) {
		shotsFired = shots;
	}
	
	public short getShotsFired() {
		return shotsFired;
	}
}

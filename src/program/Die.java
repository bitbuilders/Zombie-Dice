package program;

import java.util.Random;

public class Die {
	private DieColor color;
	private DieFace face;
	
	public DieColor getColor() {
		return color;
	}
	
	public DieFace getFace() {
		return face;
	}
	
	public void createDie(DieColor dc) {
		color = dc;
	}
	
	public DieFace rollDie() {
		Random rand = new Random();
		int x = rand.nextInt(6) + 1;
		
		if (x == 1 || x == 2)
			face = DieFace.FOOTPRINTS;
		else
			if (color.equals(DieColor.GREEN))
				if (x < 6)
					face = DieFace.BRAIN;
				else 
					face = DieFace.SHOTGUN_BLAST;
			else if (color.equals(DieColor.YELLOW))
				if (x < 5)
					face = DieFace.BRAIN;
				else
					face = DieFace.SHOTGUN_BLAST;
			else
				if (x < 4)
					face = DieFace.BRAIN;
				else
					face = DieFace.SHOTGUN_BLAST;
				
		return face;
	}
}

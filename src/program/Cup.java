package program;

import java.util.Random;

public class Cup {
	private static Die die;
	
	public static Die produceDice() {
		Random rand = new Random();
		int x = rand.nextInt(100) + 1;
		die = new Die();
		if (x >= 0 && x <= 46)
			die.createDie(DieColor.GREEN);
		else if (x > 46 && x <= 77)
			die.createDie(DieColor.YELLOW);
		else
			die.createDie(DieColor.RED);
		
		return die;
	}
}

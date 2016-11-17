package program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Game {
	
	private static int difficulty;
	private static Player winningPlayer = null;
	private static Player playerTurn;
	private static Player player1 = new Player();
	private static Player player2 = new Player();
	private static Player player3;
	private static Player player4;
	private static int numOfPlayers;
	private static Player[] players = new Player[4];
	private static ArrayList<Die> diceInCup = new ArrayList<Die>();
	private static ArrayList<Die> diceOutCup = new ArrayList<Die>();
	private static Die[] hand = new Die[3];
	private static BufferedReader reader;
	
	public static void main(String[] args) {
		promptForDiffuculty();
		promptForPlayers("How many players are playing?");
	}
	
	private static void promptForDiffuculty() {
		reader = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		System.out.print("What difficulty would you like to play with?\n1) Easy\n2) Medium\n3) Hard ");
		
		do {
			try {
				input = reader.readLine();
			}catch(IOException e) {
				System.out.println("Something went wrong.");
			}
			
			if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("easy")) {
				difficulty = 15;
			}
			else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("medium")) {
				difficulty = 8;
			}
			else if (input.equalsIgnoreCase("3") || input.equalsIgnoreCase("hard")) {
				difficulty = 3;
			}
			else {
				input = "";
				System.out.print("You have to enter in a number between 1 and 3. ");
			}
		}while(input.isEmpty());
	}
	
	private static void promptForPlayers(String message) {
		reader = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		numOfPlayers = 0;
		
		System.out.print(message);
		do {
			try {
				input = reader.readLine();
				numOfPlayers = Integer.parseInt(input);
			}catch(IOException e) {
				System.out.println("Something went wrong.");
			}catch(NumberFormatException e) {
				System.out.print("");
			}
			
			if (numOfPlayers < 2 || numOfPlayers > 4) {
				input = "";
				System.out.print("Enter in a number between 2 and 4. ");
			}
			else if (numOfPlayers == 0) {
				System.out.print("Enter in a valid integer between 2 and 4. ");
				input = "";
			}
		}while(input.isEmpty());
		
		for (int i = 0; i < numOfPlayers ; i++) {
			if (i == 2) {
				player3 = new Player();
				players[i] = player3;
			}
			else if(i == 3) {
				player4 = new Player();
				players[i] = player4;
			}
		}
		players[0] = player1;
		players[1] = player2;
		
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				promptForName("User " + (i + 1) + ", enter in your name: ", players[i]);
			}
		}
		switchTurn();
	}
	
	private static void promptForName(String message, Player player) {
		reader = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		
		System.out.print(message);
		do {
			try {
				input = reader.readLine();
			}catch(IOException e) {
				System.out.println("Something went wrong.");
			}
			
			if (input.isEmpty())
				if (player.equals(player1))
					input = "Player1";
				else if (player.equals(player2))
					input = "Player2";
				else if (player.equals(player3))
					input = "Player3";
				else
					input = "Player4";
			player.setName(input);
			
		}while(input.isEmpty());
	}
	
	private static void newTurn(Player player) {
		player.setShotsFired((byte) 0);
		player.setSurvivorsCornered((short) 0);
		displayPlayerInfo(player);
		
		diceInCup.clear();
		int greenCount = 0;
		int yellowCount = 0;
		for (int i = 0; i < difficulty + 10; i++) {
			Die die = new Die();
			if (greenCount < difficulty) {
				die.createDie(DieColor.GREEN);
				greenCount++;
			}
			else if (yellowCount < difficulty / 2) {
				die.createDie(DieColor.YELLOW);
				yellowCount++;
			}
			else {
				die.createDie(DieColor.RED);
			}
			diceInCup.add(die);
		}
		
		for (int i = 0; i < hand.length; i++) {
			Random rand = new Random();
			int x = rand.nextInt(diceInCup.size());
			hand[i] = diceInCup.get(x);
			diceOutCup.add(diceInCup.get(x));
			diceInCup.remove(x);
		}
		roll(player);
	}
	
	private static void displayPlayerInfo(Player player) {
		if (player1.getBrains() < 13 && player2.getBrains() < 13) {
		System.out.println("");
		System.out.println("");
		System.out.println(player.getName() + ", you have: " + player.getBrains() + " brains eaten, " + player.getSurvivorsCornered()
		+ " survivors cornered, and " + player.getShotsFired() + " shots fired.");
		System.out.println("");
		}
	}
	
	private static void roll(Player player) {
		System.out.println("");
		System.out.println("You roll your dice with hesitance...");
		
		for (int i = 0; i < hand.length; i++) {
			if (i == 0)
				System.out.println("Your first die rolled was a " + hand[i].getColor() + " die, and you rolled: " + hand[i].rollDie());
			else if (i == 1)
				System.out.println("Your second die rolled was a " + hand[i].getColor() + " die, and you rolled: " + hand[i].rollDie());
			else
				System.out.println("Your third die rolled was a " + hand[i].getColor() + " die, and you rolled: " + hand[i].rollDie());
			
			if (hand[i].getFace().equals(DieFace.BRAIN)) {
				player.setSurvivorsCornered((short) (player.getSurvivorsCornered() + 1));
			}
			else if (hand[i].getFace().equals(DieFace.SHOTGUN_BLAST)) {
				player.setShotsFired((byte) (player.getShotsFired() + 1));
			}
			
			if (player.getShotsFired() >= 3) {
				System.out.println("");
				System.out.println("The survivors fought back and escaped!");
				System.out.println("");
				switchTurn();
				break;
			}
			
		}
		displayPlayerInfo(player);
		System.out.println("");
		rollAgain(player);
	}
	
	private static void rollAgain(Player player) {
		if (player1.getBrains() < 13 && player2.getBrains() < 13) {
		reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(player.getName() + ", would you like to roll again?\n1) Yes\n2) No ");
		String input = "";
		
		do {
			try {
				input = reader.readLine();
			}catch(IOException e) {
				System.out.println("Something went wrong.");
			}
			
			if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("yes")) {
				Die[] rem = new Die[3];
				int footCount = 0;
				for (int i = 0; i < hand.length; i++) {
					if (hand[i].getFace().equals(DieFace.FOOTPRINTS)) {
						rem[i] = hand[i];
						hand[i] = null;
						footCount++;
					}
				}
				
				for (int i = 0; i < rem.length; i++) {
					if (rem[i] != null)
						hand[i] = rem[i];
				}
				
				refillCup(3 - footCount);
				
				roll(player);
			}
			else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("no")) {
				player.setBrains((byte) (player.getSurvivorsCornered() + player.getBrains()));
				player.setSurvivorsCornered((short) 0);
				player.setShotsFired((byte) 0);
				if (player.getBrains() >= 13 && winningPlayer == null) {
					System.out.println(player.getName() + " has " + player.getBrains() + " brains!");
					System.out.println("Each player has 1 more turn in order to get more brains than him/her.");
					finalTurn(player);
				}
				else {
					switchTurn();
				}
			}
			else {
				input = "";
				System.out.print("You have to enter in a valid option. 1 or 2. ");
			}
		}while(input.isEmpty());
		}
	}
	
	private static void finalRoll(Player player) {
		diceInCup.clear();
		int greenCount = 0;
		int yellowCount = 0;
		for (int a = 0; a < difficulty + 10; a++) {
			Die die = new Die();
			if (greenCount < difficulty) {
				die.createDie(DieColor.GREEN);
				greenCount++;
			}
			else if (yellowCount < difficulty / 2) {
				die.createDie(DieColor.YELLOW);
				yellowCount++;
			}
			else {
				die.createDie(DieColor.RED);
			}
			diceInCup.add(die);
		}
		
		for (int a = 0; a < hand.length; a++) {
			Random rand = new Random();
			int x = rand.nextInt(diceInCup.size());
			hand[a] = diceInCup.get(x);
			diceOutCup.add(diceInCup.get(x));
			diceInCup.remove(x);
		}
		
		System.out.println(player.getName() + ", you have: " + player.getBrains() + " brains eaten, " + player.getSurvivorsCornered()
				+ " survivors cornered, and " + player.getShotsFired() + " shots fired.");
				System.out.println("");
				System.out.println("");
				System.out.println("You roll your dice with hesitance...");
				
				for (int x = 0; x < hand.length; x++) {
					if (x == 0)
						System.out.println("Your first die rolled was a " + hand[x].getColor() + " die, and you rolled: " + hand[x].rollDie());
					else if (x == 1)
						System.out.println("Your second die rolled was a " + hand[x].getColor() + " die, and you rolled: " + hand[x].rollDie());
					else
						System.out.println("Your third die rolled was a " + hand[x].getColor() + " die, and you rolled: " + hand[x].rollDie());
					
					if (hand[x].getFace().equals(DieFace.BRAIN)) {
						player.setSurvivorsCornered((short) (player.getSurvivorsCornered() + 1));
					}
					else if (hand[x].getFace().equals(DieFace.SHOTGUN_BLAST)) {
						player.setShotsFired((byte) (player.getShotsFired() + 1));
					}
					
					if (player.getShotsFired() >= 3) {
						System.out.println("");
						System.out.println("The survivors fought back and escaped!");
						System.out.println("");
						break;
					}
					
				}
	}
	
	private static void finalAgain(Player player) {
		System.out.println("");
		System.out.println("");
		System.out.println(player.getName() + ", you have: " + player.getBrains() + " brains eaten, " + player.getSurvivorsCornered()
				+ " survivors cornered, and " + player.getShotsFired() + " shots fired.");
		
		System.out.println("");
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null && players[i] != player && players[i].getShotsFired() < 3)
				System.out.println(players[i].getName() + " has " + players[i].getBrains() + " brains.");
		}
		System.out.println("");
		
		if (player.getShotsFired() < 3) {
			String in = "";
			System.out.print(player.getName() + ", do you want to roll again?\n1) Yes\n2) No ");
			do {
				try {
					in = reader.readLine();
				}catch(IOException e) {
					System.out.println("Something went wrong.");
				}

				if (in.equalsIgnoreCase("1") || in.equalsIgnoreCase("yes")) {
					
				}
				else if (in.equalsIgnoreCase("2") || in.equalsIgnoreCase("no")) {
					player.setBrains((byte) (player.getSurvivorsCornered() + player.getBrains()));
					player.setShotsFired((byte) 3);
				}
				else {
					in = "";
					System.out.print("Enter 1 or 2 please. ");
				}
			}while(in.isEmpty());
		}
		else {
			player.setShotsFired((byte) 3);
		}
	}
	
	private static void finalTurn(Player playerToWin) {
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		
		Player winningPlayer = playerToWin;
		int count = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null && !players[i].equals(playerToWin)) {
				count++;
				String input = "";
				System.out.println("");
				System.out.print(players[i].getName() + ", to take your final rolls, just hit the enter key.");
				do {
					try {
						input = reader.readLine();
					}catch(IOException e) {
						System.out.println("Something went wrong.");
					}
					
					if (input.isEmpty())
						input = " ";
					else
						System.out.print("Just press the enter key.");
				}while(input.isEmpty());
				
				players[i].setShotsFired((byte) 0);
				players[i].setSurvivorsCornered((byte) 0);
				
				do {
					finalRoll(players[i]);
					finalAgain(players[i]);
				
				}while(players[i].getShotsFired() < 3);
				
				if (players[i].getBrains() > winningPlayer.getBrains()) {
					winningPlayer = players[i];
				}
			}
			else {
				if (count >= 3) {
					if (players[i] != null && players[i].getBrains() > winningPlayer.getBrains())
						winningPlayer = players[i];
					System.out.println(winningPlayer.getName() + " won the game with " + winningPlayer.getBrains() + " brains!");
					playAgain();
				}
			}
		}
		System.out.println(winningPlayer.getName() + " won the game with " + winningPlayer.getBrains() + " brains!");
		playAgain();
	}
	
	private static void refillCup(int diceNeeded) {
		if (diceInCup.size() >= diceNeeded) {
			for (int i = 0; i < hand.length; i++) {
				if (!hand[i].getFace().equals(DieFace.FOOTPRINTS)) {
					Random rand = new Random();
					int x = rand.nextInt(diceInCup.size());
					hand[i] = diceInCup.get(x);
					diceOutCup.add(diceInCup.get(x));
					diceInCup.remove(x);
				}
			}
		}
		else {
			for (int i = 0; i < diceOutCup.size(); i++) {
				if (diceOutCup.get(i).getFace().equals(DieFace.BRAIN)) {
					diceInCup.add(diceOutCup.get(i));
					diceOutCup.remove(i);
				}
			}
		}
	}
	
	private static void playAgain() {
		reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("");
		System.out.print("Would you guys like to play again?\n1) Yes\n2) No ");
		String input = "";
		
		do {
			try {
				input = reader.readLine();
			}catch(IOException e) {
				System.out.println("Something went wrong.");
			}
			
			if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("yes")) {
				player1 = new Player();
				player2 = new Player();
				promptForDiffuculty();
				promptForName("User 1, enter in your name: ", player1);
				promptForName("User 2, enter in your name: ", player2);
				switchTurn();
			}
			else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("no")) {
				end();
			}
			else {
				input = "";
				System.out.print("You have to enter in a valid option. 1 or 2. ");
			}
		}while(input.isEmpty());
	}
	
	private static void switchTurn() {
		if (playerTurn == null) {
			playerTurn = player1;
		}
		else {
			for (int i = 0; i < players.length; i++) {
				if (players[i] != null && players[i].equals(playerTurn) && i < numOfPlayers - 1) {
					players[i++].getName();
					playerTurn = players[i++];
				}
				else if (players[i] != null && players[i].equals(playerTurn) && i == numOfPlayers - 1) {
					playerTurn = players[0];
				}
			}
		}
		newTurn(playerTurn);
	}
	
	private static void end() {
		System.out.println("Thanks for playing!");
		System.out.println("Closing Zombie Dice...");
	}
}

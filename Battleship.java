import javax.swing.*;
/**
*
* This is Battleship. The player attempts to guess
* the coordinates of where Player Two has placed their boats.
* The Programs tells the player whether they hit the target or not.
* Player Two fires back and tries to hit the player's boats as well
* whoever sinks all 5 boats of their opponents wins the game.
*
* @author - Suu Magai
*/
public class Battleship {
/**
*	The main class that runs Tomatoboat
*
*	@param args arguments for player names and board size
*/
	public static void main(String[] args) {
		String option[] = {"Strong AI Player", "Random AI", "GUI Human", "Human Prompt"};
		int player = JOptionPane.showOptionDialog(null, "Please Choose Player One", "Player One Selection", 
												  JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
												  null, option, option[0]);
		System.out.println("You picked Player " + option[player]);	
		int player2 = JOptionPane.showOptionDialog(null, "Please Choose Player Two", "Player Two Selection", 
												  JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
												  null, option, option[0]);
		System.out.println("You picked Player " + option[player2]);	

	  	new Battleship(args, player, player2);
	} // END MAIN

/**
*	Constructor for the Tomato Boat game
*
*	@param args arguments for player names and board size
*	@param player first player selection
*	@param player2 second player selection
*/
	public Battleship(String[] args, int player, int player2) {
	  	int rows, columns;
	  	String name, opponentName;

		// Depending on whether the user puts in arguments, this sets the variables 
		// to either what the user put in, or the defaults.
	  	if (args.length == 0) {
	  		name = "You";
	  		opponentName = "Opponent";
	  		rows = 10;
	  		columns = 10;
	  	} else {
		  	name = args[0];
		    opponentName = args[1];
		    rows = Integer.parseInt(args[2]);
		    columns = Integer.parseInt(args[3]);
	  	}

	  	/*
			0: Strong Player
			1: Random AI
			2: GUI Human
			3: Human Prompt
		*/
		Player playerOne = null;
		Player playerTwo = null;


		//Depending on if the player is real or a computer, set the appriate game
		//mode

		if (player == 0) playerOne = new AIPlayer(name); 
		else if (player == 1) playerOne = new RandomPlayer(name); 
		else if (player == 2) playerOne = new GUIHumanPlayer(name); 
		else playerOne = new HumanPlayer(name); 

		if (player2 == 0) playerTwo = new AIPlayer(name);
		else if (player2 == 1) playerTwo = new RandomPlayer(name);
		else if (player2 == 2) playerTwo = new GUIHumanPlayer(name);
		else playerTwo = new HumanPlayer(name);

		if (((player == 2 || player == 3) && (player2 == 2 || player2 == 3)) ||
			((player == 0 || player == 1) && (player2 == 0 || player2 == 1))) {
			new Game(playerOne, playerTwo, rows, columns, name, opponentName);
		}

		if (((player == 0 || player == 1) && (player2 == 2 || player2 == 3))) {
 			new AIGame(playerTwo, playerOne, rows, columns, opponentName, name);
		} else if (((player == 2 || player == 3) && (player2 == 0 || player2 == 1))) {
			new AIGame(playerOne, playerTwo, rows, columns, name, opponentName);
		}

	} // END TOMATOBOAT
} // END CLASS

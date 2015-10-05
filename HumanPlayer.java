/**
*
* This is Battleship. The player attempts to guess
* the coordinates of where the opponent has placed their boats.
* The Programs tells the player whether they hit the target or not.
* The opponent fires back and tries to hit the player's boats as well
* whoever sinks all 5 boats of their opponents wins the game.
*
* @author - Suu Magai
*/

import javax.swing.*;

public class HumanPlayer extends Player {

	private Move move;
	private String firingLocation, tens;
	private int letter;
	private int number;
	private String name;

	/**
	*	constructor for HumanPlayer class
	*
	*	@param name name of the player
	*/
	public HumanPlayer(String name) {
		super(name);
	}

	/**
	*	The human player enters a location to fire, and their move is returned and used
	*		
	*	@param board the player's game board
	*	@return returns the move they entered
	*/
	public Move getMove(GUIBoard board) { 
		firingLocation = JOptionPane.showInputDialog(super.getPlayername() + ", please select a location to fire:");
		
		// trims String, assigns letter
		firingLocation = firingLocation.trim();
		letter = (firingLocation.toUpperCase().charAt(0) - 65); // A - 65 = 0

		// Checks if there is a number in the tens place, or not
		if (firingLocation.length() > 2) {
			try {
				tens = firingLocation.substring(1,3);
				number = Integer.parseInt(tens) - 1;
			} catch (NumberFormatException e) {
				System.out.println("Exception " + e + " caught");
				move = new Move(-1,-1);
				return move;
			}		
		} else {
			number = (firingLocation.charAt(1) - 49); // 1 - 49 = 0
		}

		move = new Move(letter, number);

		return move;  
	}
}
/**
*
* This is Battleship. The player attempts to guess
* the coordinates of where the computer has placed their boats.
* The Programs tells the player whether they hit the target or not.
* The computer fires back and tries to hit the player's boats as well
* whoever sinks all 5 boats of their opponents wins the game.
*
* @author - Suu Magai
*/

import java.util.*;

public class RandomPlayer extends Player {

	private Move move;
	private String name;
	private Random random;

	/**
	*
	*	Constructor for the RandomPlayer class
	*/
	public RandomPlayer(String name) { super(name); }

	/**
	*
	*	returns the computer move that randomly chooses a row and column to fire at
	*
	*	@param board the player's game board
	*	@return returns the move entered by computer
	*/
	
	public Move getMove(GUIBoard board) { 
		int letter, number;

		random = new Random();

		letter = random.nextInt(board.getRows());
		number = random.nextInt(board.getColumns());

		move = new Move(letter, number);

		return move; 
	}
}
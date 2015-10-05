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

public abstract class Player {
	private String name;

	/**
	*	The Constructor for the Player class
	*
	*	@param aName the player's name
	*/
	public Player(String aName) {
		name = aName;
	}

	/**
	*	returns the name of the player
	*
	*	@return player name
	*/
	public String getPlayername() { return name; }


	/**
	*	Overrides toString and returns the name of the player
	*
	*	@return player name
	*/
	public String toString() { return name; }

	/**
	*	Abstract method that gets the move from the board
	*	
	*	@param board the player's game board
	*/
	abstract public Move getMove(GUIBoard board);
}
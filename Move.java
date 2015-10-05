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

public class Move {
	
	private int column;
	private int row;

	/**
	* constructor for Move class
	*
	*	@param nRow row that the player wants to hit
	*	@param nColumn column that the player wants to hit
	*/
	public Move(int nRow, int nColumn) {
		row = nRow;
		column = nColumn;
	}

	/**
	*	returns the row to hit on board
	*
	*	@return the firing row
	*/
	public int getRow() { return row; }

	/**
	*	returns the column to hit on board
	*
	*	@return the firing column
	*/
	public int getColumn() { return column; }

}
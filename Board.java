/**
*
* This is Batteship. The player attempts to guess
* the coordinates of where the computer has placed their boats.
* The Programs tells the player whether they hit the target or not.
* The computer fires back and tries to hit the player's boats as well
* whoever sinks all 5 boats of their opponents wins the game.
*
* @author - Suu Magai
*/


import java.util.*;

public class Board {
	private int bRows, bColumns;
	private int mRow, mColumn;
	private char [][] game; 
	private boolean didIWin;
	private Random random;
	private char aChar;
	private boolean thisIsPlayerBoard;
/**
*	Constuctor for the board class
*
*	@param row number of rows in the game
*	@param column number of columns in the game
*	@param bool whether it is the player or not
*
*/
	public Board(int row, int column, int bool) {
		this.bRows = row;
		this.bColumns = column;
		didIWin = false;
		game = new char[bRows][bColumns];
		if (bool == 1) { thisIsPlayerBoard = true; }
			else if (bool == 0) { thisIsPlayerBoard = false; }
		//Initializing board
		for (int i = 0; i < bRows; i++) {
			for (int k = 0; k < bColumns; k++) {
				game[i][k] = 'E';
			}
		}
	}

/**
*	returns the number of rows in this board
*
*	@return rows given at start of program
*/
	public int getRows() {
		return bRows;
	}

/**
*	returns the number of columns in this board
*
*	@return columns given at start of program
*/
	public int getColumns() { 
		return bColumns;
	}

	/** 
	*	Returns ‘H’, ‘M’, ‘B’, or ‘E’ for Hit,
	*	Miss, Boat, or Empty respectively.
	*
	*	@param row to set value
	*	@param column to set value
	*	@return rows and column of the game board
	*/
	public char getValueAtLocation(int row, int column) { 
		return game[row][column];
	}

	/**
	*	 sets the square on the board to value.
	*
	*	@param row to set value
	*	@param column to set value
	*	@param value to set the board location to
	*
	*/
	public void setValueAtLocation(int row, int column, char value) {
		game[row][column] = value;
	}

	/**
	*	Returns a boolean value true if there is at least one square  
	*	with a boat that has not been hit, false otherwise.		    
	*	
	*	@return true or false
	*/
	public boolean floatingBoat() { 	
		// counter keeps track of unsunken parts of boat
		// if count is zero, there are no boats left
		int toGo = 0;
		for (int d = 0; d < bRows; d++) {
			for (int i = 0; i < bColumns; i++) {
				if (game[d][i] == 'B') {
					toGo++;
					didIWin = false;
				} 
				if (toGo == 0) {
					didIWin = true;
				}	
			} 
		}
		return didIWin; 
	}

	/**
	*	returns true if this is the board 		
	*	containing player’s boats,false otherwise
	*
	* 	@return true or false
	*/
	public boolean playerBoard() { 
		return thisIsPlayerBoard;
	}

	/**
	*	Places five boats on the board in random 		
	*	locations and directions (up/down or left/right). 
	*	The sizes of the boats are 5, 4, 3, 3, and 2.	
	*/
	public void randomizeBoatPlacement() {
		boatSize(5);
		boatSize(4);
		boatSize(3);
		boatSize(3);
		boatSize(2);
	}

	/**
	*	constructs the boat with the size given 
	*	@param size size to construct the boats to
	*/

	public void boatSize(int size) {
		int row, column, aCase;
		boolean worked = false;
		random = new Random();
		
		while (worked == false) {
			// assigns random row and column
			row = random.nextInt(this.getRows());
			column = random.nextInt(this.getColumns());
			// random case - vertical and horizontal
			aCase = random.nextInt(2);
			boolean aBoat = isThereABoat(aCase, size, row, column);

			///HORIZONTAL CASE///
			if (aCase == 0  && aBoat == false) {
				for (int i = column; i < (column + size); i++) {
					setValueAtLocation(row, i, 'B');
					worked = true;
				}
			} else if (aCase == 1  && aBoat == false) { 	/// VERTICAL CASE ///
				for (int i = row; i < (row + size); i++) {
					setValueAtLocation(i, column, 'B');
					worked = true;
				}
			}
		}
	}

	/**
	*	Returns true if there is a boat in the way, false spaces are empty
	*
	*	@param	aCase case in which to make the boat either vertical or horizontal
	*	@param	size size of the boat
	*	@param	row location of the row
	*	@param	column location of the column
	*	@return true or false
	*
	*/
	public boolean isThereABoat(int aCase, int size, int rows, int columns) {
		//  case 0 or 1, looks and sees if there are any boats in the way
		if (aCase == 0) {
			if ((columns + size) <= this.getColumns() - 1) {
				for (int i = columns; i < (columns + size); i++) {
					if (game[rows][i] == 'B') { return true; } 
				}
			} else { return true; }

		} else if (aCase == 1) {
			if ((rows + size) <= this.getRows() - 1) {
				for (int i = rows; i < (rows + size); i++) {
					if (game[i][columns] == 'B') { return true; } 
				}
			} else { return true; }
		}

		return false;

	}

	/**
	*	Updates the board and returns true if the player can move in the 	  
	*	requested location, false otherwise (return false if move is null)
	*
	*	@param move the players move; row and column
	*	@return true or false
	*/
	public boolean getMove(Move move) {
		// checks if move is null
		if (move == null) { return false;}
		// checks 0 < move < max rows, and checks 0 < move < max rcolumns
		// if all is well, assigns value returns true.
		if (move.getRow() < 0 || move.getRow() > this.getRows()) {
			return false;
		} else if (move.getColumn() < 0 || move.getColumn() > this.getColumns()) {
			return false;
		} else {
			aChar = getValueAtLocation(move.getRow(), move.getColumn());

			if (aChar == 'E') { aChar = 'M'; }
			if (aChar == 'B') { aChar = 'H'; }

			setValueAtLocation(move.getRow(), move.getColumn(), aChar);

			return true;
		}
	}
}
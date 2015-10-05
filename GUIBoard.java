import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.Component.*;
import java.util.*;

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
public class GUIBoard implements ActionListener {
	int rows, columns;
	char aCase;
	TButton tomatoButtons[][];
	boolean thisIsPlayerBoard;
	int moveRow, moveColumn;
	char aChar;
	JFrame frame;
	boolean didIWin;
	int previousRow, previousCol;
	PriorityBoard pBoard;
	boolean twoBoatFound;

	/**
	*	Constructor for the GUIBoard
	*
	*	@param name name of the player
	*	@param pRows number of rows in the game
	*	@param pColumns number of columns in the game
	*	@param locationX x location of the frame
	*	@param locationY y location of the frame
	*	@param dimensionX x dimension of the size of frame
	*	@param dimensionY y dimension of the size of frame
	*	@param bool whether it is the player or not
	*
	*/
	public GUIBoard(String name, int pRows, int pColumns, int locationX, int locationY, int dimensionX, int dimensionY, int bool) {
		frame = new JFrame(name);
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel(new BorderLayout());
		frame.setPreferredSize(new Dimension(dimensionX,dimensionY));
		frame.setLocation(locationX, locationY);
		frame.add(panel2);
		panel2.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		if (bool == 1) { thisIsPlayerBoard = true; }
			else if (bool == 0) { thisIsPlayerBoard = false; }
		twoBoatFound = false;

		this.rows = pRows;
		this.columns = pColumns;

		panel.add(new JButton("TB"));
		panel.setLayout(new GridLayout(rows + 1, columns + 1));
		for (int i = 1; i < (rows + 1); i++) {
			JButton gridButton = new JButton("" + i);
			panel.add(gridButton);
		}

		JButton left[] = new JButton[rows];
		char aChar = 'A';
		tomatoButtons = new TButton[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int k = 0; k < columns; k++) {
				if (k == 0) {
					left[k] = new JButton("" + (char)(i+65));
					panel.add(left[k]);
				} 
				tomatoButtons[i][k] = new TButton("", thisIsPlayerBoard);
				tomatoButtons[i][k].row(i);
				tomatoButtons[i][k].column(k);
				tomatoButtons[i][k].addActionListener(this);
				panel.add(tomatoButtons[i][k]);
			}
		}

		reset();
		if (dimensionX != 0 && dimensionY != 0) {
			frame.pack();
			frame.setVisible(true);
		}
	}
	/**
	*	the action performed when a tomatoButton is clicked
	*
	*	@param e the ActionEvent that respongs to button click
	*/
	public void actionPerformed(ActionEvent e){
		if( e.getSource() instanceof TButton && thisIsPlayerBoard == true) {
			//System.out.println("Row: " + (((TButton)e.getSource()).getRow()));
			//System.out.println("Column: " + (((TButton)e.getSource()).getColumn()));
			setMove(((TButton)e.getSource()).getRow(), ((TButton)e.getSource()).getColumn());
			//((TButton)e.getSource()).status('H');
   		}
   		//System.out.println(((TButton)e.getSource()).getStatus());

	}

	/**
	*	returns the number of rows in this board
	*
	*	@return rows given at start of program
	*/
	public int getRows() { return rows; }

	/**
	*	returns the number of columns in this board
	*
	*	@return columns given at start of program
	*/
	public  int getColumns() { return columns; }

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
		Random random = new Random();
		
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
	*	returns true if this is the board 		
	*	containing player’s boats,false otherwise
	*
	* 	@return true or false
	*/
	public boolean playerBoard() { return thisIsPlayerBoard; }
	/**
	*	Returns true if there is a boat in the way, false spaces are empty
	*
	*	@param	aCase case in which to make the boat either vertical or horizontal
	*	@param	size size of the boat
	*	@param	rows location of the row
	*	@param	columns location of the column
	*	@return true or false
	*
	*/
	public boolean isThereABoat(int aCase, int size, int rows, int columns) {
		//  case 0 or 1, looks and sees if there are any boats in the way
		if (aCase == 0) {
			if ((columns + size) <= this.getColumns() - 1) {
				for (int i = columns; i < (columns + size); i++) {
					if (tomatoButtons[rows][i].getStatus() == 'B') { return true; } 
				}
			} else { return true; }

		} else if (aCase == 1) {
			if ((rows + size) <= this.getRows() - 1) {
				for (int i = rows; i < (rows + size); i++) {
					if (tomatoButtons[i][columns].getStatus() == 'B') { return true; } 
				}
			} else { return true; }
		}

		return false;

	}

	/** 
	*	Returns ‘H’, ‘M’, ‘B’, or ‘E’ for Hit,
	*	Miss, Boat, or Empty respectively.
	*
	*	@param row to get value
	*	@param column to get value
	*	@return rows and column of the game board
	*/
	public char getValueAtLocation(int row, int column) {
		try {
			return tomatoButtons[row][column].getStatus();
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("getValueAtLocation did not work at (" + row + "," + column + ")");
			return 'N';
		}
		
	}

	/** 
	*	sets ‘H’, ‘M’, ‘B’, or ‘E’ for Hit,
	*	Miss, Boat, or Empty respectively.
	*
	*	@param row to set value
	*	@param column to set value
	*	@param value of button
	*/
	public void setValueAtLocation(int row, int column, char value) { tomatoButtons[row][column].status(value); }

	/**
	*	resets the row and column of move
	*
	*/
	public void reset() { 
		moveRow = -1;
		moveColumn = -1; 
	}
	public boolean moveBoolean() {
		if (moveRow == -1 && moveColumn == -1) { return false; } 
			else { return true; }
	}

	public void setMove(int row, int column) { 
		moveRow = row;
		moveColumn = column; 
	}

	/**
	*	Returns row of move
	*
	*	@return row of move
	*/
	public int moveRow() { return moveRow; }

	/**
	*	Returns column of move
	*
	*	@return column of move
	*/
	public int moveColumn() { return moveColumn; }

	/**
	*	Updates the board and returns true if the player can move in the 	  
	*	requested location, false otherwise (return false if move is null)
	*
	*	@param move the players move; row and column
	*	@return true or false
	*/
	public boolean getMove(Move move) {
		// checks if move is null
		if (move == null) { return false; }
		// checks 0 < move < max rows, and checks 0 < move < max rcolumns
		// if all is well, assigns value returns true.
		if (move.getRow() < 0 && move.getColumn() < 0) { 
			return false; 
		} else {
			aChar = getValueAtLocation(move.getRow(), move.getColumn());

			if (aChar == 'E') { aChar = 'M'; }
			if (aChar == 'B') { aChar = 'H'; }

			setValueAtLocation(move.getRow(), move.getColumn(), aChar);

			return true;
		}
	}

	/**
	* 	The smallest boat has been hit if there are two hits in a line 
	*	with misses on either end, and there are at least three misses 
	*	beside those two hits not counting the miss on either end of the line.
	*
	*	@param row row of board
	*	@param col column of board
	*	@return true or false
	*/
	public boolean twoBoatFound(int row, int col) {
		int count = 0;
		/// HORIZONTAL CASE
		
		if ((getValueAtLocation(row,col) == 'H') &&
		(col == 0 || getValueAtLocation(row,col - 1) == 'M') &&
		(getValueAtLocation(row,col + 1) == 'H') &&
		((col == (columns-1)) || getValueAtLocation(row,col + 2) == 'M')) {
			if ((row+1 < rows) && getValueAtLocation(row+1,col) == 'M') count++;
			if ((row+1 < rows) && (col+1 < columns) && getValueAtLocation(row+1,col+1) == 'M') count++;
			if ((row-1 >= 0) && getValueAtLocation(row-1,col) == 'M') count++;
			if ((row-1 >= 0) && (col+1 < columns) && getValueAtLocation(row-1, col+1) == 'M') count++;
		
			if (count >= 3) {
				System.out.println("\nTWO BOAT FOUND\n");
				twoBoatFound = true;
			} 
			//twoBoatFound = true;
		} else {
			twoBoatFound = false;
		}
		
		//VERTICAL CASE
		count = 0;
		if ((row == 0 || getValueAtLocation(row - 1, col) == 'M') &&
		(getValueAtLocation(row,col) == 'H') &&
		(getValueAtLocation(row + 1, col) == 'H') &&
		((row == (row-1)) || getValueAtLocation(row + 2, col) == 'M')) {
			if ((col+1 < columns) && getValueAtLocation(row,col+1) == 'M') count++;
			if ((row+1 < rows) && (col+1 < columns) && getValueAtLocation(row+1,col+1) == 'M') count++;
			if ((col-1 >= 0) && getValueAtLocation(row,col-1) == 'M') count++;
			if ((row+1 < rows) && (col-1 >= 0) && getValueAtLocation(row+1, col-1) == 'M') count++;
		
			if (count >= 3) {
				System.out.println("\nTWO BOAT FOUND\n");
				twoBoatFound = true;
			}
		} else {
			twoBoatFound = false;
		}

		return twoBoatFound;

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
		for (int d = 0; d < getRows(); d++) {
			for (int i = 0; i < getColumns(); i++) {
				if (tomatoButtons[d][i].getStatus() == 'H') {
					toGo++;
					didIWin = false;
				} 
				if (toGo == 17) {
					didIWin = true;
				}	
			} 
		}
		return didIWin; 
	}

	/**
	*	gets previous move for board
	*
	*	@param move sets previous row and previous column
	*/
	public void previousMove(Move move) {
		previousRow = move.getRow();
		previousCol = move.getColumn();
	}

	/**
	*	sets priority board so that the AI knows where to hit
	*
	*	@param ppBoard the board filled with hit priorities
	*/
	public void setPriorityBoard(PriorityBoard ppBoard) {
		this.pBoard = ppBoard;
	}

	/**
	*	Returns priority board so that the AI knows where to hit
	*
	*	@return priority
	*/
	public PriorityBoard getPriorityBoard() { return pBoard; }

	/**
	*	Returns previous row
	*
	*	@return previous row
	*/
	public int previousRow() { return previousRow; }

	/**
	*	Returns previous column
	*
	*	@return previous column
	*/
	public int previousCol() { return previousCol; }

	/**
	*	repaints the buttons in the frame
	*/
	public void repaintBoard() {
		frame.repaint();
	}
}
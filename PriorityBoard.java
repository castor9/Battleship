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
public class PriorityBoard implements ActionListener {
	int rows, columns;
	char aCase;
	TButton priorityButtons[][];
	boolean thisIsPlayerBoard;
	int moveRow, moveColumn;
	char aChar;
	JFrame frame;
	boolean didIWin;
	Move move;

	/**
	*	Constructor for the PrioritBoard
	*
	*	@param name name of the player
	*	@param pRows number of rows in the game
	*	@param pColumns number of columns in the game
	*	@param locationX x location of the frame
	*	@param locationY y location of the frame
	*	@param dimensionX x dimension of the size of frame
	*	@param dimensionY y dimension of the size of frame
	*
	*/
	public PriorityBoard(String name, int pRows, int pColumns, int locationX, int locationY, int dimensionX, int dimensionY) {
		frame = new JFrame(name);
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel(new BorderLayout());
		frame.setPreferredSize(new Dimension(dimensionX,dimensionY));
		frame.setLocation(locationX, locationY);
		frame.add(panel2);
		panel2.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		priorityButtons = new TButton[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int k = 0; k < columns; k++) {
				if (k == 0) {
					left[k] = new JButton("" + (char)(i+65));
					panel.add(left[k]);
				} 
				priorityButtons[i][k] = new TButton("1");
				priorityButtons[i][k].addActionListener(this);
				panel.add(priorityButtons[i][k]);
			}
		}
		if (dimensionX != 0 && dimensionY != 0) {
			frame.pack();
			frame.setVisible(true);
		}	
	}
	public void actionPerformed(ActionEvent e){}
	/**
	*	--	If nothing else is known about the square then assign a priority of 1
	*	--	If the square is adjacent to a HIT, assign a priority of 4
	*	--	If the square is adjacent to a HIT, and in line with at least one other HIT assign a priority
	*		of 5
	*	--	If the square is adjacent to a MISS, assign a score of zero unless adjacent to another
	*		square with priority zero. This prevents assigning two zeros in a row where the boat
	*		of size two could fit.
	*	--	If the smallest boat is already found then adapt the prior rule for three instead of two.
	*		When these rules are in conflict (eg: hit, space, miss) use the more recent move to determine the priority.
	*
	*	@param row row of button
	*	@param col column of button
	*/
	public void changePriority(int row, int col, char value) {
		if ((col + 1) <= (columns - 1)  && getValueAtLocation(row, col + 1) == 'M' ||
			(col - 1) >= 0 && getValueAtLocation(row, col - 1) == 'M' ||
			(row + 1) <= (rows - 1) && getValueAtLocation(row + 1, col) == 'M' ||
			(row - 1) >= 0 && getValueAtLocation(row - 1, col) == 'M') setPriorityAtLocation(row, col, 0);

		if ((col + 1) <= (columns - 1)  && getValueAtLocation(row, col + 1) == 'H' ||
			(col - 1) >= 0 && getValueAtLocation(row, col - 1) == 'H' ||
			(row + 1) <= (rows - 1) && getValueAtLocation(row + 1, col) == 'H' ||
			(row - 1) >= 0 && getValueAtLocation(row - 1, col) == 'H') setPriorityAtLocation(row, col, 4);

		if ((col + 2) <= (columns - 1)  && getValueAtLocation(row, col + 2) == 'H' && getValueAtLocation(row,col + 1) == 'H' ||
			(col - 2) >= 0 && getValueAtLocation(row, col - 2) == 'H' && getValueAtLocation(row,col - 1) == 'H'  ||
			(row + 2) <= (rows - 1) && getValueAtLocation(row + 2, col) == 'H' && getValueAtLocation(row + 1,col) == 'H' ||
			(row - 2) >= 0 && getValueAtLocation(row - 2, col) == 'H'  && getValueAtLocation(row - 1,col) == 'H') setPriorityAtLocation(row, col, 5);

			nearZero(row,col);
	}

	/** 
	*	Returns ‘H’, ‘M’, ‘B’, or ‘E’ for Hit,
	*	Miss, Boat, or Empty respectively.
	*
	*	@param row to get value
	*	@param column to get value
	*	@return rows and column of the game board
	*/
	public char getValueAtLocation(int row, int column) { return priorityButtons[row][column].getStatus(); }
	/** 
	*	Returns respective priority
	*
	*	@param row to get value
	*	@param column to get value
	*	@return priority to paint on the priority board
	*/
	public int getPriorityAtLocation(int row, int col) { return priorityButtons[row][col].getPriority(); }
	/** 
	*	sets ‘H’, ‘M’, ‘B’, or ‘E’ for Hit,
	*	Miss, Boat, or Empty respectively.
	*
	*	@param row to set value
	*	@param column to set value
	*	@param value of button
	*/
	public void setValueAtLocation(int row, int column, char value) { priorityButtons[row][column].status(value); }
	/** 
	*	sets the priority in the location
	*
	*	@param row to set value
	*	@param column to set value
	*	@param value of priority for the button
	*/
	public void setPriorityAtLocation(int row, int column, int priority) { priorityButtons[row][column].setPriority(priority); }

	/**
	* checks to see if button is near a zero, so it can change the priority to the appropriate number
	*
	*	@param row row of button
	*	@param col column of button
	*/
	public void nearZero(int row, int col) {
		try {
			if (getPriorityAtLocation(row, col - 1) == 0 &&
				(getPriorityAtLocation(row, col) != 5) &&
				(getPriorityAtLocation(row, col) != 4)) setPriorityAtLocation(row, col, 1);
			if (getPriorityAtLocation(row, col + 1) == 0 &&
				(getPriorityAtLocation(row, col) != 5) &&
				(getPriorityAtLocation(row, col) != 4)) setPriorityAtLocation(row, col, 1);
			if (getPriorityAtLocation(row - 1, col) == 0 &&
				(getPriorityAtLocation(row, col) != 5) &&
				(getPriorityAtLocation(row, col) != 4)) setPriorityAtLocation(row, col, 1);
			if (getPriorityAtLocation(row + 1, col) == 0 &&
				(getPriorityAtLocation(row, col) != 5) &&
				(getPriorityAtLocation(row, col) != 4)) setPriorityAtLocation(row, col, 1); 
		} catch (ArrayIndexOutOfBoundsException e) {
			//System.out.println("Error in nearZero at (row,col): (" + row + "," + col + ")");
		}
	}

	/**
	*	checks to see if board contains the priority number
	*
	*	@param num number priority to search
	*	@return true or false
	*/
	public boolean contains(int num) {
		int counter = 0;
		for (int i = 0; i < rows; i++) {
			for (int k = 0; k < columns; k++) {
				if (getPriorityAtLocation(i,k) == num && getValueAtLocation(i,k) == 'E' ) {
					counter++;
				}
			}
		}
		if (counter > 0) {
			return true;
		} else {
			return false;
		}
		
	}

	/**
	*	returns the move depending on the priority. it is selected by chance 1/n
	*
	*	@param num number priority to search
	*	@return move which to play on GUIboard
	*/
	public Move getMove(int num) {
		Random random = new Random();
		double n = 1;
		double chance;
		for (int i = 0; i < rows; i++) {
			for (int k = 0; k < columns; k++) {
				if (getPriorityAtLocation(i,k) == num) {
					if (n == 1) move = new Move(i,k);
					chance = random.nextDouble();
					if (chance <= 1/n && n != 1) {
						move = new Move(i,k);
					}
					n++;
				}
			}
		}
		return move;
	}

	/**
	*	repaints the buttons in the frame
	*/
	public void repaintBoard() { frame.repaint(); }
}

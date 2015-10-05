import javax.swing.*;
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
public class Game {

	Move playerMove, playerTwoMove;
	char aChar;
	boolean playerWin, playerTwoWin;

	public Game(Player playerOne, Player playerTwo, int rows, int columns, String name, String opponentName) {
		GUIBoard boardYouPlayOn = new GUIBoard(name, rows,columns,600,0,390,390, 1);
		GUIBoard oppPlaysThis = new GUIBoard(opponentName,rows,columns,0,0,390,390, 0);

		GUIBoard hisHiddenBoard = new GUIBoard(opponentName,rows,columns,600,600,390,390, 0);
		GUIBoard yourHiddenBoard = new GUIBoard(name, rows, columns,0,600,390,390, 1);

		PriorityBoard pb1 = new PriorityBoard("AI priority Board",rows,columns,0,600,0,0);
		PriorityBoard pb2 = new PriorityBoard("AI priority Board",rows,columns,900,600,0,0);
		
		boardYouPlayOn.setPriorityBoard(pb1);
		yourHiddenBoard.setPriorityBoard(pb2);

		// this randomizes the boat locations on the boards
		oppPlaysThis.randomizeBoatPlacement();
		hisHiddenBoard.randomizeBoatPlacement();

		playerWin = false;
		playerTwoWin = false;
		
		// playerMove gets move from the kill board. Handles null value and cancel button. If player has already used move area,
		// this makes move false as well.
		do {
			// input loop. Only exits if move input is valid
			do {
				do {
				// playerMove gets move from the kill board. Handles null value and cancel button. If player has already used move area,
				// this makes move false as well.
					try {
						playerMove = playerOne.getMove(boardYouPlayOn);
					} catch(NullPointerException e) {
						int option = JOptionPane.showConfirmDialog(null, "Are your sure you want to quit?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
						if (option == JOptionPane.YES_OPTION) {
							System.exit(0);
						}
					} catch(ArrayIndexOutOfBoundsException e) {
						System.out.println("In");
						playerMove = new Move(-1,-1);
					}
					if (boardYouPlayOn.getValueAtLocation(playerMove.getRow(), playerMove.getColumn()) == 'H' ||
						boardYouPlayOn.getValueAtLocation(playerMove.getRow(), playerMove.getColumn()) == 'M') { 
						playerMove = new Move(-1,-1);
					} 
				} while(boardYouPlayOn.getMove(playerMove) == false); // END DO WHILE
				// assigns char value to opponentBoard
				if (boardYouPlayOn.getMove(playerMove) == true) {
					if (hisHiddenBoard.getValueAtLocation(playerMove.getRow(), playerMove.getColumn()) == 'B') {
						boardYouPlayOn.setValueAtLocation(playerMove.getRow(), playerMove.getColumn(), 'H');
					} else {
						boardYouPlayOn.setValueAtLocation(playerMove.getRow(), playerMove.getColumn(), 'M');	
					}	
				}

				
				aChar = boardYouPlayOn.getValueAtLocation(playerMove.getRow(), playerMove.getColumn());
				hisHiddenBoard.setValueAtLocation(playerMove.getRow(), playerMove.getColumn(), aChar);
				for (int i = 0; i < rows; i++) {
					for (int k = 0; k < columns; k++) {
						boardYouPlayOn.twoBoatFound(i,k);
					}
				}

				pb1.setValueAtLocation(playerMove.getRow(), playerMove.getColumn(), aChar);
				for (int i = 0; i < rows; i++) {
					for (int k = 0; k < columns; k++) {
						aChar = boardYouPlayOn.getValueAtLocation(i,k);
						pb1.changePriority(i, k, aChar);
					}
				}
				
				hisHiddenBoard.repaintBoard();
				boardYouPlayOn.repaintBoard();
				//pb1.repaintBoard();
				playerWin = boardYouPlayOn.floatingBoat();
			} while(boardYouPlayOn.getValueAtLocation(playerMove.getRow(), playerMove.getColumn()) == 'H' &&
					playerWin == false);
			
			////////////// PLAYER 2 ///////////////////

			// if player misses, its player 2's turn
			do {
				// input loop. Only exits if move input is valid
				do {
					// playerMove gets move from the kill board. Handles null value and cancel button. If player has already used move area,
					// this makes move false as well.
					playerTwoMove = playerTwo.getMove(yourHiddenBoard);
					if (yourHiddenBoard.getValueAtLocation(playerTwoMove.getRow(), playerTwoMove.getColumn()) == 'H' ||
						yourHiddenBoard.getValueAtLocation(playerTwoMove.getRow(), playerTwoMove.getColumn()) == 'M') { 
						playerTwoMove = new Move(-1,-1);
					} 
				} while(yourHiddenBoard.getMove(playerTwoMove) == false); // END DO WHILE
				// assigns char value to opponentBoard
				if (yourHiddenBoard.getMove(playerTwoMove) == true) {
					if (oppPlaysThis.getValueAtLocation(playerTwoMove.getRow(), playerTwoMove.getColumn()) == 'B') {
						yourHiddenBoard.setValueAtLocation(playerTwoMove.getRow(), playerTwoMove.getColumn(), 'H');
					} else {
						yourHiddenBoard.setValueAtLocation(playerTwoMove.getRow(), playerTwoMove.getColumn(), 'M');	
					}	
				}
				
				aChar = yourHiddenBoard.getValueAtLocation(playerTwoMove.getRow(), playerTwoMove.getColumn());
				oppPlaysThis.setValueAtLocation(playerTwoMove.getRow(), playerTwoMove.getColumn(), aChar);
				pb2.setValueAtLocation(playerTwoMove.getRow(), playerTwoMove.getColumn(), aChar);
				for (int i = 0; i < rows; i++) {
					for (int k = 0; k < columns; k++) {
						aChar = oppPlaysThis.getValueAtLocation(i,k);
						pb2.changePriority(i, k, aChar);
					}
				}
				
				oppPlaysThis.repaintBoard();
				yourHiddenBoard.repaintBoard();

				playerTwoWin = oppPlaysThis.floatingBoat();
			} while (yourHiddenBoard.getValueAtLocation(playerTwoMove.getRow(), playerTwoMove.getColumn()) == 'H' &&
					 playerTwoWin == false);

		} while (playerWin == false && playerTwoWin == false);	// END MAIN DO WHILE
		
		// Win mesage depending on who wins the game
		if (playerWin) {
			JOptionPane.showMessageDialog(null, playerOne.toString() + " sunk all the boats! " + playerOne.toString() + " won the game!");
		} else {
			JOptionPane.showMessageDialog(null, playerTwo.toString() + " sunk all the boats!" + playerTwo.toString() + " won the game!");
		}
	} // END GAME
} // END CLASS
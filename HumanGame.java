import javax.swing.*;
public class HumanGame {
	int rows, columns;
	String name, opponentName;
	Move playerMove, playerTwoMove;
	char aChar;
	boolean playerWin, playerTwoWin;

	public HumanGame(Player playerOne, Player playerTwo, int rows, int columns, String name, String opponentName) {
		GUIBoard boardYouPlayOn = new GUIBoard(name + ": You",rows,columns,600,0,390,390, 1);
		GUIBoard oppPlaysThis = new GUIBoard("AI",rows,columns,0,0,390,390, 0);

		GUIBoard hisHiddenBoard = new GUIBoard("AI: small board",rows,columns,600,600,390,390, 0);
		GUIBoard yourHiddenBoard = new GUIBoard("You: Small board", rows, columns,0,600,390,390, 1);
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
					playerMove = playerOne.getMove(boardYouPlayOn);
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
				
				hisHiddenBoard.repaintBoard();
				boardYouPlayOn.repaintBoard();
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

				oppPlaysThis.repaintBoard();
				yourHiddenBoard.repaintBoard();
				playerTwoWin = oppPlaysThis.floatingBoat();
			} while (yourHiddenBoard.getValueAtLocation(playerTwoMove.getRow(), playerTwoMove.getColumn()) == 'H' &&
					 playerTwoWin == false);


			// Sees if there are still boats on the players' boards, returns boolean to see if they have won
		} while (playerWin == false && playerTwoWin == false);	// END MAIN DO WHILE
		
		// Win mesage depending on who wins the game
		if (playerWin) {
			JOptionPane.showMessageDialog(null, playerOne.toString() + " sunk all the boats! " + playerOne.toString() + " won the game!");
		} else {
			JOptionPane.showMessageDialog(null, playerTwo.toString() + " sunk all the boats!" + playerTwo.toString() + " won the game!");
		}
	}
}
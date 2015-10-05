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
public class GUIHumanPlayer extends Player {
	private Move move;

	public GUIHumanPlayer(String name) { super(name); }

	public Move getMove(GUIBoard board) { 
		board.reset();
		while (board.moveBoolean() == false) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				System.out.println("InterruptedException caught");
			}
			
		}
		move = new Move(board.moveRow(), board.moveColumn());
		return move;
	}
}
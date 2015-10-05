import java.util.*;
public class AIPlayer extends Player {
	Move move;
	public AIPlayer(String name) { super(name); }

	public Move getMove(GUIBoard board) { 
		if (board.getPriorityBoard().contains(5)) {
			move = board.getPriorityBoard().getMove(5);
		} else if (board.getPriorityBoard().contains(4)) {
			move = board.getPriorityBoard().getMove(4);
		} else {
			int letter, number;
			Random random = new Random();

			letter = random.nextInt(board.getRows());
			number = random.nextInt(board.getColumns());

			move = new Move(letter, number);
		}
		
		return move;
	}

	public Move getMove(Board board) { 
		int letter, number;

		Random random = new Random();

		letter = random.nextInt(board.getRows());
		number = random.nextInt(board.getColumns());

		move = new Move(letter, number);

		return move; 
	}
}
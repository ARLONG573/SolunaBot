import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import state.SolunaState;
import state.Stack;

/**
 * Main class for SolunaBot
 * 
 * @author Aaron Tetens
 */
public class SolunaMain {

	private static final int NUM_STARTING_STACKS = 12;

	public static void main(final String[] args) {
		final Scanner in = new Scanner(System.in);

		// Decide which player is the AI
		int aiPlayer = -1;

		while (!(aiPlayer == 0 || aiPlayer == 1)) {
			System.out.print("Which player is AI? (0 or 1): ");
			aiPlayer = Integer.parseInt(in.nextLine());
		}

		// Initial state
		final List<Stack> initialStacks = new ArrayList<>();

		for (int i = 1; i <= NUM_STARTING_STACKS; i++) {
			// Read a character
			char symbol = '?';

			while (!(symbol == 's' || symbol == 'c' || symbol == 'm' || symbol == 'h')) {
				System.out.print("Symbol " + i + ": ");
				symbol = in.nextLine().charAt(0);
			}

			initialStacks.add(new Stack(1, symbol));
		}

		// Play the game
		SolunaState state = new SolunaState(initialStacks);

		while (state.getWinningPlayers().isEmpty()) {
			// AI turn
			if (state.getCurrentPlayer() == aiPlayer) {
				System.out.println("AI is thinking...");
				state = (SolunaState) MCTS.search(state, 10, 1);
			}
			// Player turn
			else {
				// Read in a move
				String move = "";

				while (!(state.isMoveValid(move))) {
					System.out.print("Enter move: ");
					move = in.nextLine();
				}

				state.makeMove(move);
			}

			state.print();
		}

		System.out.println("Winning players = " + state.getWinningPlayers());

		in.close();
	}

}

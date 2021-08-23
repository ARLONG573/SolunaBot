package state;

import java.util.ArrayList;
import java.util.List;

import api.GameState;

/**
 * Represents a single game state in the game.
 * 
 * @author Aaron Tetens
 */
public class SolunaState implements GameState {

	private final List<Stack> stacks;

	private int currentPlayer;
	private int lastPlayer;
	private String lastMove; // just for printing after AI's turn to more easily see what it did

	public SolunaState(final List<Stack> initialStacks) {
		this.stacks = new ArrayList<>(initialStacks);
		this.currentPlayer = 0;
		this.lastPlayer = -1;
		this.lastMove = "";
	}

	private SolunaState(final SolunaState state) {
		this.stacks = new ArrayList<>(state.stacks);
		this.currentPlayer = state.currentPlayer;
		this.lastPlayer = state.lastPlayer;
		this.lastMove = state.lastMove;
	}

	/**
	 * The move is valid if the stacks exist and the first stack representation is
	 * either the same height or has the same top as the second stack
	 * representation.
	 * 
	 * @param move
	 *            The move to check
	 * @return Whether or not the move is valid
	 */
	public boolean isMoveValid(final String move) {
		// Move cannot be null
		if (move == null) {
			return false;
		}

		// Split after first letter
		int indexOfFirstLetter = -1;
		for (int i = 0; i < move.length(); i++) {
			if (Character.isLetter(move.charAt(i))) {
				indexOfFirstLetter = i;
				break;
			}
		}

		if (indexOfFirstLetter == -1 || indexOfFirstLetter == move.length() - 1) {
			return false;
		}

		final String stack1 = move.substring(0, indexOfFirstLetter + 1);
		final String stack2 = move.substring(indexOfFirstLetter + 1, move.length());

		// There must be two different stacks with these String representations that are
		// compatible
		for (int i = 0; i < this.stacks.size(); i++) {
			for (int j = 0; j < this.stacks.size(); j++) {
				if (i == j) {
					continue;
				}

				final Stack stackI = this.stacks.get(i);
				final Stack stackJ = this.stacks.get(j);

				if (stack1.equals(stackI.toString()) && stack2.equals(stackJ.toString())
						&& this.isMoveValid(stackI, stackJ)) {
					return true;
				}
			}
		}

		// No matches
		return false;
	}

	/**
	 * This method assumes that the given move is valid (checked via the isMoveValid
	 * method).
	 * 
	 * @param move
	 *            The move to make
	 */
	public void makeMove(final String move) {
		// Split after first letter
		int indexOfFirstLetter = -1;
		for (int i = 0; i < move.length(); i++) {
			if (Character.isLetter(move.charAt(i))) {
				indexOfFirstLetter = i;
				break;
			}
		}

		final String stack1 = move.substring(0, indexOfFirstLetter + 1);
		final String stack2 = move.substring(indexOfFirstLetter + 1, move.length());

		// Find the corresponding stacks
		for (int i = 0; i < this.stacks.size(); i++) {
			for (int j = 0; j < this.stacks.size(); j++) {
				if (i == j) {
					continue;
				}

				final Stack stackI = this.stacks.get(i);
				final Stack stackJ = this.stacks.get(j);

				if (stack1.equals(stackI.toString()) && stack2.equals(stackJ.toString())) {
					this.makeMove(stackI, stackJ);
					return;
				}
			}
		}
	}

	/**
	 * This method assumes that the given move is valid (checked via the isMoveValid
	 * method).
	 * 
	 * @param source
	 *            The stack to move on top of dest
	 * @param dest
	 *            The stack getting covered
	 */
	private void makeMove(final Stack source, final Stack dest) {
		this.stacks.remove(source);
		this.stacks.remove(dest);
		this.stacks.add(new Stack(source.getHeight() + dest.getHeight(), source.getTop()));

		if (this.currentPlayer == 0) {
			this.currentPlayer = 1;
			this.lastPlayer = 0;
		} else {
			this.currentPlayer = 0;
			this.lastPlayer = 1;
		}

		this.lastMove = source.toString() + dest.toString();
	}

	/**
	 * The move is valid if the source and dest stacks have either the same height
	 * or the same top symbol.
	 * 
	 * @param source
	 *            The stack to move on top of dest
	 * @param dest
	 *            The stack getting covered
	 * @return Whether or not he move is valid
	 */
	private boolean isMoveValid(final Stack source, final Stack dest) {
		return source.getHeight() == dest.getHeight() || source.getTop() == dest.getTop();
	}

	public void print() {
		System.out.println();
		System.out.println("Last move: " + this.lastMove);
		System.out.println("Remaining stacks:");
		for (final Stack stack : this.stacks) {
			System.out.println(stack);
		}
		System.out.println();
	}

	public int getCurrentPlayer() {
		return this.currentPlayer;
	}

	@Override
	public int getLastPlayer() {
		return this.lastPlayer;
	}

	@Override
	public List<GameState> getNextStates() {
		final List<GameState> nextStates = new ArrayList<>();

		for (int i = 0; i < this.stacks.size(); i++) {
			for (int j = 0; j < this.stacks.size(); j++) {
				if (i == j) {
					continue;
				}

				final Stack stackI = this.stacks.get(i);
				final Stack stackJ = this.stacks.get(j);

				if (this.isMoveValid(stackI, stackJ)) {
					final SolunaState nextState = new SolunaState(this);
					nextState.makeMove(stackI, stackJ);
					nextStates.add(nextState);
				}
			}
		}

		return nextStates;
	}

	@Override
	public GameState getRandomNextState() {
		final List<GameState> nextStates = this.getNextStates();
		final int randomIndex = (int) (Math.random() * nextStates.size());
		return nextStates.get(randomIndex);
	}

	@Override
	public List<Integer> getWinningPlayers() {
		final List<Integer> winningPlayers = new ArrayList<>();

		// If there are no possible moves, the winner is the last player to have made a
		// move
		if (this.getNextStates().isEmpty()) {
			winningPlayers.add(this.lastPlayer);
		}

		return winningPlayers;
	}

}

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

	public SolunaState(final List<Stack> initialStacks) {
		this.stacks = new ArrayList<>(initialStacks);
		this.currentPlayer = 0;
		this.lastPlayer = -1;
	}

	public boolean isMoveValid(final String move) {
		// TODO
		return true;
	}

	/**
	 * This method assumes that the given move is valid (checked via the isMoveValid
	 * method).
	 * 
	 * @param move
	 *            The move to make
	 */
	public void makeMove(final String move) {

	}

	public void print() {
		System.out.println();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameState getRandomNextState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getWinningPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

}

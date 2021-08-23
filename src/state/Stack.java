package state;

/**
 * Represents a stack of discs in the game.
 * 
 * @author Aaron Tetens
 */
public class Stack {

	private final int height;

	// 's' = star, 'c' = comet, 'm' = moon, 'h' = sun
	private final char top;

	public Stack(final int height, final char top) {
		this.height = height;
		this.top = top;
	}

	public int getHeight() {
		return this.height;
	}

	public char getTop() {
		return this.top;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.height);
		sb.append(this.top);
		return sb.toString();
	}
}

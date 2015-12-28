package fallingblocks.model;

/**
 * Immutable class representing an integer coordinate in GameBoard space.
 * @author jeremiah
 * 
 */
public class Coordinate implements Comparable {
	/** x-coordinate. */
	private final int x;

	/** y-coordinate. */
	private final int y;

	/**
	 * Sole Constructor.
	 * 
	 * @param x
	 * @param y
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinate))
			return false;
		Coordinate c = (Coordinate) o;
		return this.x == c.x && this.y == c.y;
	}

	@Override
	public int hashCode() {
		int code = 17;
		code = 37 * code + x;
		code = 37 * code + y;
		return code;
	}

	/**
	 * Returns the Coordinate immediately below this coordinate.
	 * @return the Coordinate immediately below this coordinate.
	 */
	public Coordinate below() {
		return new Coordinate(this.x, this.y - 1);
	}

	/**
	 * Returns the Coordinate immediately above this coordinate.
	 * @return the Coordinate immediately above this coordinate.
	 */
	public Coordinate above() {
		return new Coordinate(this.x, this.y + 1);
	}

	/**
	 * Returns the Coordinate immediately left of this coordinate.
	 * @return the Coordinate immediately left of this coordinate.
	 */
	public Coordinate left() {
		return new Coordinate(this.x - 1, this.y);
	}

	/**
	 * Returns the Coordinate immediately right of this coordinate.
	 * @return the Coordinate immediately right of this coordinate.
	 */
	public Coordinate right() {
		return new Coordinate(this.x + 1, this.y);
	}

	public int compareTo(Object o) {
		Coordinate c = (Coordinate)o;
		int xDiff = this.x - c.x;
		if(xDiff != 0) return xDiff;
		int yDiff = this.y - c.y;
		if(yDiff != 0) return yDiff;
		return 0;
	}
	@Override
	public String toString() {
		return new StringBuilder()
					.append("[ Coordinate x: ")
					.append(x)
					.append(", y: ")
					.append(y)
					.append(" ]")
					.toString();
	}
}
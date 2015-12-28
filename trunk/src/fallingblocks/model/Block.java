package fallingblocks.model;

/**
 * Represents a single block. This is either a single cell location on the
 * GameBoard or one component of a GamePiece.
 * 
 * @author jeremiah
 * 
 */
public class Block {
    /** The location of the block in GameBoard coordinates. */
    private Coordinate coord;

    public Block() {
    }
    /**
     * Creates a block at the given location.
     * @param coord
     */
    public Block(Coordinate coord) {
        this.coord = coord;
    }

    /**
     * Returns the location of this block in GameBoard coordinates.
     * @return the location of this block in GameBoard coordinates.
     */
    public Coordinate getCoordinate() {
        return coord;
    }

    /**
     * Sets the location of this block in GameBoard coordinates.
     * @param coord the new location.
     */
    public void setCoordinate(Coordinate coord) {
        this.coord = coord;
    }
}

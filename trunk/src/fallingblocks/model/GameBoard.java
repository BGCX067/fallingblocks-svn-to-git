package fallingblocks.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides a 2D grid for the tracking of <code>Block</code>s. Provides all
 * management for <code>Block</code>s that have been added to the GameBoard,
 * such as handling removal when a line is completed.
 * 
 * @author jeremiah
 * 
 */
public class GameBoard {
    private static final Block[] NO_BLOCKS = new Block[0];

    private static final Row NULL_ROW = new Row() {
        @Override
        public void addBlock(Block b) {
            throw new UnsupportedOperationException("Attempted to add a block to the null Row.");
        }

    };

    /** Default GameBoard width. */
    public static final int DEFAULT_WIDTH = 10;

    /** Default GameBoard height. */
    public static final int DEFAULT_HEIGHT = 20;

    /** GameBoard width. */
    private final int width;

    /** GameBoard height. */
    private final int height;

    /** GameBoard rows. */
    private final Row[] rows;

    /** Event listeners. */
    private final List<BlockAddedListener> listeners;

    /**
     * Constructs a GameBoard of default Width and Height.
     */
    public GameBoard() {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        // blocks = new ArrayList<Block>();
        rows = new Row[height];
        for (int i = 0; i < rows.length; i++)
            rows[i] = NULL_ROW;

        listeners = new ArrayList<BlockAddedListener>();
    }

    /**
     * Constructs a GameBoard of size width X height.
     * 
     * @param width
     *            the GameBoard width.
     * @param height
     *            the GameBoard height.
     */
    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        // blocks = new ArrayList<Block>();
        rows = new Row[height];
        for (int i = 0; i < rows.length; i++)
            rows[i] = NULL_ROW;

        listeners = new ArrayList<BlockAddedListener>();
    }

    /**
     * Determines whether the specified coordinate is currently occupied by a
     * Block.
     * 
     * @param c
     *            the Coordinate to check.
     * @return <code>true</code> if the GameBoard coordinate is occupied,
     *         <code>false</code> otherwise.
     */
    public boolean isOccupied(Coordinate c) {
        int rowIndex = c.getY();
        int colIndex = c.getX();
        if (rows[rowIndex] == NULL_ROW)
            return false;
        return rows[rowIndex].isOccupied(colIndex);
    }

    /**
     * Incorporates <code>newBlocks</code> into the GameBoard.
     * 
     * @param newBlocks
     *            the blocks to incorporate.
     */
    public void addBlocks(Block[] newBlocks) {
        for (Block newBlock : newBlocks) {
            if (isOccupied(newBlock.getCoordinate()))
                throw new IllegalArgumentException(
                        "A block is already located at that coordinate: "
                                + newBlock.getCoordinate());
            int rowId = newBlock.getCoordinate().getY();
            if (rows[rowId] == NULL_ROW)
                rows[rowId] = new Row(this.width, rowId);
            rows[rowId].addBlock(newBlock);
        }
        clearCompletedRows();
        fireBlockAddedEvent();
    }

    /**
     * Removes all rows that have been completed and collapses the emptied rows.
     */
    private void clearCompletedRows() {
        // Remove completed rows
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].isComplete()) {
                rows[i] = NULL_ROW;
            }
        }

        // Collapse the emptied rows
        for (int i = 0; i < rows.length; i++) {
            // if this row is NULL, find first non-NULL row and swap.
            if (rows[i] == NULL_ROW) {
                for (int j = i + 1; j < rows.length; j++) {
                    if (rows[j] != NULL_ROW) {
                        rows[i] = rows[j];
                        rows[j] = NULL_ROW;
                        break;
                    }
                }
            }
        }

        // Update row indices.
        for (int i = 0; i < rows.length; i++) {
            rows[i].setRowIndex(i);
        }
    }

    /**
     * Returns all the blocks held by this GameBoard.
     * 
     * @return all the blocks held by this GameBoard.
     */
    public Block[] getBlocks() {
        List<Block> result = new ArrayList<Block>();
        for (Row r : rows) {
            result.addAll(r.getBlocks());
        }
        return result.toArray(NO_BLOCKS);
    }

    /**
     * Returns this GameBoards height.
     * 
     * @return this GameBoards height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns this GameBoard's width.
     * 
     * @return this GameBoard's width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Registers <code>listener</code> to receive BlockAddedEvents when they
     * are fired.
     * 
     * @param listener
     *            the listener to register.
     */
    public void addBlockAddedListener(BlockAddedListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes <code>listener</code> from the registry to receive
     * BlockAddedEvents.
     * 
     * @param listener
     *            the listener to remove from the registry.
     */
    public void removeBlockAddedListener(BlockAddedListener listener) {
        listeners.remove(listener);
    }

    /**
     * Sends a BlockAddedEvent to each listener.
     */
    private void fireBlockAddedEvent() {
        BlockAddedEvent event = new BlockAddedEvent(this);
        for (BlockAddedListener listener : listeners) {
            listener.blocksAdded(event);
        }
        // System.out.println("num listeners")
    }

    /**
     * Represents a single row in a GameBoard.
     * 
     * Manages the blocks in a particular row, monitoring whether the row has
     * been completed or not.
     */
    private static class Row {
        /** Null Object to indicate the absence of a block. */
        private static final Block NULL_BLOCK = new Block() {
            public Coordinate getCoordinate() {
                return null;
            }

            public void setCoordinate(Coordinate coord) {
            }
        };

        /** Blocks in this row. */
        private final Block[] blocks;

        /** The row index in the gameboard. */
        private int rowIndex;

        /** Default constructor. */
        public Row() {
            blocks = new Block[DEFAULT_WIDTH];
            for (int i = 0; i < blocks.length; i++) {
                blocks[i] = NULL_BLOCK;
            }
            rowIndex = -1;
        }

        /**
         * Constructs a row of specified width.
         * 
         * @param width
         * @param index
         *            the row index for this row.
         */
        public Row(int width, int index) {
            blocks = new Block[width];
            for (int i = 0; i < blocks.length; i++) {
                blocks[i] = NULL_BLOCK;
            }
            rowIndex = index;
        }

        /**
         * Determines if a given row cell is already occupied or not.
         * 
         * @param index
         *            the row cell to check.
         * @return <code>true</code> if the row cell is occupied,
         *         <code>false</code> otherwise.
         */
        public boolean isOccupied(int index) {
            if (blocks[index] == NULL_BLOCK)
                return false;
            return true;
        }

        /**
         * Determines if this row is complete or not.
         * 
         * @return <code>true</code> if the row is complete,
         *         <code>false</code> otherwise.
         */
        public boolean isComplete() {
            for (Block b : blocks) {
                if (b == NULL_BLOCK)
                    return false;
            }
            return true;
        }

        /**
         * Returns the block at the specified row cell.
         * 
         * @param index
         *            the row cell to get.
         * @return the block at the given index. NULL_BLOCK is returned if a
         *         cell is not occupied.
         */
        public Block getBlock(int index) {
            return blocks[index];
        }

        /**
         * Adds a block to the row.
         * 
         * @param b
         *            the block to add.
         */
        public void addBlock(Block b) {
            if (this.isOccupied(b.getCoordinate().getX())
                    || b.getCoordinate().getY() != this.rowIndex) {
                throw new IllegalArgumentException("A block may not be added at that coordinate: "
                        + b.getCoordinate());
            }
            blocks[b.getCoordinate().getX()] = b;
        }

        /**
         * Returns all the blocks in this row.
         * 
         * @return
         */
        public List<Block> getBlocks() {
            List<Block> result = new ArrayList<Block>();
            for (Block b : blocks) {
                if (b == NULL_BLOCK)
                    continue;
                result.add(b);
            }
            return Collections.unmodifiableList(result);
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
            for (int i = 0; i < blocks.length; i++) {
                blocks[i].setCoordinate(new Coordinate(i, rowIndex));
            }
        }
    }
}

package fallingblocks.model;

import junit.framework.TestCase;

/**
 * Unit test class to exercise fallingblocks.model.GameBoard.
 * 
 * @author jeremiah
 * 
 */
public class GameBoardTest extends TestCase {
    private GameBoard board;

    protected void setUp() throws Exception {
        board = new GameBoard();
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Exercises the GameBoard.isOccupied method.
     * 
     */
    public void testIsOccupied() {
        Coordinate coord = new Coordinate(0, 0);
        assertFalse(board.isOccupied(coord));

        Block block = new Block();
        block.setCoordinate(new Coordinate(0, 0));
        board.addBlocks(new Block[] { block });
        assertTrue(board.isOccupied(coord));

        Block[] blocks = new Block[5];
        for (int i = 0; i < 5; i++) {
            blocks[i] = new Block();
            blocks[i].setCoordinate(new Coordinate(i + 1, i + 1));
        }

        board.addBlocks(blocks);
        for (int i = 1; i <= 5; i++) {
            assertTrue(board.isOccupied(new Coordinate(i, i)));
        }

    }

    /**
     * Tests adding blocks to a GameBoard.
     * 
     * Exercises the invariant checks (blocks may not be added to locations
     * already occupied.
     * 
     */
    public void testAddBlocks() {
        Block block = new Block();
        block.setCoordinate(new Coordinate(0, 0));
        board.addBlocks(new Block[] { block });

        try {
            block = new Block();
            block.setCoordinate(new Coordinate(0, 0));
            board.addBlocks(new Block[] { block });
            fail("GameBoard accepted a block in a location already occupied.");
        } catch (IllegalArgumentException e) {
            // test passed.
        }
    }

    /**
     * Test Listener to monitor whether an event is fired.
     * 
     * @author jeremiah
     * 
     */
    private static class TestBlockAddedListener implements BlockAddedListener {
        public boolean eventFired = false;

        public void blocksAdded(BlockAddedEvent event) {
            eventFired = true;
        }
    }

    /**
     * Tests that a BlockAddedEvent is fired when a Block is added.
     * 
     */
    public void testAddBlocksEvent() {
        TestBlockAddedListener listener = new TestBlockAddedListener();
        board.addBlockAddedListener(listener);
        Block block = new Block();
        block.setCoordinate(new Coordinate(0, 0));
        board.addBlocks(new Block[] { block });
        if (!listener.eventFired) {
            fail("Event never fired.");
        }
    }

    /**
     * Tests that rows are properly removed when a row is completed.
     */
    public void testClearBlocks() {
        Block[] row0 = createAlmostCompleteRow(GameBoard.DEFAULT_WIDTH, 0);
        Block[] row1 = createAlmostCompleteRow(GameBoard.DEFAULT_WIDTH, 1);

        Block block = new Block(new Coordinate(GameBoard.DEFAULT_WIDTH - 1, 0));

        board.addBlocks(row0);
        board.addBlocks(row1);
        board.addBlocks(new Block[] { block });

        Block[] remainingBlocks = board.getBlocks();

        assertEquals(GameBoard.DEFAULT_WIDTH - 1, remainingBlocks.length);
        for (Block b : remainingBlocks) {
            assertEquals(0, b.getCoordinate().getY());
        }
    }

    /**
     * Tests that multiple adjacent rows are properly removed when a row is
     * completed.
     */
    public void testClearMultipleAdjacentRowsOfBlocks() {
        Block[] row0 = createAlmostCompleteRow(GameBoard.DEFAULT_WIDTH, 0);
        Block[] row1 = createAlmostCompleteRow(GameBoard.DEFAULT_WIDTH, 1);
        Block[] row2 = createAlmostCompleteRow(GameBoard.DEFAULT_WIDTH, 2);
        Block[] row3 = createAlmostCompleteRow(GameBoard.DEFAULT_WIDTH, 3);

        Block block1 = new Block(new Coordinate(GameBoard.DEFAULT_WIDTH - 1, 1));
        Block block2 = new Block(new Coordinate(GameBoard.DEFAULT_WIDTH - 1, 2));

        board.addBlocks(row0);
        board.addBlocks(row1);
        board.addBlocks(row2);
        board.addBlocks(row3);
        board.addBlocks(new Block[] { block1, block2 });

        Block[] remainingBlocks = board.getBlocks();

        assertEquals((GameBoard.DEFAULT_WIDTH - 1) * 2, remainingBlocks.length);
        for (Block b : remainingBlocks) {
            assertTrue(b.getCoordinate().getY() == 0 || b.getCoordinate().getY() == 1);
        }
    }

    /**
     * Tests that multiple nonadjacent rows are properly removed when a row is
     * completed.
     */
    public void testClearMultipleNonAdjacentRowsOfBlocks() {
        Block[] row0 = createAlmostCompleteRow(GameBoard.DEFAULT_WIDTH, 0);
        Block[] row1 = createAlmostCompleteRow(GameBoard.DEFAULT_WIDTH, 1);
        Block[] row2 = createAlmostCompleteRow(GameBoard.DEFAULT_WIDTH, 2);
        Block[] row3 = createAlmostCompleteRow(GameBoard.DEFAULT_WIDTH, 3);

        Block block1 = new Block(new Coordinate(GameBoard.DEFAULT_WIDTH - 1, 0));
        Block block2 = new Block(new Coordinate(GameBoard.DEFAULT_WIDTH - 1, 2));

        board.addBlocks(row0);
        board.addBlocks(row1);
        board.addBlocks(row2);
        board.addBlocks(row3);
        board.addBlocks(new Block[] { block1, block2 });

        Block[] remainingBlocks = board.getBlocks();

        assertEquals((GameBoard.DEFAULT_WIDTH - 1) * 2, remainingBlocks.length);
        for (Block b : remainingBlocks) {
            assertTrue(b.getCoordinate().getY() == 0 || b.getCoordinate().getY() == 1);
        }
    }

    /**
     * Test of clearing a middle row with multiple sparse rows above. Created to
     * resolve issue where not all rows fall as designed.
     * 
     */
    public void testClearRows() {
        Block[] blocks = new Block[25];
        for (int i = 0; i < 8; i++) {
            blocks[i] = new Block(new Coordinate(i, 0));
        }
        for (int i = 1; i < 10; i++) {
            blocks[7 + i] = new Block(new Coordinate(i, 1));
        }
        blocks[17] = new Block(new Coordinate(9, 0));
        blocks[18] = new Block(new Coordinate(9, 2));
        blocks[19] = new Block(new Coordinate(9, 3));
        blocks[20] = new Block(new Coordinate(9, 4));
        blocks[21] = new Block(new Coordinate(9, 5));
        blocks[22] = new Block(new Coordinate(5, 2));
        blocks[23] = new Block(new Coordinate(6, 2));
        blocks[24] = new Block(new Coordinate(4, 2));

        board.addBlocks(blocks);

        Block clearingBlock = new Block(new Coordinate(0, 1));
        board.addBlocks(new Block[] { clearingBlock });

        Block[] remainingBlocks = board.getBlocks();
        assertEquals(16, remainingBlocks.length);
        for (Block b : remainingBlocks) {
            assertTrue(b.getCoordinate().getY() < 5);
        }
    }

    private Block[] createAlmostCompleteRow(int rowWidth, int rowIndex) {
        Block[] row = new Block[rowWidth - 1];
        for (int i = 0; i < rowWidth - 1; i++) {
            row[i] = new Block(new Coordinate(i, rowIndex));
        }
        return row;
    }

}

package fallingblocks.render;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;

import fallingblocks.model.Block;
import fallingblocks.model.BlockAddedEvent;
import fallingblocks.model.BlockAddedListener;
import fallingblocks.model.Coordinate;
import fallingblocks.model.GameBoard;

/**
 * Manages the rendering for a GameBoard.
 * @author jeremiah
 *
 */
public class GameBoardRenderer implements BlockAddedListener {
    /** The BranchGroup to which the rendered GameBoard is attached in the scene graph. */
    private BranchGroup parentGroup;

    /** The root node of the rendered GameBoard. */
    private BranchGroup boardBranchGroup;

    /** The root node of the blocks the GameBoard manages. */
    private BranchGroup blockBranchGroup;

    /** The GameBoard to be rendered. */
    private GameBoard board;

    /**
     * 
     * @param board
     * @param parentGroup
     */
    public GameBoardRenderer(GameBoard board, BranchGroup parentGroup) {
        this.board = board;
        this.parentGroup = parentGroup;
        this.board.addBlockAddedListener(this);
        initializeBranchGroup();
    }

    /**
     * Creates the SceneGraph nodes for this rendered GameBoard.
     */
    private void initializeBranchGroup() {
        BranchGroup bg = new BranchGroup();

        float worldBoardWidth = (float) this.board.getWidth() * 0.2f;
        float worldBoardHeight = (float) this.board.getHeight() * 0.2f;

        createLeftWall(bg, worldBoardWidth, worldBoardHeight);
        createRightWall(bg, worldBoardWidth, worldBoardHeight);
        
        //center the gameboard at the origin.
        Transform3D boardPos = new Transform3D();
        boardPos.set(new Vector3f(-(worldBoardWidth / 2.0f), -(worldBoardHeight / 2.0f), 0.0f));
        TransformGroup tg = new TransformGroup(boardPos);

        this.boardBranchGroup = new BranchGroup();
        boardBranchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        boardBranchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);

        tg.addChild(this.boardBranchGroup);
        bg.addChild(tg);
        // Center the board around the origin.

        bg.compile();
        this.parentGroup.addChild(bg);
    }

    /**
     * Attaches the Right Wall scene graph to the BranchGroup provided.
     * @param bg The node to attach the right wall scene graph.
     * @param worldBoardWidth
     * @param worldBoardHeight
     */
    private void createRightWall(BranchGroup bg, float worldBoardWidth, float worldBoardHeight) {
        Box rightWall = new Box(0.1f, worldBoardHeight / 2.0f, 0.1f, getWallAppearance());
        Transform3D rightWallPos = new Transform3D();
        rightWallPos.set(new Vector3f((worldBoardWidth / 2.0f), -0.1f, 0.0f));
        TransformGroup rwtg = new TransformGroup(rightWallPos);
        rwtg.addChild(rightWall);
        bg.addChild(rwtg);
    }

    /**
     * Attaches the Left Wall scene graph to the BranchGroup provided.
     * @param bg The node to attach the right wall scene graph.
     * @param worldBoardWidth
     * @param worldBoardHeight
     */
    private void createLeftWall(BranchGroup bg, float worldBoardWidth, float worldBoardHeight) {
        BranchGroup lwall = new BranchGroup();
        Box leftWall = new Box(0.1f, worldBoardHeight / 2.0f, 0.1f, getWallAppearance());
        Transform3D leftWallPos = new Transform3D();
        leftWallPos.set(new Vector3f(-0.2f - (worldBoardWidth / 2.0f), -0.1f, 0.0f));
        TransformGroup lwtg = new TransformGroup(leftWallPos);
        lwtg.addChild(leftWall);
        lwall.addChild(lwtg);
        bg.addChild(lwall);
    }

    /**
     * Returns the Appearance to use for the GameBoard walls.
     * @return the Appearance to use for the GameBoard walls.
     */
    private Appearance getWallAppearance() {
        Appearance blockAppear = new Appearance();
        ColoringAttributes attrib = new ColoringAttributes();
        attrib.setColor(new Color3f(0.0f, 1.0f, 0.0f));
        blockAppear.setColoringAttributes(attrib);
        return blockAppear;
    }

    /**
     * Monitors the GameBoard for BlockAddedEvents.   Implementation for BlockAddedListener.
     */
    public void blocksAdded(BlockAddedEvent event) {
        // TODO Decide this.board vs. event.getSource()
        GameBoard board = (GameBoard) event.getSource(); // Should be the
        // same as
        // this.board
        Block[] blocks = board.getBlocks();

        updateBranchGroup(blocks);
    }

    /**
     * Updates the Scene Graph for the blocks in the GameBoard.
     * @param blocks the blocks still present in the GameBoard.
     */
    private void updateBranchGroup(Block[] blocks) {
        BranchGroup bg = new BranchGroup();
        bg.setCapability(BranchGroup.ALLOW_DETACH);
        BlockRenderer blockRenderer = new BlockRenderer();
        for (Block block : blocks) {
            Transform3D translate = new Transform3D();
            Coordinate c = block.getCoordinate();
            translate.set(new Vector3f((float) c.getX() * 0.2f, (float) c.getY() * 0.2f, 0.0f));
            TransformGroup transformGroup = new TransformGroup(translate);
            transformGroup.addChild(blockRenderer.createBranchGroup(block));
            bg.addChild(transformGroup);
        }
        bg.compile();

        if (this.blockBranchGroup != null)
            this.blockBranchGroup.detach();
        this.blockBranchGroup = bg;
        boardBranchGroup.addChild(this.blockBranchGroup);
    }

}

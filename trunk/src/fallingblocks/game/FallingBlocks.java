package fallingblocks.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import fallingblocks.model.Block;
import fallingblocks.model.Coordinate;
import fallingblocks.model.GameBoard;
import fallingblocks.render.BlockRenderer;
import fallingblocks.render.GameBoardRenderer;
import fallingblocks.ui.GameWindow;

/**
 * Main class for the FallingBlocks game.
 * 
 * PRESENTLY ONLY A HACK IMPLEMENTATION TO GET A DEMO WORKING.
 * 
 * @author jeremiah
 *
 */
public class FallingBlocks {

    private static GameBoard board;
    
    private static Block movingBlock;
    private static BranchGroup blockGrandParent;
    private static BranchGroup blockParent;

    /**
     * @param args
     */
    public static void main(String[] args) {
        KeyListener listener = new KeyListener() {
            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
                // Invoked from the Swing thread. Spawn a new thread to update
                // the model.
                // This is probably not actually thread safe right now.
                // TODO define all necessary threads for the game
                // TODO make Swing -> Model communication thread safe.
                Runnable r = new Runnable() {
                    public void run() {
                        movingBlock.setCoordinate(movingBlock.getCoordinate().below());
                        if(movingBlock.getCoordinate().getY() == 1) {
                            blockParent.detach();
                            board.addBlocks(new Block[] { movingBlock });
                            return;
                        }
                        
                        BranchGroup bg = new BranchGroup();
                        bg.setCapability(BranchGroup.ALLOW_DETACH);
                        BlockRenderer blockRenderer = new BlockRenderer();
                        
                        Transform3D translate = new Transform3D();
                        Coordinate c = movingBlock.getCoordinate();
                        translate.set(new Vector3f((float) c.getX() * 0.2f, (float) c.getY() * 0.2f, 0.0f));
                        TransformGroup transformGroup = new TransformGroup(translate);
                        transformGroup.addChild(blockRenderer.createBranchGroup(movingBlock));
                        bg.addChild(transformGroup);
                        bg.compile();
                        if(blockParent != null)
                            blockParent.detach();
                        blockParent = bg;
                        blockGrandParent.addChild(blockParent);
                    }
                };
                new Thread(r).start();
            }
        };

        GameWindow window = new GameWindow(listener);
        
        board = new GameBoard();
        
        movingBlock = new Block(new Coordinate(0, 19));
        
        blockGrandParent = new BranchGroup();
        blockGrandParent.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        blockGrandParent.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        float worldBoardWidth = (float) board.getWidth() * 0.2f;
        float worldBoardHeight = (float) board.getHeight() * 0.2f;

        Transform3D boardPos = new Transform3D();
        boardPos.set(new Vector3f(-(worldBoardWidth / 2.0f), -(worldBoardHeight / 2.0f), 0.0f));
        TransformGroup blockTg = new TransformGroup(boardPos);
        blockTg.addChild(blockGrandParent);
        
        BranchGroup bg = new BranchGroup();
        bg.setCapability(BranchGroup.ALLOW_DETACH);
        BlockRenderer blockRenderer = new BlockRenderer();
        
        Transform3D translate = new Transform3D();
        Coordinate c = movingBlock.getCoordinate();
        translate.set(new Vector3f((float) c.getX() * 0.2f, (float) c.getY() * 0.2f, 0.0f));
        TransformGroup transformGroup = new TransformGroup(translate);
        transformGroup.addChild(blockRenderer.createBranchGroup(movingBlock));
        bg.addChild(transformGroup);
        bg.compile();
        blockParent = bg;
        blockGrandParent.addChild(blockParent);
        
        BranchGroup gameBoardParent = new BranchGroup();
        gameBoardParent.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        gameBoardParent.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        
        Transform3D boardPosition = new Transform3D();
        boardPosition.set(new Vector3f(0.0f, 0.0f, -3.0f));
        TransformGroup tg = new TransformGroup(boardPosition);
        tg.addChild(gameBoardParent);
        tg.addChild(blockTg);
        
        BranchGroup rootBG = new BranchGroup();
        rootBG.addChild(tg);
        rootBG.compile();

        window.getUniverse().addBranchGraph(rootBG);

        new GameBoardRenderer(board, gameBoardParent);

        Block[] blocks = new Block[25];
        for (int i = 0; i < 8; i++) {
            blocks[i] = new Block(new Coordinate(i, 0));
        }
        for (int i = 1; i < 10; i++) {
            blocks[7+i] = new Block(new Coordinate(i, 1));
        }
        blocks[17] = new Block(new Coordinate(9, 0));
        blocks[18] = new Block(new Coordinate(9, 2));
        blocks[19] = new Block(new Coordinate(9, 3));
        blocks[20] = new Block(new Coordinate(6, 3));
        blocks[21] = new Block(new Coordinate(5, 3));
        blocks[22] = new Block(new Coordinate(5, 2));
        blocks[23] = new Block(new Coordinate(6, 2));
        blocks[24] = new Block(new Coordinate(4, 2));
               
        board.addBlocks(blocks);
    }

}

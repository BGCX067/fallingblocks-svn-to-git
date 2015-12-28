package fallingblocks.render;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Box;

import fallingblocks.model.Block;

/**
 * Manages the creation of assets to render a Block.
 * 
 * @author jeremiah
 * 
 */
public class BlockRenderer {

    public BlockRenderer() {
    }

    /**
     * Creates the nodes for the scene graph representing the provided block.
     * @param block the block to render.
     * @return the scene graph nodes to render the block.
     */
    public BranchGroup createBranchGroup(Block block) {
        BranchGroup blockBg = new BranchGroup();
        Appearance blockAppear = new Appearance();
        ColoringAttributes attrib = new ColoringAttributes();
        float redColor = (float) block.getCoordinate().getX() * 0.1f;
        float blueColor = 1.0f - redColor;
        attrib.setColor(new Color3f(redColor, 0.0f, blueColor));
        blockAppear.setColoringAttributes(attrib);

        Box box = new Box(0.1f, 0.1f, 0.1f, blockAppear);

        blockBg.addChild(box);
        blockBg.compile();

        return blockBg;
    }
}

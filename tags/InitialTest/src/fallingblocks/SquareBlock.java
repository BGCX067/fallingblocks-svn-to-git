package fallingblocks;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class SquareBlock extends JFrame implements KeyListener {

    private static final long serialVersionUID = 1L;
    
    private TransformGroup translateTG;
    
    private float xVal;
    private float yVal;
    
    public SquareBlock() {
        xVal = 0;
        setLayout(new BorderLayout());
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas3D.addKeyListener(this);
        add("Center", canvas3D);

        //BranchGroup scene = createSceneGraphRotatedSpinningCube();
        //scene.compile();

        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        simpleU.getViewingPlatform().setNominalViewingTransform();
        
        simpleU.addBranchGraph(createSceneGraph());
        
    }
    
    private BranchGroup createSceneGraph() {
        BranchGroup blockBg = new BranchGroup();
        Appearance boxAppear = new Appearance();
        ColoringAttributes attrib = new ColoringAttributes();
        attrib.setColor(new Color3f(0.5f, 0.5f, 1.0f));
        boxAppear.setColoringAttributes(attrib);
        
        Box box = new Box(0.1f, 0.1f, 0.1f, boxAppear);
        
        Transform3D translate = new Transform3D();
        translate.set(new Vector3f(xVal, 0.0f, 0.0f));
        translateTG = new TransformGroup(translate);
        translateTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        translateTG.addChild(box);
        
        blockBg.addChild(translateTG);
        blockBg.compile();
        return blockBg;
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        switch(c) {
        case 'a':
        case KeyEvent.VK_A:
            xVal -= 0.1f;
            e.consume();
            break;
        case 'd':
        case KeyEvent.VK_D:
            xVal += 0.1f;
            e.consume();
            break;
        case 'w':
        case KeyEvent.VK_W:
            yVal += 0.1f;
            e.consume();
            break;
        case 's':
        case KeyEvent.VK_S:
            yVal -= 0.1f;
            e.consume();
            break;
        default:
            return;
        }
        Transform3D translate = new Transform3D();
        translate.set(new Vector3f(xVal, yVal, 0.0f));
        translateTG.setTransform(translate);
    }
    
    public static void main(String[] args) {
        JFrame frame = new SquareBlock();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

}

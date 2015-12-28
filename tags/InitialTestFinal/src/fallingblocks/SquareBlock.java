package fallingblocks;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Group;
import javax.swing.JFrame;
import javax.vecmath.Vector3f;

//import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class SquareBlock extends JFrame implements KeyListener {

    private static final long serialVersionUID = 1L;
    
    private Piece activePiece;
    private SimpleUniverse simpleU;
    private BranchGroup rootBranch;
    private float xVal;
    private float yVal;
    private int angle;
    private int nameDigit = 0;
    
    public SquareBlock() {
        xVal = 0;
        
        rootBranch = new BranchGroup();
        rootBranch.setCapability(Group.ALLOW_CHILDREN_EXTEND);
    	rootBranch.setCapability(Group.ALLOW_CHILDREN_READ);
    	rootBranch.setCapability(Group.ALLOW_CHILDREN_WRITE);    	
    	rootBranch.setName("rootBranch");
        setLayout(new BorderLayout());
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        //Added Q & E which rotate the pieces
        canvas3D.addKeyListener(this);
        add("Center", canvas3D);

        simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        rootBranch.addChild(mySceneGraph(1));
        //rootBranch.addChild(createBehaviorBranch());
        simpleU.addBranchGraph(rootBranch);
        nameDigit++;
    }
    
    private BranchGroup mySceneGraph(int i)
    {
    	BranchGroup aBranchGroup = new BranchGroup();
        aBranchGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        aBranchGroup.setCapability(Group.ALLOW_CHILDREN_READ);
        aBranchGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
        aBranchGroup.setCapability(BranchGroup.ALLOW_DETACH);
        aBranchGroup.setPickable(true);
    	aBranchGroup.setName("pieceBranch"+nameDigit);
    	
    	this.activePiece = new Piece();
        this.activePiece.buildPiece(i);        
        this.activePiece.setName("Piece"+nameDigit);
        
        aBranchGroup.addChild(this.activePiece);        
        
        //The following makes the current piece disappear when a new one is added.
//        if (nameDigit > 0)
//        {
//	        Enumeration anEnum = rootBranch.getAllChildren();
//	        BranchGroup tempBranchGroup;
//	        
//	        while (anEnum.hasMoreElements())
//	        {
//	        	tempBranchGroup = (BranchGroup) anEnum.nextElement();
//	        	if (tempBranchGroup.getName().equals("myBranch"+(nameDigit - 1)))
//	        		tempBranchGroup.detach();
//	        }
//        }    
	        
        aBranchGroup.compile();
        return aBranchGroup;
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
            xVal -= 0.04f;
            e.consume();
            break;
        case 'd':
        case KeyEvent.VK_D:
            xVal += 0.04f;
            e.consume();
            break;
        case 'w':
        case KeyEvent.VK_W:
            yVal += 0.04f;
            e.consume();
            break;
        case 's':
        case KeyEvent.VK_S:
            yVal -= 0.04f;
            e.consume();
            break;
        case 'q':
        case KeyEvent.VK_Q:
        	this.angle += 1;
        	e.consume();
        	break;
        case 'e':
        case KeyEvent.VK_E:
        	this.angle -= 1;
        	e.consume();
        	break;
        case '1':
        	rootBranch.addChild(mySceneGraph(1));
        	//rootBranch.addChild(createBehaviorBranch());
        	nameDigit++;
        	e.consume();
        	break;
        case '2':
        	rootBranch.addChild(mySceneGraph(2));
        	//rootBranch.addChild(createBehaviorBranch());
        	nameDigit++;
        	e.consume();
        	break;
        case '3':
        	rootBranch.addChild(mySceneGraph(3));
        	//rootBranch.addChild(createBehaviorBranch());
        	nameDigit++;
        	e.consume();
        	break;
        case '4':
        	rootBranch.addChild(mySceneGraph(4));
        	//rootBranch.addChild(createBehaviorBranch());
        	nameDigit++;
        	e.consume();
        	break;
        case '5':
        	rootBranch.addChild(mySceneGraph(5));
        	//rootBranch.addChild(createBehaviorBranch());
        	nameDigit++;
        	e.consume();
        	break;
        case '6':
        	rootBranch.addChild(mySceneGraph(6));
        	//rootBranch.addChild(createBehaviorBranch());
        	nameDigit++;
        	e.consume();
        	break;
        default:
            break;
        }
        
        if (this.angle > 3 || this.angle < -3)
        	this.angle = 0;
        
        this.activePiece.setTransform(new Vector3f(xVal, yVal, 0.0f), this.angle);
    }
    
    public static void main(String[] args) {
        JFrame frame = new SquareBlock();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

}

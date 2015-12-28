package fallingblocks.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyListener;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Main window for FallingBlocks.
 * 
 * @author jeremiah
 * 
 */
public class GameWindow {
    /**
     * Serial version since this class implements Serializable.
     */
    private static final long serialVersionUID = 1L;

    private final SimpleUniverse universe;

    public GameWindow(KeyListener listener) {
        JFrame frame = new JFrame();
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        this.universe = new SimpleUniverse(canvas3D);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 800);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add("Center", canvas3D);
        frame.add("Center", panel);

        universe.getViewingPlatform().setNominalViewingTransform();

        canvas3D.addKeyListener(listener);
        
        frame.setVisible(true);
    }

    public SimpleUniverse getUniverse() {
        return universe;
    }
}

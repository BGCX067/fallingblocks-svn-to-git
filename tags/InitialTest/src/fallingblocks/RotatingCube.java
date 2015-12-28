package fallingblocks;

import java.applet.Applet;
import java.awt.BorderLayout;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Example applet from the Java 3D tutorial here:
 * http://java.sun.com/developer/onlineTraining/java3d/ Chapter 1.
 * 
 * @author jeremiah
 */
public class RotatingCube extends Applet {
    private static final long serialVersionUID = 1L;

    public RotatingCube() {
        setLayout(new BorderLayout());
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", canvas3D);

        BranchGroup scene = createSceneGraphRotatedSpinningCube();
        scene.compile();

        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);
    }

    public final BranchGroup createSceneGraphSimpleRotatingCube() {
        BranchGroup objRoot = new BranchGroup();

        TransformGroup objSpin = new TransformGroup();
        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(objSpin);

        objSpin.addChild(new ColorCube(0.4));

        Alpha rotationAlpha = new Alpha(-1, 4000);

        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
                objSpin);

        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        objSpin.addChild(rotator);

        return objRoot;
    }

    public final BranchGroup createSceneGraphRotatedSpinningCube() {
        BranchGroup objRoot = new BranchGroup();

        Transform3D rotate = new Transform3D();
        Transform3D tmpRotate = new Transform3D();

        rotate.rotX(Math.PI / 4.0d);
        tmpRotate.rotY(Math.PI / 5.0d);
        rotate.mul(tmpRotate);

        TransformGroup objRotate = new TransformGroup(rotate);

        // Create the transform group node and initialize it to the
        // identity. Enable the TRANSFORM_WRITE capability so that
        // our behavior code can modify it at runtime. Add it to the
        // root of the subgraph.
        TransformGroup objSpin = new TransformGroup();
        
        
        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        TransformGroup objSpin2 = new TransformGroup();
        objSpin2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        objSpin.addChild(objSpin2);

        objRoot.addChild(objRotate);
        objRotate.addChild(objSpin);

        objSpin2.addChild(new ColorCube(0.4));

        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1, 4000);

        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
                objSpin, yAxis, 0.0f, (float) Math.PI * 2.0f);
        
        Transform3D xAxis = new Transform3D();
        xAxis.rotZ(Math.PI/2.0d);
        
        Alpha rotationXAlpha = new Alpha(-1, 500);
        
        RotationInterpolator rotatorX = new RotationInterpolator(rotationXAlpha,
                objSpin2, xAxis, 0.0f, (float) Math.PI * 2.0f);
        
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        objSpin.addChild(rotator);
        
        BoundingSphere bounds2 = new BoundingSphere();
        rotatorX.setSchedulingBounds(bounds2);
        objSpin2.addChild(rotatorX);
        
        return objRoot;
    }
}

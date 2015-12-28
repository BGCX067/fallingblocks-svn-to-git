package fallingblocks;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

public class Piece extends TransformGroup
{
	public Piece()
	{
		super();
		this.setBoundsAutoCompute(true);
		this.setPickable(true);
		aTranslate = new Transform3D();
    	aTranslate.set(new Vector3f(0.0f, 0.0f, 0.0f));    	
    	this.setTransform(aTranslate);
    	this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	}
	
	private Transform3D aTranslate;
	private int whatAmI;
	private int pieceAngle;
	
	public int getAngle()
	{
		return this.pieceAngle;
	}
	
	public TransformGroup getTransformGroup()
	{
		return this;
	}
	
	public Transform3D getTranslate()
	{
		return this.aTranslate;
	}
	
	public void setTransform(Vector3f values, int angle)
	{	
		aTranslate.setTranslation(values);
		Matrix4f aMatrix4f = new Matrix4f();
		aTranslate.get(aMatrix4f);
		this.pieceAngle = angle;
		
		switch (angle)
		{
			case 0:
				aMatrix4f.m00 = 1;
				aMatrix4f.m01 = 0;
				aMatrix4f.m10 = 0;
				aMatrix4f.m11 = 1;
				break;
			case 1:
			case -3:
				aMatrix4f.m00 = 0;
				aMatrix4f.m01 = -1;
				aMatrix4f.m10 = 1;
				aMatrix4f.m11 = 0;
				break;
			case 2:
			case -2:
				aMatrix4f.m00 = -1;
				aMatrix4f.m01 = 0;
				aMatrix4f.m10 = 0;
				aMatrix4f.m11 = -1;
				break;
			case 3:
			case -1:
				aMatrix4f.m00 = 0;
				aMatrix4f.m01 = 1;
				aMatrix4f.m10 = -1;
				aMatrix4f.m11 = 0;
				break;
			default:
				break;
		}
        
		aTranslate.set(aMatrix4f);
        this.setTransform(aTranslate);        
	}
     
     public void buildPiece(int whichPiece)
     {
    	 this.whatAmI = whichPiece;
    	 switch(whichPiece)
    	 {
    	 	case 1:
    	 		buildStraight();
    	 		break;
    	 	case 2:
    	 		buildL();
    	 		break;
    	 	case 3:
    	 		buildS();
    	 		break;
    	 	case 4:
    	 		buildReverseS();
    	 		break;
    	 	case 5:
    	 		buildT();
    	 		break;
    	 	case 6:
    	 		buildSquare();
    	 		break;
    	 	default:
    	 		break;
    	 }
     }
     
     private void buildStraight()
     {
    	 this.addChild(new Cube( 0.00f, 0.00f, whatAmI));
         this.addChild(new Cube( 0.00f, 0.04f, whatAmI));
         this.addChild(new Cube( 0.00f, 0.08f, whatAmI));
         this.addChild(new Cube( 0.00f, 0.12f, whatAmI));
     }
     
     private void buildL()
     {
    	 this.addChild(new Cube( 0.00f, 0.00f, whatAmI));
    	 this.addChild(new Cube( 0.00f, 0.04f, whatAmI));
         this.addChild(new Cube( 0.00f, 0.08f, whatAmI));
         this.addChild(new Cube(-0.04f, 0.00f, whatAmI));
     }
     
     private void buildS()
     {
    	 this.addChild(new Cube( 0.00f, 0.00f, whatAmI));
         this.addChild(new Cube( 0.00f, 0.04f, whatAmI));
         this.addChild(new Cube(-0.04f, 0.00f, whatAmI));
         this.addChild(new Cube( 0.04f, 0.04f, whatAmI));
     }
     
     private void buildReverseS()
     {
    	 this.addChild(new Cube( 0.00f, 0.00f, whatAmI));
    	 this.addChild(new Cube( 0.00f, 0.04f, whatAmI));
    	 this.addChild(new Cube(-0.04f, 0.04f, whatAmI));
    	 this.addChild(new Cube( 0.04f, 0.00f, whatAmI));
     }
     
     private void buildT()
     {
    	 this.addChild(new Cube( 0.00f, 0.00f, whatAmI));
    	 this.addChild(new Cube( 0.00f, 0.04f, whatAmI));
    	 this.addChild(new Cube( 0.00f, 0.08f, whatAmI));
    	 this.addChild(new Cube(-0.04f, 0.04f, whatAmI));
     }
     
     private void buildSquare()
     {
    	 this.addChild(new Cube( 0.00f, 0.00f, whatAmI));
    	 this.addChild(new Cube( 0.00f, 0.04f, whatAmI));
    	 this.addChild(new Cube( 0.04f, 0.00f, whatAmI));
    	 this.addChild(new Cube( 0.04f, 0.04f, whatAmI));
     }

}

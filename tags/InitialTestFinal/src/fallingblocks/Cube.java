package fallingblocks;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3f;

public class Cube extends Shape3D
{
	public Cube(float xFloat, float yFloat, int pieceType)
	{
		super();
		this.pieceColor = pieceType;
		this.buildCube(xFloat, yFloat);
		this.defineAppearance();
	}
	
	private String fileName = "graphics/block2.jpg";
	private int pieceColor;
	
	public String getFileName()
	{
		return this.fileName;
	}
	
	public void setFileName(String value)
	{
		this.fileName = value;
	}
	
    /**
     * Build a cube from an IndexedQuadArray.  This method creates
     * the vertices as a set of eight points and the normals as a set of 
     * six vectors (one for each face).  The data is then defined such
     * that each vertex has a different normal associated with it when 
     * it is being used for a different face.  The shape is created with
     * texture coordinates so that when the appearance is set it will
     * use the appearance texture on the surface.
     * @return Node that is the shape.
     */
     private void buildCube(float xFloat, float yFloat)
     {
    	IndexedQuadArray indexedCube = new IndexedQuadArray(8, IndexedQuadArray.COORDINATES|IndexedQuadArray.NORMALS|IndexedQuadArray.TEXTURE_COORDINATE_2, 24);
    	Point3f[] cubeCoordinates = { new Point3f( 0.02f + xFloat, 0.02f + yFloat, 0.02f),
    								  new Point3f(-0.02f + xFloat, 0.02f + yFloat, 0.02f),
    								  new Point3f(-0.02f + xFloat,-0.02f + yFloat, 0.02f),
    								  new Point3f( 0.02f + xFloat,-0.02f + yFloat, 0.02f),
    								  new Point3f( 0.02f + xFloat, 0.02f + yFloat,-0.02f),
    								  new Point3f(-0.02f + xFloat, 0.02f + yFloat,-0.02f),
    								  new Point3f(-0.02f + xFloat,-0.02f + yFloat,-0.02f),
    								  new Point3f( 0.02f + xFloat,-0.02f + yFloat,-0.02f)};
    	
    	Vector3f[] normals= { new Vector3f( 0.0f, 0.0f, 1.0f),
    				   		  new Vector3f( 0.0f, 0.0f,-1.0f),
    				   		  new Vector3f( 1.0f, 0.0f, 0.0f),
    				   		  new Vector3f(-1.0f, 0.0f, 0.0f),
    				   		  new Vector3f( 0.0f, 1.0f, 0.0f),
    				   		  new Vector3f( 0.0f,-1.0f, 0.0f)};
    	
    	//Define the texture coordinates.  These are defined
    	//as floating point pairs of values that are used to 
    	//map the corners of the texture image onto the vertices
    	//of the face.  We then define the indices into this
    	//array of values in a similar way to that used for
    	//the vertices and normals.
    	TexCoord2f[] textCoord = { new TexCoord2f(1.0f,1.0f),
    							   new TexCoord2f(0.0f,1.0f),
    							   new TexCoord2f(0.0f,0.0f),
    							   new TexCoord2f(1.0f,0.0f)};
    	int coordIndices[] =  {0,1,2,3,7,6,5,4,0,3,7,4,5,6,2,1,0,4,5,1,6,7,3,2};
    	int normalIndices[] = {0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5};
    	int textIndices[] =   {0,1,2,3,3,0,1,2,1,2,3,0,1,2,3,0,3,0,1,2,1,2,3,0};
    	indexedCube.setCoordinates(0, cubeCoordinates);
    	indexedCube.setCoordinateIndices(0, coordIndices);
    	indexedCube.setNormals(0,normals);
    	indexedCube.setNormalIndices(0, normalIndices);
    	indexedCube.setTextureCoordinates(0,0,textCoord);
    	indexedCube.setTextureCoordinateIndices(0,0,textIndices);
    	this.addGeometry(indexedCube);
    }
     
     private void defineAppearance()
     {
    	 BufferedImage aBufferedImage;
    	 Appearance app = new Appearance();
    	 try
    	 {
    		 //Load the texture from the external image file
    		 //TextureLoader textLoad = new TextureLoader("c:/temp/block2.jpg");
    		 File pictureFile = new File(this.fileName);
    		 ImageInputStream anImageInputStream = ImageIO.createImageInputStream(pictureFile);
    		 aBufferedImage = ImageIO.read(anImageInputStream);
         	 //Access the image from the loaded texture
         	 ImageComponent2D textImage = new ImageComponent2D(ImageComponent.FORMAT_RGB, aBufferedImage);
         	 //Create a two dimensional texture 
         	 Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, Texture.RGB, textImage.getWidth(), textImage.getHeight());
         	 //Set the texture from the image loaded
         	 texture.setImage(0, textImage);
         	 //Create the appearance that will use the texture
         	 app.setTexture(texture);
         	 //Define how the texture will be mapped onto the surface
         	 //by creating the appropriate texture attributes
         	 TextureAttributes textAttr = new TextureAttributes();
         	 textAttr.setTextureMode(TextureAttributes.BLEND);
         	 
         	 switch (this.pieceColor)
         	 {
         	 	case 1:
         	 		textAttr.setTextureBlendColor(new Color4f(0.5f, 0.5f, 1.0f, 0.5f));
         	 		break;
         	 	case 2:
         	 		textAttr.setTextureBlendColor(new Color4f(0.5f, 1.0f, 0.5f, 0.5f));
         	 		break;
         	 	case 3:
         	 		textAttr.setTextureBlendColor(new Color4f(1.0f, 0.5f, 0.5f, 0.5f));
         	 		break;
         	 	case 4:
         	 		textAttr.setTextureBlendColor(new Color4f(1.0f, 1.0f, 0.5f, 0.5f));
         	 		break;
         	 	case 5:
         	 		textAttr.setTextureBlendColor(new Color4f(0.5f, 1.0f, 1.0f, 0.5f));
         	 		break;
         	 	case 6:
         	 		textAttr.setTextureBlendColor(new Color4f(1.0f, 0.5f, 1.0f, 0.5f));
         	 		break;
         	 	default:
         	 		break;
         	 }
         	 
         	 app.setTextureAttributes(textAttr);
         	 app.setMaterial(new Material());
    	 }
    	 catch (IllegalArgumentException iae)
    	 {
    		 ColoringAttributes attrib = new ColoringAttributes();
    	     attrib.setColor(new Color3f(0.5f, 0.5f, 1.0f));
    	     app.setColoringAttributes(attrib);    		 
    	 }
    	 catch (IOException ioe)
    	 {
    		 //Strangely enough when the file isn't there it doesn't actually throw this.  However I'm required
    		 //to handle it so this is mostly a just in case.
    		 ColoringAttributes attrib = new ColoringAttributes();
    	     attrib.setColor(new Color3f(0.5f, 0.5f, 1.0f));
    	     app.setColoringAttributes(attrib);
    	 }
    	 
     	this.setAppearance(app);
     }
}

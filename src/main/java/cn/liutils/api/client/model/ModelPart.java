/**
 * 
 */
package cn.liutils.api.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.IModelCustom;

/**
 * @author WeathFolD
 *
 */
public class ModelPart {
    
    IModelCustom theModel;
    String partName;
                   
    public Vec3 pivotPt = Vec3.createVectorHelper(0.0, 0.0, 0.0); //旋转中心点
    
    public double //旋转角度
        rotationX,
        rotationY,
        rotationZ;
    
    public ModelPart(IModelCustom model) {
        theModel = model;
    }

    public ModelPart(IModelCustom model, String part) {
        theModel = model;
        partName = part;
    }
    
    public ModelPart(IModelCustom model, String part, double px, double py, double pz) {
        this(model, part);
        pivotPt.xCoord = px;
        pivotPt.yCoord = py;
        pivotPt.zCoord = pz; 
    }
    
    public void render() {
        GL11.glPushMatrix(); {
            
            GL11.glTranslated(pivotPt.xCoord, pivotPt.yCoord, pivotPt.zCoord);
            GL11.glRotated(rotationZ, 0.0, 0.0, 1.0);
            GL11.glRotated(rotationY, 0.0, 1.0, 0.0);
            GL11.glRotated(rotationX, 1.0, 0.0, 0.0);
            GL11.glTranslated(-pivotPt.xCoord, -pivotPt.yCoord, -pivotPt.zCoord);
            
            if(partName != null) {
                theModel.renderPart(partName);
            } else {
                theModel.renderAll();
            }
            
        } GL11.glPopMatrix();
    }
    
    public void setRotation(double x, double y, double z) {
        rotationX = x;
        rotationY = y;
        rotationZ = z;
    }

}

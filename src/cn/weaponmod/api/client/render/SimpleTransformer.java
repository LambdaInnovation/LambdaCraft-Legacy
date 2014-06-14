/**
 * 
 */
package cn.weaponmod.api.client.render;

import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 *
 */
public class SimpleTransformer {

	Vec3 rotationVec = RenderUtils.newV3(0, 0, 0);
	Vec3 offsetVec3 = RenderUtils.newV3(0, 0, 0);
	EnumAxis[] dirs = { EnumAxis.YAXIS, EnumAxis.XAXIS, EnumAxis.ZAXIS };
	
	enum EnumAxis {
		XAXIS(1, 0, 0), YAXIS(0, 1, 0), ZAXIS(0, 0, 1);
		public int x, y, z;
		EnumAxis(int a, int b, int c) {
			x = a;y = b;z = c;
		}
	}

	public void doTransform() {
		doTransformation();
		doRotation();
	}
	
	protected final void doTransformation() {
		GL11.glTranslated(offsetVec3.xCoord, offsetVec3.yCoord, offsetVec3.zCoord);
	}
	
	protected final void doRotation() {
		for(EnumAxis e : dirs) {
			double rotation = 0.0F;
			switch(e) {
			case XAXIS:
				rotation = rotationVec.xCoord;
			case YAXIS:
				rotation = rotationVec.yCoord;
				break;
			case ZAXIS:
				rotation = rotationVec.zCoord;
				break;
			default:
				break;
			}
			GL11.glRotated(rotation, e.x, e.y, e.z);
		}
	}

}

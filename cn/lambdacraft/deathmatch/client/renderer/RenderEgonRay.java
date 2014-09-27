package cn.lambdacraft.deathmatch.client.renderer;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.deathmatch.entity.fx.EntityEgonRay;
import cn.liutils.api.util.Motion3D;

/**
 * Egon ray rendering class.
 * 
 * @author WeAthFolD
 * 
 */
public class RenderEgonRay extends RenderEntity {

	public static final double WIDTH = 0.04F, RADIUS = 0.05F;
	public static final int IDENSITY = 16;
	protected static Tessellator tessellator = Tessellator.instance;
	protected Random rand = new Random();

	@Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {

		EntityEgonRay egon = (EntityEgonRay) par1Entity;
		if (!egon.draw) {
			return;
		}
		
		Motion3D motion = new Motion3D(egon);
		MovingObjectPosition trace = egon.worldObj.rayTraceBlocks(motion.getPosVec(egon.worldObj), 
				motion.move(100.0F).getPosVec(egon.worldObj));
		Vec3 end = (trace == null) ? motion.getPosVec(egon.worldObj)
				: trace.hitVec;
		tessellator = Tessellator.instance;
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPushMatrix(); {
		
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		
		boolean fp = egon.isClient && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
		double dx = end.xCoord - egon.posX;
		double dy = end.yCoord - egon.posY;
		double dz = end.zCoord - egon.posZ;
		double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
		float angle = egon.ticksExisted;
		float du = -egon.ticksExisted * 0.05F;
		
		double tx = .0, tz = .07;
		double ty = -0.15;
		if(!fp) {
			tx = .7;
			tz = .6;
			ty = -1.2;
		}
		
		//光束
		Vec3 v1 = newV3(.0, 0, -WIDTH), 
				v2 = newV3(.0, 0, WIDTH),
				v3 = newV3(d, 0, -WIDTH), 
				v4 = newV3(d, 0, WIDTH),
				v5 = newV3(.0, WIDTH, 0),
				v6 = newV3(.0, -WIDTH, 0),
				v7 = newV3(d, -WIDTH, 0), 
				v8 = newV3(d, WIDTH, 0);

		//螺旋线所对应圆柱
		Vec3[] vecs = new Vec3[2 * IDENSITY];
		for (int i = 0; i < 2 * IDENSITY; i++) {
			double j = 2 * Math.PI * i / IDENSITY;
			vecs[i] = Vec3.createVectorHelper(-0.15, Math.sin(j) * RADIUS,
					Math.cos(j) * RADIUS);
		}

		// Translations and rotations
		float yaw = 270.0F - (fp || egon.thrower == null ? egon.rotationYaw : egon.thrower.renderYawOffset);
		GL11.glTranslated(par2, par4, par6);
		GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F); // 左右旋转
		GL11.glTranslated(tx, ty, tz);
		GL11.glRotatef(egon.rotationPitch, 0.0F, 0.0F, -1.0F); // 上下旋转
		
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ClientProps.EGON_BEAM_PATH1);
		GL11.glColor4f(1F, 1F, 1F, .55F);
		tessellator.startDrawingQuads();
		tessellator.setBrightness(15728880);
		final float dt = 0.5f;
		for (int i = 0; i < 2 * IDENSITY - 1; i++) {
			addVertex(vecs[i], dt + i / 2.0 / IDENSITY, d * 1.8 - du * .8);
			addVertex(vecs[i + 1], dt + (i + 1) / 2.0 / IDENSITY, d * 1.8 - du * .8);
			addVertex(vecs[i + 1].addVector(d, 0, 0), dt + (i + 1) / 2.0 / IDENSITY, 0 - du * .8);
			addVertex(vecs[i].addVector(d, 0, 0), dt + i / 2.0 / IDENSITY, 0 - du * .8);
		} 
		tessellator.draw();
		
		GL11.glColor4f(1F, 1F, 1F, .8F);
		Minecraft.getMinecraft().renderEngine.bindTexture(ClientProps.EGON_BEAM_PATH);
		tessellator.startDrawingQuads();
		tessellator.setBrightness(15728880);
		
		addVertex(v1, 0 + du, 0);
		addVertex(v2, 0 + du, 1);
		addVertex(v3, d + du, 1);
		addVertex(v4, d + du, 0);

		addVertex(v8, d + du, 0);
		addVertex(v7, d + du, 1);
		addVertex(v6, 0 + du, 1);
		addVertex(v5, 0 + du, 0);

		tessellator.draw();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		} GL11.glPopMatrix();
	}

	protected void addVertex(Vec3 vec3, double texU, double texV) {
		tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
				texU, texV);
	}

	public static Vec3 newV3(double x, double y, double z) {
		return Vec3.createVectorHelper(x, y, z);
	}

}

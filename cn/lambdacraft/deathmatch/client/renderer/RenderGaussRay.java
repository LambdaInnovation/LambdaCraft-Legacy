package cn.lambdacraft.deathmatch.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.deathmatch.entity.fx.EntityGaussRay;
import cn.liutils.api.client.util.RenderUtils;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * Gauss ray rendering class.
 * 
 * @author WeAthFolD
 * 
 */
public class RenderGaussRay extends RenderEntity {

	public static double WIDTH = 0.05F;
	private static Tessellator tessellator = Tessellator.instance;
	protected boolean renderColor;

	public RenderGaussRay(boolean hasColor) {
		this.renderColor = hasColor;
	}
	
	private Vec3 newV3(double x, double y, double z){
		return RenderUtils.newV3(x, y, z);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {

		EntityGaussRay gauss = (EntityGaussRay) par1Entity;
		
		
		tessellator = Tessellator.instance;

		GL11.glPushMatrix();

		double d = gauss.distanceToRender;
		Vec3 v1 = newV3(0.05, 0, -WIDTH), v2 = newV3(0.05, 0, WIDTH), v3 = newV3(
				d, 0, -WIDTH), v4 = newV3(d, 0, WIDTH),

		v5 = newV3(0.05, WIDTH, 0), v6 = newV3(0.05, -WIDTH, 0), v7 = newV3(d,
				-WIDTH, 0), v8 = newV3(d, WIDTH, 0);

		// Translations and rotations
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHTING);
		if (renderColor)
			Minecraft.getMinecraft().renderEngine.bindTexture(ClientProps.GAUSS_BEAM_PATH);
		else {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
		GL11.glRotatef(-90.0F + gauss.rotationYaw, 0.0F, 1.0F, 0.0F); // 左右旋转
		GL11.glRotatef(gauss.rotationPitch, 0.0F, 0.0F, -1.0F); // 上下旋转
		GL11.glTranslatef(0, 0.4F, 0);
		GL11.glRotatef(7.5F, -1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0, -0.4F, 0);
		
		// drawing>)
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		tessellator.startDrawingQuads();
		tessellator.setBrightness(15728880);
		
		addVertex(v1, 0, 0);
		addVertex(v2, 0, 1);
		addVertex(v3, d, 1);
		addVertex(v4, d, 0);

		addVertex(v4, d, 0);
		addVertex(v3, d, 1);
		addVertex(v2, 0, 1);
		addVertex(v1, 0, 0);

		addVertex(v5, 0, 0);
		addVertex(v6, 0, 1);
		addVertex(v7, d, 1);
		addVertex(v8, d, 0);

		addVertex(v8, d, 0);
		addVertex(v7, d, 1);
		addVertex(v6, 0, 1);
		addVertex(v5, 0, 0);

		tessellator.draw();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}

	protected int getTexture(String path) {
		return FMLClientHandler.instance().getClient().renderEngine.
				getTexture(new ResourceLocation(path)).getGlTextureId();
	}

	protected void addVertex(Vec3 vec3, double texU, double texV) {
		tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
				texU, texV);
	}

}

package cn.lambdacraft.terrain.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.liutils.api.client.util.RenderUtils;

public class RenderXenPortal extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y,
			double z, float f) {
		Tessellator t = Tessellator.instance;
		EntityLivingBase living = Minecraft.getMinecraft().thePlayer;
		
		GL11.glPushMatrix();
		int textureID = (int) ((te.worldObj.getWorldTime() % 20) / 2);
		RenderUtils.loadTexture(ClientProps.PORTAL_PATH[textureID]);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslated(x, y, z);	
		
		double dx = te.xCoord + 0.5 - living.posX, dy = te.yCoord + 0.5 - living.posY, dz = te.zCoord + 0.5 - living.posZ;
		float rotationYaw = (float) (Math.atan2(dx, dz) * 180.0D / Math.PI);
		float f3 = MathHelper.sqrt_double(dx * dx + dz * dz);
		float rotationPitch = (float) (Math.atan2(dy, f3) * 180.0D / Math.PI);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(rotationYaw - 90, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rotationPitch, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		t.startDrawingQuads();
		t.setBrightness(15728880);
		t.addVertexWithUV(0.5, 1.0, 0.0, 0.0, 0.0);
		t.addVertexWithUV(0.5, 1.0, 1.0, 1.0, 0.0);
		t.addVertexWithUV(0.5, 0.0, 1.0, 1.0, 1.0);
		t.addVertexWithUV(0.5, 0.0, 0.0, 0.0, 1.0);
		t.draw();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}


}

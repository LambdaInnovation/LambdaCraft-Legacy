package cn.lambdacraft.deathmatch.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.liutils.api.client.util.RenderUtils;

public class RenderMuzzleFlash {

	private static Vec3 newV3(double x, double y, double z) {
		return RenderUtils.newV3(x, y, z);
	}
	
	public static void renderItemIn2d(Tessellator t, double tx, double ty,
			double tz) {

		Vec3 a1 = newV3(1.2, -0.4, -0.5), a2 = newV3(1.2, 0.4, -0.5), a3 = newV3(
				1.2, 0.4, 0.3), a4 = newV3(1.2, -0.4, 0.3);

		float u1 = 0.0F, v1 = 0.0F, u2 = 1.0F, v2 = 1.0F;

		t = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
		RenderUtils.loadTexture(ClientProps.getRandomMuzzleFlash());
		
		GL11.glTranslated(tx, ty + 0.1F, tz + 0.1F);
		GL11.glRotatef(45, 0.0F, 0.0F, 1.0F);
		
		t.startDrawingQuads();
		t.setColorRGBA_F(0.8F, .8F, .8F, 1.0F);
		t.setBrightness(15728880);
		RenderUtils.addVertex(a1, u2, v2);
		RenderUtils.addVertex(a2, u1, v2);
		RenderUtils.addVertex(a3, u1, v1);
		RenderUtils.addVertex(a4, u2, v1);
		t.draw();
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();

	}
}

package cn.lambdacraft.deathmatch.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cn.liutils.api.client.util.RenderUtils;

public class RenderHornet extends Render {

	Tessellator t = Tessellator.instance;

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2,
			float f, float f1) {
		Vec3 v1 = RenderUtils.newV3(-0.25, -0.25, 0), v2 = RenderUtils.newV3(
				0.25, -0.25, 0), v3 = RenderUtils.newV3(0, 0.25, 0), v4 = RenderUtils
				.newV3(0, 0, 1);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslated(d0, d1, d2);

		GL11.glRotatef(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.rotationPitch, -1.0F, 0.0F, 0.0F);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		
		t.startDrawing(GL11.GL_TRIANGLES);
		t.setColorRGBA(115, 11, 11, 255);
		
		RenderUtils.addVertex(v1);
		RenderUtils.addVertex(v2);
		RenderUtils.addVertex(v3);

		RenderUtils.addVertex(v2);
		RenderUtils.addVertex(v1);
		RenderUtils.addVertex(v4);

		RenderUtils.addVertex(v1);
		RenderUtils.addVertex(v3);
		RenderUtils.addVertex(v4);

		RenderUtils.addVertex(v3);
		RenderUtils.addVertex(v2);
		RenderUtils.addVertex(v4);

		t.draw();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}


}

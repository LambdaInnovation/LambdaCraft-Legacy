package cn.lambdacraft.deathmatch.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.deathmatch.entity.EntitySatchel;
import cn.liutils.api.client.util.RenderUtils;

public class RenderSatchel extends RenderEntity {
	Tessellator t = Tessellator.instance;

	@Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {

		GL11.glPushMatrix();

		GL11.glTranslated(par2, par4 - EntitySatchel.HEIGHT / 2, par6);
		GL11.glRotatef(((EntitySatchel) par1Entity).rotationFactor, 0, 1, 0);

		double h = EntitySatchel.HEIGHT, w1 = EntitySatchel.WIDTH1, w2 = EntitySatchel.WIDTH2;
		Vec3 v1 = Vec3.createVectorHelper(-w1, h, -w2), v2 = Vec3
				.createVectorHelper(-w1, h, w2), v3 = Vec3.createVectorHelper(
				w1, h, w2), v4 = Vec3.createVectorHelper(w1, h, -w2);
		Vec3 v5 = Vec3.createVectorHelper(-w1, -h, -w2), v6 = Vec3
				.createVectorHelper(-w1, -h, w2), v7 = Vec3.createVectorHelper(
				w1, -h, w2), v8 = Vec3.createVectorHelper(w1, -h, -w2);

		RenderUtils.loadTexture(ClientProps.SATCHEL_TOP_PATH);
		t.startDrawingQuads();
		addVertex(v1, 0, 0);
		addVertex(v2, 0, 1);
		addVertex(v3, 1, 1);
		addVertex(v4, 1, 0);
		t.draw();

		RenderUtils.loadTexture(ClientProps.SATCHEL_BOTTOM_PATH);
		t.startDrawingQuads();
		addVertex(v7, 1, 1);
		addVertex(v6, 0, 1);
		addVertex(v5, 0, 0);
		addVertex(v8, 1, 0);
		t.draw();

		RenderUtils.loadTexture(ClientProps.SATCHEL_SIDE2_PATH);
		t.startDrawingQuads();
		addVertex(v1, 0, 0);
		addVertex(v5, 1, 0);
		addVertex(v6, 1, 1);
		addVertex(v2, 0, 1);

		addVertex(v7, 1, 1);
		addVertex(v8, 1, 0);
		addVertex(v4, 0, 0);
		addVertex(v3, 0, 1);
		t.draw();

		RenderUtils.loadTexture(ClientProps.SATCHEL_SIDE_PATH);
		t.startDrawingQuads();
		addVertex(v2, 0, 0);
		addVertex(v6, 1, 0);
		addVertex(v7, 1, 1);
		addVertex(v3, 0, 1);

		addVertex(v8, 1, 1);
		addVertex(v5, 1, 0);
		addVertex(v1, 0, 0);
		addVertex(v4, 0, 1);
		t.draw();

		GL11.glPopMatrix();
	}

	protected void addVertex(Vec3 vec3, double texU, double texV) {
		t.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, texU, texV);
	}
}

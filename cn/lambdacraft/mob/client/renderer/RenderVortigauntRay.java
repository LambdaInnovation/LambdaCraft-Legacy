/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.mob.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.mob.entity.EntityVortigauntRay;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 *
 */
public class RenderVortigauntRay extends Render {

	public static final double WIDTH = 0.3F;
	
	/* (non-Javadoc)
	 * @see net.minecraft.client.renderer.entity.Render#doRender(net.minecraft.entity.Entity, double, double, double, float, float)
	 */
	@Override
	public void doRender(Entity entity, double d0, double d1, double d2,
			float f, float f1) {
		EntityVortigauntRay ray = (EntityVortigauntRay)entity;

		Tessellator tessellator = Tessellator.instance;

		GL11.glPushMatrix();

		double dx = ray.destX - ray.startX;
		double dy = ray.destY - ray.startY;
		double dz = ray.destZ - ray.startZ;
		double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
		float angle = ray.ticksExisted;
		double tx = 0.0, tz = 0.0;
		double ty = -0.63;
		
		Vec3 v1 = RenderUtils.newV3(0, 0, -WIDTH), v2 = RenderUtils.newV3(0, 0,
				WIDTH),

		v5 = RenderUtils.newV3(0, WIDTH, 0), v6 = RenderUtils.newV3(0, -WIDTH, 0);

		// Translations and rotations
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glTranslatef((float) d0, (float) d1, (float) d2);
		// GL11.glRotatef(angle, 1.0F, 0, 0);
		int rand = RenderUtils.rand.nextInt(3);
		Minecraft.getMinecraft().renderEngine.bindTexture(ClientProps.VORTIGAUNT_RAY_PATH[rand]);

		
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		tessellator.startDrawingQuads();
		tessellator.setBrightness(15728880);
		tessellator.setColorRGBA(50, 200, 50, 200);
		
		RenderUtils.addVertex(v1.addVector(tx, ty, tz), 0, 0);
		RenderUtils.addVertex(v2.addVector(tx, ty, tz), 1, 0);
		RenderUtils.addVertex(v2.addVector(dx, dy, dz), 1, d);
		RenderUtils.addVertex(v1.addVector(dx, dy, dz), 0, d);

		RenderUtils.addVertex(v5.addVector(tx, ty, tz), 0, d);
		RenderUtils.addVertex(v6.addVector(tx, ty, tz), 1, d);
		RenderUtils.addVertex(v6.addVector(dx, dy, dz), 1, 0);
		RenderUtils.addVertex(v5.addVector(dx, dy, dz), 0, 0);

		tessellator.draw();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}


	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO 自动生成的方法存根
		return null;
	}

}

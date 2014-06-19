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

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.proxy.ClientProps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * 狗眼冲击波的渲染。
 * @author WeAthFolD
 */
public class RenderShockwave extends Render {

	/**
	 * 
	 */
	public RenderShockwave() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.renderer.entity.Render#doRender(net.minecraft.entity.Entity, double, double, double, float, float)
	 */
	@Override
	public void doRender(Entity entity, double x, double y, double z,
			float f, float f1) {
		Tessellator t = Tessellator.instance;
		int tickTime = entity.ticksExisted;
		Minecraft.getMinecraft().renderEngine.bindTexture(ClientProps.SHOCKWAVE_PATH);
		renderWave(t, x, y + 0.1, z, tickTime * 0.48F);
		renderWave(t, x, y + 0.24, z, tickTime * 0.48F);
		if(tickTime > 5) {
			renderWave(t, x, y + 0.14, z, (tickTime - 5) * 0.55F);
			renderWave(t, x, y + 0.25, z, (tickTime - 5) * 0.55F);
		}
	}
	
	protected void renderWave(Tessellator t, double x, double y, double z, float radius) {
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		t.startDrawingQuads();
		t.setColorRGBA(255, 255, 255, 128);
		t.setBrightness(15728880);
		
		t.addVertexWithUV(-radius, 0.0, -radius, 0.0, 0.0);
		t.addVertexWithUV(-radius, 0.0, radius, 0.0, 1.0);
		t.addVertexWithUV(radius, 0.0, radius, 1.0, 1.0);
		t.addVertexWithUV(radius, 0.0, -radius, 1.0, 0.0);
		
		t.draw();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO 自动生成的方法存根
		return null;
	}

}

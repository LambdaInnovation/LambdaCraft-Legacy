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
package cn.lambdacraft.terrain.client.renderer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.terrain.client.model.ModelXenAmethyst;
import cn.lambdacraft.terrain.tileentity.TileEntityXenAmethyst;
import cn.liutils.api.client.render.RenderTileEntityModel;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 *
 */
public class RenderTileXenAmethyst extends RenderTileEntityModel {

	private static final float WIDTH = 0.15F;
	
	public RenderTileXenAmethyst() {
		super(new ModelXenAmethyst());
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		TileEntityXenAmethyst amethyst = (TileEntityXenAmethyst) tileentity;
		GL11.glPushMatrix();
		if(amethyst.ticksSinceLastAtack < 10) {
			renderRay(amethyst, Tessellator.instance, d0, d1, d2);
		}
		super.renderTileEntityAt(tileentity, d0, d1, d2, f);
		GL11.glPopMatrix();
	}
	
	/**
	 * Render the Amethyst ray
	 * @param ent
	 * @param t
	 * @param x
	 * @param y
	 * @param z
	 */
    private void renderRay(TileEntityXenAmethyst ent, Tessellator t, double x, double y, double z) {
    	double dx = ent.lastxCoord, dy = ent.lastyCoord, dz = ent.lastzCoord ;
//    	double tx = 0.0, ty = 0.4, tz = 0.0;
    	double tx = 0.5, ty = 0.2, tz = 0.5;
    	GL11.glPushMatrix();
    	GL11.glDisable(GL11.GL_CULL_FACE);
    	GL11.glDisable(GL11.GL_LIGHTING);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	RenderUtils.loadTexture(ClientProps.VORTIGAUNT_RAY_PATH[rng.nextInt(3)]);
    	
    	Vec3 v1 = RenderUtils.newV3(tx - WIDTH, ty, tz), v2 = RenderUtils.newV3(tx + WIDTH, ty, tz),
    		v3 = RenderUtils.newV3(tx, ty - WIDTH, tz), v4 = RenderUtils.newV3(tx, ty + WIDTH, tz);
    	double d = Math.sqrt(dx * dx +  dy * dy + dz * dz) * 1.3;
    	
    	GL11.glTranslated(x, y, z);
    	
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		t.startDrawingQuads();
		t.setBrightness(15728880);
		t.setColorRGBA_F(0.663F, 0.263F, 1.0F, 0.65F);
    	RenderUtils.addVertex(v1 , 0, 0);
    	RenderUtils.addVertex(v2 , 1, 0);
    	RenderUtils.addVertex(v2.addVector(dx, dy, dz) , 1, d);
    	RenderUtils.addVertex(v1.addVector(dx, dy, dz) , 0, d);
    	
    	RenderUtils.addVertex(v3 , 0, 0);
    	RenderUtils.addVertex(v4 , 1, 0);
    	RenderUtils.addVertex(v4.addVector(dx, dy, dz) , 1, d);
    	RenderUtils.addVertex(v3.addVector(dx, dy, dz) , 0, d);
    	t.draw();
    	
    	GL11.glEnable(GL11.GL_CULL_FACE);
    	GL11.glEnable(GL11.GL_LIGHTING);
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glPopMatrix();
    	
    }

}

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

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.mob.block.tile.TileSentryRay;
import cn.lambdacraft.mob.register.CBCMobBlocks;
import cn.liutils.api.client.render.RendererSidedCube;
import cn.liutils.api.client.util.RenderUtils;


/**
 * @author WeAthFolD
 *
 */
public class RenderSentryRay extends RendererSidedCube {

	public RenderSentryRay() {
		super(CBCMobBlocks.sentryRay);
	}

	@Override
	public ResourceLocation getTexture(TileEntity te, int side, int metadata) {
		if(side == metadata)
			return ClientProps.SS_MAIN_PATH[((TileSentryRay)te).isActivated ? 0 : 1];
		if(side == 0 || side == 1)
			return ClientProps.SS_SIDE_PATH[1];
		return ClientProps.SS_SIDE_PATH[0];
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y,
			double z, float f) {
		this.doRender(tile, x, y, z, f);
		TileSentryRay ray = (TileSentryRay) tile, ray2 = ray.linkedBlock;
		double width = 0.015;
		if(ray2 != null) {
			Vec3 v = ray2.getRayOffset();
			Vec3 va = ray.getRayOffset();
			double x1 = x + ray2.xCoord + v.xCoord - ray.xCoord, y1 =  y + ray2.yCoord + v.yCoord - ray.yCoord, z1 = z + ray2.zCoord + v.zCoord - ray.zCoord;
			x += va.xCoord;
			y += va.yCoord;
			z += va.zCoord;
			Vec3 v1 = getVec3(0, 0, -width).addVector(x, y, z), v2 = getVec3(
					0, 0, width).addVector(x, y, z), v3 = getVec3(
					0, 0, width).addVector(x1, y1, z1), v4 = getVec3(
					0, 0, -width).addVector(x1, y1, z1);

			Vec3 v5 = getVec3(-width, 0, 0).addVector(x, y, z), v6 = getVec3(
					width, 0, 0).addVector(x, y, z), v7 = getVec3(
					width, 0, 0).addVector(x1, y1, z1), v8 = getVec3(
					-width, 0, 0).addVector(x1, y1, z1);

			Vec3 v9 = getVec3(0, -width, 0).addVector(x, y, z), v10 = getVec3(
					0, width, 0).addVector(x, y, z), v11 = getVec3(
					0, width, 0).addVector(x1, y1, z1), v12 = getVec3(
					0, -width, 0).addVector(x1, y1, z1);
			double dist = ray.getDistanceFrom(ray2.xCoord, ray2.yCoord, ray2.zCoord);
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			Tessellator t = Tessellator.instance;
			
			t.startDrawingQuads();
			t.setColorRGBA_F(1.0F, 0.2F, 0.2F, 0.65F);
			t.setBrightness(512);
			RenderUtils.addVertex(v1, 0, 0);
			RenderUtils.addVertex(v2, 0, 1);
			RenderUtils.addVertex(v3, dist, 1);
			RenderUtils.addVertex(v4, dist, 0);

			RenderUtils.addVertex(v5, 0, 0);
			RenderUtils.addVertex(v6, 0, 1);
			RenderUtils.addVertex(v7, dist, 1);
			RenderUtils.addVertex(v8, dist, 0);
			RenderUtils.addVertex(v9, 0, 0);
			RenderUtils.addVertex(v10, 0, 1);
			RenderUtils.addVertex(v11, dist, 1);
			RenderUtils.addVertex(v12, dist, 0);

			t.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}
	
	private Vec3 getVec3(double x, double y, double z) {
		return Vec3.createVectorHelper(x, y, z);
	}

}

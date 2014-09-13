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
package cn.lambdacraft.crafting.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.crafting.block.tile.TileWire;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 * 
 */
public class RenderWire extends TileEntitySpecialRenderer {

	public static float WIDTH = 0.10F;

	public RenderWire() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer#
	 * renderTileEntityAt(net.minecraft.tileentity.TileEntity, double, double,
	 * double, float)
	 */
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		TileWire wire = (TileWire) tileentity;
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslated(d0 + 0.5, d1 + 0.5, d2 + 0.5);
		renderWireBox(t, -1, wire.renderSides);
		for (int i = 0; i < 6; i++) {
			if (wire.renderSides[i])
				renderWireBox(t, i, wire.renderSides);
		}
		GL11.glPopMatrix();
	}

	private void renderWireBox(Tessellator t, int side, boolean[] theArray) {
		ForgeDirection[] dirs = ForgeDirection.values();
		Vec3 v1 = RenderUtils.newV3(-WIDTH, -WIDTH, -WIDTH), v2 = RenderUtils
				.newV3(WIDTH, -WIDTH, -WIDTH), v3 = RenderUtils.newV3(WIDTH,
				-WIDTH, WIDTH), v4 = RenderUtils.newV3(-WIDTH, -WIDTH, WIDTH), v5 = RenderUtils
				.newV3(-WIDTH, WIDTH, -WIDTH), v6 = RenderUtils.newV3(WIDTH,
				WIDTH, -WIDTH), v7 = RenderUtils.newV3(WIDTH, WIDTH, WIDTH), v8 = RenderUtils
				.newV3(-WIDTH, WIDTH, WIDTH);
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float dx = 0.0F, dy = 0.0F, dz = 0.0F;
		switch (side) {
		case 0:
			dy = -1;
			break;
		case 1:
			dy = 1;
			break;
		case 4:
			dx = -1;
			break;
		case 5:
			dx = 1;
			break;
		case 2:
			dz = -1;
			break;
		case 3:
			dz = 1;
			break;
		}
		float offset = (0.5F + WIDTH) / 2F;
		GL11.glTranslatef(dx * offset, dy * offset, dz * offset);
		if (side != -1) {
			float scale = 2F;
			GL11.glScalef(Math.abs(dx == 0.0F ? 1 : dx * scale),
					Math.abs(dy == 0.0F ? 1 : dy * scale),
					Math.abs(dz == 0.0F ? 1 : dz * scale));
		}
		int a = 0;
		for (int i = 0; i < theArray.length; i++)
			if (theArray[i])
				a++;
		for (int i = 0; i < 6; i++) {
			if (!doesRenderSide(side, i, theArray))
				continue;
			Vec3 vec1 = null, vec2 = null, vec3 = null, vec4 = null;
			dx = 0.0F;
			dy = 0.0F;
			dz = 0.0F;
			switch (i) {
			case 0:
				vec1 = v4;
				vec2 = v3;
				vec3 = v2;
				vec4 = v1;
				dy = -1.0F;
				break;
			case 1:
				vec1 = v5;
				vec2 = v6;
				vec3 = v7;
				vec4 = v8;
				dy = 1.0F;
				break;
			case 4:
				vec1 = v1;
				vec2 = v5;
				vec3 = v8;
				vec4 = v4;
				dx = -1.0F;
				break;
			case 5:
				vec1 = v2;
				vec2 = v3;
				vec3 = v7;
				vec4 = v6;
				dx = 1.0F;
				break;
			case 2:
				vec1 = v1;
				vec2 = v2;
				vec3 = v6;
				vec4 = v5;
				dz = -1.0F;
				break;
			case 3:
				vec1 = v4;
				vec2 = v8;
				vec3 = v7;
				vec4 = v3;
				dz = 1.0F;
				break;
			}
			GL11.glPushMatrix();
			if (side == -1) {
				if (a == 1 && theArray[dirs[i].getOpposite().ordinal()])
					RenderUtils.loadTexture(ClientProps.WIRE_MAIN_PATH);
				else
					RenderUtils.loadTexture(ClientProps.WIRE_SIDE_PATH);
			} else {
				RenderUtils.loadTexture(ClientProps.WIRE_SIDE_PATH2);
			}

			t.startDrawingQuads();
			t.setNormal(dx, dy, dz);
			RenderUtils.addVertex(vec4, 0.0, 1.0);
			RenderUtils.addVertex(vec3, 1.0, 1.0);
			RenderUtils.addVertex(vec2, 1.0, 0.0);
			RenderUtils.addVertex(vec1, 0.0, 0.0);
			t.draw();
			GL11.glPopMatrix();
		}
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	private boolean doesRenderSide(int blockSide, int subSide,
			boolean[] theArray) {
		ForgeDirection[] dirs = ForgeDirection.values();
		if (blockSide == -1) {
			if (theArray[subSide])
				return false;
			return true;
		}
		if (dirs[blockSide].getOpposite().ordinal() == subSide
				|| (blockSide == subSide && theArray[subSide]))
			return false;
		return true;
	}
}

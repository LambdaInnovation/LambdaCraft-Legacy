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

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_COLOR;
import static org.lwjgl.opengl.GL11.GL_SRC_COLOR;
import static org.lwjgl.opengl.GL11.GL_ZERO;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL12.GL_RESCALE_NORMAL;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.crafting.entity.EntitySpray;
import cn.liutils.api.client.util.RenderUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 喷漆的渲染类
 * 
 * @author mkpoli 洋气书生(友情提供。。。)
 * 
 */
@SideOnly(Side.CLIENT)
public class RenderSpray extends Render {

	boolean isHLSpray = false;

	/**
	 * 渲染器入口
	 */
	@Override
	public void doRender(Entity entity, double pos_x, double pos_y, double pos_z, float rotation_yaw, float partial_tick_time) {
		this.renderEntity((EntitySpray) entity, pos_x, pos_y, pos_z); // 调用renderEntity，忽略rotationYaw和partucalTickTime
	}
	
	/*
	 * 载入Texture
	 */
	private void loadTexture(int title_id) {
		if (title_id >= 2)
			// 从custom文件夹下载入制定文件名的bitmap
			RenderUtils.loadTexture(ClientProps.getSprayPath(title_id - 2));
		else
			// 普通喷漆
			RenderUtils.loadTexture(ClientProps.SPRY_PATH[title_id]);
	}

	/**
	 * 渲染Spray
	 * 
	 * @param entity
	 *            Spray Entity
	 * @param pos_x
	 *            X
	 * @param pos_y
	 *            Y
	 * @param pos_z
	 *            Z
	 */
	public void renderEntity(EntitySpray entity, double pos_x, double pos_y, double pos_z) {
		
		if(entity.tId < 0)
			return;
		
		isHLSpray = (entity.tId >= 2);
		glPushMatrix(); // 打开栈
		
		glEnable(GL_RESCALE_NORMAL);
		glTranslatef((float) pos_x, (float) pos_y, (float) pos_z); // 把世界轴Transfer到Entity为原点的轴
		glRotatef(entity.rotationYaw, 0.0F, 1.0F, 0.0F); // 沿y轴旋转rotationYaw度(根据朝向)
		
		loadTexture(entity.tId);
		draw(entity); // 正式渲染
		
		glDisable(GL_RESCALE_NORMAL);
		glPopMatrix();
	}

	/**
	 * Draw size and lightmaps
	 * 
	 * @param entity
	 *            Spray Entity
	 */
	private void draw(EntitySpray entity) {

		float start_x; // x的起点(宽度的一半)
		float start_y; // Ditto.
		float half_thickness = 0.00001F;
		
		if (isHLSpray) {
			start_x = -0.5F; // 半格
			start_y = 0.5F; // 半格
			
			glEnable(GL_BLEND); // 当为HLSpray时打开Blend
			float left = start_x;
			float right = left + 1;
			float top = start_y;
			float bottom = top - 1;

			//this.render_light(entity, (right + left) / 2, (bottom + top) / 2);
			int r = 255, g = 255, b = 255;
			if(entity.color != null) {
				r = entity.color.red;
				g = entity.color.green;
				b = entity.color.blue;
			}
			glColor3f(r, g, b);
			glBlendFunc(GL_ONE, GL_ZERO);
			glBlendFunc(GL_SRC_COLOR, GL_ONE_MINUS_SRC_COLOR);
			
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads(); {
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				tessellator.addVertexWithUV(right, top, half_thickness, 1, 0);
				tessellator.addVertexWithUV(left, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, half_thickness, 0, 1);
				tessellator.addVertexWithUV(right, bottom, half_thickness, 1, 1);
			}
			tessellator.draw();
			
			glDisable(GL_BLEND);
		} else {
			start_x = -EntitySpray.GRIDS_WIDTHS[entity.tId] / 2.0F;
			start_y = EntitySpray.GRIDS_HEIGHTS[entity.tId] / 2.0F;
			
			for (float i = 0; i < EntitySpray.GRIDS_WIDTHS[entity.tId]; i++) { // i为从0到宽度的所有值
				for (float j = 0; j < EntitySpray.GRIDS_HEIGHTS[entity.tId]; j++) { // j为从0到高度的所有值

					float left = start_x + i;
					float right = left + 1;
					float top = start_y - j;
					float bottom = top - 1;

					this.render_light(entity, (right + left) / 2, (bottom + top) / 2);

					float texture_left;
					float texture_right;
					float texture_top;
					float texture_bottom;

					texture_left = i / EntitySpray.GRIDS_WIDTHS[entity.tId];
					texture_right = (i + 1) / EntitySpray.GRIDS_WIDTHS[entity.tId];
					texture_top = j / EntitySpray.GRIDS_HEIGHTS[entity.tId];
					texture_bottom = (j + 1) / EntitySpray.GRIDS_HEIGHTS[entity.tId];

					Tessellator tessellator = Tessellator.instance;
					tessellator.startDrawingQuads();

					tessellator.setNormal(0.0F, 0.0F, -1.0F);
					tessellator.addVertexWithUV(right, top, half_thickness, texture_right, texture_top);
					tessellator.addVertexWithUV(left, top, half_thickness, texture_left, texture_top);
					tessellator.addVertexWithUV(left, bottom, half_thickness, texture_left, texture_bottom);
					tessellator.addVertexWithUV(right, bottom, half_thickness, texture_right, texture_bottom);

					tessellator.draw();
				}
			}
		}
	}

	private void render_light(EntitySpray entity, float center_x, float center_y) {
		int x = MathHelper.floor_double(entity.posX);
		int y = MathHelper.floor_double(entity.posY + center_y);
		int z = MathHelper.floor_double(entity.posZ);

		if (entity.hangingDirection == 2) {
			x = MathHelper.floor_double(entity.posX - center_x);
		}

		if (entity.hangingDirection == 1) {
			z = MathHelper.floor_double(entity.posZ + center_x);
		}

		if (entity.hangingDirection == 0) {
			x = MathHelper.floor_double(entity.posX + center_x);
		}

		if (entity.hangingDirection == 3) {
			z = MathHelper.floor_double(entity.posZ - center_x);
		}

		int light = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(x, y, z, 0);
		int var8 = light % 65536;
		int var9 = light / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var8, var9);
		glColor3f(1.0F, 1.0F, 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}


}
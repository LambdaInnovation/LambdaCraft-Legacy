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
package cn.lambdacraft.terrain.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.terrain.tileentity.TileEntityXenLight;
import cn.liutils.api.client.model.ITileEntityModel;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 * 
 */
public class ModelXenLight extends ModelBase implements ITileEntityModel {

	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;

	public ModelXenLight() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 0, 11);
		Shape1.addBox(0F, 0F, 0F, 3, 4, 3);
		Shape1.setRotationPoint(-1F, 20F, -1F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 4);
		Shape2.addBox(0F, 0F, 0F, 1, 6, 1);
		Shape2.setRotationPoint(0F, 14F, 0F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 0);
		Shape3.addBox(0F, 0F, 0F, 1, 2, 2);
		Shape3.setRotationPoint(0F, 12F, 0F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

	@Override
	public void render(TileEntity is, double x, double y, double z, float f1,
			float scale, float f) {
		TileEntityXenLight tile = (TileEntityXenLight) is;
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		RenderUtils.loadTexture(ClientProps.XENLIGHT_PATH);
		Shape1.render(scale);
		//说好的伸缩~
		GL11.glRotated(12.5 * MathHelper.sin(tile.ticksExisted * 0.07F), 0.1, 1, -0.1);
		if(tile.isLighting) {
			if(tile.tickSinceChange < 15)
				GL11.glTranslatef(0.0F, 0.0166F * (15 - tile.tickSinceChange), 0.0F);
		} else { 
			if(tile.tickSinceChange < 5) 
				GL11.glTranslatef(0.0F, 0.05F * tile.tickSinceChange, 0.0F);
			else GL11.glTranslatef(0.0F, 0.25F, 0.0F);
		}
		Shape2.render(scale);
		Shape3.render(scale);
		GL11.glPopMatrix();
	}
}

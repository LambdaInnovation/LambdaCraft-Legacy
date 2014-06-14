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
package cn.lambdacraft.mob.client.model;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.mob.entity.EntityHeadcrab;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * @author WeAthFolD
 * 
 */
public class ModelHeadcrab extends ModelBase {

	// fields
	ModelRenderer leg41;
	ModelRenderer leg31;
	ModelRenderer body;
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg4;

	public ModelHeadcrab() {
		textureWidth = 64;
		textureHeight = 32;

		leg41 = new ModelRenderer(this, 0, 0);
		leg41.addBox(-1F, -1F, 0F, 1, 3, 1);
		leg41.setRotationPoint(3F, 22F, -6.533333F);
		leg41.setTextureSize(64, 32);
		leg41.mirror = true;
		setRotation(leg41, -0.8109963F, 0F, 0F);
		
		leg31 = new ModelRenderer(this, 0, 0);
		leg31.addBox(-1F, -1F, 0F, 1, 3, 1);
		leg31.setRotationPoint(-2F, 22F, -6F);
		leg31.setTextureSize(64, 32);
		leg31.mirror = true;
		setRotation(leg31, -0.8109963F, 0F, 0F);
		
		body = new ModelRenderer(this, 4, 0);
		body.addBox(-4F, -6F, -7F, 6, 6, 3);
		body.setRotationPoint(1F, 15F, 4F);
		body.setTextureSize(64, 32);
		body.mirror = true;
		setRotation(body, 1.570796F, 0F, 0F);
		
		leg1 = new ModelRenderer(this, 0, 9);
		leg1.addBox(-1F, 0F, -1F, 1, 3, 1);
		leg1.setRotationPoint(-2F, 21F, 4F);
		leg1.setTextureSize(64, 32);
		leg1.mirror = true;
		setRotation(leg1, 0.0826735F, 0F, 0F);
		
		leg2 = new ModelRenderer(this, 0, 9);
		leg2.addBox(-1F, 0F, -1F, 1, 3, 1);
		leg2.setRotationPoint(3F, 21F, 4F);
		leg2.setTextureSize(64, 32);
		leg2.mirror = true;
		setRotation(leg2, 0.0826735F, 0F, 0F);
		
		leg3 = new ModelRenderer(this, 0, 4);
		leg3.addBox(-1F, 0F, -1F, 1, 4, 1);
		leg3.setRotationPoint(-2F, 22F, -1F);
		leg3.setTextureSize(64, 32);
		leg3.mirror = true;
		setRotation(leg3, -1.48353F, 0F, 0F);
		
		leg4 = new ModelRenderer(this, 0, 4);
		leg4.addBox(0F, 0F, -1F, 1, 4, 1);
		leg4.setRotationPoint(2F, 22F, -2F);
		leg4.setTextureSize(64, 32);
		leg4.mirror = true;
		setRotation(leg4, -1.466077F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		if(!entity.onGround) {
			EntityHeadcrab crab = (EntityHeadcrab) entity;
			GL11.glTranslatef(0.0F, 1.5F, 0.0F);
			if(crab.attacher == null) {
				float angle = -MathHelper.sin((crab.ticksExisted - crab.lastJumpTick) * 0.1F) * 40F;
				GL11.glRotatef(angle, 1.0F, 0.0F, 0.0F);
			} else {
				GL11.glRotatef(35F, 1.0F, 0.0F, 0.0F);
			}
			GL11.glTranslatef(0.0F, -1.5F, 0.0F);
		}
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		leg41.render(f5);
		leg31.render(f5);
		body.render(f5);
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1 * 0.8F;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * f1 * 0.4F;
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}

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

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * @author WeAthFolD, Kelas
 * 
 */
public class ModelHoundeye extends ModelBase {
	
	// fields
	ModelRenderer head;
	ModelRenderer head0;
	ModelRenderer body;
	ModelRenderer body0;
	ModelRenderer bleg0;
	ModelRenderer lleg0;
	ModelRenderer rleg0;
	ModelRenderer back0;
	ModelRenderer back1;
	ModelRenderer back2;
	ModelRenderer lleg1;
	ModelRenderer rleg1;
	ModelRenderer bleg1;
	ModelRenderer rleg2;
	ModelRenderer lleg2;
	ModelRenderer bleg2;

	public ModelHoundeye() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4F, -4F, -3.666667F, 8, 8, 5);
		head.setRotationPoint(0F, 15F, -5F);
		head.setTextureSize(64, 64);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		head0 = new ModelRenderer(this, 0, 40);
		head0.addBox(-4F, -3F, -1F, 8, 3, 4);
		head0.setRotationPoint(0F, 14F, -5F);
		head0.setTextureSize(64, 64);
		head0.mirror = true;
		setRotation(head0, 0.2094395F, 0F, 0F);
		body = new ModelRenderer(this, 0, 13);
		body.addBox(-4F, -3F, 0F, 8, 6, 8);
		body.setRotationPoint(0F, 16F, -4F);
		body.setTextureSize(64, 64);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		body0 = new ModelRenderer(this, 0, 27);
		body0.addBox(-3F, -1F, 0F, 6, 4, 9);
		body0.setRotationPoint(0F, 12F, -3F);
		body0.setTextureSize(64, 64);
		body0.mirror = true;
		setRotation(body0, -0.2443461F, 0F, 0F);
		bleg0 = new ModelRenderer(this, 32, 13);
		bleg0.addBox(-2F, 0F, -1F, 4, 7, 4);
		bleg0.setRotationPoint(0F, 14F, 3F);
		bleg0.setTextureSize(64, 64);
		bleg0.mirror = true;
		setRotation(bleg0, 0.2094395F, 0F, 0F);
		lleg0 = new ModelRenderer(this, 26, 0);
		lleg0.addBox(0F, 0F, -2F, 2, 6, 4);
		lleg0.setRotationPoint(4F, 15F, -5F);
		lleg0.setTextureSize(64, 64);
		lleg0.mirror = true;
		setRotation(lleg0, 0F, 0F, 0F);
		rleg0 = new ModelRenderer(this, 26, 0);
		rleg0.addBox(-2F, 0F, -2F, 2, 6, 4);
		rleg0.setRotationPoint(-4F, 15F, -5F);
		rleg0.setTextureSize(64, 64);
		rleg0.mirror = true;
		setRotation(rleg0, 0F, 0F, 0F);
		back0 = new ModelRenderer(this, 0, 47);
		back0.addBox(-4F, -1F, -1F, 8, 2, 3);
		back0.setRotationPoint(0F, 13F, -2F);
		back0.setTextureSize(64, 64);
		back0.mirror = true;
		setRotation(back0, 0.6981317F, 0F, 0F);
		back1 = new ModelRenderer(this, 0, 52);
		back1.addBox(-3F, -1F, 0F, 6, 2, 2);
		back1.setRotationPoint(0F, 14F, 1.466667F);
		back1.setTextureSize(64, 64);
		back1.mirror = true;
		setRotation(back1, 0.837758F, 0F, 0F);
		back2 = new ModelRenderer(this, 0, 56);
		back2.addBox(-2F, -2F, 0F, 4, 2, 2);
		back2.setRotationPoint(0F, 15F, 4F);
		back2.setTextureSize(64, 64);
		back2.mirror = true;
		setRotation(back2, 0.3839724F, 0F, 0F);
		lleg1 = new ModelRenderer(this, 38, 0);
		lleg1.addBox(0F, 5F, 1F, 2, 4, 2);
		lleg1.setRotationPoint(4F, 15F, -5F);
		lleg1.setTextureSize(64, 64);
		lleg1.mirror = true;
		setRotation(lleg1, -0.2792527F, 0F, 0F);
		rleg1 = new ModelRenderer(this, 38, 0);
		rleg1.addBox(-2F, 4F, 1.3F, 2, 4, 2);
		rleg1.setRotationPoint(-4F, 15F, -5F);
		rleg1.setTextureSize(64, 64);
		rleg1.mirror = true;
		setRotation(rleg1, -0.2792527F, 0F, 0F);
		bleg1 = new ModelRenderer(this, 38, 0);
		bleg1.addBox(-1F, 0F, -1F, 2, 4, 2);
		bleg1.setRotationPoint(0F, 20F, 5F);
		bleg1.setTextureSize(64, 64);
		bleg1.mirror = true;
		setRotation(bleg1, 0.2792527F, 0F, 0F);
		rleg2 = new ModelRenderer(this, 26, 10);
		rleg2.addBox(-2F, 8F, -2F, 2, 1, 3);
		rleg2.setRotationPoint(-4F, 15F, -5F);
		rleg2.setTextureSize(64, 64);
		rleg2.mirror = true;
		setRotation(rleg2, 0F, 0F, 0F);
		lleg2 = new ModelRenderer(this, 26, 10);
		lleg2.addBox(0F, 8F, -2F, 2, 1, 3);
		lleg2.setRotationPoint(4F, 15F, -5F);
		lleg2.setTextureSize(64, 64);
		lleg2.mirror = true;
		setRotation(lleg2, 0F, 0F, 0F);
		bleg2 = new ModelRenderer(this, 26, 10);
		bleg2.addBox(-1F, 3F, -1F, 2, 1, 3);
		bleg2.setRotationPoint(0F, 20F, 5F);
		bleg2.setTextureSize(64, 64);
		bleg2.mirror = true;
		setRotation(bleg2, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		head0.render(f5);
		body.render(f5);
		body0.render(f5);
		bleg0.render(f5);
		lleg0.render(f5);
		rleg0.render(f5);
		back0.render(f5);
		back1.render(f5);
		back2.render(f5);
		lleg1.render(f5);
		rleg1.render(f5);
		bleg1.render(f5);
		rleg2.render(f5);
		lleg2.render(f5);
		bleg2.render(f5);
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
		float angle = MathHelper.cos(f * 0.35F) * f1 * 0.9F, 
			  angle2 = MathHelper.cos(f * 0.35F + (float)Math.PI) * f1 * 1.3F;
		this.lleg0.rotateAngleX = angle;
		this.lleg1.rotateAngleX = angle;
		this.lleg2.rotateAngleX = angle;
		this.rleg0.rotateAngleX = angle;
		this.rleg1.rotateAngleX = angle;
		this.rleg2.rotateAngleX = angle;
		bleg0.rotateAngleX = -angle;
		bleg1.rotateAngleX = -angle;
		bleg2.rotateAngleX = -angle;
	}
}

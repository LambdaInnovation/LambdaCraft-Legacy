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

import cn.lambdacraft.mob.entity.EntitySentry;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @author WeAthFolD
 * 
 */
public class ModelTurret extends ModelBase {
	// fields
	ModelRenderer chassis;
	ModelRenderer leg0;
	ModelRenderer leg10;
	ModelRenderer leg11;
	ModelRenderer contact;
	ModelRenderer side0;
	ModelRenderer side1;
	ModelRenderer top;
	ModelRenderer sensor;
	ModelRenderer gun0;
	ModelRenderer gun1;

	public ModelTurret() {
		textureWidth = 64;
		textureHeight = 32;

		chassis = new ModelRenderer(this, 44, 4);
		chassis.addBox(-2.5F, 0F, -2.5F, 5, 2, 5);
		chassis.setRotationPoint(0F, 14F, 0F);
		chassis.setTextureSize(64, 32);
		chassis.mirror = true;
		setRotation(chassis, 0F, 0F, 0F);
		leg0 = new ModelRenderer(this, 51, 11);
		leg0.addBox(-1F, 0F, 0F, 2, 9, 1);
		leg0.setRotationPoint(0F, 16F, 1F);
		leg0.setTextureSize(64, 32);
		leg0.mirror = true;
		setRotation(leg0, 0.3141593F, 0F, 0F);
		leg10 = new ModelRenderer(this, 60, 11);
		leg10.addBox(-1F, 0F, -1F, 1, 9, 1);
		leg10.setRotationPoint(-1F, 16F, -1F);
		leg10.setTextureSize(64, 32);
		leg10.mirror = true;
		setRotation(leg10, -0.4363323F, 0.2617994F, 0F);
		leg11 = new ModelRenderer(this, 60, 11);
		leg11.addBox(-1F, 0F, -1F, 1, 9, 1);
		leg11.setRotationPoint(1.7F, 16F, -1F);
		leg11.setTextureSize(64, 32);
		leg11.mirror = true;
		setRotation(leg11, -0.4363323F, -0.2617994F, 0F);
		contact = new ModelRenderer(this, 56, 0);
		contact.addBox(-1F, 0F, -1F, 2, 2, 2);
		contact.setRotationPoint(0F, 12.5F, 0F);
		contact.setTextureSize(64, 32);
		contact.mirror = true;
		setRotation(contact, 0F, 0F, 0F);
		side0 = new ModelRenderer(this, 0, 8);
		side0.addBox(1F, 0F, -1.5F, 1, 11, 3);
		side0.setRotationPoint(0F, 2F, 0F);
		side0.setTextureSize(64, 32);
		side0.mirror = true;
		setRotation(side0, 0F, 0F, 0F);
		side1 = new ModelRenderer(this, 0, 8);
		side1.addBox(0F, 0F, 0F, 1, 11, 3);
		side1.setRotationPoint(-2F, 2F, -1.5F);
		side1.setTextureSize(64, 32);
		side1.mirror = true;
		setRotation(side1, 0F, 0F, 0F);
		top = new ModelRenderer(this, 0, 0);
		top.addBox(0F, 0F, -1.5F, 2, 1, 3);
		top.setRotationPoint(-1F, 2F, 0F);
		top.setTextureSize(64, 32);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
		sensor = new ModelRenderer(this, 0, 4);
		sensor.addBox(-1F, -1F, -1F, 2, 1, 2);
		sensor.setRotationPoint(1F, 2F, 0F);
		sensor.setTextureSize(64, 32);
		sensor.mirror = true;
		setRotation(sensor, 0F, 0F, 0F);
		gun0 = new ModelRenderer(this, 16, 0);
		gun0.addBox(-1F, -1F, -1F, 2, 7, 2);
		gun0.setRotationPoint(0F, 5F, 0F);
		gun0.setTextureSize(64, 32);
		gun0.mirror = true;
		setRotation(gun0, 0F, 0F, 0F);
		gun1 = new ModelRenderer(this, 24, 0);
		gun1.addBox(0F, 0F, -0.5F, 2, 3, 2);
		gun1.setRotationPoint(-1F, 5F, 1F);
		gun1.setTextureSize(64, 32);
		gun1.mirror = true;
		setRotation(gun1, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		chassis.render(f5);
		leg0.render(f5);
		leg10.render(f5);
		leg11.render(f5);
		contact.render(f5);
	}
	
	public void renderTop(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		side0.render(f5);
		side1.render(f5);
		top.render(f5);
		sensor.render(f5);
		gun0.render(f5);
		gun1.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity e) {
		EntitySentry sentry = (EntitySentry) e;
		if(sentry.isActivated) {
			if(sentry.activationCounter < 20) {
				float rotation = 1.570796F * sentry.activationCounter / 20;
				gun0.rotateAngleX = -rotation;
				gun1.rotateAngleX = rotation;
				sensor.rotateAngleZ = rotation;
			} else {
				float rotation = -0.01745329F * sentry.rotationPitch;
				if(rotation > 1.0F)
					rotation = 1.0F;
				else if(rotation < -1.0F)
					rotation = -1.0F;
				gun0.rotateAngleX = -1.570796F +rotation;
				gun1.rotateAngleX = 1.570796F +rotation;
				sensor.rotateAngleZ = 1.570796F;
			}
		} else {
			gun0.rotateAngleX = 0.0F;
			gun1.rotateAngleX = 0.0F;
			sensor.rotateAngleZ = 0.0F;
		}
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

}

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
 * @author WeAthFolD
 * 
 */
public class ModelBarnacle extends ModelBase {

	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape41;
	ModelRenderer Shape42;
	ModelRenderer Shape43;
	ModelRenderer Shape44;
	ModelRenderer Shape45;
	ModelRenderer Shape46;
	ModelRenderer Shape47;
	ModelRenderer Shape48;
	ModelRenderer Shape49;
	ModelRenderer Shape40;
	ModelRenderer Shape4a;

	public ModelBarnacle() {
		textureWidth = 128;
		textureHeight = 64;

		Shape1 = new ModelRenderer(this, 0, 18);
		Shape1.addBox(-6F, 0F, -6F, 12, 6, 12);
		Shape1.setRotationPoint(0F, 8F, 0F);
		Shape1.setTextureSize(128, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 36);
		Shape2.addBox(-5.5F, 0F, -5.5F, 11, 3, 11);
		Shape2.setRotationPoint(0F, 14F, 0F);
		Shape2.setTextureSize(128, 64);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 50);
		Shape3.addBox(-5F, 0F, -5F, 9, 4, 10);
		Shape3.setRotationPoint(0F, 17F, 0F);
		Shape3.setTextureSize(128, 64);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape41 = new ModelRenderer(this, 0, 0);
		Shape41.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape41.setRotationPoint(3F, 21F, -4F);
		Shape41.setTextureSize(128, 64);
		Shape41.mirror = true;
		setRotation(Shape41, 0F, 0F, 0F);
		Shape42 = new ModelRenderer(this, 0, 0);
		Shape42.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape42.setRotationPoint(2F, 21F, -2F);
		Shape42.setTextureSize(128, 64);
		Shape42.mirror = true;
		setRotation(Shape42, 0F, 0F, 0F);
		Shape43 = new ModelRenderer(this, 0, 0);
		Shape43.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape43.setRotationPoint(3F, 21F, 1F);
		Shape43.setTextureSize(128, 64);
		Shape43.mirror = true;
		setRotation(Shape43, 0F, 0F, 0F);
		Shape44 = new ModelRenderer(this, 0, 0);
		Shape44.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape44.setRotationPoint(3F, 21F, 3F);
		Shape44.setTextureSize(128, 64);
		Shape44.mirror = true;
		setRotation(Shape44, 0F, 0F, 0F);
		Shape45 = new ModelRenderer(this, 0, 0);
		Shape45.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape45.setRotationPoint(1F, 21F, 4F);
		Shape45.setTextureSize(128, 64);
		Shape45.mirror = true;
		setRotation(Shape45, 0F, 0F, 0F);
		Shape46 = new ModelRenderer(this, 0, 0);
		Shape46.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape46.setRotationPoint(-2F, 21F, 3F);
		Shape46.setTextureSize(128, 64);
		Shape46.mirror = true;
		setRotation(Shape46, 0F, 0F, 0F);
		Shape47 = new ModelRenderer(this, 0, 0);
		Shape47.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape47.setRotationPoint(-4F, 21F, 3F);
		Shape47.setTextureSize(128, 64);
		Shape47.mirror = true;
		setRotation(Shape47, 0F, 0F, 0F);
		Shape48 = new ModelRenderer(this, 0, 0);
		Shape48.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape48.setRotationPoint(1F, 21F, -5.133333F);
		Shape48.setTextureSize(128, 64);
		Shape48.mirror = true;
		setRotation(Shape48, 0F, 0F, 0F);
		Shape49 = new ModelRenderer(this, 0, 0);
		Shape49.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape49.setRotationPoint(-2F, 21F, -3F);
		Shape49.setTextureSize(128, 64);
		Shape49.mirror = true;
		setRotation(Shape49, 0F, 0F, 0F);
		Shape40 = new ModelRenderer(this, 0, 0);
		Shape40.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape40.setRotationPoint(-5F, 21F, 0F);
		Shape40.setTextureSize(128, 64);
		Shape40.mirror = true;
		setRotation(Shape40, 0F, 0F, 0F);
		Shape4a = new ModelRenderer(this, 0, 0);
		Shape4a.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape4a.setRotationPoint(-4F, 21F, -2F);
		Shape4a.setTextureSize(128, 64);
		Shape4a.mirror = true;
		setRotation(Shape4a, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape41.render(f5);
		Shape42.render(f5);
		Shape43.render(f5);
		Shape44.render(f5);
		Shape45.render(f5);
		Shape46.render(f5);
		Shape47.render(f5);
		Shape48.render(f5);
		Shape49.render(f5);
		Shape40.render(f5);
		Shape4a.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity ent) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
		float fa = 0.7853981634F;
		setRotation(Shape1, 0.0F, MathHelper.sin(ent.ticksExisted * 0.1F) * 0.05F, 0.0F);
		setRotation(Shape2, 0.0F, -MathHelper.cos(ent.ticksExisted * 0.1F) * 0.05F, 0.0F);
		setRotation(Shape3, 0.0F, MathHelper.sin(ent.ticksExisted * 0.1F + fa) * 0.05F, 0.0F);
	}
}

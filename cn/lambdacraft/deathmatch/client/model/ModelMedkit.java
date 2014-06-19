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
package cn.lambdacraft.deathmatch.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Medkit model.
 * 
 * @author Kelas
 * 
 */
public class ModelMedkit extends ModelBase {

	// fields
	ModelRenderer Shape1;
	ModelRenderer Shapea;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;

	public ModelMedkit() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 0, 19);
		Shape1.addBox(-3F, 0F, -4F, 6, 2, 8);
		Shape1.setRotationPoint(0F, 22F, 0F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);

		Shapea = new ModelRenderer(this, 0, 11);
		Shapea.addBox(-4F, 0F, -3F, 8, 2, 6);
		Shapea.setRotationPoint(0F, 22F, 0F);
		Shapea.setTextureSize(64, 32);
		Shapea.mirror = true;
		setRotation(Shapea, 0F, 0F, 0F);

		Shape2 = new ModelRenderer(this, 0, 4);
		Shape2.addBox(-3F, 0F, -3F, 6, 1, 6);
		Shape2.setRotationPoint(0F, 21.5F, 0F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);

		Shape3 = new ModelRenderer(this, 10, -2);
		Shape3.addBox(-4F, -1F, -2F, 8, 2, 2);
		Shape3.setRotationPoint(0F, 23F, -3F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);

		Shape4 = new ModelRenderer(this, 0, -1);
		Shape4.addBox(-2F, -1F, -3F, 4, 2, 1);
		Shape4.setRotationPoint(-2F, 23F, -3F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		GL11.glTranslatef(0.0F, -1.1F, 0.0F);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f5);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
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

}

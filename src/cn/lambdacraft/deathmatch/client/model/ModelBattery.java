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

import cn.liutils.api.client.model.IItemModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * @author Mkpoli
 * 
 */
public class ModelBattery extends ModelBase implements IItemModel {
	
	public ModelBattery() {
		BatSide = new ModelRenderer(this, 0, 0);
		BatSide.addBox(-5F, 12F, -4F, 8, 13, 8, 0F);
		BatSide.setRotationPoint(0F, 0F, 0F);
		BatSide.rotateAngleX = 0F;
		BatSide.rotateAngleY = 0F;
		BatSide.rotateAngleZ = 0F;
		BatSide.mirror = false;
		BatContact = new ModelRenderer(this, 24, 0);
		BatContact.addBox(-2F, 8F, -6F, 2, 3, 1, 0F);
		BatContact.setRotationPoint(0F, 0F, 0F);
		BatContact.rotateAngleX = 0.6981317F;
		BatContact.rotateAngleY = 0F;
		BatContact.rotateAngleZ = 0F;
		BatContact.mirror = false;
		BatTop = new ModelRenderer(this, 30, 0);
		BatTop.addBox(-2.466667F, 11F, 0.8F, 3, 1, 3, 0F);
		BatTop.setRotationPoint(0F, 0F, 0F);
		BatTop.rotateAngleX = 0F;
		BatTop.rotateAngleY = 0F;
		BatTop.rotateAngleZ = 0F;
		BatTop.mirror = false;
		BatPanel = new ModelRenderer(this, 0, 0);
		BatPanel.addBox(-2F, 9.533334F, -3F, 2, 3, 2, 0F);
		BatPanel.setRotationPoint(0F, 0F, 0F);
		BatPanel.rotateAngleX = 0F;
		BatPanel.rotateAngleY = 0F;
		BatPanel.rotateAngleZ = 0F;
		BatPanel.mirror = false;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		BatSide.render(f5);
		BatContact.render(f5);
		BatTop.render(f5);
		BatPanel.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity ent) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
	}

	// fields
	public ModelRenderer BatSide;
	public ModelRenderer BatContact;
	public ModelRenderer BatTop;
	public ModelRenderer BatPanel;
	
	@Override
	public void render(ItemStack is, float f5, float f) {
		BatSide.render(f5);
		BatContact.render(f5);
		BatTop.render(f5);
		BatPanel.render(f5);
	}

	@Override
	public void setRotationAngles(ItemStack is, double posX, double posY,
			double posZ, float f) {
	}
}

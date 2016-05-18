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

import cn.liutils.api.client.model.IItemModel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

/**
 * @author WeAthFolD
 * 
 */
@SideOnly(Side.CLIENT)
public class ModelSnark extends ModelBase implements IItemModel {
    ModelRenderer head;
    ModelRenderer body1;
    ModelRenderer body2;
    ModelRenderer body3;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer leg4;

    public ModelSnark() {
        textureWidth = 64;
        textureHeight = 32;

        head = new ModelRenderer(this, 0, 0);
        head.addBox(-1F, 0F, -1F, 2, 2, 2);
        head.setRotationPoint(1F, 20F, -4F);
        head.setTextureSize(64, 32);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        body1 = new ModelRenderer(this, 0, 4);
        body1.addBox(-2F, -1F, 0F, 4, 2, 3);
        body1.setRotationPoint(1F, 21F, -4F);
        body1.setTextureSize(64, 32);
        body1.mirror = true;
        setRotation(body1, 0.2094395F, 0F, 0F);
        body2 = new ModelRenderer(this, 0, 16);
        body2.addBox(-2F, -1F, -1F, 4, 2, 4);
        body2.setRotationPoint(1F, 20F, -1F);
        body2.setTextureSize(64, 32);
        body2.mirror = true;
        setRotation(body2, -0.2094395F, 0F, 0F);
        body3 = new ModelRenderer(this, 0, 9);
        body3.addBox(-1F, 0F, -3F, 2, 2, 5);
        body3.setRotationPoint(1F, 21F, 0F);
        body3.setTextureSize(64, 32);
        body3.mirror = true;
        setRotation(body3, 0F, 0F, 0F);
        leg1 = new ModelRenderer(this, 8, 16);
        leg1.addBox(-1F, 0F, 0F, 1, 3, 1);
        leg1.setRotationPoint(1F, 23F, 1F);
        leg1.setTextureSize(64, 32);
        leg1.mirror = true;
        setRotation(leg1, 0.0174533F, 0.0174533F, 1.047198F);
        leg2 = new ModelRenderer(this, 8, 0);
        leg2.addBox(0F, 0F, 0F, 1, 3, 1);
        leg2.setRotationPoint(1F, 23F, 1F);
        leg2.setTextureSize(64, 32);
        leg2.mirror = true;
        setRotation(leg2, 0F, 0F, -1.117011F);
        leg3 = new ModelRenderer(this, 8, 0);
        leg3.addBox(-1F, 0F, 0F, 1, 3, 1);
        leg3.setRotationPoint(1F, 23F, -2F);
        leg3.setTextureSize(64, 32);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 1.117011F);
        leg4 = new ModelRenderer(this, 8, 0);
        leg4.addBox(0F, 0F, 0F, 1, 3, 1);
        leg4.setRotationPoint(1F, 23F, -2F);
        leg4.setTextureSize(64, 32);
        leg4.mirror = true;
        setRotation(leg4, 0F, 0F, -1.117011F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3,
            float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        head.render(f5);
        body1.render(f5);
        body2.render(f5);
        body3.render(f5);
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
    public void setRotationAngles(float par1, float par2, float par3,
            float par4, float par5, float par6, Entity ent) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, ent);
        this.head.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.head.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.leg1.rotateAngleX = MathHelper.cos(par1 * 4.0F) * 0.5F * par2;
        this.leg2.rotateAngleX = MathHelper.cos(par1 * 4.0F + (float) Math.PI)
                * 0.5F * par2;
        this.leg3.rotateAngleX = MathHelper.cos(par1 * 4.0F + (float) Math.PI)
                * 0.5F * par2;
        this.leg4.rotateAngleX = MathHelper.cos(par1 * 4.0F) * 0.5F * par2;
    }

    @Override
    public void render(ItemStack is, float f5, float f) {
        setRotationAngles(is, 0, 0, 0, f);
        head.render(f5);
        body1.render(f5);
        body2.render(f5);
        body3.render(f5);
        leg1.render(f5);
        leg2.render(f5);
        leg3.render(f5);
        leg4.render(f5);
    }

    @Override
    public void setRotationAngles(ItemStack is, double posX, double posY,
            double posZ, float f) {
        this.head.rotateAngleY = .5F * MathHelper.sin(f + 0.25F * (float)Math.PI);
        this.body1.rotateAngleY = .3F * MathHelper.sin(f + 0.25F * (float)Math.PI);
        this.head.rotateAngleX = .5F * MathHelper.cos(f);
        this.body1.rotateAngleX = .3F * MathHelper.cos(f);
    }
}

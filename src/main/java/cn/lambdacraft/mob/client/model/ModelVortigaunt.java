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

import cn.lambdacraft.mob.entity.EntityAlienSlave;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * 好巨大的模型类……谢谢昂叔和绿城的倾力支持
 * 
 * @author WeAthFolD
 * 
 */
public class ModelVortigaunt extends ModelBase {

    // fields
    ModelRenderer chest;
    ModelRenderer stomache;
    ModelRenderer head;
    ModelRenderer rarm0;
    ModelRenderer rarm1;
    ModelRenderer larm0;
    ModelRenderer larm1;
    ModelRenderer tent0;
    ModelRenderer rhand;
    ModelRenderer lhand;
    ModelRenderer rfinger0;
    ModelRenderer rfinger1;
    ModelRenderer rear;
    ModelRenderer lear;
    ModelRenderer neck;
    ModelRenderer collar1;
    ModelRenderer collar2;
    ModelRenderer collar3;
    ModelRenderer lfinger0;
    ModelRenderer lfinger1;
    ModelRenderer lcollar1;
    ModelRenderer collar4;
    ModelRenderer collar5;
    ModelRenderer rcollar1;
    ModelRenderer rleg0;
    ModelRenderer lleg0;
    ModelRenderer rleg1;
    ModelRenderer lleg1;
    ModelRenderer rleg2;
    ModelRenderer lleg2;
    ModelRenderer rleg3;
    ModelRenderer lleg3;
    ModelRenderer rleg4;
    ModelRenderer rleg5;
    ModelRenderer lleg4;
    ModelRenderer lleg5;
    ModelRenderer collar6;

    public ModelVortigaunt() {
        textureWidth = 64;
        textureHeight = 32;

        chest = new ModelRenderer(this, 40, 3);
        chest.addBox(0F, 0F, 0F, 7, 7, 5);
        chest.setRotationPoint(-3F, 3F, 0F);
        chest.setTextureSize(64, 32);
        chest.mirror = true;
        setRotation(chest, 0.3316126F, 0F, 0F);
        stomache = new ModelRenderer(this, 20, 18);
        stomache.addBox(1F, -2F, 7F, 5, 6, 3);
        stomache.setRotationPoint(-3F, 13F, -2F);
        stomache.setTextureSize(64, 32);
        stomache.mirror = true;
        setRotation(stomache, 0.296706F, 0F, 0F);
        head = new ModelRenderer(this, 0, 15);
        head.addBox(-3F, -2.7F, -5F, 5, 4, 5);
        head.setRotationPoint(1F, 5F, -1F);
        head.setTextureSize(64, 32);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        rarm0 = new ModelRenderer(this, 38, 24);
        rarm0.addBox(-1F, 0F, -1F, 2, 6, 2);
        rarm0.setRotationPoint(-4F, 5F, 2F);
        rarm0.setTextureSize(64, 32);
        rarm0.mirror = true;
        setRotation(rarm0, 0F, 0F, 0F);
        rarm1 = new ModelRenderer(this, 36, 15);
        rarm1.addBox(-1F, 4F, -9F, 2, 2, 7);
        rarm1.setRotationPoint(-4F, 5F, 2F);
        rarm1.setTextureSize(64, 32);
        rarm1.mirror = true;
        setRotation(rarm1, 0.5410521F, 0F, 0F);
        larm0 = new ModelRenderer(this, 38, 24);
        larm0.addBox(0F, 0F, -1F, 2, 6, 2);
        larm0.setRotationPoint(4F, 5F, 2F);
        larm0.setTextureSize(64, 32);
        larm0.mirror = true;
        setRotation(larm0, 0F, 0F, 0F);
        larm1 = new ModelRenderer(this, 36, 15);
        larm1.addBox(0F, 4.5F, -8.7F, 2, 2, 7);
        larm1.setRotationPoint(4F, 5F, 2F);
        larm1.setTextureSize(64, 32);
        larm1.mirror = true;
        setRotation(larm1, 0.5410521F, 0F, 0F);
        tent0 = new ModelRenderer(this, 8, 0);
        tent0.addBox(2F, 2F, 4F, 2, 3, 1);
        tent0.setRotationPoint(-3F, 4F, -1F);
        tent0.setTextureSize(64, 32);
        tent0.mirror = true;
        setRotation(tent0, -0.5759587F, 0F, 0F);
        rhand = new ModelRenderer(this, 30, 4);
        rhand.addBox(-1F, 8F, -8F, 2, 2, 3);
        rhand.setRotationPoint(-4F, 5F, 2F);
        rhand.setTextureSize(64, 32);
        rhand.mirror = true;
        setRotation(rhand, 0F, 0F, 0F);
        lhand = new ModelRenderer(this, 30, 4);
        lhand.addBox(0F, 8F, -7F, 2, 2, 3);
        lhand.setRotationPoint(4F, 5F, 2F);
        lhand.setTextureSize(64, 32);
        lhand.mirror = true;
        setRotation(lhand, 0F, 0F, 0F);
        rfinger0 = new ModelRenderer(this, 26, 0);
        rfinger0.addBox(1.166667F, 12F, 0F, 1, 4, 1);
        rfinger0.setRotationPoint(-4F, 5F, 2F);
        rfinger0.setTextureSize(64, 32);
        rfinger0.mirror = true;
        setRotation(rfinger0, -0.6632251F, 0.3141593F, 0F);
        rfinger1 = new ModelRenderer(this, 26, 0);
        rfinger1.addBox(-4F, 11F, 0F, 1, 4, 1);
        rfinger1.setRotationPoint(-4F, 5F, 2F);
        rfinger1.setTextureSize(64, 32);
        rfinger1.mirror = true;
        setRotation(rfinger1, -0.6806784F, -0.5410521F, 0F);
        rear = new ModelRenderer(this, 60, 1);
        rear.addBox(-4F, -1F, -4F, 1, 1, 1);
        rear.setRotationPoint(1F, 5F, -1F);
        rear.setTextureSize(64, 32);
        rear.mirror = true;
        setRotation(rear, 0F, 0F, 0F);
        lear = new ModelRenderer(this, 60, 1);
        lear.addBox(2F, -1F, -4F, 1, 1, 1);
        lear.setRotationPoint(1F, 5F, -1F);
        lear.setTextureSize(64, 32);
        lear.mirror = true;
        setRotation(lear, 0F, 0F, 0F);
        neck = new ModelRenderer(this, -1, 7);
        neck.addBox(-1F, 0F, 0F, 5, 5, 3);
        neck.setRotationPoint(-1F, 2F, -1F);
        neck.setTextureSize(64, 32);
        neck.mirror = true;
        setRotation(neck, 0F, 0F, 0F);
        collar1 = new ModelRenderer(this, 61, 16);
        collar1.addBox(0F, -3F, -1F, 1, 1, 1);
        collar1.setRotationPoint(-1F, 4F, 0F);
        collar1.setTextureSize(64, 32);
        collar1.mirror = true;
        setRotation(collar1, 0F, 0F, 0F);
        collar2 = new ModelRenderer(this, 56, 18);
        collar2.addBox(4F, 0F, 0F, 1, 1, 3);
        collar2.setRotationPoint(-5F, 0F, -1F);
        collar2.setTextureSize(64, 32);
        collar2.mirror = true;
        setRotation(collar2, 0F, 0F, 0F);
        collar3 = new ModelRenderer(this, 61, 16);
        collar3.addBox(4F, -3F, 1F, 1, 1, 1);
        collar3.setRotationPoint(-5F, 4F, 0F);
        collar3.setTextureSize(64, 32);
        collar3.mirror = true;
        setRotation(collar3, 0F, 0F, 0F);
        lfinger0 = new ModelRenderer(this, 26, 0);
        lfinger0.addBox(2.8F, 11F, -1F, 1, 4, 1);
        lfinger0.setRotationPoint(4F, 5F, 2F);
        lfinger0.setTextureSize(64, 32);
        lfinger0.mirror = true;
        setRotation(lfinger0, -0.5235988F, 0.418879F, 0F);
        lfinger1 = new ModelRenderer(this, 26, 0);
        lfinger1.addBox(1F, 11F, -2.2F, 1, 4, 1);
        lfinger1.setRotationPoint(4F, 5F, 2F);
        lfinger1.setTextureSize(64, 32);
        lfinger1.mirror = true;
        setRotation(lfinger1, -0.4886922F, 0F, 0F);
        lcollar1 = new ModelRenderer(this, 14, 4);
        lcollar1.addBox(-1F, 5F, -8F, 4, 4, 2);
        lcollar1.setRotationPoint(4F, 5F, 2F);
        lcollar1.setTextureSize(64, 32);
        lcollar1.mirror = true;
        setRotation(lcollar1, 0.2443461F, 0F, 0F);
        collar4 = new ModelRenderer(this, 14, 0);
        collar4.addBox(2F, 6F, -1F, 2, 1, 2);
        collar4.setRotationPoint(-3F, 4F, 0F);
        collar4.setTextureSize(64, 32);
        collar4.mirror = true;
        setRotation(collar4, 0F, 0F, 0F);
        collar5 = new ModelRenderer(this, 22, 0);
        collar5.addBox(2F, 5F, 3F, 1, 2, 1);
        collar5.setRotationPoint(-3F, 5F, -1F);
        collar5.setTextureSize(64, 32);
        collar5.mirror = true;
        setRotation(collar5, -0.5759587F, -0.0174533F, 0F);
        rcollar1 = new ModelRenderer(this, 14, 4);
        rcollar1.addBox(-2F, 5.4F, -8.5F, 4, 4, 2);
        rcollar1.setRotationPoint(-4F, 5F, 2F);
        rcollar1.setTextureSize(64, 32);
        rcollar1.mirror = true;
        setRotation(rcollar1, 0.2443461F, 0F, 0F);
        rleg0 = new ModelRenderer(this, 46, 24);
        rleg0.addBox(-1F, 0F, -1F, 2, 6, 2);
        rleg0.setRotationPoint(-3F, 12F, 7F);
        rleg0.setTextureSize(64, 32);
        rleg0.mirror = true;
        setRotation(rleg0, 0F, -0.0174533F, 0F);
        lleg0 = new ModelRenderer(this, 46, 24);
        lleg0.addBox(-1F, 0F, -1F, 2, 6, 2);
        lleg0.setRotationPoint(4F, 12F, 7F);
        lleg0.setTextureSize(64, 32);
        lleg0.mirror = true;
        setRotation(lleg0, 0F, 0F, 0F);
        rleg1 = new ModelRenderer(this, 54, 22);
        rleg1.addBox(-1F, 3F, 4F, 2, 7, 3);
        rleg1.setRotationPoint(-3F, 12F, 7F);
        rleg1.setTextureSize(64, 32);
        rleg1.mirror = true;
        setRotation(rleg1, -0.8552113F, -0.0174533F, 0F);
        lleg1 = new ModelRenderer(this, 54, 22);
        lleg1.addBox(-1F, 3F, 4F, 2, 7, 3);
        lleg1.setRotationPoint(4F, 12F, 7F);
        lleg1.setTextureSize(64, 32);
        lleg1.mirror = true;
        setRotation(lleg1, -0.8552113F, 0F, 0F);
        rleg2 = new ModelRenderer(this, 0, 24);
        rleg2.addBox(-2F, 9F, -7F, 4, 3, 5);
        rleg2.setRotationPoint(-3F, 12F, 7F);
        rleg2.setTextureSize(64, 32);
        rleg2.mirror = true;
        setRotation(rleg2, 0F, -0.0174533F, 0F);
        lleg2 = new ModelRenderer(this, 0, 24);
        lleg2.addBox(-2F, 9F, -7F, 4, 3, 5);
        lleg2.setRotationPoint(4F, 12F, 7F);
        lleg2.setTextureSize(64, 32);
        lleg2.mirror = true;
        setRotation(lleg2, 0F, 0F, 0F);
        rleg3 = new ModelRenderer(this, 18, 27);
        rleg3.addBox(-1F, 10F, -10F, 2, 2, 3);
        rleg3.setRotationPoint(-3F, 12F, 7F);
        rleg3.setTextureSize(64, 32);
        rleg3.mirror = true;
        setRotation(rleg3, 0F, 0F, 0F);
        lleg3 = new ModelRenderer(this, 18, 27);
        lleg3.addBox(-1F, 10F, -10F, 2, 2, 3);
        lleg3.setRotationPoint(4F, 12F, 7F);
        lleg3.setTextureSize(64, 32);
        lleg3.mirror = true;
        setRotation(lleg3, 0F, 0F, 0F);
        rleg4 = new ModelRenderer(this, 28, 28);
        rleg4.addBox(-2F, 11F, -2F, 1, 1, 3);
        rleg4.setRotationPoint(-3F, 12F, 7F);
        rleg4.setTextureSize(64, 32);
        rleg4.mirror = true;
        setRotation(rleg4, 0F, 0F, 0F);
        rleg5 = new ModelRenderer(this, 28, 28);
        rleg5.addBox(1F, 11F, -2F, 1, 1, 3);
        rleg5.setRotationPoint(-3F, 12F, 7F);
        rleg5.setTextureSize(64, 32);
        rleg5.mirror = true;
        setRotation(rleg5, 0F, 0F, 0F);
        lleg4 = new ModelRenderer(this, 28, 28);
        lleg4.addBox(-2F, 11F, -2F, 1, 1, 3);
        lleg4.setRotationPoint(4F, 12F, 7F);
        lleg4.setTextureSize(64, 32);
        lleg4.mirror = true;
        setRotation(lleg4, 0F, 0F, 0F);
        lleg5 = new ModelRenderer(this, 28, 28);
        lleg5.addBox(1F, 11F, -2F, 1, 1, 3);
        lleg5.setRotationPoint(4F, 12F, 7F);
        lleg5.setTextureSize(64, 32);
        lleg5.mirror = true;
        setRotation(lleg5, 0F, 0F, 0F);
        collar6 = new ModelRenderer(this, 22, 0);
        collar6.addBox(0F, 0F, 0F, 1, 2, 1);
        collar6.setRotationPoint(0F, 11F, -1F);
        collar6.setTextureSize(64, 32);
        collar6.mirror = true;
        setRotation(collar6, -0.5759587F, -0.0174533F, -0.2792527F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3,
            float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        GL11.glTranslatef(0.0F, -0.45F, 0.0F);
        GL11.glScalef(1.3F, 1.3F, 1.3F);
        EntityAlienSlave slave = (EntityAlienSlave) entity;
        setRotationAngles(f, f1, f2, f3, f4, f5, slave);
        chest.render(f5);
        stomache.render(f5);
        head.render(f5);
        rarm0.render(f5);
        rarm1.render(f5);
        larm0.render(f5);
        larm1.render(f5);
        tent0.render(f5);
        rhand.render(f5);
        lhand.render(f5);
        if(!slave.isCharging) {
            rfinger0.render(f5);
            rfinger1.render(f5);
            lfinger0.render(f5);
            lfinger1.render(f5);
        }
        rear.render(f5);
        lear.render(f5);
        neck.render(f5);
        collar1.render(f5);
        collar2.render(f5);
        collar3.render(f5);
        lcollar1.render(f5);
        collar4.render(f5);
        collar5.render(f5);
        rcollar1.render(f5);
        rleg0.render(f5);
        lleg0.render(f5);
        rleg1.render(f5);
        lleg1.render(f5);
        rleg2.render(f5);
        lleg2.render(f5);
        rleg3.render(f5);
        lleg3.render(f5);
        rleg4.render(f5);
        rleg5.render(f5);
        lleg4.render(f5);
        lleg5.render(f5);
        collar6.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    /**
     * 天杀的MC模型！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
     * @param f
     * @param f1
     * @param f2
     * @param f3
     * @param f4
     * @param f5
     * @param e
     */
    public void setRotationAngles(float f, float f1, float f2, float f3,
            float f4, float f5, EntityAlienSlave e) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
        float rotation_1 = MathHelper.cos(f * 0.5F) * f1, rotation_2 = MathHelper
                .cos(f * 0.5F + (float) Math.PI) * f1;
        float headX = f4 / (180F / (float) Math.PI), headY = f3 / (180F / (float) Math.PI);
        this.head.rotateAngleY = headY;
        this.head.rotateAngleX = headX;
        this.lear.rotateAngleY = headY;
        this.lear.rotateAngleX = headX;
        this.rear.rotateAngleY = headY;
        this.rear.rotateAngleX = headX;

        // 关节那么多写的苦手啊……
        rleg0.rotateAngleX = rotation_1;
        rleg1.rotateAngleX = -0.85521133347722145F + rotation_1;
        rleg2.rotateAngleX = rotation_1;
        rleg3.rotateAngleX = rotation_1;
        rleg4.rotateAngleX = rotation_1;
        rleg5.rotateAngleX = rotation_1;
        
        lleg0.rotateAngleX = rotation_2;
        lleg1.rotateAngleX = -0.85521133347722145F + rotation_2;
        lleg2.rotateAngleX = rotation_2;
        lleg3.rotateAngleX = rotation_2;
        lleg4.rotateAngleX = rotation_2;
        lleg5.rotateAngleX = rotation_2;

        if (e.isCharging) {
            float rotation = 0.31415926535897931F;
            rarm0.rotateAngleY = 0.0F - rotation;
            rarm1.rotateAngleY = 0.0F - rotation;
            rhand.rotateAngleY = -rotation;
            rcollar1.rotateAngleY = 0.0F - rotation;
            rfinger0.rotateAngleY = -0.6632251F - rotation;
            rfinger1.rotateAngleY = -0.5410521F - rotation;
            
            larm0.rotateAngleY = 0.0F + rotation;
            larm1.rotateAngleY = 0.0F + rotation;
            lcollar1.rotateAngleY = 0.0F + rotation;
            lhand.rotateAngleY = 0.0F + rotation;
            lfinger0.rotateAngleY = -0.5235988F + rotation;
            lfinger1.rotateAngleY = 0.F + rotation;
            
            rotation = MathHelper.sin(e.chargeTick * 0.0523598775F);
            rarm0.rotateAngleX = 0.0F - rotation;
            rhand.rotateAngleX = 0.0F - rotation;
            rarm1.rotateAngleX = .5410521F - rotation;
            rcollar1.rotateAngleX = 0.2443461F - rotation;
            rfinger0.rotateAngleX = -0.6632251F - rotation;
            rfinger1.rotateAngleX = -0.6632251F - rotation;
            
            larm0.rotateAngleX = 0.0F - rotation;
            larm1.rotateAngleX = .5410521F - rotation;
            lhand.rotateAngleX = 0.0F - rotation;
            lcollar1.rotateAngleX = 0.2443461F - rotation;
            lfinger0.rotateAngleX = -0.5235988F - rotation;
            lfinger1.rotateAngleX = -0.5235988F - rotation;
            
        } else {
            rarm0.rotateAngleY = 0.0F;
            rarm1.rotateAngleY = 0.0F;
            rhand.rotateAngleY = 0.0F;
            rcollar1.rotateAngleY = 0.0F;
            rfinger0.rotateAngleY = 0.3141593F;
            rfinger1.rotateAngleY = -0.5410521F;
            
            larm0.rotateAngleY = 0.0F;
            larm1.rotateAngleY = 0.0F;
            lhand.rotateAngleY = 0.0F;
            lcollar1.rotateAngleY = 0.0F;
            lfinger0.rotateAngleY = 0.418879F;
            lfinger1.rotateAngleY = 0.F;
            
            rotation_1 *= 0.7F;
            rotation_2 *= 0.7F;
            rarm0.rotateAngleX = rotation_2;
            rhand.rotateAngleX = rotation_2;
            rarm1.rotateAngleX = .5410521F + rotation_2;
            rcollar1.rotateAngleX = 0.2443461F + rotation_2;
            rfinger0.rotateAngleX = -0.6632251F + rotation_2;
            rfinger1.rotateAngleX = -0.5410521F + rotation_2;
            
            larm0.rotateAngleX = rotation_1;
            larm1.rotateAngleX = .5410521F + rotation_1;
            lhand.rotateAngleX = rotation_1;
            lcollar1.rotateAngleX = 0.2443461F + rotation_1;
            lfinger0.rotateAngleX = -0.5235988F + rotation_1;
            lfinger1.rotateAngleX = -0.5235988F + rotation_1;
        }
    }

}

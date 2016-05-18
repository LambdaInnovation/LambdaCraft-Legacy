/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.liutils.api.util;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * A entity that has its position and motion properties. used for all kinds of spacial calculations.
 * @author WeAthFolD
 */
public class Motion3D {

    private static final Random RNG = new Random();
    public double motionX, motionY, motionZ;
    public double posX, posY, posZ;
    public static final double OFFSET_SCALE = 0.5D;

    public Motion3D(double pX, double pY, double pZ, double moX, double moY, double moZ) {
        posX = pX;
        posY = pY;
        posZ = pZ;

        motionX = moX;
        motionY = moY;
        motionZ = moZ;
    }

    public Motion3D(Vec3 posVec, double moX, double moY, double moZ) {
        this(posVec.xCoord, posVec.yCoord, posVec.zCoord, moX, moY, moZ);
    }

    public Motion3D(Motion3D another) {
        this(another.posX, another.posY, another.posZ, another.motionX, another.motionY, another.motionZ);
    }
    
    /**
     * Create an Motion3D based on its position and moving direction.
     */
    public Motion3D(Entity ent) {
        this(ent, 0, false);
    }

    /**
     * @see cn.liutils.api.util.Motion3D(Entity, int, boolean)
     * @param ent entity
     * @param dirFlag false:use moving direction; true:use head-looking direction
     */
    public Motion3D(Entity ent, boolean dirFlag) {
        this(ent, 0, dirFlag);
    }

    /**
     * Create an Motion3D from an entity, selectible offset.
     * @param entity
     * @param offset direction offset
     * @param dirFlag false:use moving direction; true:use head-looking direction
     */
    public Motion3D(Entity entity, int offset, boolean dirFlag) {
        this.posX = entity.posX;
        this.posY = entity.posY + entity.getEyeHeight();
        this.posZ = entity.posZ;

        if (dirFlag) {
            float var3 = 1.0F, var4 = 0.0F;
            
            float rotationYaw = entity.getRotationYawHead() + (RNG.nextFloat() - 0.5F) * offset;
            float rotationPitch = entity.rotationPitch + (RNG.nextFloat() - 0.5F) * offset;
            this.motionX = -MathHelper.sin(rotationYaw / 180.0F
                    * (float) Math.PI)
                    * MathHelper.cos(rotationPitch / 180.0F
                            * (float) Math.PI) * var3;
            this.motionZ = MathHelper.cos(rotationYaw / 180.0F
                    * (float) Math.PI)
                    * MathHelper.cos(rotationPitch / 180.0F
                            * (float) Math.PI) * var3;
            this.motionY = -MathHelper.sin((rotationPitch + var4)
                    / 180.0F * (float) Math.PI)
                    * var3;
            
            
        } else {
            motionX = entity.motionX;
            motionY = entity.motionY;
            motionZ = entity.motionZ;
            setMotionOffset(offset);
        }
        
    }
    
    public void update(Entity entity, boolean dirFlag) {
        this.posX = entity.posX;
        this.posY = entity.posY + entity.getEyeHeight();
        this.posZ = entity.posZ;

        if (dirFlag) {
            float var3 = 1.0F, var4 = 0.0F;
            
            double rotationYaw = entity.rotationYaw;
            double rotationPitch = entity.rotationYaw;
            this.motionX = -MathHelper.sin(entity.rotationYaw / 180.0F
                    * (float) Math.PI)
                    * MathHelper.cos(entity.rotationPitch / 180.0F
                            * (float) Math.PI) * var3;
            this.motionZ = MathHelper.cos(entity.rotationYaw / 180.0F
                    * (float) Math.PI)
                    * MathHelper.cos(entity.rotationPitch / 180.0F
                            * (float) Math.PI) * var3;
            this.motionY = -MathHelper.sin((entity.rotationPitch + var4)
                    / 180.0F * (float) Math.PI)
                    * var3;
            
        } else {
            motionX = entity.motionX;
            motionY = entity.motionY;
            motionZ = entity.motionZ;
        }
    }

    
    /**
     * Add a 3-dimension randomized offset for the motion vec.
     * @param par1
     * @return
     */
    public Motion3D setMotionOffset(double par1) {
        this.motionX += (RNG.nextDouble() - .5) * par1 * OFFSET_SCALE;
        this.motionY += (RNG.nextDouble() - .5) * par1 * OFFSET_SCALE;
        this.motionZ += (RNG.nextDouble() - .5) * par1 * OFFSET_SCALE;
        return this;
    }
    
    /*
     * @param mo
     * @param e
     */
    public void applyToEntity(Entity e) {
        e.setPosition(this.posX, this.posY, this.posZ);
        e.motionX = this.motionX;
        e.motionY = this.motionY;
        e.motionZ = this.motionZ;
    }
    
    /**
     * Move this motion3D towards motion vec direction.
     * @param step
     * @return this
     */
    public Motion3D move(double step) {
        posX += motionX * step;
        posY += motionY * step;
        posZ += motionZ * step;
        return this;
    }
    
    /**
     * Normalize the motion vector.
     * @return this
     */
    public Motion3D normalize() {
        double z = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= z;
        motionY /= z;
        motionZ /= z;
        return this;
    }
    
    public double distanceTo(double x, double y, double z) {
        double a = x - posX, b = y - posY, c = z - posZ;
        return Math.sqrt(a * a + b * b + c * c);
    }

    /**
     * 获取以本身和另外一个MotionXYZ的pos为顶点的碰撞箱。
     * @param another
     * @return
     */
    public final AxisAlignedBB getBoundingBox(Motion3D another) {
        double minX, minY, minZ, maxX, maxY, maxZ;
        if (another.posX > this.posX) {
            minX = this.posX;
            maxX = another.posX;
        } else {
            minX = another.posX;
            maxX = this.posX;
        }
        if (another.posY > this.posY) {
            minY = this.posY;
            maxY = another.posY;
        } else {
            minY = another.posY;
            maxY = this.posY;
        }
        if (another.posZ > this.posZ) {
            minZ = this.posZ;
            maxZ = another.posZ;
        } else {
            minZ = another.posZ;
            maxZ = this.posZ;
        }
        return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    /**
     * get position vector
     * @param world
     * @return
     */
    public Vec3 getPosVec(World world) {
        return Vec3.createVectorHelper(posX, posY, posZ);
    }
    
    public Vec3 getMotionVec(World world) {
        return Vec3.createVectorHelper(motionX, motionY, motionZ);
    }
    
    @Override
    public String toString() {
        return "[ Motion3D POS" + DebugUtils.formatArray(posX, posY, posZ) + "MOTION" + DebugUtils.formatArray(motionX, motionY, motionZ) + " ]";
    }

}

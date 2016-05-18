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
package cn.lambdacraft.deathmatch.entity.fx;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cn.liutils.api.util.Motion3D;

/**
 * @author WeAthFolD
 *
 */
public class EntityCrossbowStill extends Entity {

    /**
     * @param par1World
     */
    public EntityCrossbowStill(World world, Motion3D motion, float yaw, float pitch) {
        super(world);
        motion.applyToEntity(this);
        this.setRotation(yaw, pitch);
        this.onGround = false;
    }
    
    public EntityCrossbowStill(World world) {
        super(world);
        this.onGround = false;
    }

    /* (non-Javadoc)
     * @see net.minecraft.entity.Entity#entityInit()
     */
    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        posX = nbt.getDouble("posX");
        posY = nbt.getDouble("posY");
        posZ = nbt.getDouble("posZ");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setDouble("posX", posX);
        nbt.setDouble("posY", posY);
        nbt.setDouble("posZ", posZ);
    }
    
    @Override
    public void onUpdate() {
        if(++ticksExisted > 12000)
            this.setDead();
    }
    
    @Override
    public void onEntityUpdate()
    {
        
    }


}

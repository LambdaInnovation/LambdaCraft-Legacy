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
package cn.lambdacraft.mob.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
public class EntityFakeTarget extends Entity {

    /**
     * @param par1World
     */
    public EntityFakeTarget(World par1World) {
        super(par1World);
    }

    /* (non-Javadoc)
     * @see net.minecraft.entity.Entity#entityInit()
     */
    @Override
    protected void entityInit() {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void onUpdate() {
        if(worldObj.isRemote)
            setDead();
    }

    /* (non-Javadoc)
     * @see net.minecraft.entity.Entity#readEntityFromNBT(net.minecraft.nbt.NBTTagCompound)
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        setDead();
    }

    /* (non-Javadoc)
     * @see net.minecraft.entity.Entity#writeEntityToNBT(net.minecraft.nbt.NBTTagCompound)
     */
    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        // TODO Auto-generated method stub

    }

}

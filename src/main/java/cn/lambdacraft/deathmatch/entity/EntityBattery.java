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
package cn.lambdacraft.deathmatch.entity;

import java.util.List;

import cn.lambdacraft.core.util.EnergyUtils;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.liutils.api.util.GenericUtils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * 触碰会自动给HEV充能的电池实体。
 * 
 * @author WeAthFolD
 * 
 */
public class EntityBattery extends Entity {

    public static final int EU_PER_BATTERY = 50000;
    private int currentEnergy;
    private boolean spawnItem = true;

    public EntityBattery(World world, EntityPlayer player, double x, double y,
            double z, int energy) {
        super(world);
        this.setPosition(x, y, z);
        this.setSize(0.25f, 0.4f);
        this.currentEnergy = energy;
        if (player.capabilities.isCreativeMode)
            spawnItem = false;
    }

    public EntityBattery(World world) {
        super(world);
        this.setSize(0.25f, 0.4f);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround) {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
        }

        if (++this.ticksExisted < 20 || worldObj.isRemote)
            return;

        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(posX - 0.15,
                posY - 0.3, posZ - 0.15, posX + 0.15, posY + 0.3, posZ + 0.15);
        List<EntityPlayer> list = worldObj
                .getEntitiesWithinAABBExcludingEntity(this, box,
                        GenericUtils.selectorPlayer);
        if (list == null || list.size() != 1)
            return;
        EntityPlayer player = list.get(0);
        currentEnergy -= EnergyUtils.tryChargeArmor(player, currentEnergy, 2,
                true);
        if (spawnItem) {
            ItemStack newBattery = new ItemStack(CBCItems.battery);
            CBCItems.battery.setItemCharge(newBattery, currentEnergy);
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY,
                    posZ, newBattery));
        }
        this.playSound("lambdacraft:entities.battery", 0.5F, 1.0F);
        this.setDead();
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        posX = nbt.getDouble("posX");
        posY = nbt.getDouble("posY");
        posZ = nbt.getDouble("posZ");
        currentEnergy = nbt.getInteger("currentEnergy");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setDouble("posX", posX);
        nbt.setDouble("posY", posY);
        nbt.setDouble("posZ", posZ);
        nbt.setInteger("currentEnergy", currentEnergy);
    }

}

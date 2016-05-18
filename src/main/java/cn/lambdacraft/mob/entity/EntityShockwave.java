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

import java.util.List;

import cn.liutils.api.util.GenericUtils;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

/**
 * 狗眼冲击波。
 * @author WeAthFolD
 */
public class EntityShockwave extends Entity {

    public static final float DAMAGE_SCALE = 15.0F;
    protected boolean attacked = false;
    
    private boolean firstUpdate = false;
    
    protected EntityHoundeye houndeye;
    
    public static IEntitySelector selector = new IEntitySelector() {

        @Override
        public boolean isEntityApplicable(Entity entity) {
            return GenericUtils.selectorLiving.isEntityApplicable(entity) && !(entity instanceof EntityHoundeye);
        }
        
    };
    
    /**
     * @param par1World
     */
    public EntityShockwave(World par1World, EntityHoundeye attacker, double x, double y, double z) {
        super(par1World);
        this.setSize(8.0F, 0.6F);
        houndeye = attacker;
        this.setPosition(x, y, z);
    }
    
    public EntityShockwave(World world) {
        super(world);
        this.setSize(8.0F, 0.6F);
    }
    
    @Override
    public void onUpdate() {
        if(worldObj.isRemote) {
            if(firstUpdate) {
                int blockX = (int) Math.round(posX);
                int blockY = (int) Math.round(posY);
                int blockZ = (int) Math.round(posZ);
                worldObj.setLightValue(EnumSkyBlock.Sky, blockX, blockY, blockZ, 15);
                worldObj.updateLightByType(EnumSkyBlock.Block, blockX, blockY + 1, blockZ);
                worldObj.updateLightByType(EnumSkyBlock.Block, blockX - 1, blockY, blockZ);
                worldObj.updateLightByType(EnumSkyBlock.Block, blockX, blockY, blockZ - 1);
            }
        }
        
        if(++ticksExisted >= 5 && !attacked) {
            this.attemptEntityAttack();
            attacked = true;
            return;
        } else if(ticksExisted > 15) {
            this.setDead();
        }
    }
    
    private void attemptEntityAttack() {
        if(worldObj.isRemote)
            return;
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(posX - 4.0, posY - 2.0, posZ - 4.0, posX + 4.0,  posY + 2.0, posZ + 4.0);
        List<EntityLiving> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, box, selector);
        for(EntityLivingBase e : list) {
            double distanceSq = e.getDistanceSqToEntity(this);
            distanceSq = (33.0 - distanceSq) / 33.0;
            int dmg = (int) Math.round(distanceSq * DAMAGE_SCALE);
            e.attackEntityFrom(DamageSource.causeMobDamage(houndeye), dmg);
            e.addPotionEffect(new PotionEffect(Potion.confusion.id,(int) Math.round(200 * distanceSq),0));
        }
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        this.setDead();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        this.setDead();
    }
    
}

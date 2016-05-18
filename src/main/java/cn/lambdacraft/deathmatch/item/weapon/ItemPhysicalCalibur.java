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
package cn.lambdacraft.deathmatch.item.weapon;

import cn.lambdacraft.core.CBCMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

/**
 * @author WeAthFolD
 *
 */
public class ItemPhysicalCalibur extends Weapon_Crowbar {

    /**
     * @param item_id
     */
    public ItemPhysicalCalibur() {
        super();
        setCreativeTab(CBCMod.cct);
        setUnlocalizedName("physcalibur");
    }
    
    /*
    @Override
    public float getDamageVsEntity(Entity par1Entity, ItemStack itemStack)
    {
        return 18;
    }*/
    
    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase attackedEntity, EntityLivingBase player)
    {
        attackedEntity.hurtResistantTime = -1;
        attackedEntity.attackEntityFrom(DamageSource.causeMobDamage(player), 15);
        
        float f = 1.0F;
        double motionX = -MathHelper.sin(player.rotationYaw / 180.0F
                * (float) Math.PI)
                * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)
                * f;
        double motionZ = MathHelper.cos(player.rotationYaw / 180.0F
                * (float) Math.PI)
                * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)
                * f;
        double motionY = 0.5;
        attackedEntity.addVelocity(motionX, motionY, motionZ);
        EntityLightningBolt bolt = new EntityLightningBolt(attackedEntity.worldObj, attackedEntity.posX, attackedEntity.posY, attackedEntity.posZ);
        attackedEntity.worldObj.spawnEntityInWorld(bolt);
        //attackedEntity.onStruckByLightning(bolt);
        return true;
    }

}

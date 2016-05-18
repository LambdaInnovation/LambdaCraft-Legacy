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

import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cn.lambdacraft.core.item.LCElectItemManager;
import cn.weaponmod.api.feature.IClickHandler;
import cn.weaponmod.api.feature.IModdable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_Crowbar_Electrical extends Weapon_Crowbar implements ISpecialElectricItem, IModdable, IClickHandler {


    public Weapon_Crowbar_Electrical() {
        super();
        this.setUnlocalizedName("weapon_elcrowbar");
        this.setMaxDamage(10000);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:weapon_crowbar");
    }
    
    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return 10000;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 2;
    }
    
    /*    
    @Override
    public float getDamageVsEntity(Entity par1Entity, ItemStack i)
    {
        return 6;
    }
    */
    
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }
    
    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase attackedEntity, EntityLivingBase par3EntityLiving)
    {
        if(getMode(par1ItemStack) == 0 && getManager(par1ItemStack).discharge(par1ItemStack, 400, 2, true, false, false) == 400){
            attackedEntity.hurtResistantTime = -1;
            attackedEntity.attackEntityFrom(DamageSource.causeMobDamage(par3EntityLiving), 5);
            if(attackedEntity instanceof EntityCreeper && itemRand.nextFloat() >= 0.9) {
                EntityLightningBolt bolt = new EntityLightningBolt(attackedEntity.worldObj, attackedEntity.posX, attackedEntity.posY, attackedEntity.posZ);
                if(!attackedEntity.worldObj.isDaytime())
                    attackedEntity.worldObj.spawnEntityInWorld(bolt);
                attackedEntity.onStruckByLightning(bolt);
            }
        }
        return true;
    }
    
    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return 128;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack,
            EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
            par3List.add(StatCollector.translateToLocal("gui.curenergy.name")
                    + " : " + getItemCharge(par1ItemStack) + "/"
                    + 10000 + " EU");
    }

    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,
            List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        ItemStack chargedItem = new ItemStack(par1, 1, 9999);
        par3List.add(chargedItem);
    }
    
    private int getItemCharge(ItemStack itemStack) {
        return itemStack.getMaxDamage() - itemStack.getItemDamage();
    }
    
    private void setItemCharge(ItemStack itemStack, int i) {
        itemStack.setItemDamage(itemStack.getMaxDamage() - i);
    }

    @Override
    public void onModeChange(ItemStack item, EntityPlayer player, int newMode) {
        if(item.stackTagCompound == null)
            item.stackTagCompound = new NBTTagCompound();
        NBTTagCompound nbt = item.stackTagCompound;
        nbt.setInteger("mode", newMode);
    }

    @Override
    public int getMode(ItemStack item) {
        if(item.stackTagCompound == null)
            item.stackTagCompound = new NBTTagCompound();
        NBTTagCompound nbt = item.stackTagCompound;
        return nbt.getInteger("mode");
    }

    @Override
    public int getMaxModes() {
        return 2;
    }

    @Override
    public String getModeDescription(int mode) {
        return "mode.elcrowbar" + mode;
    }

    @Override
    public IElectricItemManager getManager(ItemStack itemStack) {
        return LCElectItemManager.INSTANCE;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public void onItemClick(World world, EntityPlayer player, ItemStack stack,
            int keyid) {
    }

    @Override
    public void onItemRelease(World world, EntityPlayer pl, ItemStack stack,
            int keyid) {
        if(keyid == 1) {
            this.onModeChange(stack, pl, (getMode(stack) + 1) % 2);
        }
    }

    @Override
    public void onItemUsingTick(World world, EntityPlayer player,
            ItemStack stack, int keyid, int ticks) {
        // TODO Auto-generated method stub
        
    }

}

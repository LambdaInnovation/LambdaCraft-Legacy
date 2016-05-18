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
import cn.lambdacraft.core.item.CBCGenericItem;
import cn.lambdacraft.deathmatch.entity.EntityHGrenade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Hand grenade weapon class.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_Hgrenade extends CBCGenericItem {

    public Weapon_Hgrenade() {
        super();
        setUnlocalizedName("weapon_hgrenade");
        setCreativeTab(CBCMod.cct);
        setIconName("weapon_hgrenade");
        setMaxStackSize(8);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
            EntityPlayer par3EntityPlayer, int par4) {

        if (par4 >= 395) {
            return;
        }

        int duration = (par4 > 340) ? 400 - par4 : 60; // used time: if large
                                                        // than 3s use 3s

        if (par1ItemStack.stackSize > 0 && !par2World.isRemote)
            par2World.spawnEntityInWorld(new EntityHGrenade(par2World,
                    par3EntityPlayer, duration));

        if (!par3EntityPlayer.capabilities.isCreativeMode) {
            --par1ItemStack.stackSize;
            if (par1ItemStack.stackSize == 0)
                par1ItemStack = null;
        }

        return;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
            EntityPlayer par3EntityPlayer) {

        par2World.playSoundAtEntity(par3EntityPlayer,
                "lambdacraft:weapons.hgrenadepin", 0.5F,
                0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        par3EntityPlayer.setItemInUse(par1ItemStack,
                this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;

    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 400; // 20s
    }

}

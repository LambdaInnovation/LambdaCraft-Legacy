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
package cn.lambdacraft.deathmatch.item;

import cn.lambdacraft.core.item.ElectricItem;
import cn.lambdacraft.deathmatch.entity.EntityBattery;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author Administrator
 * 
 */
public class ItemBattery extends ElectricItem {

	public ItemBattery(int par1) {
		super(par1);
		setUnlocalizedName("hevbattery");
		this.setIconName("battery");
		this.tier = 2;
		this.transferLimit = 128;
		this.maxCharge = EntityBattery.EU_PER_BATTERY;
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		if (!par3World.isRemote)
			ItemBattery.spawnBatteryAt(par1ItemStack, par3World,
					par2EntityPlayer, par4, par5, par6, par7);
		if (!par2EntityPlayer.capabilities.isCreativeMode) {
			par1ItemStack.splitStack(1);
		}
		return true;
	}

	public static void spawnBatteryAt(ItemStack itemStack, World par0World,
			EntityPlayer player, int par1, int par2, int par3, int side) {
		Entity entity = null;
		double x = par1 + 0.5, y = par2 + 0.5, z = par3 + 0.5;
		if (side == 0) {
			return;
		} else if (side == 1) {
			y += 0.8;
		} else if (side == 2) {
			z -= 0.8;
		} else if (side == 3) {
			z += 0.8;
		} else if (side == 4) {
			x -= 0.8;
		} else if (side == 5) {
			x += 0.8;
		}
		entity = new EntityBattery(par0World, player, x, y, z,
				EntityBattery.EU_PER_BATTERY - itemStack.getItemDamage());
		par0World.spawnEntityInWorld(entity);
	}

	@Override
	public boolean canUse(ItemStack itemStack, int amount) {
		return itemStack.getMaxDamage() - itemStack.getItemDamage() > 0;
	}

	@Override
	public boolean canShowChargeToolTip(ItemStack itemStack) {
		return true;
	}

}

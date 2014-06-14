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
package cn.lambdacraft.crafting.block.container;

import cn.lambdacraft.crafting.block.tile.TileWeaponCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * 合成机中的合成表结果Item槽，能被点击，不能被取出。
 * 
 * @author WeAthFolD
 */
public class SlotOutput extends Slot {

	public SlotOutput(TileWeaponCrafter par1iInventory, int par2, int par3,
			int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer,
			ItemStack par2ItemStack) {
		((TileWeaponCrafter) inventory).attemptItemCrafting(this.slotNumber);
	}

	@Override
	public ItemStack decrStackSize(int par1) {
		return null;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
		return true;
	}

}

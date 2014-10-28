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
package cn.lambdacraft.crafting.block.crafter;

import cn.lambdacraft.crafting.block.SlotLocked;
import cn.lambdacraft.crafting.block.SlotOutput;
import cn.lambdacraft.crafting.block.SlotResult;
import cn.lambdacraft.crafting.block.crafter.BlockWeaponCrafter.CrafterIconType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 武器合成机和高级武器合成机的Container类。
 * 
 * @author WeAthFolD
 */
public class ContainerWeaponCrafter extends ContainerCrafterBase {

	public final TileWeaponCrafter tileEntity;
	
	public ContainerWeaponCrafter(TileWeaponCrafter te) {
		super(te);
		this.tileEntity = te;
	}

	public ContainerWeaponCrafter(InventoryPlayer inventoryPlayer,
			TileWeaponCrafter te) {
		super(inventoryPlayer, te);
		tileEntity = te;

		addSlots(te);
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			icrafting.sendProgressBarUpdate(this, 4, tileEntity.maxBurnTime);
			icrafting.sendProgressBarUpdate(this, 5, tileEntity.burnTimeLeft);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		super.updateProgressBar(par1, par2);
		if(par1 == 4)
			tileEntity.maxBurnTime = par2;
		else if(par1 == 5)
			tileEntity.burnTimeLeft = par2;
	}
}

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
package cn.lambdacraft.deathmatch.block.container;

import cn.lambdacraft.deathmatch.block.TileMedkitFiller;
import cn.lambdacraft.deathmatch.block.TileMedkitFiller.EnumMedFillerBehavior;
import cn.lambdacraft.deathmatch.item.ItemMedkit;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

/**
 * @author WeAthFolD
 * 
 */
public class ContainerMedFiller extends Container {

	protected TileMedkitFiller te;

	/**
	 * 
	 */
	public ContainerMedFiller(TileMedkitFiller t, InventoryPlayer playerinv) {
		te = t;
		/**
		 * Potion slot
		 */
		for (int i = 0; i < 3; i++) {
			addSlotToContainer(new Slot(te, i, 30 + 29 * i, 55));
		}
		addSlotToContainer(new Slot(te, 3, 59, 21));
		addSlotToContainer(new Slot(te, 4, 88, 21));
		addSlotToContainer(new SlotElectricItem(te, 5, 127, 40));
		bindPlayerInventory(playerinv);

	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			icrafting.sendProgressBarUpdate(this, 0, te.currentEnergy / 2);
			for (int j = 0; j < 3; j++) {
				icrafting.sendProgressBarUpdate(this, j + 1, te.progresses[j]);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			te.currentEnergy = par2 * 2;
		} else if (par1 < 4) {
			te.progresses[par1 - 1] = par2;
		} else {
			te.currentBehavior = EnumMedFillerBehavior.values()[par2];
		}
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						8 + j * 21, 103 + i * 22));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 21, 174));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return te.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);
		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			if (stackInSlot == null)
				return null;
			stack = stackInSlot.copy();

			// places it into the tileEntity is possible since its in the player
			// inventory
			if (slot >= 6) {
				/*
				 * if (!this.mergeItemStack(stackInSlot, 0, 6, true)) { return
				 * null; }
				 */
				if (stackInSlot.getItem() instanceof ItemPotion) {
					if (!this.mergeItemStack(stackInSlot, 0, 3, false))
						return null;
				} else if (stackInSlot.getItem() instanceof ItemMedkit) {
					if (!this.mergeItemStack(stackInSlot, 3, 4, false))
						return null;
				} else if (stackInSlot.getItem() == Items.redstone
						|| stackInSlot.getItem() == Items.glowstone_dust) {
					if (!this.mergeItemStack(stackInSlot, 4, 5, false))
						return null;
				} else if (!this.mergeItemStack(stackInSlot, 5, 6, false))
					return null;
			}
			// merges the item into player inventory since its in the tileEntity
			else {
				if (!this.mergeItemStack(stackInSlot, 6, 41, false))
					return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

}

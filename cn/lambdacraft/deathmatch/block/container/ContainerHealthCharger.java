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

import ic2.api.item.IElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cn.lambdacraft.deathmatch.block.TileHealthCharger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 * 
 */
public class ContainerHealthCharger extends Container {

	protected TileHealthCharger te;

	/**
	 * 
	 */
	public ContainerHealthCharger(TileHealthCharger t, InventoryPlayer playerinv) {
		te = t;
		addSlotToContainer(new Slot(t, 0, 19, 60));
		addSlotToContainer(new Slot(t, 1, 41, 60));
		addSlotToContainer(new Slot(t, 2, 149, 60));
		bindPlayerInventory(playerinv);

	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			icrafting.sendProgressBarUpdate(this, 0, te.currentEnergy);
			icrafting.sendProgressBarUpdate(this, 1, te.mainEff);
			icrafting.sendProgressBarUpdate(this, 2, te.sideEff);
			icrafting.sendProgressBarUpdate(this, 3, te.prgAddMain);
			icrafting.sendProgressBarUpdate(this, 4, te.prgAddSide);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0)
			te.currentEnergy = par2;
		else if (par1 == 1)
			te.mainEff = par2;
		else if (par1 == 2)
			te.sideEff = par2;
		else if (par1 == 3)
			te.prgAddMain = par2;
		else
			te.prgAddSide = par2;
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
			stack = stackInSlot.copy();

			// places it into the tileEntity is possible since its in the player
			// inventory
			if (slot >= 3) {
				if (stackInSlot.getItem() instanceof IElectricItem) {
					if (!this.mergeItemStack(stackInSlot, 2, 3, true)) {
						return null;
					}
				} else if (!this.mergeItemStack(stackInSlot, 0, 2, true)) {
					return null;
				}
			}
			// merges the item into player inventory since its in the tileEntity
			else {
				if (!this.mergeItemStack(stackInSlot, 4, 39, false))
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

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

import ic2.api.item.ISpecialElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cn.lambdacraft.crafting.block.tile.TileGeneratorFire;
import cn.lambdacraft.deathmatch.block.container.SlotElectricItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 * 
 */
public class ContainerGenerator extends Container {

	// 0:fuel 1:charge
	TileGeneratorFire te;

	public ContainerGenerator(TileGeneratorFire ent, InventoryPlayer player) {
		te = ent;
		// 燃料槽
		addSlotToContainer(new Slot(ent, 0, 108, 34));
		// 充电槽
		addSlotToContainer(new SlotElectricItem(ent, 1, 133, 34));
		bindPlayerInventory(player);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						6 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 6 + i * 18, 142));
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			icrafting.sendProgressBarUpdate(this, 0, te.currentEnergy);
			icrafting.sendProgressBarUpdate(this, 1, te.tickLeft);
			icrafting.sendProgressBarUpdate(this, 2, te.maxBurnTime);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		super.updateProgressBar(par1, par2);
		if (par1 == 0) {
			te.currentEnergy = par2;
		} else if (par1 == 1) {
			te.tickLeft = par2;
		} else
			te.maxBurnTime = par2;
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

			// 将玩家物品栏中的物品放到TileEntity中
			if (slot >= 2) {
				if (stackInSlot.getItem() instanceof ISpecialElectricItem) {
					if (!this.mergeItemStack(stackInSlot, 1, 2, true)) {
						return null;
					}
				} else if (!this.mergeItemStack(stackInSlot, 0, 1, true)) {
					return null;
				}
			}
			// 将TileEntity中的物品放到玩家物品栏中
			else {
				if (!this.mergeItemStack(stackInSlot, 2, 33, false))
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

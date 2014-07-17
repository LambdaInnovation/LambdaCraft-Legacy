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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.crafting.block.tile;

import cn.lambdacraft.api.energy.item.ICustomEnItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 * @author WeAthFolD, Rikka
 * 
 */
public class TileGeneratorFire extends TileGeneratorBase implements IInventory {

	public ItemStack[] slots = new ItemStack[2];
	public boolean isBurning = false;
	public int tickLeft = 0, maxBurnTime = 0;

	/**
	 * @param tier
	 * @param store
	 */
	public TileGeneratorFire() {
		super(1, 5000);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;

		if (isBurning) {
			tickLeft--;
			currentEnergy += this.sendEnergy(5);
			if (currentEnergy > maxStorage)
				currentEnergy = maxStorage;
			if (tickLeft <= 0)
				isBurning = false;
		} else {
			if (currentEnergy < maxStorage)
				tryBurn();
		}

		if (currentEnergy > 0) {
			int all = currentEnergy > 5 ? 5 : currentEnergy;
			int rev = sendEnergy(all);
			currentEnergy -= (all - rev);
			if (slots[1] != null) {
				currentEnergy -= ((ICustomEnItem) slots[1].getItem()).charge(
						slots[1], currentEnergy, 2, false, false);
			}
		}

	}

	private void tryBurn() {
		if (slots[0] != null) {
			this.tickLeft = TileEntityFurnace.getItemBurnTime(slots[0]) / 4;
			this.maxBurnTime = this.tickLeft;
			if (maxBurnTime == 0)
				return;
			if (slots[0].getItem() == Items.lava_bucket) {
				slots[0] = new ItemStack(Items.bucket);
			} else {
				if (--slots[0].stackSize <= 1)
					slots[0] = null;
			}
			isBurning = true;
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		for (int i = 0; i < slots.length; i++) {
			short id = nbt.getShort("id" + i), damage = nbt.getShort("damage"
					+ i);
			byte count = nbt.getByte("count" + i);
			if (id == 0)
				continue;
			ItemStack is = new ItemStack(Item.getItemById(id), count, damage);
			slots[i] = is;
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] == null)
				continue;
			nbt.setShort("id" + i, (short) Item.getIdFromItem(slots[i].getItem()));
			nbt.setByte("count" + i, (byte) slots[i].stackSize);
			nbt.setShort("damage" + i, (short) slots[i].getItemDamage());
		}
	}

	@Override
	public int getMaxEnergyOutput() {
		return 5;
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return slots[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		slots[i] = itemstack;
	}
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public String getInventoryName() {
		return "cbc.tile.genfire";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {
	}
}

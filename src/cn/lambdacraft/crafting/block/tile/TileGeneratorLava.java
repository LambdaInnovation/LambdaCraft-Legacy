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
import cn.lambdacraft.api.energy.item.IEnItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author WeAthFolD, Rikka
 * 
 */
public class TileGeneratorLava extends TileGeneratorBase implements IInventory {

	public static final int ENERGY_PER_BUCKET = 20000;
	public ItemStack[] slots = new ItemStack[2];
	public int bucketCnt = 0;

	/**
	 * @param tier
	 * @param store
	 */
	public TileGeneratorLava() {
		super(1, 20);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;
		tryBurn();
		if (currentEnergy > 0) {
			if (this.slots[1] != null
					&& slots[1].getItem() instanceof ICustomEnItem) {
				currentEnergy -= ((ICustomEnItem) slots[1].getItem()).charge(
						slots[1], currentEnergy, 1, false, false);
			}
			int toConsume = 10 - sendEnergy(currentEnergy > 10 ? 10
					: currentEnergy);
			currentEnergy -= toConsume;
		} else {
			if (bucketCnt-- > 0)
				currentEnergy += ENERGY_PER_BUCKET;
			else {
				bucketCnt = 0;
				currentEnergy = 0;
			}
		}
	}

	private void tryBurn() {
		int energyReq = maxStorage - bucketCnt;

		if (energyReq >= 1 && slots[0] != null) {
			if (slots[0].itemID == Item.bucketLava.itemID) {
				this.setInventorySlotContents(0, new ItemStack(
						Item.bucketEmpty, 1, 0));
				bucketCnt += 1;
			}
		}
	}

	@Override
	public int getMaxEnergyOutput() {
		return 10;
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
			ItemStack is = new ItemStack(id, count, damage);
			slots[i] = is;
		}
		bucketCnt = nbt.getShort("bucket");
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
			nbt.setShort("id" + i, (short) slots[i].itemID);
			nbt.setByte("count" + i, (byte) slots[i].stackSize);
			nbt.setShort("damage" + i, (short) slots[i].getItemDamage());
		}
		nbt.setShort("bucket", (short) bucketCnt);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return slots[i];
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		slots[i] = itemstack;
	}

	@Override
	public String getInvName() {
		return "cbc.tile.genlava";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i == 0)
			return true;
		else
			return (itemstack.getItem() instanceof IEnItem);
	}

}

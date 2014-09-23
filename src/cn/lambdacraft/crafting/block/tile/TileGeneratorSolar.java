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
package cn.lambdacraft.crafting.block.tile;

import ic2.api.item.ISpecialElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author WeAthFolD
 * 
 */
public class TileGeneratorSolar extends TileGeneratorBase implements IInventory {

	public int currentEnergy = 0;
	public boolean isEmitting;
	public ItemStack[] slots = new ItemStack[1];

	/**
	 * @param tier
	 * @param store
	 */
	public TileGeneratorSolar() {
		super(1, 10000);
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
	public int getInventoryStackLimit() {
		return 2;
	}

	@Override
	public String getInventoryName() {
		return "cbc.tile.genfire";
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
	public ItemStack getStackInSlotOnClosing(int i) {
		return slots[i];
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i == 0)
			return true;
		else
			return (itemstack.getItem() instanceof ISpecialElectricItem);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		slots = this.restoreInventory(nbt, "inv", slots.length);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		slots[i] = itemstack;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;

		if (this.worldObj.isDaytime()
				&& worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
			currentEnergy += 4;
			int amt = currentEnergy > 16 ? 16 : currentEnergy;
			amt -= this.sendEnergy(amt);
			currentEnergy -= amt;
			if (currentEnergy > maxStorage)
				currentEnergy = maxStorage;
			isEmitting = true;
		}
		isEmitting = false;

		if (currentEnergy > 0 && this.slots[0] != null && slots[0].getItem() instanceof ISpecialElectricItem) {
			currentEnergy -= ((ISpecialElectricItem) slots[0].getItem()).getManager(slots[0]).charge(slots[0], currentEnergy, 1, false, false);
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.storeInventory(nbt, slots, "inv");
	}

	@Override
	public double getOfferedEnergy() {
		return Math.min(currentEnergy, 32);
	}

	@Override
	public void drawEnergy(double amount) {
		currentEnergy -= amount;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}
	
	

}

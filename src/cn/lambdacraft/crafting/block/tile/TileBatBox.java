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

import ic2.api.energy.tile.IEnergySink;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.core.util.EnergyUtils;
import cn.lambdacraft.crafting.register.CBCBlocks;

/**
 * @author WeAthFolD, Rikka
 * 
 */
public class TileBatBox extends TileGeneratorBase implements IInventory, IEnergySink {

	public ItemStack[] slots = new ItemStack[2];
	public final int type;

	public static class TileBoxSmall extends TileBatBox {
		public TileBoxSmall() {
			super(0);
		}
	}

	public static class TileBoxLarge extends TileBatBox {
		public TileBoxLarge() {
			super(1);
		}
	}

	public TileBatBox(int t) {
		super(t == 0 ? 1 : 2, t == 0 ? 40000 : 200000);
		type = t;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;

		// emit the energy frequently
		int amt = Math.min(32, currentEnergy);
		amt -= this.sendEnergy(amt);
		currentEnergy -= amt;

		// charge from slot0
		if (currentEnergy < maxStorage) {
			int energyReq = maxStorage - currentEnergy;
			ItemStack sl = slots[0];
			if (sl != null) {
				currentEnergy += EnergyUtils.tryChargeFromStack(sl, energyReq);
				if (sl.stackSize <= 0)
					this.setInventorySlotContents(0, null);
			}
		}

		// charge the chargeable in slot1
		if (currentEnergy > 0) {
			ItemStack sl = slots[1];
			if (sl != null && sl.getItem() instanceof ISpecialElectricItem) {
				ISpecialElectricItem item = (ISpecialElectricItem) sl.getItem();
				currentEnergy -= item.getManager(sl).charge(sl, currentEnergy, type, false,
						false);
			}
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
	public double injectEnergyUnits(ForgeDirection paramDirection, double amount) {
		this.currentEnergy += amount;
		if (currentEnergy > maxStorage) 
			currentEnergy = maxStorage;
		return 0;
	}

	@Override
	public double demandedEnergyUnits() {
		return getMaxEnergy() - getCurrentEnergy();
	}

	/**
	 * @return the currentEnergy
	 */
	public int getCurrentEnergy() {
		return currentEnergy;
	}

	/**
	 * @return the maxEnergy
	 */
	public int getMaxEnergy() {
		return maxStorage;
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
	public ItemStack getStackInSlotOnClosing(int i) {
		return slots[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		slots[i] = itemstack;
	}

	@Override
	public String getInventoryName() {
		return (this.type == 0 ? CBCBlocks.storageS.getUnlocalizedName()
				: CBCBlocks.storageL.getUnlocalizedName()) + ".name";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean emitsEnergyTo(TileEntity emTileEntity, ForgeDirection emDirection) {
		return emDirection.ordinal() == this.blockMetadata;
	}

	@Override
	public int getMaxSafeInput() {
		return type == 0 ? 32 : 128;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity paramTileEntity,
			ForgeDirection paramDirection) {
		return currentEnergy < maxStorage
				&& paramDirection.ordinal() != this.blockMetadata;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public double getOfferedEnergy() {
		return Math.min(currentEnergy, type == 1 ? 32 : 128);
	}

	@Override
	public void drawEnergy(double amount) {
		if(amount > 0) currentEnergy -= amount;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}
}

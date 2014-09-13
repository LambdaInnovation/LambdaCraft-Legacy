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
package cn.lambdacraft.deathmatch.block;

import ic2.api.item.ISpecialElectricItem;

import java.util.HashSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.core.block.TileElectricStorage;
import cn.lambdacraft.core.util.EnergyUtils;

/**
 * @author WeAthFolD, Rikka
 */
public class TileArmorCharger extends TileElectricStorage implements IInventory {

	public static int ENERGY_MAX = 400000; // 10 BatBox, 4HEV Armor
	public boolean isCharging = false;
	public boolean isUsing = false;
	public HashSet<EntityPlayer> chargers = new HashSet();

	/**
	 * slot 0-3: HEV Armor slot, accept ICustomEnItem(LC) or
	 * ICustomElectricItem(IC2) only. slot 4-6: Battery slot.
	 */
	public ItemStack slots[] = new ItemStack[7];
	public EnumBehavior currentBehavior = EnumBehavior.NONE;
	public boolean isRSActivated;

	public enum EnumBehavior {
		NONE, CHARGEONLY, RECEIVEONLY, DISCHARGE, EMIT;

		@Override
		public String toString() {
			switch (this) {
			case NONE:
				return "rs.donothing.name";
			case CHARGEONLY:
				return "rs.chargeonly.name";
			case RECEIVEONLY:
				return "rs.reciveonly.name";
			case EMIT:
				return "rs.emit.name";
			case DISCHARGE:
				return "rs.discharge.name";
			default:
				return "rs.donothing.name";
			}
		}

	}

	public void nextBehavior() {
		int cur = currentBehavior.ordinal();
		currentBehavior = EnumBehavior.values()[cur == EnumBehavior.values().length - 1 ? 0
				: cur + 1];
	}

	public TileArmorCharger() {
		super(2, ENERGY_MAX);
	}

	public void startUsing(EntityPlayer player) {
		chargers.add(player);
		isUsing = true;
	}

	public void stopUsing(EntityPlayer player) {
		chargers.remove(player);
		if (chargers.size() == 0)
			isUsing = false;
	}

	@Override
	public void updateEntity() {
		if(!this.addedToNet)
			this.isRSActivated = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		super.updateEntity();
		if (worldObj.isRemote)
			return;

		int energyReq = ENERGY_MAX - currentEnergy;
		// discharge
		if (this.isRSActivated && currentBehavior == EnumBehavior.DISCHARGE) {
			for (int i = 0; i < 4; i++) {
				ItemStack arm = slots[i];
				if (arm == null)
					continue;
				ISpecialElectricItem item = (ISpecialElectricItem) arm.getItem();
				int e = item.getManager(arm).discharge(arm, ENERGY_MAX - currentEnergy, 2,
						false, false);
				currentEnergy += e;
			}
		} else // Charge the energy into armor
		if (currentEnergy > 0
				&& !(!this.isRSActivated && currentBehavior == EnumBehavior.CHARGEONLY)) {
			boolean flag = false;
			for (int i = 0; i < 4; i++) {
				ItemStack arm = slots[i];
				if (arm == null)
					continue;
				ISpecialElectricItem item = (ISpecialElectricItem) arm.getItem();
				int e = item.getManager(arm).charge(arm, currentEnergy > 128 ? 128
						: currentEnergy, 2, false, worldObj.isRemote);
				currentEnergy -= e;
				flag = flag || e > 0;
			}
			isCharging = flag;
		} else
			isCharging = false;

		if (currentEnergy < 0)
			currentEnergy = 0;

		if (this.isUsing) {
			for (EntityPlayer charger : chargers) {
				int received = EnergyUtils.tryChargeArmor(charger,
						this.currentEnergy, 2, false);
				currentEnergy -= received;
				if (received <= 0) {
					worldObj.playSoundAtEntity(charger,
							"lambdacraft:entities.suitchargeno", 0.5F, 1.0F);
					this.stopUsing(charger);
				}
				if (worldObj.getWorldTime() % 40 == 0) {
					worldObj.playSoundAtEntity(charger,
							"lambdacraft:entities.suitcharge", 0.3F, 1.0F);
				}
				if (currentEnergy <= 0) {
					this.chargers.clear();
					this.isUsing = false;
					worldObj.playSoundAtEntity(charger,
							"lambdacraft:entities.suitchargeno", 0.5F, 1.0F);
				}
			}

		}

		/**
		 * 
		 * Charge the energy into tileentity
		 */
		if (currentEnergy < ENERGY_MAX
				&& !(!this.isRSActivated && currentBehavior == EnumBehavior.RECEIVEONLY)) {
			for (int i = 4; i < 7; i++) {
				ItemStack sl = slots[i];
				if (sl == null)
					continue;
				if (sl.getItem() == Items.redstone) {
					if (energyReq > 500) {
						this.decrStackSize(i, 1);
					}
					currentEnergy += 500;
				} else if (sl.getItem() instanceof ISpecialElectricItem) {
					ISpecialElectricItem item = (ISpecialElectricItem) sl.getItem();
					if (!item.canProvideEnergy(sl))
						continue;
					int cn = energyReq < 128 ? energyReq : 128;
					cn = item.getManager(sl).discharge(sl, cn, 2, false, false);
					currentEnergy += cn;
				}
			}
		}

	}

	@Override
	public int getSizeInventory() {
		return 8;
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
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.slots[i] = itemstack;
	}

	@Override
	public String getInventoryName() {
		return "armorcharger";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i <= 3 && !(itemstack.getItem() instanceof ISpecialElectricItem))
			return false;
		return true;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		slots = restoreInventory(nbt, "inv", slots.length);
		currentEnergy = nbt.getInteger("energy");

	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		storeInventory(nbt, slots, "inv");
		nbt.setInteger("energy", currentEnergy);
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity paramTileEntity,
			ForgeDirection paramDirection) {
		return !(currentBehavior == EnumBehavior.RECEIVEONLY && !this.isRSActivated);
	}

	@Override
	public int getMaxSafeInput() {
		return 128;
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

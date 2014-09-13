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

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.core.block.TileElectricStorage;

/**
 * 
 * @author WeAthFolD, Rikka
 * 
 */
public class TileHealthCharger extends TileElectricStorage implements IInventory {

	public static final int ENERGY_MAX = 30000, EFFECT_MAX = 14400,
			PROGRESS_TIME = 100, HEALTH_MAX = 100; // 1.25 batbox, 4-6 potions
	public boolean isUsing = false;
	public HashSet<EntityPlayer> chargers = new HashSet();
	public int mainEff = 0, sideEff = 0;
	public int prgAddMain = 0, prgAddSide = 0;
	private int sideEffectId = 0;

	public static Set<Integer> availableIds = new HashSet();
	static {
		availableIds.add(Potion.digSpeed.id);
		availableIds.add(Potion.damageBoost.id);
		availableIds.add(Potion.fireResistance.id);
		availableIds.add(Potion.invisibility.id);
		availableIds.add(Potion.jump.id);
		availableIds.add(Potion.moveSpeed.id);
		availableIds.add(Potion.nightVision.id);
		availableIds.add(Potion.resistance.id);
		availableIds.add(Potion.waterBreathing.id);
	}

	/**
	 * Slot 0:涓昏嵂姘存Ы銆� Slot 1:鍓嵂姘存Ы銆� Slot 2:鏀剧數姹犳Ы銆�
	 */
	public ItemStack slots[] = new ItemStack[3];
	public int currentBehavior;
	public boolean isRSActivated;

	public void startUsing(EntityPlayer player) {
		chargers.add(player);
		isUsing = true;
	}

	public void stopUsing(EntityPlayer player) {
		chargers.remove(player);
		if (chargers.size() == 0)
			isUsing = false;
	}

	public enum EnumBehavior {
		NONE, RECEIVEONLY, EMIT;

		@Override
		public String toString() {
			switch (this) {
			case NONE:
				return "rs.donothing.name";
			case RECEIVEONLY:
				return "rs.reciveonly.name";
			case EMIT:
				return "rs.emit.name";
			default:
				return "rs.donothing.name";
			}
		}
	}

	public void nextBehavior() {
		currentBehavior = currentBehavior == 4 ? 0 : currentBehavior + 1;
	}

	public TileHealthCharger() {
		super(2, ENERGY_MAX);
		try {
			if(fldDuration == null) {
				this.fldDuration = PotionEffect.class.getDeclaredField("duration");
				fldDuration.setAccessible(true);
			}
		} catch(Exception e) {
			//NOPE
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;
		int energyReq = ENERGY_MAX - currentEnergy;

		if (currentEnergy < 0)
			currentEnergy = 0;

		/**
		 * 
		 * Charge the energy into tileentity
		 */
		if (currentEnergy < ENERGY_MAX
				&& !(!this.isRSActivated && this.getCurrentBehavior() == EnumBehavior.RECEIVEONLY)) {
			ItemStack sl = slots[2];
			if (sl != null && sl.getItem() == Items.redstone) {
				if (energyReq > 500) {
					this.decrStackSize(2, 1);
				}
				currentEnergy += 500;
			} else if (sl != null && sl.getItem() instanceof ISpecialElectricItem) {
				ISpecialElectricItem item = (ISpecialElectricItem) sl.getItem();
				if (item.canProvideEnergy(sl)) {
					int cn = energyReq < 128 ? energyReq : 128;
					cn = item.getManager(sl).discharge(sl, cn, 2, false, false);
					currentEnergy += cn;
				}
			}
		}

		// 澶勭悊F閿娇鐢ㄨ涓�
		if (this.isUsing) {

			for (EntityPlayer charger : chargers) {
				currentEnergy -= 5;// 5EU/T per player
				this.doHealing(charger);
				if (worldObj.getWorldTime() % 15 == 0) {
					worldObj.playSoundAtEntity(charger,
							"lambdacraft:entities.medcharge", 0.3F, 1.0F);
				}
				if (currentEnergy <= 0) {
					this.chargers.clear();
					this.isUsing = false;
					worldObj.playSoundAtEntity(charger,
							"lambdacraft:entities.medshotno", 0.5F, 1.0F);
				}
			}

		}

		if (slots[0] != null && slots[0].getItem() == Items.potionitem) {
			int dmg = slots[0].getItemDamage();
			List<PotionEffect> list = Items.potionitem.getEffects(dmg);
			PotionEffect effect = list.get(0);
			if (mainEff < HEALTH_MAX) {
				if (effect.getPotionID() == Potion.heal.getId()) {

					if (currentEnergy > 5) {
						currentEnergy -= 5;
						prgAddMain++;
					} else
						currentEnergy = 0;
					if (prgAddMain >= PROGRESS_TIME) {
						this.setInventorySlotContents(0, null);
						mainEff += (effect.getAmplifier() == 0 ? 12 : 18);
						prgAddMain = 0;
					}

				} else if (effect.getPotionID() == Potion.regeneration.getId()) {

					if (currentEnergy > 5) {
						currentEnergy -= 5;
						prgAddMain++;
					} else
						currentEnergy = 0;
					if (prgAddMain >= PROGRESS_TIME) {
						this.setInventorySlotContents(0, null);
						if (dmg == 8193)
							mainEff += 36;
						else if (dmg == 8257)
							mainEff += 96;
						else if (dmg == 8225)
							mainEff += 38;
						prgAddMain = 0;
					}

				} else
					prgAddMain = 0;
			}
			if (mainEff > TileHealthCharger.HEALTH_MAX)
				mainEff = HEALTH_MAX;
		} else prgAddMain = 0;

		if (slots[1] != null && slots[1].getItem() == Items.potionitem) {

			int dmg = slots[1].getItemDamage();
			List<PotionEffect> list = Items.potionitem.getEffects(dmg);
			PotionEffect effect = list.get(0);
			if (TileHealthCharger.availableIds.contains(effect.getPotionID())
					&& sideEff < EFFECT_MAX) {
				if (this.sideEffectId == 0
						|| effect.getPotionID() == sideEffectId
						|| this.sideEff == 0) {
					this.sideEffectId = effect.getPotionID();
					if (currentEnergy > 5) {
						currentEnergy -= 5;
						prgAddSide++;
					}
					if (prgAddSide >= PROGRESS_TIME) {
						this.setInventorySlotContents(1, null);
						sideEff += effect.getDuration();
						prgAddSide = 0;
					}
				} else
					prgAddSide = 0;
			}
			if (sideEff > TileHealthCharger.EFFECT_MAX)
				sideEff = EFFECT_MAX;

		} else prgAddSide = 0;
	}

	public static Field fldDuration;
	
	public void doHealing(EntityPlayer charger) {
		if (mainEff > 0 && charger.getHealth() < 20) {
			if (worldObj.getWorldTime() % 10 == 0) {
				charger.heal(1);
				this.mainEff -= 1;
			}
		}
		if (sideEff > 0 && sideEffectId != 0) {
			int amt = sideEff > 10 ? 10 : sideEff;
			this.sideEff -= amt;
			PotionEffect eff = charger
					.getActivePotionEffect(Potion.potionTypes[sideEffectId]);
			if (eff != null) {
				try {
					fldDuration.set(eff, (Integer)fldDuration.get(eff) + amt);
				} catch(Exception e) {
					e.printStackTrace();
				}
				charger.addPotionEffect(eff);
			} else
				charger.addPotionEffect(new PotionEffect(sideEffectId, amt, 0));
		}
	}

	@Override
	public int getSizeInventory() {
		return 3;
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
		slots = this.restoreInventory(nbt, "inv", slots.length);
		currentEnergy = nbt.getInteger("energy");
		mainEff = nbt.getShort("mainEff");
		sideEff = nbt.getShort("sideEff");
		prgAddMain = nbt.getShort("prgAddMain");
		prgAddSide = nbt.getShort("prgAddSide");
		sideEffectId = nbt.getByte("sideEfeectId");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.storeInventory(nbt, slots, "inv");
		nbt.setInteger("energy", currentEnergy);
		nbt.setShort("mainEff", (short) mainEff);
		nbt.setShort("sideEff", (short) sideEff);
		nbt.setShort("prgAddMain", (short) prgAddMain);
		nbt.setShort("prgAddSide", (short) prgAddSide);
		nbt.setByte("sideEffectId", (byte) sideEffectId);
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity paramTileEntity,
			ForgeDirection paramDirection) {
		return !(getCurrentBehavior() == EnumBehavior.RECEIVEONLY && !this.isRSActivated);
	}

	public EnumBehavior getCurrentBehavior() {
		return getBehavior(currentBehavior);
	}

	private EnumBehavior getBehavior(int i) {
		switch (i) {
		case 0:
			return EnumBehavior.NONE;
		case 1:
			return EnumBehavior.RECEIVEONLY;
		case 2:
			return EnumBehavior.EMIT;
		}
		return EnumBehavior.NONE;
	}

	@Override
	public int getMaxSafeInput() {
		return 32;
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
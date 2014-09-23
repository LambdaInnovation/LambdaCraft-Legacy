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

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.tile.IEnergySink;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.api.energy.events.EnergyTileSourceEvent;
import cn.lambdacraft.core.util.EnergyUtils;
import cn.lambdacraft.crafting.block.BlockWeaponCrafter.CrafterIconType;
import cn.lambdacraft.crafting.item.ItemMaterial;
import cn.lambdacraft.crafting.recipe.RecipeCrafter;
import cn.lambdacraft.crafting.recipe.RecipeWeapons;

/**
 * @author WeAthFolD, Rikka
 * 
 */
public class TileElCrafter extends TileWeaponCrafter implements IEnergySink {

	/**
	 * 最大存储
	 */
	public static int MAX_STORAGE = 80000;

	public int currentEnergy;
	public boolean isLoad = false;

	public TileElCrafter() {
		super();
		this.maxHeat = 10000;
	}

	@Override
	public void updateEntity() {
		if (!isLoad) {
			this.onECNetLoad();
			isLoad = true;
			this.writeRecipeInfoToSlot();
		}

		if(worldObj.isRemote) {
			if(isBuffering) {
				if(heatForRendering < heat) {
					heatForRendering += BUFFER_SPEED;
					if(heatForRendering >= heat) {
						heatForRendering = heat;
						isBuffering = false;
					}
				} else {
					heatForRendering -= BUFFER_SPEED;
					if(heatForRendering <= heat) {
						heatForRendering = heat;
						isBuffering = false;
					}
				}
			} else if(Math.abs(heat - heatForRendering) > 300) {
				isBuffering = true;
			} else heatForRendering = heat;
			return;
		}

		if (heat > 0)
			heat--;

		if (iconType == CrafterIconType.NOMATERIAL
				&& worldObj.getWorldTime() - lastTime > 20) {
			iconType = isCrafting ? CrafterIconType.CRAFTING
					: CrafterIconType.NONE;
		}

		if (isCrafting && currentRecipe != null) {
			if (currentRecipe.heatRequired <= this.heat
					&& hasEnoughMaterial(currentRecipe)) {
				craftItem();
			}
			if (currentEnergy >= 7) {
				currentEnergy -= 14;
				heat += 6;
			}
		}

		int energyReq = MAX_STORAGE - currentEnergy;
		if (inventory[1] != null && energyReq > 0) {
			currentEnergy += EnergyUtils.tryChargeFromStack(inventory[1],
					energyReq);
			if (inventory[1].stackSize <= 0)
				inventory[1] = null;
		}

		//if (++this.tickUpdate > 3)
		//	this.onInventoryChanged();
	}
	
	protected void onECNetLoad() {
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
	}

	@Override
	protected void writeRecipeInfoToSlot() {
		clearRecipeInfo();
		int length;
		length = RecipeWeapons.getECRecipeLength(this.page);

		for (int i = 0; i < length && i < 3; i++) {
			RecipeCrafter r = RecipeWeapons.getECRecipe(this.page, i
					+ scrollFactor);
			if (r == null)
				return;
			for (int j = 0; j < 3; j++) {
				if (r.input.length > j)
					this.setInventorySlotContents(j + i * 3, r.input[j]);
			}
			this.setInventorySlotContents(9 + i, r.output);
		}
	}

	@Override
	public String getInventoryName() {
		return "lambdacraft:elcrafter";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i > 12 && itemstack.getItem() instanceof ItemMaterial)
			return true;
		return true;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.currentEnergy = nbt.getInteger("energy");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("energy", currentEnergy);
	}

	@Override
	public void addScrollFactor(boolean isForward) {
		if (!RecipeWeapons.doesECNeedScrollBar(page))
			return;
		List<RecipeCrafter> recipes[] = RecipeWeapons.recipeEC;
		if (isForward) {
			if (scrollFactor < recipes[page].size() - 3) {
				scrollFactor++;
			}
		} else {
			if (scrollFactor > 0) {
				scrollFactor--;
			}
		}
		this.writeRecipeInfoToSlot();
	}

	@Override
	public void addPage(boolean isForward) {
		List<RecipeCrafter> recipes[] = RecipeWeapons.recipeEC;
		if (isForward) {
			if (page < recipes.length - 1) {
				page++;
			}
		} else {
			if (page > 0) {
				page--;
			}
		}
		scrollFactor = 0;
		this.writeRecipeInfoToSlot();
	}

	@Override
	public RecipeCrafter getRecipeBySlotAndScroll(int slot, int factor) {
		int i = 0;
		if (slot == 0)
			i = 0;
		if (slot == 4)
			i = 1;
		if (slot == 8)
			i = 2;
		return RecipeWeapons.getECRecipe(page, factor + i);
	}

	public int sendEnergy(int amm) {
		EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, amm);
		MinecraftForge.EVENT_BUS.post(event);
		return event.amount;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity paramTileEntity,
			ForgeDirection paramDirection) {
		return true;
	}

	@Override
	public double demandedEnergyUnits() {
		return MAX_STORAGE - currentEnergy;
	}

	@Override
	public double injectEnergyUnits(ForgeDirection paramDirection, double amount) {
		this.currentEnergy += amount;
		if (currentEnergy > MAX_STORAGE) {
			int amt = currentEnergy - MAX_STORAGE;
			currentEnergy = MAX_STORAGE;
			return amt;
		}
		return 0;
	}

	@Override
	public int getMaxSafeInput() {
		return 128;
	}

}

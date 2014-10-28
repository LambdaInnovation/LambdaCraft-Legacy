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
package cn.lambdacraft.crafting.block.crafter;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.core.util.EnergyUtils;
import cn.lambdacraft.crafting.item.ItemMaterial;
import cn.lambdacraft.crafting.recipe.RecipeWeapons;
import cn.liutils.api.energy.event.EnergyTileSourceEvent;

/**
 * @author WeAthFolD, Rikka
 * 
 */
public class TileElCrafter extends TileCrafterBase implements IEnergySink {

	/**
	 * 最大存储
	 */
	public static int MAX_STORAGE = 80000;
	public int currentEnergy;
	public boolean isNetLoad = false;

	public TileElCrafter() {
		super();
		this.maxHeat = 10000;
	}

	@Override
	public void updateEntity() {
		if (!isNetLoad) {
			this.onECNetLoad();
			isNetLoad = true;
		}
		//System.out.println("HR:" + heatRequired);
		super.updateEntity();
		
		//System.out.println(worldObj.isRemote + " " + heat + " " + heatRequired);
		if(isCrafting && this.heat < this.heatRequired) {
			double enereq = 14;
			enereq = Math.min(enereq, currentEnergy);
			currentEnergy -= enereq;
			//System.out.println("Heating " + worldObj.isRemote + " " + currentRecipe);
			this.heat += (int) (enereq * 3 / 7.0);
			//System.out.println("CM " + enereq + "HEAT " + heat);
		}

		int energyReq = MAX_STORAGE - currentEnergy;
		if (inventory[1] != null && energyReq > 0) {
			currentEnergy += EnergyUtils.tryChargeFromStack(inventory[1],
					energyReq);
			if (inventory[1].stackSize <= 0)
				inventory[1] = null;
		}
	}
	
	protected void onECNetLoad() {
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
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

	@Override
	protected boolean onCrafterLoad() {
		this.recipes = RecipeWeapons.getMachineRecipes(2);
		this.writeRecipeInfoToSlot();
		return true;
	}

}

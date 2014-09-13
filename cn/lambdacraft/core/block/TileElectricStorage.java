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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开原协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.core.block;

import ic2.api.energy.tile.IEnergySink;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author WeAthFolD, Rikka
 */
public abstract class TileElectricStorage extends TileElectrical implements IEnergySink {

	public int maxEnergy;
	public int currentEnergy;
	protected int tier;
	protected int lastTick;

	/**
	 * 
	 */
	public TileElectricStorage(int tier, int max) {
		this.setMaxEnergy(max);
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
		nbt.setInteger("energy", getCurrentEnergy());
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
		return maxEnergy;
	}

	/**
	 * @param maxEnergy
	 *            the maxEnergy to set
	 */
	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	@Override
	public double injectEnergyUnits(ForgeDirection paramDirection, double amt) {
		this.currentEnergy += amt;
		if (currentEnergy > maxEnergy) {
			currentEnergy = maxEnergy;
			return currentEnergy - maxEnergy;
		}
		return 0;
	}
}

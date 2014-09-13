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

import ic2.api.energy.tile.IEnergySource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.core.block.TileElectrical;

/**
 * @author WeAthFolD, Rikka
 * 
 */
public abstract class TileGeneratorBase extends TileElectrical implements IEnergySource {

	public final int maxStorage, tier;
	public int currentEnergy;

	/**
	 * The generator production in this tick
	 */
	public int production;

	public TileGeneratorBase(int tier, int store) {
		this.maxStorage = store;
		this.tier = tier;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

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
	public boolean emitsEnergyTo(TileEntity paramTileEntity, ForgeDirection paramDirection) {
		return true;
	}

}

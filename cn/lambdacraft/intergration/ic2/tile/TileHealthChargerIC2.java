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
package cn.lambdacraft.intergration.ic2.tile;

import cn.lambdacraft.deathmatch.block.TileHealthCharger;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author WeAthFolD
 *
 */
public class TileHealthChargerIC2 extends TileHealthCharger implements ic2.api.energy.tile.IEnergySink {

	/**
	 * 
	 */
	public TileHealthChargerIC2() {
		super();
	}
	
	@Override
	public boolean onElectricTileLoad() {
		return MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileLoadEvent(this));
	}

	@Override
	public void onTileUnload() {
		super.onTileUnload();
		MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileUnloadEvent(this));
		this.addedToNet = false;
	}
	
	// IC2 Compatibility
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		return true;
	}

	@Override
	public double injectEnergyUnits(ForgeDirection directionFrom, double amount) {
		this.currentEnergy += amount;
		int var3 = 0;
		if (this.currentEnergy > this.maxEnergy) {
			var3 = this.currentEnergy - this.maxEnergy;
			this.currentEnergy = this.maxEnergy;
		}
		return var3;	
	}

	@Override
	public double demandedEnergyUnits() {
		return super.demandsEnergy();
	}

}

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

import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergyTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.core.block.TileElectrical;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD, Rikka
 * 
 */
public class TileWire extends TileElectrical implements IEnergyConductor {

	public boolean[] renderSides = new boolean[6];

	public TileWire() {
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	@Override
	public void frequentUpdate() {
		updateSides();
	}

	public void updateSides() {
		ForgeDirection[] dirs = ForgeDirection.values();
		for (int i = 0; i < 6; i++) {
			TileEntity ent = worldObj.getTileEntity(xCoord
					+ dirs[i].offsetX, yCoord + dirs[i].offsetY, zCoord
					+ dirs[i].offsetZ);
			if (ent != null && ent instanceof IEnergyTile) {
				renderSides[i] = true;
			} else
				renderSides[i] = false;
		}
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		return true;
	}

	@Override
	public double getConductionLoss() {
		return 1.0;
	}

	@Override
	public int getInsulationEnergyAbsorption() {
		return 512;
	}

	@Override
	public int getInsulationBreakdownEnergy() {
		return 512;
	}

	@Override
	public int getConductorBreakdownEnergy() {
		return 512;
	}

	@Override
	public void removeInsulation() {
	}

	@Override
	public void removeConductor() {
		this.onTileUnload();
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        AxisAlignedBB bb = INFINITE_EXTENT_AABB;
        bb = AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
        return bb;
    }

	@Override
	public boolean emitsEnergyTo(TileEntity paramTileEntity,
			ForgeDirection paramDirection) {
		return true;
	}
}

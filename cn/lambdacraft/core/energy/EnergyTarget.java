package cn.lambdacraft.core.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class EnergyTarget {
	TileEntity tileEntity;
	ForgeDirection direction;

	EnergyTarget(TileEntity tileEntity, ForgeDirection direction) {
		this.tileEntity = tileEntity;
		this.direction = direction;
	}
}

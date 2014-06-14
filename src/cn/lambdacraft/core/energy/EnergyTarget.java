package cn.lambdacraft.core.energy;

import cn.lambdacraft.api.LCDirection;
import net.minecraft.tileentity.TileEntity;

public class EnergyTarget {
	TileEntity tileEntity;
	LCDirection direction;

	EnergyTarget(TileEntity tileEntity, LCDirection direction) {
		this.tileEntity = tileEntity;
		this.direction = direction;
	}
}

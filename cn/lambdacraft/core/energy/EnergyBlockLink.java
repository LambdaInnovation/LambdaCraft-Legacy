package cn.lambdacraft.core.energy;

import net.minecraftforge.common.ForgeDirection;

public class EnergyBlockLink {

	ForgeDirection direction;
	double loss;

	EnergyBlockLink(ForgeDirection dir, double loss) {
		this.direction = dir;
		this.loss = loss;
	}

}

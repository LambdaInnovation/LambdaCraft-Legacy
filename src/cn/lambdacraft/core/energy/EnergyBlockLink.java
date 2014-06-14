package cn.lambdacraft.core.energy;

import cn.lambdacraft.api.LCDirection;

public class EnergyBlockLink {

	LCDirection lcdirection;
	double loss;

	EnergyBlockLink(LCDirection direction, double loss) {
		this.lcdirection = direction;
		this.loss = loss;
	}

}

package cn.liutils.core.energy;

import net.minecraftforge.common.util.ForgeDirection;

public class EnergyBlockLink {

    ForgeDirection direction;
    double loss;

    EnergyBlockLink(ForgeDirection dir, double loss) {
        this.direction = dir;
        this.loss = loss;
    }

}

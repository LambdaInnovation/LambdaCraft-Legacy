package cn.lambdacraft.core.energy;

import java.util.HashSet;
import java.util.Set;

import cn.lambdacraft.api.LCDirection;
import cn.lambdacraft.api.energy.tile.IEnConductor;

import net.minecraft.tileentity.TileEntity;

public class EnergyPath {
	TileEntity target = null;
	LCDirection targetDirection;
	Set<IEnConductor> conductors = new HashSet();

	int minX = 2147483647;
	int minY = 2147483647;
	int minZ = 2147483647;
	int maxX = -2147483648;
	int maxY = -2147483648;
	int maxZ = -2147483648;

	double loss = 0.0D;
	int minInsulationEnergyAbsorption = 2147483647;
	int minInsulationBreakdownEnergy = 2147483647;
	int minConductorBreakdownEnergy = 2147483647;
	long totalEnergyConducted = 0L;

}

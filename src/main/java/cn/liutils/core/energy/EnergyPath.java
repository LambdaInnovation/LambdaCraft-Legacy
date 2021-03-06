package cn.liutils.core.energy;

import ic2.api.energy.tile.IEnergyConductor;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class EnergyPath {
    TileEntity target = null;
    ForgeDirection targetDirection;
    Set<IEnergyConductor> conductors = new HashSet();

    int minX = 2147483647;
    int minY = 2147483647;
    int minZ = 2147483647;
    int maxX = -2147483648;
    int maxY = -2147483648;
    int maxZ = -2147483648;

    double loss = 0.0D;
    double minInsulationEnergyAbsorption = 2147483647;
    double minInsulationBreakdownEnergy = 2147483647;
    double minConductorBreakdownEnergy = 2147483647;
    long totalEnergyConducted = 0L;

}

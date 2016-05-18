package cn.liutils.core.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class EnergyTarget {
    TileEntity tileEntity;
    ForgeDirection direction;

    EnergyTarget(TileEntity tileEntity, ForgeDirection direction) {
        this.tileEntity = tileEntity;
        this.direction = direction;
    }
}

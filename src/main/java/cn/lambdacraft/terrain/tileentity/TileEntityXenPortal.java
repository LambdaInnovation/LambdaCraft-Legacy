package cn.lambdacraft.terrain.tileentity;

import net.minecraft.tileentity.TileEntity;
/**
 * 用于渲染传送门的TileEntity，空着就好
 * 
 * @author F
 */
public class TileEntityXenPortal extends TileEntity {
    
    @Override public boolean canUpdate() {
        return false;
    }
    
}

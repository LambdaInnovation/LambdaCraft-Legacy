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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.terrain.tileentity;

import java.util.List;

import cn.lambdacraft.terrain.register.XenBlocks;
import cn.liutils.api.util.GenericUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author WeAthFolD
 *
 */
public class TileEntityXenLight extends TileEntity {

	private static final int TICKRATE = 4;
	public boolean isLighting = false;
	private int tickSinceLastUpdate;
	public int tickSinceChange = 0;
	public int ticksExisted = 0;
	
    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    @Override
	public void updateEntity() {
    	int blockId = worldObj.getBlockId(xCoord, yCoord, zCoord);
    	++tickSinceChange;
    	++ticksExisted;
    	if(blockId == XenBlocks.light_on.blockID) {
    		isLighting = true;
    		if(++tickSinceLastUpdate >= TICKRATE) {
    			tickSinceLastUpdate = 0;
    			List list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.
    				getBoundingBox(xCoord - 2, yCoord - 2, zCoord - 2, xCoord + 3, yCoord + 3, zCoord + 3), GenericUtils.selectorPlayer);
    			if(list != null && list.size() > 0) {
    				worldObj.setBlock(xCoord, yCoord, zCoord, XenBlocks.light_off.blockID, 0, 3);
    				tickSinceChange = 0;
    			}
    		}
    	} else {
    		isLighting = false;
    		if(++tickSinceLastUpdate >= 80) {
    			tickSinceLastUpdate = 0;
    			List list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.
        				getBoundingBox(xCoord - 2, yCoord - 2, zCoord - 2, xCoord + 3, yCoord + 3, zCoord + 3), GenericUtils.selectorPlayer);
        		if(list == null || list.size() == 0) {
        			worldObj.setBlock(xCoord, yCoord, zCoord, XenBlocks.light_on.blockID, 0, 3);
        			tickSinceChange = 0;
        		}
    		}
    	}
    }

}

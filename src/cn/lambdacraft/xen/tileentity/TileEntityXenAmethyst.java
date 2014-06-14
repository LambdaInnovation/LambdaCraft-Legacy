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
package cn.lambdacraft.xen.tileentity;

import cn.lambdacraft.mob.util.MobHelper;
import cn.liutils.api.util.GenericUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

/**
 * @author WeAthFolD
 *
 */
public class TileEntityXenAmethyst extends TileEntity {

	public int ticksSinceLastAtack;
	public double lastxCoord, lastyCoord, lastzCoord;
	
	/**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    @Override
	public void updateEntity() {
    	if(++ticksSinceLastAtack > 40) {
    		Entity e = MobHelper.getNearestTargetWithinAABB(worldObj, xCoord + 0.5, yCoord - 3.0, zCoord + 0.5, 5.0F, GenericUtils.selectorLiving);
    		if(e != null) {
    			ticksSinceLastAtack = 0;
    			e.attackEntityFrom(DamageSource.generic, 2);
    				lastxCoord = e.posX - xCoord - 0.5;
    				lastyCoord = e.posY + e.height - yCoord - 0.5;
    				lastzCoord = e.posZ - zCoord - 0.5;
    		}
    	}
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return TileEntity.INFINITE_EXTENT_AABB;
    }


}

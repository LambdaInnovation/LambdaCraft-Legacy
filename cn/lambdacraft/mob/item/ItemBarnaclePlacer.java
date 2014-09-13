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
package cn.lambdacraft.mob.item;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.mob.entity.EntityBarnacle;

/**
 * @author WeAthFolD
 *
 */
public class ItemBarnaclePlacer extends LCMobSpawner {

	/**
	 * @param par1
	 */
	public ItemBarnaclePlacer() {
		super(EntityBarnacle.class, "barnacle");
	}

	/**
	 * @param id
	 * @param entityClass
	 * @param name
	 */
	public ItemBarnaclePlacer(Class<? extends EntityLiving> entityClass, String name) {
		super(entityClass, name);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}
	
	@Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y,
    		int z, int side, float par8, float par9, float par10)
    {
		if(world.isRemote)
			return false;
		if(side == 0) {
			if(world.isSideSolid(x, y, z, ForgeDirection.DOWN)) {
				EntityBarnacle barnacle = new EntityBarnacle(world, x, y, z);
				world.spawnEntityInWorld(barnacle);
				return true;
			}
		}
        return false;
    }

}

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
package cn.lambdacraft.core.client.key;

import cn.lambdacraft.api.tile.IUseable;
import cn.liutils.api.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * 和使用方块按键相关的各种功能。
 * 
 * @author WeAthFolD
 * 
 */
public class UsingUtils {

	/**
	 * 在你的onBlockRightClick函数中使用它来让该方块进入被使用状态。
	 * 
	 * @param player
	 *            玩家实体
	 * @param x
	 *            方块X
	 * @param y
	 *            方块Y
	 * @param z
	 *            方块Z
	 */
	public static void setBlockInUse(EntityPlayer player, int x, int y, int z) {
		NBTTagCompound nbt = player.getEntityData();
		nbt.setInteger("usingX", x);
		nbt.setInteger("usingY", y);
		nbt.setInteger("usingZ", z);
	}

	/**
	 * 在键位被按下的时候调用方块的使用函数。 **内部函数，一般不建议在你的方块类中调用。**
	 * 
	 * @param block
	 *            要调用的方块
	 * @param world
	 * @param player
	 */
	public static void useBlock(BlockPos block, World world, EntityPlayer player) {
		if (!(block.block instanceof IUseable))
			return;
		if (block.equals(getCurrentUsingBlock(world, player))) {
			if (player.getDistance(block.x, block.y, block.z) > 8.0) {
				stopUsingBlock(world, player);
			}
			return;
		}
		((IUseable) block.block).onBlockUse(world, player,
				block.x, block.y, block.z);
	}

	/**
	 * 在键位被松开的时候调用停止使用的函数。 **内部函数，一般不建议在你的方块中使用。**
	 * 
	 * @param world
	 * @param player
	 */
	public static void stopUsingBlock(World world, EntityPlayer player) {
		BlockPos block = getCurrentUsingBlock(world, player);
		if (block != null) {
			((IUseable) block.block).onBlockStopUsing(
					world, player, block.x, block.y, block.z);
		}
		NBTTagCompound nbt = player.getEntityData();
		nbt.setInteger("usingX", -1);
		nbt.setInteger("usingY", -1);
		nbt.setInteger("usingZ", -1);
	}

	/**
	 * 获取当前正在使用的方块。如果没有，则返回null。
	 * 
	 * @param world
	 *            世界
	 * @param player
	 *            要查找的玩家
	 * @return
	 */
	public static BlockPos getCurrentUsingBlock(World world, EntityPlayer player) {
		NBTTagCompound nbt = player.getEntityData();
		int x = nbt.getInteger("usingX"), y = nbt.getInteger("usingY"), z = nbt
				.getInteger("usingZ");
		Block block = world.getBlock(x, y, z);
		if (!(block instanceof IUseable)) {
			return null;
		} else {
			return new BlockPos(x, y, z, block);
		}
	}

}

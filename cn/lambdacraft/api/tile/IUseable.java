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
package cn.lambdacraft.api.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * 用来指明一个方块可以被Mod中的使用按钮调用。
 * @see cn.lambdacraft.core.network.MsgKeyUsing
 * @see cn.lambdacraft.core.client.key.KeyUse
 * @author WeAthFolD
 */
public interface IUseable {

	/**
	 * 处理方块被使用时的行为。
	 * 
	 * @param world
	 *            当前世界
	 * @param player
	 *            使用的玩家
	 * @param bx
	 *            方块X坐标
	 * @param by
	 *            方块Y坐标
	 * @param bz
	 *            方块Z坐标
	 */
	void onBlockUse(World world, EntityPlayer player, int bx, int by, int bz);

	/**
	 * 处理方块被停止使用时的行为。只有当你在onBlockUse中调用了KeyUse.setBlockInUse(...),它才会被调用。
	 * 
	 * @param world
	 *            当前世界
	 * @param player
	 *            使用的玩家
	 * @param bx
	 *            方块X坐标
	 * @param by
	 *            方块Y坐标
	 * @param bz
	 *            方块Z坐标
	 */
	void onBlockStopUsing(World world, EntityPlayer player, int bx, int by,
			int bz);

}

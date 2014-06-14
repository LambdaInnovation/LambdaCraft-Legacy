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
package cn.lambdacraft.api.energy.events;

import cn.lambdacraft.api.energy.tile.IEnergyTile;
import net.minecraft.world.World;

/**
 * 能量TileEntity发送能量的事件。
 * 
 * @author HopeAsd
 * 
 */
public class EnergyTileSourceEvent extends EnergyTileEvent {
	public int amount;
	public World world;

	public EnergyTileSourceEvent(World tworld, IEnergyTile energyTile,
			int amount) {
		super(tworld, energyTile);
		this.amount = amount;
		this.world = tworld;
	}

}
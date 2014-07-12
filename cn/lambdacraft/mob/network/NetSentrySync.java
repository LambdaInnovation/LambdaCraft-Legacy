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
package cn.lambdacraft.mob.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cn.lambdacraft.core.proxy.Proxy;
import cn.lambdacraft.mob.block.tile.TileSentryRay;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * @author WeAthFolD
 * 
 */
public class NetSentrySync implements IMessage {
	
	int x, y, z;
	int lx, ly, lz;
	boolean isValid;
	
	public NetSentrySync(TileSentryRay tile) {
		isValid = !(tile.linkedBlock == null);
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
		if(isValid) {
			lx = tile.linkedBlock.xCoord;
			ly = tile.linkedBlock.yCoord;
			lz = tile.linkedBlock.zCoord;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		isValid = ByteBufUtils.readVarInt(buf, 1) == 1;
		x = ByteBufUtils.readVarInt(buf, 4);
		y = ByteBufUtils.readVarInt(buf, 4);
		z = ByteBufUtils.readVarInt(buf, 4);
		lx = ByteBufUtils.readVarInt(buf, 4);
		ly = ByteBufUtils.readVarInt(buf, 4);
		lz = ByteBufUtils.readVarInt(buf, 4);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, isValid ? 1 : 0, 1);
		ByteBufUtils.writeVarInt(buf, x, 4);
		ByteBufUtils.writeVarInt(buf, y, 4);
		ByteBufUtils.writeVarInt(buf, z, 4);
		if(isValid) {
			ByteBufUtils.writeVarInt(buf, lx, 4);
			ByteBufUtils.writeVarInt(buf, ly, 4);
			ByteBufUtils.writeVarInt(buf, lz, 4);
		}
	}
	
	public static class Handler implements IMessageHandler<NetSentrySync, IMessage> {

		@Override
		public IMessage onMessage(NetSentrySync message, MessageContext ctx) {
			int x = message.x, y = message.y, z = message.z;
			World world = ((EntityPlayer)ctx.getServerHandler().playerEntity).worldObj;
			TileEntity te = world.getTileEntity(x, y, z);
			if (te == null || !(te instanceof TileSentryRay)) {
				return null;
			}
			TileSentryRay ray = (TileSentryRay) te;
			boolean isVaild = message.isValid;
			if (isVaild) {
				x = message.lx;
				y = message.ly;
				z = message.lz;
				te = world.getTileEntity(x, y, z);
				if (te == null || !(te instanceof TileSentryRay)) {
					Proxy.logExceptionMessage(te, "Couldn't find the right partner TileEntity.");
					return null;
				}
				ray.linkedBlock = (TileSentryRay) te;
			} else {
				ray.linkedBlock = null;
			}
			return null;
		}
		
	}

}

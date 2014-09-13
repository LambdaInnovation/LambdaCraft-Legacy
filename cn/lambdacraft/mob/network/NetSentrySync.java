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

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cn.lambdacraft.core.misc.CBCNetHandler;
import cn.lambdacraft.core.prop.GeneralProps;
import cn.lambdacraft.core.proxy.Proxy;
import cn.lambdacraft.mob.block.tile.TileSentryRay;
import cn.liutils.api.register.IChannelProcess;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * @author WeAthFolD
 * 
 */
public class NetSentrySync implements IChannelProcess {

	public static void sendSyncPacket(TileSentryRay tile) {
		int dataSize = tile.linkedBlock == null ? 11 : 21;
		ByteArrayOutputStream bos = CBCNetHandler.getStream(
				GeneralProps.NET_ID_SENTRYSYNCER, dataSize);
		DataOutputStream outputStream = new DataOutputStream(bos);
		boolean isVaild = tile.isActivated && tile.linkedBlock != null;
		try {
			outputStream.writeInt(tile.xCoord);
			outputStream.writeShort(tile.yCoord);
			outputStream.writeInt(tile.zCoord);
			outputStream.writeBoolean(isVaild);
			if (tile.linkedBlock != null) {
				outputStream.writeInt(tile.linkedBlock.xCoord);
				outputStream.writeShort(tile.linkedBlock.yCoord);
				outputStream.writeInt(tile.linkedBlock.zCoord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = GeneralProps.NET_CHANNEL_CLIENT;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		int dimension = tile.worldObj.getWorldInfo().getVanillaDimension();
		PacketDispatcher.sendPacketToAllInDimension(packet, dimension);
	}

	@Override
	public void onPacketData(DataInputStream stream, Player player) {
		try {
			int x = stream.readInt(), y = stream.readShort(), z = stream
					.readInt();
			World world = ((EntityPlayer)player).worldObj;
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te == null || !(te instanceof TileSentryRay)) {
				return;
			}
			TileSentryRay ray = (TileSentryRay) te;
			boolean isVaild = stream.readBoolean();
			if (isVaild) {
				x = stream.readInt();
				y = stream.readShort();
				z = stream.readInt();
				te = world.getBlockTileEntity(x, y, z);
				if (te == null || !(te instanceof TileSentryRay)) {
					Proxy.logExceptionMessage(te, "Couldn't find the right partner TileEntity.");
					return;
				}
				ray.linkedBlock = (TileSentryRay) te;
			} else {
				ray.linkedBlock = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

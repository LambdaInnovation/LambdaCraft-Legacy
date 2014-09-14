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
package cn.lambdacraft.deathmatch.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cn.lambdacraft.core.misc.CBCNetHandler;
import cn.lambdacraft.core.prop.GeneralProps;
import cn.lambdacraft.deathmatch.block.TileArmorCharger;
import cn.liutils.api.register.IChannelProcess;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * @author Administrator
 * 
 */
public class NetChargerClient implements IChannelProcess {

	public static void sendChargerPacket(TileArmorCharger te) {
		ByteArrayOutputStream bos = CBCNetHandler.getStream(
				GeneralProps.NET_ID_CHARGER_CL, 10);
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeInt(te.xCoord);
			outputStream.writeShort(te.yCoord);
			outputStream.writeInt(te.zCoord);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = GeneralProps.NET_CHANNEL;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}

	@Override
	public void onPacketData(DataInputStream stream, Player player) {
		World world = ((EntityPlayerMP) player).worldObj;

		int x, y, z;

		try {
			x = stream.readInt();
			y = stream.readShort();
			z = stream.readInt();
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te == null || !(te instanceof TileArmorCharger))
				throw new RuntimeException(
						"Cannot't get the right tileEntity of armor charger.");
			else {
				TileArmorCharger tt = (TileArmorCharger) te;
				tt.nextBehavior();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}

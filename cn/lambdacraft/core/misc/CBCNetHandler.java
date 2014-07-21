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
package cn.lambdacraft.core.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import cn.lambdacraft.core.proxy.GeneralProps;
import cn.liutils.api.register.IChannelProcess;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * 通用的网络包处理类。 请在任何网络包被发送之前（一般是Init中）注册Channel。 用一个独立的Byte值注册Channel。
 * 
 * @author WeAthFolD
 */
public class CBCNetHandler implements IPacketHandler {
	
	private static HashMap<Byte, IChannelProcess> channels = new HashMap();

	protected final String server, client;
	
	public CBCNetHandler() {
		server = GeneralProps.NET_CHANNEL_SERVER;
		client = GeneralProps.NET_CHANNEL_CLIENT;
	}
	
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {

		DataInputStream inputStream = new DataInputStream(
				new ByteArrayInputStream(packet.data));

		byte i = -1;
		try {
			i = inputStream.readByte();
		} catch (IOException e) {
			e.printStackTrace();
		}
		IChannelProcess p = channels.get(i);
		if (packet.channel.equals(client) || packet.channel.equals(server)) {
			if (p != null)
				p.onPacketData(inputStream, player);
		}
	}

	/**
	 * 注册一个网络包频道和一个处理类。
	 * 
	 * @param channel
	 *            频道
	 * @param process
	 *            包处理类
	 */
	public static boolean addChannel(byte channel, IChannelProcess process) {
		if (channels.containsKey(channel)) {
			return false;
		}
		channels.put(channel, process);
		return true;
	}

	/**
	 * 获取一个没有被使用的Channel ID。
	 * 
	 * @return ID
	 */
	public static byte getUniqueChannelID() {
		for (byte i = 0; i < Byte.MAX_VALUE; i++) {
			if (!channels.containsKey(i))
				return i;
		}
		return -1;
	}

	/**
	 * 如果使用CBCNetHandler来处理数据，请使用这个来获取用于发送的OutputStream。
	 * 
	 * @param id
	 *            频道ID
	 * @param size
	 *            包数据大小。
	 * @return 所要求的输出流，频道信息已经预先写入。
	 */
	public static ByteArrayOutputStream getStream(short id, int size) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(size + 1);
		DataOutputStream stream = new DataOutputStream(bos);
		try {
			stream.writeByte(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos;
	}

}

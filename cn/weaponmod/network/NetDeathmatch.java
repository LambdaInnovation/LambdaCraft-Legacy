/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.weaponmod.network;


import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import cn.liutils.api.register.IChannelProcess;
import cn.weaponmod.api.feature.IModdable;
import cn.weaponmod.api.weapon.IReloaddable;
import cn.weaponmod.proxy.WMGeneralProps;
import cn.weaponmod.register.WMPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * @author WeAthFolD
 *
 */
public class NetDeathmatch implements IChannelProcess {
	public static void sendModePacket(byte stackInSlot, byte id, byte newMode) {
		ByteArrayOutputStream bos = WMPacketHandler.getStream( WMGeneralProps.NET_ID_DM, 3);
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeByte(stackInSlot);
			outputStream.writeByte(id);
			outputStream.writeByte(newMode);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = WMGeneralProps.NET_CHANNEL_SERVER;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}

	private static int[] getModePacket(DataInputStream inputStream) {
		int[] arr = new int[3];
		try {
			arr[0] = inputStream.readByte();
			arr[1] = inputStream.readByte();
			arr[2] = inputStream.readByte();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return arr;
	}

	@Override
	public void onPacketData(DataInputStream packet, Player player) {
		EntityPlayer p = (EntityPlayer) player;
		int[] prop = getModePacket(packet);
		ItemStack is = p.inventory.mainInventory[prop[0]];

		if (is == null)
			return;
		Item item = is.getItem();
		if (prop[1] == 1) {
			if (!(item instanceof IReloaddable))
				return;
			((IReloaddable)item).onSetReload(is, p);
		} else {
			if(!(item instanceof IModdable))
				return;
			IModdable moddable = (IModdable) item;
			moddable.onModeChange(is, p, prop[2]);
		}
		return;
	}
}

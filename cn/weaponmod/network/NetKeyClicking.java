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
import cn.weaponmod.WeaponMod;
import cn.weaponmod.api.feature.ISpecialUseable;
import cn.weaponmod.events.ItemHelper;
import cn.weaponmod.proxy.WMGeneralProps;
import cn.weaponmod.register.WMPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * @author WeAthFolD
 *
 */
public class NetKeyClicking implements IChannelProcess {

	/**
	 * 1 : leftUse, 2 : leftStop
	 * -1 : rightUse, -2 : rightStop
	 * @param type
	 * @param additional
	 */
	public static void sendPacketData(int type) {
		ByteArrayOutputStream bos = WMPacketHandler.getStream( WMGeneralProps.NET_ID_CLICKING, 1);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
			outputStream.writeByte(type);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = WMGeneralProps.NET_CHANNEL_SERVER;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}

	/* (non-Javadoc)
	 * @see cn.lambdacraft.core.register.IChannelProcess#onPacketData(java.io.DataInputStream, cpw.mods.fml.common.network.Player)
	 */
	@Override
	public void onPacketData(DataInputStream stream, Player dummyPlayer) {
		try {
			int type = stream.readByte();
			EntityPlayer player = (EntityPlayer) dummyPlayer;
			boolean wrong = false;
			
			if(type == 1 || type == -1) { //use
				boolean left = type > 0;
				ItemStack stack = player.getCurrentEquippedItem();
				if(stack != null) {
					Item item = stack.getItem();
					if(item instanceof ISpecialUseable) {
						((ISpecialUseable)item).onItemClick(player.worldObj, player, stack, left);
					} else wrong = true;
				} else wrong = true;
				
			} else { //stop
				
				ItemHelper.stopUsingItem(player, type > 0);
				
			}
			if(wrong)
				WeaponMod.log.severe("Coudn't find the right left clicking Item instance...");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

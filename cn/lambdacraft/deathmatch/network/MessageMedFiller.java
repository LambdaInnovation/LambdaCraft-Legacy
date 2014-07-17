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

import io.netty.buffer.ByteBuf;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cn.lambdacraft.core.proxy.LCGeneralProps;
import cn.lambdacraft.deathmatch.block.TileMedkitFiller;
import cn.lambdacraft.deathmatch.block.TileMedkitFiller.EnumMedFillerBehavior;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * @author WeAthFolD
 * 
 */
public class MessageMedFiller implements IMessage {
	
	public int x, y, z;
	
	public MessageMedFiller() {}
	
	public MessageMedFiller(TileMedkitFiller te) {
		x = te.xCoord;
		y = te.yCoord;
		z = te.zCoord;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = ByteBufUtils.readVarInt(buf, 4);
		y = ByteBufUtils.readVarInt(buf, 4);
		z = ByteBufUtils.readVarInt(buf, 4);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, x, 4);
		ByteBufUtils.writeVarInt(buf, y, 4);
		ByteBufUtils.writeVarInt(buf, z, 4);
	}
	
	public static class Handler implements IMessageHandler<MessageMedFiller, IMessage> {

		@Override
		public IMessage onMessage(MessageMedFiller message, MessageContext ctx) {
			World world = ctx.getServerHandler().playerEntity.worldObj;
			TileEntity te = world.getTileEntity(message.x, message.y, message.z);
			if (te == null || !(te instanceof TileMedkitFiller))
				throw new RuntimeException("Cannot't get the right tileEntity of medkit filler.");
			else {

				TileMedkitFiller tt = (TileMedkitFiller) te;
				int value = tt.currentBehavior.ordinal();
				if (value == EnumMedFillerBehavior.values().length - 1)
					value = 0;
				else
					value++;
				tt.currentBehavior = EnumMedFillerBehavior.values()[value];
			}
			return null;
		}
		
	}

}

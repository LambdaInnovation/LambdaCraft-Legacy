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
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cn.lambdacraft.core.proxy.Proxy;
import cn.lambdacraft.mob.block.tile.TileSentryRay;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * @author WeAthFolD
 * 
 */
public class MsgSentrySync implements IMessage {

	public int xCoord, yCoord, zCoord;
	public boolean isValid;
	public int linkX, linkY, linkZ;
	
	public MsgSentrySync(TileSentryRay tile) {
		xCoord = tile.xCoord;
		yCoord = tile.yCoord;
		zCoord = tile.zCoord;
		isValid = tile.isActivated && tile.linkedBlock != null;
		if(isValid) {
			linkX = tile.linkedBlock.xCoord;
			linkY = tile.linkedBlock.yCoord;
			linkZ = tile.linkedBlock.zCoord;
		}
	}
	
	public MsgSentrySync() {
		
	}

	@Override
	public void fromBytes(ByteBuf stream) {
		xCoord = stream.readInt();
		yCoord = stream.readInt();
		zCoord = stream.readInt();
		isValid = stream.readBoolean();
		if (isValid) {
			linkX = stream.readInt();
			linkY = stream.readInt();
			linkZ = stream.readInt();
		}
	}

	@Override
	public void toBytes(ByteBuf outputStream) {
		outputStream.writeInt(xCoord);
		outputStream.writeInt(yCoord);
		outputStream.writeInt(zCoord);
		outputStream.writeBoolean(isValid);
		if (isValid) {
			outputStream.writeInt(linkX);
			outputStream.writeInt(linkY);
			outputStream.writeInt(linkZ);
		}
	}
	
	public static class Handler implements IMessageHandler<MsgSentrySync, IMessage> {

		@Override
		public IMessage onMessage(MsgSentrySync msg, MessageContext ctx) {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			World world = player.worldObj;
			TileEntity te = world.getTileEntity(msg.xCoord, msg.yCoord, msg.zCoord);
			if (te == null || !(te instanceof TileSentryRay)) {
				return null;
			}
			TileSentryRay ray = (TileSentryRay) te;
			if(msg.isValid) {
				te = world.getTileEntity(msg.linkX, msg.linkY, msg.linkZ);
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

package cn.lambdacraft.crafting.network;

import io.netty.buffer.ByteBuf;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cn.lambdacraft.crafting.block.tile.TileWeaponCrafter;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageCrafter implements IMessage {

	public byte id;
	public int blockX, blockY, blockZ;
	public boolean direction;

	public MessageCrafter(TileWeaponCrafter te, int id, boolean fow) {
		this.id = (byte) id;
		blockX = te.xCoord;
		blockY = te.yCoord;
		blockZ = te.zCoord;
		direction = fow;
	}

	public MessageCrafter getCrafterPacket(DataInputStream inputStream) {

		try {
			id = inputStream.readByte();
			blockX = inputStream.readInt();
			blockY = inputStream.readShort();
			blockZ = inputStream.readInt();
			direction = inputStream.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		id = (byte) ByteBufUtils.readVarInt(buf, 1);
		blockX = ByteBufUtils.readVarInt(buf, 4);
		blockY = ByteBufUtils.readVarInt(buf, 4);
		blockZ = ByteBufUtils.readVarInt(buf, 4);
		direction = ByteBufUtils.readVarInt(buf, 1) == 1;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, id, 1);
		ByteBufUtils.writeVarInt(buf, blockX, 4);
		ByteBufUtils.writeVarInt(buf, blockY, 4);
		ByteBufUtils.writeVarInt(buf, blockZ, 4);
		ByteBufUtils.writeVarInt(buf, direction ? 1 : 0, 1);
	}
	
	public static class Handler implements IMessageHandler<MessageCrafter, IMessage> {

		@Override
		public IMessage onMessage(MessageCrafter message, MessageContext ctx) {
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			TileEntity te = p.worldObj.getTileEntity(message.blockX, message.blockY, message.blockZ);
			if (te != null && !te.getWorldObj().isRemote) {
				if (message.id == 0) {
					((TileWeaponCrafter) te).addScrollFactor(message.direction);
				} else {
					((TileWeaponCrafter) te).addPage(message.direction);
				}
			}
			return null;
		}
		
	}

}

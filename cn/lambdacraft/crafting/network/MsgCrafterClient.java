package cn.lambdacraft.crafting.network;

import io.netty.buffer.ByteBuf;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cn.lambdacraft.crafting.block.tile.TileWeaponCrafter;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MsgCrafterClient implements IMessage {

	public short id;
	public int blockX, blockY, blockZ;
	public boolean direction;

	/**
	 * Creates the WeaponCrafter information message.
	 * 
	 * @param i
	 *            id(0 = factor, 1 = page)
	 * @param dir
	 *            方向（true=下，false=上）
	 */
	public MsgCrafterClient(int id, TileEntity te, boolean dir) {
		this.id = (short) id;
		blockX = te.xCoord;
		blockY = te.yCoord;
		blockZ = te.zCoord;
		direction = dir;
	}
	
	public MsgCrafterClient() {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readByte();
		blockX = buf.readInt();
		blockY = buf.readShort();
		blockZ = buf.readInt();
		direction = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte((byte) id);
		buf.writeInt(blockX);
		buf.writeShort(blockY);
		buf.writeInt(blockZ);
		buf.writeBoolean(direction);
	}
	
	public static class Handler implements IMessageHandler<MsgCrafterClient, IMessage> {

		@Override
		public IMessage onMessage(MsgCrafterClient msg, MessageContext ctx) {
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			TileEntity te = p.worldObj.getTileEntity(msg.blockX, msg.blockY, msg.blockZ);
			if (te != null && !te.getWorldObj().isRemote) {
				if (msg.id == 0) {
					((TileWeaponCrafter) te).addScrollFactor(msg.direction);
				} else {
					((TileWeaponCrafter) te).addPage(msg.direction);
				}
			}
			return null;
		}
		
	}

}

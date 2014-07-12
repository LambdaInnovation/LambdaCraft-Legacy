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


import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import cn.weaponmod.api.feature.IModdable;
import cn.weaponmod.api.weapon.IReloaddable;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * @author WeAthFolD
 *
 */
public class MessageDM implements IMessage {
	public int slot, id, newMode;
	
	public MessageDM(int stackInSlot, int id, int newMode) {
		this.slot = stackInSlot;
		this.id = id;
		this.newMode  = newMode;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		slot = ByteBufUtils.readVarInt(buf, 1);
		id = ByteBufUtils.readVarInt(buf, 1);
		newMode = ByteBufUtils.readVarInt(buf, 1);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, slot, 1);
		ByteBufUtils.writeVarInt(buf, id, 1);
		ByteBufUtils.writeVarInt(buf, newMode, 1);
	}

	public static class Handler implements IMessageHandler<MessageDM, IMessage> {
		@Override
		public IMessage onMessage(MessageDM message, MessageContext ctx) {
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			int[] prop = { message.slot, message.id, message.newMode };
			ItemStack is = p.inventory.mainInventory[prop[0]];

			if (is == null)
				return null;
			Item item = is.getItem();
			if (prop[1] == 1) {
				if (!(item instanceof IReloaddable))
					return null;
				((IReloaddable)item).onSetReload(is, p);
			} else {
				if(!(item instanceof IModdable))
					return null;
				IModdable moddable = (IModdable) item;
				moddable.onModeChange(is, p, prop[2]);
				String s = moddable.getModeDescription(moddable.getMode(is));
				if(s != null)
				p.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("mode.new")
						+ ": \u00a74"
						+ StatCollector.translateToLocal(s)));
			}
			return null;
		}
	}

}

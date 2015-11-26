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
package cn.lambdacraft.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cn.lambdacraft.core.client.key.UsingUtils;
import cn.liutils.api.util.BlockPos;
import cn.liutils.api.util.Motion3D;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * 用来处理使用键的网络信息
 * @author WeAthFolD
 */
public class MsgKeyUsing implements IMessage {
	
	boolean isUsing;
	
	public MsgKeyUsing(boolean b) {
		isUsing = b;
	}
	
	public MsgKeyUsing() {
		
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		isUsing = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isUsing);
	}
	
	public static class Handler implements IMessageHandler<MsgKeyUsing, IMessage> {

		@Override
		public IMessage onMessage(MsgKeyUsing msg, MessageContext ctx) {
			EntityPlayer thePlayer = ctx.getServerHandler().playerEntity;
			World world = thePlayer.worldObj;
			
			if (msg.isUsing) {
				
				Motion3D begin = new Motion3D(thePlayer, true);
				MovingObjectPosition mop = world.rayTraceBlocks(
						begin.getPosVec(world), begin.move(8.0).getPosVec(world)
					);
				if (mop == null || mop.sideHit == -1)
					return null;
				Block block = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
				UsingUtils.useBlock(new BlockPos(mop.blockX, mop.blockY,
						mop.blockZ, block), world, thePlayer);

			} else {
				
				UsingUtils.stopUsingBlock(world, thePlayer);
				
			}
			return null;
		}
		
	}

}

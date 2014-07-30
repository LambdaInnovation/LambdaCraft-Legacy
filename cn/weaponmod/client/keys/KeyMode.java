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
package cn.weaponmod.client.keys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.StatCollector;
import cn.liutils.api.client.register.IKeyProcess;
import cn.weaponmod.api.feature.IModdable;
import cn.weaponmod.network.NetDeathmatch;

public class KeyMode implements IKeyProcess {

	@Override
	public void onKeyDown(int keyCode, boolean isEnd) {
		if(isEnd)
			return;
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP player = mc.thePlayer;
		if (player == null || mc.currentScreen != null)
			return;
		ItemStack currentItem = player.inventory.getCurrentItem();
		if (currentItem != null && currentItem.getItem() instanceof IModdable) {
			IModdable wpn = (IModdable) currentItem.getItem();
			onModeChange(currentItem, player, wpn.getMaxModes());
		}
	}

	@Override
	public void onKeyUp(int keyCode, boolean isEnd) {
	}

	private void onModeChange(ItemStack itemStack, EntityPlayer player, int maxModes) {

		IModdable wpn = (IModdable) itemStack.getItem();
		int stackInSlot = -1;
		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			if (player.inventory.mainInventory[i] == itemStack) {
				stackInSlot = i;
				break;
			}
		}
		if (stackInSlot == -1)
			return;

		int mode = wpn.getMode(itemStack);
		mode = (maxModes - 1 <= mode) ? 0 : mode + 1;
		NetDeathmatch.sendModePacket((byte) stackInSlot, (byte) 0, (byte) mode);
		String s = wpn.getModeDescription(wpn.getMode(itemStack));
		if(s != null)
		player.sendChatToPlayer(ChatMessageComponent.createFromText(StatCollector.translateToLocal("mode.new")
				+ ": \u00a74"
				+ StatCollector.translateToLocal(s)));
	}

}
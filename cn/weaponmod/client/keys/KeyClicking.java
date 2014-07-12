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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cn.liutils.api.client.register.IKeyProcess;
import cn.weaponmod.WeaponMod;
import cn.weaponmod.api.feature.ISpecialUseable;
import cn.weaponmod.events.ItemControlHandler;
import cn.weaponmod.network.MessageClicking;

/**
 * @author WeAthFolD
 *
 */
public class KeyClicking implements IKeyProcess {

	private KeyMode keyMode = new KeyMode();
	
	private boolean isLeft;
	
	public KeyClicking(boolean left) {
		isLeft = left;
	}

	@Override
	public void onKeyDown(int keyCode, boolean isEnd) {
		if(!isLeft)
			keyMode.onKeyDown(keyCode, isEnd);
		if(isEnd)
			return;
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP player = mc.thePlayer;
		if (player == null || mc.currentScreen != null)
			return;
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null) {
			Item item = stack.getItem();
			if (item instanceof ISpecialUseable) {
				((ISpecialUseable) item).onItemClick(mc.theWorld, player, stack, isLeft);
				WeaponMod.netHandler.sendToServer(new MessageClicking(isLeft ? 1 : -1));
			}
		}
	}

	@Override
	public void onKeyUp(int keyCode, boolean isEnd) {
		if(!isLeft)
			keyMode.onKeyUp(keyCode, isEnd);
		if(!isEnd)
			return;
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		if (player == null || mc.currentScreen != null)
			return;
		ItemControlHandler.stopUsingItem(player, isLeft);
	}

}

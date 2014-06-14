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
package cn.weaponmod.api.information;

import net.minecraft.item.ItemStack;

/**
 * @author WeAthFolD
 *
 */
public class InformationWeapon {

	public ItemStack itemStack;
	public int ticksExisted,  lastTick_left, lastTick_right, recoverTick;
	public boolean lastShooting_left, lastShooting_right;
	public boolean isRecovering;
	public float originPitch;

	public InformationWeapon(ItemStack i) {
		isRecovering = false;
		itemStack = i;
	}

	public void updateTick() {
		ticksExisted++;
	}
	
	public void setLastTick(boolean left) {
		if(left) {
			lastTick_left = ticksExisted;
			lastShooting_left = false;
		} else { 
			lastTick_right = ticksExisted;
			lastShooting_right = false;
		}
	}
	
	public void setLastTick_Shoot(boolean side) {
		setLastTick(side);
		if(side)
			lastShooting_left = true;
		else lastShooting_right = true;
	}
	
	public int getDeltaTick(boolean left) {
		return ticksExisted - (left ? lastTick_left : lastTick_right);
	}

	public void resetState() {
		ticksExisted = lastTick_left = lastTick_right = recoverTick = 0;
		isRecovering = false;
	}

}

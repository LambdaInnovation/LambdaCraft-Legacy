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
public class InformationBullet extends InformationWeapon {
	// 357,9mmAR,9mmhandgun等的辅助类

	public boolean isReloading;
	public int muzzle_left, muzzle_right;

	public InformationBullet(ItemStack par8Weapon) {

		super(par8Weapon);
		ticksExisted = 0;
		recoverTick = 0;

		isReloading = false;

	}
	
	public void setMuzzleTick(boolean side) {
		if(side)
			muzzle_left = 3;
		else muzzle_right = 3;
	}
	
	
	@Override
	public void updateTick() {
		super.updateTick();
		--muzzle_left;
		--muzzle_right;
	}


}

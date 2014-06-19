package cn.lambdacraft.deathmatch.item.ammos;

import cn.lambdacraft.core.CBCMod;

public class Ammo_shotgun extends ItemAmmo {

	public Ammo_shotgun(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(64);
		setIAndU("ammo_shotgun");
	}
	
}

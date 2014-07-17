package cn.lambdacraft.deathmatch.item.ammos;

import cn.lambdacraft.core.LCMod;

public class Ammo_shotgun extends ItemAmmo {

	public Ammo_shotgun() {
		super();
		setCreativeTab(LCMod.cct);
		setMaxStackSize(64);
		setIAndU("ammo_shotgun");
	}
	
}

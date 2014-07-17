package cn.lambdacraft.deathmatch.item.ammos;

import cn.lambdacraft.core.LCMod;

public class Ammo_bow extends ItemAmmo {

	public Ammo_bow() {
		super();
		setCreativeTab(LCMod.cct);
		setIAndU("ammo_bow");
		setMaxDamage(6);
	}
	
}

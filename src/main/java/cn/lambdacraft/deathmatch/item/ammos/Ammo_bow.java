package cn.lambdacraft.deathmatch.item.ammos;

import cn.lambdacraft.core.CBCMod;

public class Ammo_bow extends ItemAmmo {

	public Ammo_bow() {
		super();
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_bow");
		setFull3D();
		setMaxDamage(6);
	}
	
}

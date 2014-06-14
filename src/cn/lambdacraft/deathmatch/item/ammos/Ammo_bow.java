package cn.lambdacraft.deathmatch.item.ammos;

import cn.lambdacraft.core.CBCMod;

public class Ammo_bow extends ItemAmmo {

	public Ammo_bow(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_bow");
		setMaxDamage(6);
	}
	
}

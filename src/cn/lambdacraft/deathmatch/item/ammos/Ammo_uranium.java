package cn.lambdacraft.deathmatch.item.ammos;

import cn.lambdacraft.core.CBCMod;

public class Ammo_uranium extends ItemAmmo {

	public Ammo_uranium(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_uranium");
		setMaxDamage(100);
	}
	
}

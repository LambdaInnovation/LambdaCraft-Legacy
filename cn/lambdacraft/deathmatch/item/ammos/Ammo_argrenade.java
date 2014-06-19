package cn.lambdacraft.deathmatch.item.ammos;

import cn.lambdacraft.core.CBCMod;

public class Ammo_argrenade extends ItemAmmo {

	public Ammo_argrenade(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_argrenade");
		setMaxStackSize(10);
	}
	
}

package cn.lambdacraft.deathmatch.item.ammos;

import cn.lambdacraft.core.CBCMod;

public class Ammo_rpg extends ItemAmmo {

	public Ammo_rpg(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_rpg");
		setMaxStackSize(10);
	}
	
}

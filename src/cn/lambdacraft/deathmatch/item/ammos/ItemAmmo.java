package cn.lambdacraft.deathmatch.item.ammos;

import cn.lambdacraft.core.item.CBCGenericItem;

public abstract class ItemAmmo extends CBCGenericItem {
	
	public ItemAmmo(int par1) {
		super(par1);
		setMaxStackSize(1);
	}
	
	public boolean isSpecialAmmo() {
		return this.hasSubtypes;
	}
	
}

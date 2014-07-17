package cn.lambdacraft.deathmatch.item.ammos;

import cn.lambdacraft.core.item.LCGenericItem;

public abstract class ItemAmmo extends LCGenericItem {
	
	public ItemAmmo() {
		super();
		setMaxStackSize(1);
	}
	
	public boolean isSpecialAmmo() {
		return this.hasSubtypes;
	}
	
}

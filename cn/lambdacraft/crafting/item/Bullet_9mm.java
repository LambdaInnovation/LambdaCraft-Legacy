package cn.lambdacraft.crafting.item;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.item.CBCGenericItem;

public class Bullet_9mm extends CBCGenericItem {

	public Bullet_9mm(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cctMisc);
		setIAndU("bullet_9mm");
		setMaxStackSize(64);
	}

}

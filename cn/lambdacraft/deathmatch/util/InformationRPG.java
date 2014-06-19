package cn.lambdacraft.deathmatch.util;

import cn.lambdacraft.deathmatch.entity.EntityRPGDot;
import cn.weaponmod.api.information.InformationBullet;
import net.minecraft.item.ItemStack;

public class InformationRPG extends InformationBullet {

	public EntityRPGDot currentDot;

	public InformationRPG(ItemStack par8Weapon) {
		super(par8Weapon);
	}

}

package cn.lambdacraft.crafting.recipe;

import cn.lambdacraft.deathmatch.item.ammos.ItemAmmo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class RecipeRepair extends CrafterRecipeNormal {

	public Item inputA, inputB;
	public int scale;

	public RecipeRepair(Item ia, Item ib) {
		super(new ItemStack(ia, 1, 0), 0, new ItemStack(ia, 1,
				ia.getMaxDamage() - 1),
				new ItemStack(ib, ia.getMaxDamage() - 1));
		inputA = ia;
		inputB = ib;
		scale = 1;
	}

	public RecipeRepair(Item ia, Item ib, int s) {
		super(new ItemStack(ia, 1, 0), 0, new ItemStack(ia, 1,
				ia.getMaxDamage() - 1), new ItemStack(ib,
				(ia.getMaxDamage() - 1) / s));
		inputA = ia;
		inputB = ib;
		scale = s;
	}
	
	/*
	@Override
	public boolean hasEnoughMaterial(ItemStack[] inv) {
		boolean flag1 = false, flag2 = false;
		
		for (int i = 2; i < 20; i++) {
			ItemStack is = inv[i];
			if (is == null)
				continue;
			if (is.getItem() == inputA) {
				if (is.getItemDamage() > 0)
					flag1 = true;
			} else if (is.getItem() == inputB) {
				flag2 = true;
			}
		}
		
		return flag1 && flag2;
	} */

	@Override
	public String toString() {
		return StatCollector.translateToLocal("repair.name") + " : "
				+ inputA.getItemStackDisplayName(null);
	}

}

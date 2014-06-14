package cn.lambdacraft.crafting.recipe;

import cn.lambdacraft.deathmatch.item.ammos.ItemAmmo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class RecipeRepair extends RecipeCrafter {

	public ItemAmmo inputA;
	public Item inputB;
	public int scale;

	public RecipeRepair(ItemAmmo ia, Item ib) {
		super(new ItemStack(ia, 1, 0), 0, new ItemStack(ia, 1,
				ia.getMaxDamage() - 1),
				new ItemStack(ib, ia.getMaxDamage() - 1));
		inputA = ia;
		inputB = ib;
		scale = 1;
	}

	public RecipeRepair(ItemAmmo ia, Item ib, int s) {
		super(new ItemStack(ia, 1, 0), 0, new ItemStack(ia, 1,
				ia.getMaxDamage() - 1), new ItemStack(ib,
				(ia.getMaxDamage() - 1) / s));
		inputA = ia;
		inputB = ib;
		scale = s;
	}

	@Override
	public String toString() {
		return StatCollector.translateToLocal("repair.name") + " : "
				+ inputA.getLocalizedName(null);
	}

}

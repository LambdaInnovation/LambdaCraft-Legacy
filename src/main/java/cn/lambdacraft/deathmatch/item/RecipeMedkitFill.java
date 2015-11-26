/**
 * 
 */
package cn.lambdacraft.deathmatch.item;

import cn.lambdacraft.deathmatch.item.ItemMedkit.EnumAddingType;
import cn.lambdacraft.deathmatch.register.DMItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * 填充医疗包的合成表
 * @author WeathFolD
 */
public class RecipeMedkitFill implements IRecipe {

	public RecipeMedkitFill() {
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		boolean b1 = false, b2 = false;
		//Just one medkit and one potion
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack s = inv.getStackInSlot(i);
			if (s != null) {
				if (s.getItem() == DMItems.medkit) {
					if(b1 == true)
						return false;
					b1 = true;
				} else if (s.getItem() == Items.potionitem) {
					if(b2 == true)
						return false;
					b2 = true;
				}
			}
		}
		return b1 && b2;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack medkit = null, potion = null;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack s = inv.getStackInSlot(i);
			if (s != null) {
				if (s.getItem() == DMItems.medkit) {
					medkit = s;
				} else {
					potion = s;
				}
			}
		}
		if(medkit == null || potion == null) //This ain't suppose to happen
			return null;
		
		ItemStack res = medkit.copy();
		int i = ItemMedkit.tryAddEffectTo(res, potion, EnumAddingType.DURATION);
		return i > 0 ? res : null;
	}

	@Override
	public int getRecipeSize() {
		return 4;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

}

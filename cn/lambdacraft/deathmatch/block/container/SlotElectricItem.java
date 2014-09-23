package cn.lambdacraft.deathmatch.block.container;

import ic2.api.item.ISpecialElectricItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotElectricItem extends Slot {

	public SlotElectricItem(IInventory par1iInventory, int par2, int par3,
			int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return (par1ItemStack != null && par1ItemStack.getItem() instanceof ISpecialElectricItem);
	}
}

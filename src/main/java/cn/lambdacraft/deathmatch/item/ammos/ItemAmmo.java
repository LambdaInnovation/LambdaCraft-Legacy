package cn.lambdacraft.deathmatch.item.ammos;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cn.lambdacraft.core.item.CBCGenericItem;

public abstract class ItemAmmo extends CBCGenericItem {
	
	public ItemAmmo() {
		super();
		setMaxStackSize(1);
	}
	
	public boolean isSpecialAmmo() {
		return this.hasSubtypes;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
    	if(this.getMaxDamage() > 0)
    		list.add("" + ((stack.getMaxDamage() - stack.getItemDamage() - 1) + "/" + (stack.getMaxDamage() - 1)));
    }
	
}

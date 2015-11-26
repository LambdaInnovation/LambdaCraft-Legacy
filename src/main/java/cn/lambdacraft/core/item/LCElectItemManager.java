/**
 * Code by Lambda Innovation, 2013.
 */
package cn.lambdacraft.core.item;

import cn.liutils.api.util.GenericUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;

/**
 * @author WeAthFolD
 *
 */
public class LCElectItemManager implements IElectricItemManager {
	
	public static final LCElectItemManager INSTANCE = new LCElectItemManager();

	@Override
	public double discharge(ItemStack itemStack, double amount, int tier,
			boolean ignoreTransferLimit, boolean __, boolean simulate) {
		double en = getCharge(itemStack);
		double amt = Math.min(amount, Math.min(en, ignoreTransferLimit ? 100000 : getTransLimit(itemStack)));
		if(!simulate) itemStack.setItemDamage((int) (itemStack.getItemDamage() + amt));
		return amt;
	}

	@Override
	public double charge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
		int en = itemStack.getItemDamage();
		double amt = Math.min(amount, Math.min(en, ignoreTransferLimit ? 100000 : getTransLimit(itemStack)));
		if(!simulate) itemStack.setItemDamage((int) (en - amt));
		return amt;
	}
	
	public LCElectItemManager() {
	}
	
	private double getMaxCharge(ItemStack stack) {
		ISpecialElectricItem ic = (ISpecialElectricItem) stack.getItem();
		return ic.getMaxCharge(stack);
	}
	
	private double getTransLimit(ItemStack stack) {
		ISpecialElectricItem ic = (ISpecialElectricItem) stack.getItem();
		return ic.getTransferLimit(stack);
	}
	
	private ISpecialElectricItem getItem(ItemStack stack) {
		return (ISpecialElectricItem) stack.getItem();
	}


	/* (non-Javadoc)
	 * @see ic2.api.item.IElectricItemManager#getCharge(net.minecraft.item.ItemStack)
	 */
	@Override
	public double getCharge(ItemStack itemStack) {
		return itemStack.getMaxDamage() - itemStack.getItemDamage();
	}

	@Override
	public boolean canUse(ItemStack itemStack, double amount) {
		return amount <= getCharge(itemStack);
	}

	/* (non-Javadoc)
	 * @see ic2.api.item.IElectricItemManager#use(net.minecraft.item.ItemStack, int, net.minecraft.entity.EntityLivingBase)
	 */
	@Override
	public boolean use(ItemStack itemStack, double amount, EntityLivingBase entity) {
		return amount <= getCharge(itemStack);
	}

	/* (non-Javadoc)
	 * @see ic2.api.item.IElectricItemManager#chargeFromArmor(net.minecraft.item.ItemStack, net.minecraft.entity.EntityLivingBase)
	 */
	@Override
	public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity) {
	}

	/* (non-Javadoc)
	 * @see ic2.api.item.IElectricItemManager#getToolTip(net.minecraft.item.ItemStack)
	 */
	@Override
	public String getToolTip(ItemStack itemStack) {
		return "" + getCharge(itemStack) + "/" + getMaxCharge(itemStack) + " EU";
	}
}

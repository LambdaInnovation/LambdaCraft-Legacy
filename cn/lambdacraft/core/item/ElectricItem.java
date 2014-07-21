/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.core.item;

import java.util.List;

import cn.lambdacraft.api.energy.item.ICustomEnItem;
import cn.lambdacraft.core.CBCMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

/**
 * Generic electric item.
 * 
 * @author WeAthFolD
 * 
 */
public abstract class ElectricItem extends CBCGenericItem implements
		ICustomEnItem {

	protected int tier = 1, transferLimit = 100, maxCharge;

	public ElectricItem(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CBCMod.cct);
	}

	public void setMaxCharge(int p) {
		maxCharge = p;
	}

	public void setTier(int p) {
		tier = p;
	}

	public void setTransferLimit(int p) {
		transferLimit = p;
	}

	/**
	 * Returns the maximum damage an item can take.
	 */
	@Override
	public int getMaxDamage() {
		return this.maxCharge;
	}

	@Override
	//public int getItemMaxDamageFromStack(ItemStack is) {
	public int getMaxDamage(ItemStack stack){
		return maxCharge;
	}

	/**
	 * Return the itemDamage represented by this ItemStack. Defaults to the
	 * itemDamage field on ItemStack, but can be overridden here for other
	 * sources such as NBT.
	 * 
	 * @param stack
	 *            The itemstack that is damaged
	 * @return the damage value
	 */
	@Override
	//public int getItemDamageFromStack(ItemStack stack) {
	public int getDamage(ItemStack stack)
	{
		return maxCharge - getItemCharge(stack);
	}

	/**
	 * Return the itemDamage display value represented by this itemstack.
	 * 
	 * @param stack
	 *            the stack
	 * @return the damage value
	 */
	@Override
	//public int getItemDamageFromStackForDisplay(ItemStack stack) {
	public int getDisplayDamage(ItemStack stack)
	{
		return maxCharge - getItemCharge(stack);
	}

	@Override
	public int getChargedItemId(ItemStack itemStack) {
		return this.itemID;
	}

	@Override
	public int getEmptyItemId(ItemStack itemStack) {
		return this.itemID;
	}

	@Override
	public int getMaxCharge(ItemStack itemStack) {
		return this.maxCharge;
	}

	@Override
	public int discharge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate) {
		if (getItemCharge(itemStack) == 0)
			return 0;
		int en = getItemCharge(itemStack);
		if (!ignoreTransferLimit)
			amount = this.getTransferLimit(itemStack);
		if (en > amount) {
			if (!simulate)
				setItemCharge(itemStack, getItemCharge(itemStack) - amount);
			return amount;
		} else {
			if (!simulate)
				setItemCharge(itemStack, getItemCharge(itemStack) - en);
			return en;
		}
	}

	@Override
	public int charge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate) {

		int chg = getItemCharge(itemStack), lim = getTransferLimit(itemStack);
		int en = this.maxCharge - chg - 1;
		if (en == 0)
			return 0;
		if (!ignoreTransferLimit)
			amount = lim > amount ? amount : lim;
		en = en > amount ? amount : en;
		if (!simulate)
			setItemCharge(itemStack, chg + en);
		return en;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		if (this.canShowChargeToolTip(par1ItemStack))
			par3List.add(StatCollector.translateToLocal("gui.curenergy.name")
					+ " : " + getItemCharge(par1ItemStack) + "/"
					+ this.maxCharge + " EU");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		ItemStack chargedItem = new ItemStack(par1, 1, 0);
		this.setItemCharge(chargedItem, maxCharge);
		par3List.add(chargedItem);
	}

	@Override
	public boolean canShowChargeToolTip(ItemStack itemStack) {
		return true;
	}

	public void setItemCharge(ItemStack stack, int charge) {
		loadCompound(stack).setInteger(
				"charge",
				(charge > 0) ? (charge > this.maxCharge ? this.maxCharge
						: charge) : 0);
	}

	protected int getItemCharge(ItemStack stack) {
		return loadCompound(stack).getInteger("charge");
	}

	private NBTTagCompound loadCompound(ItemStack stack) {
		if (stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		return stack.stackTagCompound;
	}

	/**
	 * Set the damage for this itemstack. Note, this method is responsible for
	 * zero checking.
	 * 
	 * @param stack
	 *            the stack
	 * @param damage
	 *            the new damage value
	 */
	@Override
	//public void setItemDamageForStack(ItemStack stack, int damage) {
	public void setDamage(ItemStack stack, int damage){
		setItemCharge(stack, damage);
	}

	/**
	 * Return if this itemstack is damaged. Note only called if
	 * {@link #isDamageable()} is true.
	 * 
	 * @param stack
	 *            the stack
	 * @return if the stack is damaged
	 */
	@Override
	//public boolean isItemStackDamaged(ItemStack stack) {
	public boolean isDamaged(ItemStack stack)
	{
		return getItemCharge(stack) < maxCharge;
	}

	@Override
	public int getTier(ItemStack itemStack) {
		return tier;
	}

	@Override
	public int getTransferLimit(ItemStack itemStack) {
		return this.transferLimit;
	}

	@Override
	public boolean canUse(ItemStack itemStack, int amount) {
		return getItemCharge(itemStack) > 0;
	}

}

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

import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ISpecialArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 * 
 */
public abstract class ElectricArmor extends CBCGenericArmor implements
	ISpecialElectricItem, ISpecialArmor {

	protected int tier = 1, transferLimit = 100, maxCharge;

	protected int energyPerDamage = 500;

	/**
	 * @param id
	 * @param mat
	 * @param renderIndex
	 * @param armorType
	 */
	public ElectricArmor(ArmorMaterial mat, int renderIndex,
			int armorType) {
		super(mat, renderIndex, armorType);
	}

	public void setEnergyPerDamage(int p) {
		energyPerDamage = p;
	}

	public void setMaxCharge(int p) {
		maxCharge = p;
	}

	public void setTier(int p) {
		tier = p;
	}

	protected void setTransferLimit(int p) {
		transferLimit = p;
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
	public int getDamage(ItemStack stack) {
		return getMaxDamage(stack) - getItemCharge(stack);
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
		return getMaxCharge(stack) - getItemCharge(stack);
	}

	@Override
	public Item getChargedItem(ItemStack itemStack) {
		return this;
	}

	@Override
	public Item getEmptyItem(ItemStack itemStack) {
		return this;
	}

	@Override
	public int getMaxCharge(ItemStack itemStack) {
		return this.maxCharge;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
			par3List.add(StatCollector.translateToLocal("gui.curenergy.name")
					+ " : " + getItemCharge(par1ItemStack) + "/"
					+ getMaxDamage(par1ItemStack) + " EU");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		ItemStack chargedItem = new ItemStack(par1, 1, 0);
		this.setItemCharge(chargedItem, maxCharge);
		par3List.add(chargedItem);
	}

	protected void setItemCharge(ItemStack stack, int charge) {
		loadCompound(stack).setInteger(
				"charge",
				(charge > 0) ? (charge > getMaxDamage(stack) ? getMaxDamage(stack)
						: charge) : 0);
	}

	public static int getItemCharge(ItemStack stack) {
		return loadCompound(stack).getInteger("charge");
	}

	private static NBTTagCompound loadCompound(ItemStack stack) {
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
	public void setDamage(ItemStack stack, int damage) {
		setItemCharge(stack, maxCharge - damage);
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
	public boolean isDamaged(ItemStack stack){
		return getItemCharge(stack) < getMaxDamage();
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
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
		getManager(stack).discharge(stack, damage * this.energyPerDamage, 2, true, false);
		if(getItemCharge(stack) < this.energyPerDamage)
			this.setItemCharge(stack, 0);
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return false;
	}

	@Override
	public IElectricItemManager getManager(ItemStack itemStack) {
		return LCElectItemManager.INSTANCE;
	}

}

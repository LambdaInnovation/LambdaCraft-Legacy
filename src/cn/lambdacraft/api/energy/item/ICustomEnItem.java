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
package cn.lambdacraft.api.energy.item;

import net.minecraft.item.ItemStack;

/**
 * 部分参考了工业2的代码，电力物品的兼容类。 The interface has referenced the IC2's code.
 * 
 * @author WeAthFolD
 * 
 */
public interface ICustomEnItem extends IEnItem {

	/**
	 * Charge an item with a specified amount of energy
	 * 
	 * @param itemStack
	 *            electric item's stack
	 * @param amount
	 *            amount of energy to charge in EU
	 * @param tier
	 *            tier of the charging device, has to be at least as high as the
	 *            item to charge
	 * @param ignoreTransferLimit
	 *            ignore the transfer limit specified by getTransferLimit()
	 * @param simulate
	 *            don't actually change the item, just determine the return
	 *            value
	 * @return Energy transferred into the electric item
	 */
	public int charge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate);

	/**
	 * Discharge an item by a specified amount of energy
	 * 
	 * @param itemStack
	 *            electric item's stack
	 * @param amount
	 *            amount of energy to charge in EU
	 * @param tier
	 *            tier of the discharging device, has to be at least as high as
	 *            the item to discharge
	 * @param ignoreTransferLimit
	 *            ignore the transfer limit specified by getTransferLimit()
	 * @param simulate
	 *            don't actually discharge the item, just determine the return
	 *            value
	 * @return Energy retrieved from the electric item
	 */
	public int discharge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate);

	/**
	 * Determine if the specified electric item has at least a specific amount
	 * of EU. This is supposed to be used in the item code during operation, for
	 * example if you want to implement your own electric item. BatPacks are not
	 * taken into account.
	 * 
	 * @param itemStack
	 *            electric item's stack
	 * @param amount
	 *            minimum amount of energy required
	 * @return true if there's enough energy
	 */
	public boolean canUse(ItemStack itemStack, int amount);

	/**
	 * Determine whether to show the charge tool tip with NEI or other means.
	 * 
	 * Return false if IC2's handler is incompatible, you want to implement your
	 * own or you don't want to display the charge at all.
	 * 
	 * @return true to show the tool tip (x/y EU)
	 */
	public boolean canShowChargeToolTip(ItemStack itemStack);
}

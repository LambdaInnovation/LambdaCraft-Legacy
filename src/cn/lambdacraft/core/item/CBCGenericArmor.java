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

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import cn.lambdacraft.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 通用盔甲类。
 */
public class CBCGenericArmor extends ItemArmor {

	private String iconName = "";
	protected String description;
	protected boolean useDescription = false;

	/**
	 * @param id
	 * @param mat
	 * @param renderIndex
	 * @param armorType
	 */
	public CBCGenericArmor(ArmorMaterial mat, int renderIndex,
			int armorType) {
		super(mat, renderIndex, armorType);
		setCreativeTab(CBCMod.cct);
	}
	
	 public CBCGenericArmor setIAndU(String names) {
		this.setUnlocalizedName(names);
		this.setIconName(names);
		return this;
	} 

	public void setIconName(String name) {
		this.iconName = name;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister
				.registerIcon("lambdacraft:" + iconName);
	}

	public CBCGenericArmor setDescription(String d) {
		this.description = d;
		useDescription = true;
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if (useDescription)
			par3List.add(description);
	}

}

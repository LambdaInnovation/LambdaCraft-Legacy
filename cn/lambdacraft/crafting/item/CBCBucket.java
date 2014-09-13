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
package cn.lambdacraft.crafting.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import cn.lambdacraft.core.CBCMod;

/**
 * @author WeAthFolD
 *
 */
public class CBCBucket extends ItemBucket {

	protected String name;
	
	/**
	 * @param par1
	 * @param flowLiquidID
	 */
	public CBCBucket(Block flowLiquid, String unlocalizedName) {
		super(flowLiquid);
		this.setCreativeTab(CBCMod.cct);
		this.setContainerItem(Items.bucket);
		this.setUnlocalizedName(unlocalizedName);
		name = unlocalizedName;
	}
	
	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:" + name);
	}

}

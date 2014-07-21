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

import cn.lambdacraft.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;

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
	public CBCBucket(int par1, int flowLiquidID, String unlocalizedName) {
		super(par1, flowLiquidID);
		this.setCreativeTab(CBCMod.cctMisc);
		this.setContainerItem(Item.bucketEmpty);
		this.setUnlocalizedName(unlocalizedName);
		name = unlocalizedName;
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:" + name);
	}

}

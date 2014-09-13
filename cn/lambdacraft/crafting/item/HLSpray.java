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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.core.prop.ClientProps;

/**
 * Custom spray.
 * @author mkpoli
 */
public class HLSpray extends ItemSpray {

	public HLSpray() {
		super(ClientProps.getSprayId() + 2);
		setIAndU("hlspray");
	}
	
	@Override
	public boolean onItemUse(ItemStack item_stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float x_off,
			float y_off, float z_off) {
		//胡妹你看看你这参传的，既然tID是Item构建时确定，运行时修改当然不会影响结果= =
		this.tId = ClientProps.getSprayId() + 2;
		return super.onItemUse(item_stack, player, world, x, y, z, side, x_off, y_off, z_off);
	}

}

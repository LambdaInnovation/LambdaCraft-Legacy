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
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import cn.lambdacraft.core.LCMod;
import cn.lambdacraft.core.item.LCGenericItem;
import cn.lambdacraft.core.proxy.LCClientProps;
import cn.lambdacraft.crafting.entity.EntitySpray;

/**
 * @author mkpoli
 * 
 */
public class HLSpray extends LCGenericItem {
	
	public HLSpray() {
		super();
		setMaxStackSize(1);
		setMaxDamage(10);
		setCreativeTab(LCMod.cctMisc);
		setUnlocalizedName("hlspray");
		setTextureName("lambdacraft:hlspray");
	}
	
	@Override
	public boolean onItemUse(ItemStack item_stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float x_off,
			float y_off, float z_off) {
		// 对其他方块的上下表面使用时，不触发任何效果
		if (side == 0)
			return false;
		if (side == 1)
			return false;

		// 创建Facing(3D) to Direction(2D)数组传递第Side个对象为in
		int direction = Direction.facingToDirection[side];
		// 创建EntityArt实例
		EntitySpray entity = new EntitySpray(world, x, y, z, direction, LCClientProps.getSprayId() + 2, player);
		// 判断是否被放置在了可用的表面
		if (entity.onValidSurface()) {
			if (!world.isRemote) {
				world.spawnEntityInWorld(entity); // 生成entity
				world.playSoundAtEntity(player, "lambdacraft:entities.sprayer", 0.7f, 1);
			}
			item_stack.damageItem(1, player);
		}
		return true;
	}
}

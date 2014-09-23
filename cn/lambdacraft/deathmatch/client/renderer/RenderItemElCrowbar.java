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
package cn.lambdacraft.deathmatch.client.renderer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import cn.liutils.api.client.util.RenderUtils;
import cn.weaponmod.api.feature.IModdable;

/**
 * @author WeAthFolD
 *
 */
public class RenderItemElCrowbar implements IItemRenderer {

	

	/* (non-Javadoc)
	 * @see net.minecraftforge.client.IItemRenderer#handleRenderType(net.minecraft.item.ItemStack, net.minecraftforge.client.IItemRenderer.ItemRenderType)
	 */
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see net.minecraftforge.client.IItemRenderer#shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType, net.minecraft.item.ItemStack, net.minecraftforge.client.IItemRenderer.ItemRendererHelper)
	 */
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	/* (non-Javadoc)
	 * @see net.minecraftforge.client.IItemRenderer#renderItem(net.minecraftforge.client.IItemRenderer.ItemRenderType, net.minecraft.item.ItemStack, java.lang.Object[])
	 */
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if(type == ItemRenderType.INVENTORY) {
			renderInventory(item);
		} else {
			renderEquipped((EntityLivingBase) data[1], item);
		}
		
	}
	
	private void renderInventory(ItemStack item) {
		RenderUtils.renderItemInventory(item);
		if(((IModdable)item.getItem()).getMode(item) == 0 && item.getItemDamage() < item.getMaxDamage() - 1)
			RenderUtils.renderEnchantGlint_Inv();
		
	}
	
	private void renderEquipped(EntityLivingBase ent, ItemStack item) {
		RenderUtils.renderItemIn2d(item, 0.0625);
		if(((IModdable)item.getItem()).getMode(item) == 0 && item.getItemDamage() < item.getMaxDamage() - 1)
			RenderUtils.renderEnchantGlint_Equip();
	}

}

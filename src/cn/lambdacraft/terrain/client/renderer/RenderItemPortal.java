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
package cn.lambdacraft.terrain.client.renderer;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 * 
 */
public class RenderItemPortal implements IItemRenderer {

	/**
	 * 
	 */
	public RenderItemPortal() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraftforge.client.IItemRenderer#handleRenderType(net.minecraft
	 * .item.ItemStack, net.minecraftforge.client.IItemRenderer.ItemRenderType)
	 */
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
		case ENTITY:
			return true;
		case EQUIPPED:
			return true;
		case INVENTORY:
			return true;
		default:
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraftforge.client.IItemRenderer#shouldUseRenderHelper(net.
	 * minecraftforge.client.IItemRenderer.ItemRenderType,
	 * net.minecraft.item.ItemStack,
	 * net.minecraftforge.client.IItemRenderer.ItemRendererHelper)
	 */
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		switch (helper) {
		case ENTITY_ROTATION:
		case ENTITY_BOBBING:
			return true;
		default:
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraftforge.client.IItemRenderer#renderItem(net.minecraftforge
	 * .client.IItemRenderer.ItemRenderType, net.minecraft.item.ItemStack,
	 * java.lang.Object[])
	 */
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type) {
		case ENTITY:
			renderEntity(item, (RenderBlocks)data[0], (EntityItem) data[1]);
			break;
		case EQUIPPED:
			renderEquipped(item, (RenderBlocks)data[0], (EntityLivingBase) data[1]);
			break;
		case INVENTORY:
			renderInventory(item, (RenderBlocks)data[0]);
			break;
			default:
				return;
		}
	}
	
	private void renderEntity(ItemStack item, RenderBlocks render, EntityItem ent) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
		RenderUtils.renderItemIn2d(item, 0.0625F);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	private void renderEquipped(ItemStack is, RenderBlocks render, EntityLivingBase entity) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
		RenderUtils.renderItemIn2d(is, 0.0625F);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	private void renderInventory(ItemStack is, RenderBlocks render) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
		RenderUtils.renderItemInventory(is);
		GL11.glDisable(GL11.GL_BLEND);
	}

}

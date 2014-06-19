/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.weaponmod.api.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cn.liutils.api.client.model.IItemModel;
import cn.weaponmod.api.weapon.WeaponGeneralBullet;

/**
 * @author WeAthFolD
 *
 */
public class RenderDualWieldWeapon extends RenderModelBulletWeapon {

	/**
	 * @param mdl
	 * @param type
	 * @param texture
	 * @param muzzleflashPath
	 */
	public RenderDualWieldWeapon(IItemModel mdl, WeaponGeneralBullet type,
			ResourceLocation texture, String... muzzleflashPath) {
		super(mdl, type, texture, muzzleflashPath);
	}
	
	@Override
	public void renderEquipped(ItemStack item, RenderBlocks render,
			EntityLivingBase entity,  ItemRenderType type) {

		GL11.glPushMatrix();
		
		super.doRenderEquipped(item, render, entity, false, type);
		
		boolean firstPerson = (entity == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) && 
				Minecraft.getMinecraft().currentScreen == null;
		
		GL11.glTranslatef(0.0F, 0.0F, firstPerson ? -1.7F : 0.8F);
		GL11.glScalef(1.0F, 1.0F, -1.0F);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		super.doRenderEquipped(item, render, entity, true, type);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
		
	}

	@Override
	public void renderInventory() {
		GL11.glPushMatrix();
		GL11.glTranslatef(-1.5F, -3F, -6.0F);
		super.renderInventory();
		
		GL11.glTranslatef(3.0F, 3.0F, 6.0F);
		GL11.glRotatef(20, 0.0F, 1.0F, 0.0F);
		super.renderInventory();
		GL11.glPopMatrix();
	}
	
}

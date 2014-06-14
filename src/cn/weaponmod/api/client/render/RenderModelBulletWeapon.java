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

import java.util.Random;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cn.liutils.api.client.model.IItemModel;
import cn.liutils.api.client.render.RenderModelItem;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.api.weapon.WeaponGeneralBullet;
import cn.weaponmod.events.ItemHelper;

/**
 * @author WeAthFolD
 *
 */
public class RenderModelBulletWeapon extends RenderModelItem {

	public String[] muzzleflash = {""};
	public float tx = 0F, ty = 0F, tz = 0F;
	protected WeaponGeneralBullet weaponType;
	protected static Random RNG = new Random();
	
	
	/**
	 * @param mdl
	 * @param texture
	 */
	public RenderModelBulletWeapon(IItemModel mdl, WeaponGeneralBullet type, ResourceLocation texture, String ...muzzleflashPath) {
		super(mdl, texture);
		weaponType = type;
		if(muzzleflashPath.length != 0)
			muzzleflash = muzzleflashPath;
		this.setInvRotation(0F, 0F, 37.5F);
	}
	
	public RenderModelBulletWeapon setMuzzleflashOffset(float x, float y, float z) {
		tx = x;
		ty = y;
		tz = z;
		return this;
	}
	
	@Override
	public void renderEquipped(ItemStack item, RenderBlocks render,
			EntityLivingBase entity, ItemRenderType type) {
		boolean left = true;
		if(entity instanceof EntityPlayer)
			left = ItemHelper.getUsingTickLeft((EntityPlayer) entity, false) == 0 ? true : ItemHelper.getUsingSide((EntityPlayer) entity);
		doRenderEquipped(item, render, entity, left, type);
	}
	
	@Override
	public RenderModelItem setInformationFrom(RenderModelItem a) {
		super.setInformationFrom(a);
		if(a instanceof RenderModelBulletWeapon) {
			RenderModelBulletWeapon b = (RenderModelBulletWeapon) a;
			setMuzzleflashOffset(b.tx, b.ty, b.tz);
			muzzleflash = ((RenderModelBulletWeapon) a).muzzleflash;
		}
		return this;
	}

	public void doRenderEquipped(ItemStack item, RenderBlocks render,
			EntityLivingBase entity, boolean left,  ItemRenderType type) {
		
		InformationBullet inf = (InformationBullet) weaponType.getInformation(item, entity.worldObj);
		if(inf == null) {
			super.renderEquipped(item, render, entity, type);
			return;
		}
		
		int mt = inf == null ? -1 : (left ? inf.muzzle_left : inf.muzzle_right);
		if(left ? inf.lastShooting_left : inf.lastShooting_right) {
			int dt = inf.getDeltaTick(left);
			float recoverTime = 0.5F * weaponType.upLiftRadius / weaponType.recoverRadius;
			if(dt < recoverTime) {
				float uplift = 2.0F * weaponType.upLiftRadius * MathHelper.cos(mt * (float)Math.PI / recoverTime);
				GL11.glRotatef(uplift, 0.0F, 0.0F, 1.0F);
			}
		}
		
		GL11.glRotatef(2.5F * MathHelper.sin(inf.getDeltaTick(left) * (float)Math.PI * 0.025F), 0.0F, 0.0F, 1.0F);
		super.renderEquipped(item, render, entity, type);
		if(mt > 0) {
			mt = 3 - mt;
			RenderBulletWeapon.renderMuzzleflashIn2d(t, muzzleflash[mt < muzzleflash.length ? mt : muzzleflash.length - 1], tx, ty, tz);
		}
	}

}

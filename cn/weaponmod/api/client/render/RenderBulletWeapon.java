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
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cn.liutils.api.client.util.RenderUtils;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.api.weapon.WeaponGeneralBullet;
import cn.weaponmod.events.ItemHelper;

/**
 * @author WeAthFolD
 * 
 */
public class RenderBulletWeapon implements IItemRenderer {

	Minecraft mc = Minecraft.getMinecraft();
	float tx = 0F, ty = 0F, tz = 0F;
	float width = 0.0625F;
	private WeaponGeneralBullet weaponType;
	public ResourceLocation[] muzzleflash = {};
	public float recoilRatio = 0.02F, upliftRatio = 1.0F;
	
	/**
	 * Reload animation style.
	 * 0: Normal reload. rotating left.
	 * 1: Another style. rotating downwards and slightly right.
	 */
	public int reloadAnimStyle = 0;
	
	public RenderBulletWeapon(WeaponGeneralBullet weapon, float w, ResourceLocation[] tex) {
		weaponType = weapon;
		width = w / 2F;
		muzzleflash = tex;
	}

	public RenderBulletWeapon(WeaponGeneralBullet weapon, float w) {
		weaponType = weapon;
		width = w / 2F;
	}

	public RenderBulletWeapon(WeaponGeneralBullet weapon, float width, float x,
			float y, float z, ResourceLocation[] texPath) {
		this(weapon, width, texPath);
		tx = x;
		ty = y;
		tz = z;
	}
	
	public RenderBulletWeapon setReloadStyle(int style) {
		reloadAnimStyle = style;
		return this;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		switch (type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
			return true;
		default:
			return false;
		}

	}

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

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
			renderEquipped(item, (RenderBlocks) data[0],
					(EntityLivingBase) data[1]);
			break;
		default:
			break;

		}

	}

	public void renderEquipped(ItemStack item, RenderBlocks render,
			EntityLivingBase entity) {
		Tessellator t = Tessellator.instance;

		int mode = 0;
		if (item.stackTagCompound != null)
			mode = item.getTagCompound().getInteger("mode");

		GL11.glPushMatrix();

		InformationBullet inf = (InformationBullet) weaponType
				.getInformation(item, entity.worldObj);
		if (inf == null) {
			RenderUtils.renderItemIn2d(item, width);
			return;
		}
		
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			boolean left = ItemHelper.getUsingTickLeft(player, false) == 0 ? true : ItemHelper.getUsingSide(player);
			boolean firstPerson = (entity == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) 
					&& Minecraft.getMinecraft().currentScreen == null;
			int mt = inf == null ? -1 : (left ? inf.muzzle_left : inf.muzzle_right);
			if (left ? inf.lastShooting_left : inf.lastShooting_right) {
				int dt = inf.getDeltaTick(left);
				float recoverTime = 0.5F * weaponType.upLiftRadius
						/ weaponType.recoverRadius;
				if (dt < recoverTime) {
					float uplift = 2.0F
							* weaponType.upLiftRadius
							* MathHelper
									.cos(mt * (float) Math.PI / recoverTime);
					GL11.glRotatef(uplift, 0.0F, 0.0F, 1.0F);
				}
			}

			if (mt > 0) {
				mt = 3 - mt;
				RenderBulletWeapon.renderMuzzleflashIn2d(t,
						muzzleflash[mt < muzzleflash.length ? mt
								: muzzleflash.length - 1], tx, ty, tz);
			}
			
			if(firstPerson && inf.isReloading) {
				float rotation = weaponType.getRotationForReload(item);
				if(reloadAnimStyle == 0)
					GL11.glRotatef(upliftRatio * rotation * 75F, 0.0F, 1.0F, 0.0F);
				else if(reloadAnimStyle == 1) {
					GL11.glRotatef(upliftRatio * rotation * 17F, 0.0F, 0.0F, -1.0F);
					GL11.glRotatef(upliftRatio * rotation * 30F, 0.0F, -1.0F, 0.0F);
					GL11.glRotatef(upliftRatio * rotation * 14F, 1.0F, 0.0F, 0.0F);
				}
			}
		}
		RenderUtils.renderItemIn2d(item, width);
		GL11.glPopMatrix();
	}

	protected static void addVertex(Vec3 vec3, double texU, double texV) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
				texU, texV);
	}

	public static void renderMuzzleflashIn2d(Tessellator t, ResourceLocation texture,
			double tx, double ty, double tz) {

		Vec3 a1 = RenderUtils.newV3(1.2, -0.4, -0.5), a2 = RenderUtils.newV3(
				1.2, 0.4, -0.5), a3 = RenderUtils.newV3(1.2, 0.4, 0.3), a4 = RenderUtils
				.newV3(1.2, -0.4, 0.3);

		float u1 = 0.0F, v1 = 0.0F, u2 = 1.0F, v2 = 1.0F;

		t = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderUtils.loadTexture(texture);

		GL11.glRotatef(45, 0.0F, 0.0F, 1.0F);
		GL11.glTranslated(tx, ty + 0.1F, tz + 0.1F);
		
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		t.startDrawingQuads();
		t.setBrightness(15728880);
		addVertex(a1, u2, v2);
		addVertex(a2, u2, v1);
		addVertex(a3, u1, v1);
		addVertex(a4, u1, v2);
		t.draw();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();

	}

}

package cn.lambdacraft.deathmatch.client.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.deathmatch.client.model.ModelEgonHead;
import cn.liutils.api.client.render.RenderModelItem;
import cn.weaponmod.api.client.render.RenderBulletWeapon;
import cn.weaponmod.events.ItemHelper;

/**
 * Weapon Egon Renderer Class.
 */
public class RenderEgon extends RenderModelItem {

	public RenderEgon() {
		super(new ModelEgonHead(), ClientProps.EGON_HEAD_PATH);
		this.renderInventory = false;
		this.setRotation(0F, 179.35F, 0F);
		this.inventorySpin = false;
		this.setEquipOffset(0.438F, -0.152F, -0.172F);
		this.setOffset(0.0F, 0.2F, -0.302F);
		this.setScale(2.45F);
	}

	Tessellator t = Tessellator.instance;

	@Override
	public void renderEquipped(ItemStack item, RenderBlocks render,
			EntityLivingBase entity, ItemRenderType type) {
		super.renderEquipped(item, render, entity, type);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if(player.getCurrentEquippedItem() == item &&
					ItemHelper.getUsingTickLeft(player, true) > 0) {
				RenderBulletWeapon.renderMuzzleflashIn2d(Tessellator.instance, ClientProps.EGON_MUZZLE, 0.1F, -0.12F, -0.2F);
			}
		}
	}
	
}

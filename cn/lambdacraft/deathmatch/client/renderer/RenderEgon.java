package cn.lambdacraft.deathmatch.client.renderer;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.deathmatch.client.model.ModelEgonHead;
import cn.liutils.api.client.render.RenderModelItem;
import cn.weaponmod.api.client.render.RendererBulletWeapon;
import cn.weaponmod.core.event.ItemControlHandler;

/**
 * Weapon Egon Renderer Class.
 */
public class RenderEgon extends RenderModelItem {

	public RenderEgon() {
		super(new ModelEgonHead(), ClientProps.EGON_HEAD_PATH);
		this.renderInventory = false;
		this.setStdRotation(5.0F, 173.42F, 0.0F);
		this.inventorySpin = false;
		this.setEquipOffset(0.318, -0.142, -0.142);
		this.setOffset(0.0F, 0.2F, -0.302F);
		this.setScale(2.46F);
	}

	Tessellator t = Tessellator.instance;

	@Override
	public void renderEquipped(ItemStack item, RenderBlocks render,
			EntityLivingBase entity, ItemRenderType type) {
		super.renderEquipped(item, render, entity, type);
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if(player.getCurrentEquippedItem() == item &&
					ItemControlHandler.getUsingTicks(player, 0) > 0) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
				RendererBulletWeapon.renderMuzzleflashIn2d(Tessellator.instance, ClientProps.EGON_MUZZLE, 0.0F, -0.12F, -0.2F);
			}
		}
	}
	
}

package cn.lambdacraft.mob.util;

import cn.lambdacraft.mob.entity.CBCEntityMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class CBCRenderMob extends RenderLiving {
	
	public final static TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;

	public CBCRenderMob(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}

	// getTexture
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		if (entity == null || !(entity instanceof CBCEntityMob)) {
			return null;
		}
		CBCEntityMob e = (CBCEntityMob) entity;
		return e.getTexture();
	}
	
	protected void loadTexture(ResourceLocation str) {
		renderEngine.bindTexture(str);
	}


}

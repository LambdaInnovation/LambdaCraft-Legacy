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
package cn.lambdacraft.mob.client.renderer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.mob.client.model.ModelTurret;
import cn.lambdacraft.mob.entity.EntitySentry;
import cn.liutils.api.client.render.LIRenderMob;
import cn.liutils.api.client.util.RenderUtils;
import cn.liutils.api.util.GenericUtils;

/**
 * @author WeAthFolD
 *
 */
public class RenderTurret extends LIRenderMob {

	private static ModelTurret model = new ModelTurret();
	
	public RenderTurret() {
		super(model, 0.4F);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.renderer.entity.Render#doRender(net.minecraft.entity.Entity, double, double, double, float, float)
	 */
	@Override
	public void doRender(Entity entity, double par2, double par4, double par6,
			float par8, float f1) {
		EntitySentry turret = (EntitySentry) entity;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		RenderUtils.loadTexture(ClientProps.TURRET_PATH);
		super.doRender(entity, par2, par4, par6, par8, f1);
		GL11.glTranslatef((float) par2, (float) par4 + turret.height - turret.deathTime * 0.06F,(float) par6);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		if(turret.hurtResistantTime > 15)
			GL11.glColor3f(1.0F, 0.3F, 0.3F);
		GL11.glRotatef(turret.deathTime * 3, 1.0F, 0.2F, -1.0F);
		GL11.glRotatef(180.0F - turret.rotationYawHead, 0.0F, 1.0F, 0.0F);
		model.renderTop(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
	
	@Override
    protected boolean func_110813_b(EntityLivingBase par1EntityLiving)
    {
		return false;
    }

}

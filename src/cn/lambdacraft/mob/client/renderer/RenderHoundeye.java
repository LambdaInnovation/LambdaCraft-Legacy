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

import java.util.Random;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.mob.client.model.ModelHoundeye;
import cn.lambdacraft.mob.entity.EntityHoundeye;
import cn.lambdacraft.mob.util.CBCRenderMob;
import net.minecraft.entity.EntityLiving;

/**
 * @author WeAthFolD
 *
 */
public class RenderHoundeye extends CBCRenderMob {

	protected static ModelHoundeye model = new ModelHoundeye();
	protected Random rand = new Random();
	/**
	 * @param par1ModelBase
	 * @param par2
	 */
	public RenderHoundeye() {
		super(model, 0.5F);
		// TODO Auto-generated constructor stub
	}
	
    @Override
	public void doRenderLiving(EntityLiving ent, double par2, double par4, double par6, float par8, float par9)
    {
    	GL11.glPushMatrix();
    	float yOffset = ent.prevLimbSwingAmount + (ent.limbSwingAmount - ent.prevLimbSwingAmount) * par9;
    	float xOffset = 0.0F, zOffset = 0.0F;
    	if(((EntityHoundeye)ent).isCharging) {
    		xOffset = rand.nextFloat() * 0.3F;
    		zOffset = rand.nextFloat() * 0.3F;
    		yOffset += rand.nextFloat() * 0.3F;
    	}
    	if(yOffset > 1.0F)
    		yOffset = 1.0F;
    	yOffset *= 0.2F;
    	GL11.glTranslatef(xOffset, yOffset, zOffset);
    	super.doRenderLiving(ent, par2, par4, par6, par8, par9);
    	GL11.glPopMatrix();
    }

}

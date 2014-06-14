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

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.mob.client.model.ModelVortigaunt;
import cn.lambdacraft.mob.entity.EntityAlienSlave;
import cn.lambdacraft.mob.util.CBCRenderMob;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 *
 */
public class RenderAlienSlave extends CBCRenderMob {

	private static final float WIDTH = 0.25F;
	protected static final Random rand = new Random();
	
	/**
	 * @param par1ModelBase
	 * @param par2
	 */
	public RenderAlienSlave() {
		super(new ModelVortigaunt(), 0.5F);
	}

    @Override
	public void doRender(Entity entity, double x, double y, double z, float par8, float par9)
    {
    	GL11.glPushMatrix();
    	EntityAlienSlave slave = (EntityAlienSlave) entity;
    	Tessellator t = Tessellator.instance;
    	if(slave.isCharging) {
    		for(Vec3 vec : slave.electrolyze_left) {
    			renderElectro(slave, vec, t, x, y, z, true);
    		}
    		
    		for(Vec3 vec : slave.electrolyze_right) {
    			renderElectro(slave, vec, t, x, y, z, false);
    		}
    	}
    	if(entity.hurtResistantTime > 10)
    		GL11.glColor4f(1.0F, 0.3F, 0.3F, 1.0F);
    	super.doRender(entity, x, y, z, par8, par9);
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	GL11.glPopMatrix();
    }
    
    private void renderElectro(EntityAlienSlave ent, Vec3 vec3, Tessellator t, double x, double y, double z, boolean isLeft) {
    	double dx = vec3.xCoord, dy = vec3.yCoord, dz = vec3.zCoord ;
    	double tx = 0.0, ty = 1.2, tz = 0.0;
    	GL11.glPushMatrix();
    	GL11.glDisable(GL11.GL_CULL_FACE);
    	GL11.glDisable(GL11.GL_LIGHTING);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	this.loadTexture(ClientProps.VORTIGAUNT_RAY_PATH[rand.nextInt(3)]);
    	
    	Vec3 v1 = RenderUtils.newV3(tx - WIDTH, ty, tz), v2 = RenderUtils.newV3(tx + WIDTH, ty, tz),
    		v3 = RenderUtils.newV3(tx, ty - WIDTH, tz), v4 = RenderUtils.newV3(tx, ty + WIDTH, tz);
    	double d = Math.sqrt(dx * dx +  dy * dy + dz * dz) * 1.3;
    	
    	GL11.glTranslated(x, y, z);
    	
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		t.startDrawingQuads();
		t.setBrightness(15728880);
		t.setColorRGBA_F(0.1F, 0.9F, 0.1F, 0.7F);
    	RenderUtils.addVertex(v1 , 0, 0);
    	RenderUtils.addVertex(v2 , 1, 0);
    	RenderUtils.addVertex(v2.addVector(dx, dy, dz) , 1, d);
    	RenderUtils.addVertex(v1.addVector(dx, dy, dz) , 0, d);
    	
    	RenderUtils.addVertex(v3 , 0, 0);
    	RenderUtils.addVertex(v4 , 1, 0);
    	RenderUtils.addVertex(v4.addVector(dx, dy, dz) , 1, d);
    	RenderUtils.addVertex(v3.addVector(dx, dy, dz) , 0, d);
    	t.draw();
    	
    	GL11.glEnable(GL11.GL_CULL_FACE);
    	GL11.glEnable(GL11.GL_LIGHTING);
    	GL11.glPopMatrix();
    	
    }
}

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

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.mob.client.model.ModelBarnacle;
import cn.lambdacraft.mob.entity.EntityBarnacle;
import cn.liutils.api.client.util.RenderUtils;

/**
 * 藤壶怪渲染。
 * @author WeAthFolD
 *
 */
public class RenderBarnacle extends Render {

    private ModelBase model = new ModelBarnacle();
    public static final double WIDTH = 0.05;
    
    public RenderBarnacle() {
    }

    /* (non-Javadoc)
     * @see net.minecraft.client.renderer.entity.Render#doRender(net.minecraft.entity.Entity, double, double, double, float, float)
     */
    @Override
    public void doRender(Entity entity, double par2, double par4, double par6,
            float par8, float par9) {
        EntityBarnacle barnacle = (EntityBarnacle) entity;
        Tessellator t = Tessellator.instance;
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(ClientProps.BARNACLE_PATH);
        GL11.glTranslatef((float) par2, (float) par4 + 2 * entity.height,
                (float) par6);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glTranslatef(0.0F, 1.5F, 0.0F);
        if(barnacle.getHealth() <= 0)
            GL11.glRotatef(barnacle.deathTime * 6.5F, 1.0F, 0.0F, -1.0F);
        GL11.glTranslatef(0.0F, -1.5F, 0.0F);
        if(barnacle.hurtResistantTime > 10)
            GL11.glColor3f(1.0F, 0.5F, 0.5F);
        
        this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F,
                0.0625F);
    
        if(barnacle.getHealth() > 0) {
            //Barnacle Tentacle Render
            double length = barnacle.tentacleLength;
            double h1 = 0.5, h2 = length + 1.0;
            Vec3 v1 = RenderUtils.newV3(-WIDTH, h1, -WIDTH),
                 v2 = RenderUtils.newV3(-WIDTH, h1, WIDTH),
                 v3 = RenderUtils.newV3(WIDTH, h1, WIDTH),
                 v4 = RenderUtils.newV3(WIDTH, h1, -WIDTH);
            Vec3 v5 = RenderUtils.newV3(-WIDTH, h1, -WIDTH),
                     v6 = RenderUtils.newV3(-WIDTH, h2, WIDTH),
                     v7 = RenderUtils.newV3(WIDTH, h2, WIDTH),
                     v8 = RenderUtils.newV3(WIDTH, h2, -WIDTH);
            Minecraft.getMinecraft().renderEngine.bindTexture(ClientProps.BARNACLE_TENTACLE_PATH);
            
            t.startDrawingQuads();
            RenderUtils.addVertex(v1, 0.0, 0.0);
            RenderUtils.addVertex(v5, 0.0, length);
            RenderUtils.addVertex(v6, 1.0, length);
            RenderUtils.addVertex(v2, 1.0, 0.0);
        
            RenderUtils.addVertex(v2, 0.0, 0.0);
            RenderUtils.addVertex(v6, 0.0, length);
            RenderUtils.addVertex(v7, 1.0, length);
            RenderUtils.addVertex(v3, 1.0, 0.0);
        
            RenderUtils.addVertex(v3, 0.0, 0.0);
            RenderUtils.addVertex(v7, 0.0, length);
            RenderUtils.addVertex(v8, 1.0, length);
            RenderUtils.addVertex(v4, 1.0, 0.0);
        
            RenderUtils.addVertex(v4, 0.0, 0.0);
            RenderUtils.addVertex(v8, 0.0, length);
            RenderUtils.addVertex(v5, 1.0, length);
            RenderUtils.addVertex(v1, 1.0, 0.0);
        
            RenderUtils.addVertex(v8, 0.0, 0.0);
            RenderUtils.addVertex(v7, 0.0, 0.1);
            RenderUtils.addVertex(v6, 0.1, 0.1);
            RenderUtils.addVertex(v5, 0.1, 0.0);
            t.draw();
        }
        GL11.glColor3f(1.0F,1.0F,1.0F);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }


}

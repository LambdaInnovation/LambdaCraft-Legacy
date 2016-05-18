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

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import cn.liutils.api.client.util.RenderUtils;
import cn.weaponmod.api.weapon.WeaponGeneric;

/**
 * @author WeAthFolD
 * 
 */
public class RendererBulletWeapon extends RendererBulletWeaponBase {

    float width = 0.0625F;
    
    public RendererBulletWeapon(WeaponGeneric weapon, float w) {
        super(weapon);
        weaponType = weapon;
        width = w / 2F;
    }

    protected static void addVertex(Vec3 vec3, double texU, double texV) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
                texU, texV);
    }
    
    public static void renderMuzzleflashIn2d(Tessellator t, ResourceLocation texture,
            double tx, double ty, double tz, float size) {
        Vec3 
            a1 = RenderUtils.newV3(1.2, -0.4, -0.5), 
            a2 = RenderUtils.newV3(1.2, 0.4, -0.5),
            a3 = RenderUtils.newV3(1.2, 0.4, 0.3), 
            a4 = RenderUtils.newV3(1.2, -0.4, 0.3);

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
        GL11.glScalef(size, size, size);
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

    public static void renderMuzzleflashIn2d(Tessellator t, ResourceLocation texture,
            double tx, double ty, double tz) {
        renderMuzzleflashIn2d(t, texture, tx, ty, tz, 1.0F);
    }

    @Override
    protected void renderWeapon(ItemStack stack, EntityPlayer pl,
            ItemRenderType type) {
        Tessellator t = Tessellator.instance;

        GL11.glPushMatrix();
        RenderUtils.renderItemIn2d(stack, width);
        GL11.glPopMatrix();
    }

}

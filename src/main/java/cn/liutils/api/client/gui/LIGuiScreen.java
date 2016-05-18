/**
 * Copyright (C) Lambda-Innovation, 2013-2014
 * This code is open-source. Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 */
package cn.liutils.api.client.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cn.liutils.api.client.gui.part.LIGuiPart;
import cn.liutils.api.client.util.RenderUtils;

/**
 * 自建的简单GUIAPI。一般作为GUI类内部的一个对象单独使用。
 * @author WeAthFolD
 *
 */
public class LIGuiScreen {
    
    /**
     * Set of all GUI elements
     */
    private HashSet<LIGuiPage> activatePages = new HashSet();
    
    int xSize, ySize;
    
    private int width, height;
    
    float zLevel = -90;

    public LIGuiScreen(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
    }
    
    /**
     * 更新屏幕大小，请在绘制和侦听动作之前调用。
     */
    public void updateScreenSize(int w, int h) {
        width = w;
        height = h;
    }
    
    public Set<LIGuiPage> getActivePages() {
        return activatePages;
    }

    /**
     * 绘制pages和GUIElements，请务必在drawGui时调用。
     */
    public void drawElements(int mx, int my) {
        for(LIGuiPage pg : activatePages) {
            Iterator<LIGuiPart> parts = pg.getParts();
            float x0 = (width - xSize) / 2F;
            float y0 = (height - ySize) / 2F;
            
            
            GL11.glPushMatrix(); {
                GL11.glTranslatef(x0 + pg.originX, y0 + pg.originY, 0F);
                pg.drawPage();
            } GL11.glPopMatrix();
            
            while(parts.hasNext()) {
                LIGuiPart pt = parts.next();
                if(pt != null) {
                    IGuiTip tip = pt.tip;
                    //System.out.println("Tip:" + tip + " P " + e.name);
                    if(tip != null && isPointWithin(pt, pg, mx, my)) {
                        //System.out.println("Rendering tip : " + tip);
                        List<String> list = new ArrayList<String>();
                        list.add(tip.getHeader());
                        list.add(tip.getText());
                        this.drawHoveringText(list, mx, my,
                                Minecraft.getMinecraft().fontRenderer);
                    }
                    if(pt.doesDraw) {
                        drawElement(pg, pt, mx, my, x0, y0);
                    }
                }
            }
        }
    }
    
    private void drawElement(LIGuiPage page, LIGuiPart e, int x, int y, float x0, float y0) {
        GL11.glPushMatrix(); {
            GL11.glTranslatef(x0 + e.posX + page.originX, y0 + e.posY + page.originY, 0F);
            e.drawAtOrigin(x - x0 - page.originX - e.posX, y - y0 - page.originY - e.posY, isPointWithin(e, page, x, y));
        } GL11.glPopMatrix();
    }


    protected void mouseClicked(int par1, int par2, int par3) {
        for(LIGuiPage pg : activatePages) {
            Iterator<LIGuiPart> parts = pg.getParts();
            while(parts.hasNext()) {
                LIGuiPart pt = parts.next();
                checkElement(pt, pg, par1, par2, par3);
            }
        }
    }
    
    protected boolean isPointWithin(LIGuiPart element, LIGuiPage page, int x, int y) {
        float x0 = (this.width - this.xSize) / 2F,
            y0 = (this.height - this.ySize) / 2F;
        float epx = element.posX + x0 + page.originX, epy = element.posY + y0 + page.originY;
        return epx <= x && epy <= y && x <= epx + element.width && y <= epy + element.height;
    }
    
    private void checkElement(LIGuiPart b, LIGuiPage pg, int par1, int par2, int par3) {
        float x0 = (this.width - this.xSize) / 2F,
                y0 = (this.height - this.ySize) / 2F;
        float hitX = par1 - x0 - pg.originX, hitY = par2 - y0 - pg.originY;
        if (isPointWithin(b, pg, par1, par2)) {
            if(b.onPartClicked(hitX - b.posX, hitY - b.posY))
                pg.onPartClicked(b, hitX, hitY);
        }
    }
    
    protected void drawHoveringText(List p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font)
    {
        GL11.glPushMatrix(); {
            RenderUtils.pushTextureState();
            if (!p_146283_1_.isEmpty())
            {
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                RenderHelper.disableStandardItemLighting();
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                int k = 0;
                Iterator iterator = p_146283_1_.iterator();
    
                while (iterator.hasNext())
                {
                    String s = (String)iterator.next();
                    int l = font.getStringWidth(s);
    
                    if (l > k)
                    {
                        k = l;
                    }
                }
    
                int j2 = p_146283_2_ + 12;
                int k2 = p_146283_3_ - 12;
                int i1 = 8;
    
                if (p_146283_1_.size() > 1)
                {
                    i1 += 2 + (p_146283_1_.size() - 1) * 10;
                }
    
                if (j2 + k > this.width)
                {
                    j2 -= 28 + k;
                }
    
                if (k2 + i1 + 6 > this.height)
                {
                    k2 = this.height - i1 - 6;
                }
    
                this.zLevel = 300.0F;
                int j1 = -267386864;
                this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
                this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
                this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
                this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
                this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
                int k1 = 1347420415;
                int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
                this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
                this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
                this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
                this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);
    
                for (int i2 = 0; i2 < p_146283_1_.size(); ++i2)
                {
                    String s1 = (String)p_146283_1_.get(i2);
                    font.drawStringWithShadow(s1, j2, k2, -1);
    
                    if (i2 == 0)
                    {
                        k2 += 2;
                    }
    
                    k2 += 10;
                }
    
                this.zLevel = 0.0F;
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                RenderHelper.enableStandardItemLighting();
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            }
            RenderUtils.popTextureState();
            GL11.glColor4f(1F, 1F, 1F, 1F);
        } GL11.glPopMatrix();
    }
    
    protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = (par5 >> 24 & 255) / 255.0F;
        float f1 = (par5 >> 16 & 255) / 255.0F;
        float f2 = (par5 >> 8 & 255) / 255.0F;
        float f3 = (par5 & 255) / 255.0F;
        float f4 = (par6 >> 24 & 255) / 255.0F;
        float f5 = (par6 >> 16 & 255) / 255.0F;
        float f6 = (par6 >> 8 & 255) / 255.0F;
        float f7 = (par6 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex(par3, par2, this.zLevel);
        tessellator.addVertex(par1, par2, this.zLevel);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex(par1, par4, this.zLevel);
        tessellator.addVertex(par3, par4, this.zLevel);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}

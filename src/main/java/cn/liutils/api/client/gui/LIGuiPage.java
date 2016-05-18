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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import cn.liutils.api.client.gui.part.LIGuiPart;

/**
 * Page代表GUI中不同的子页面。它具有比GUI稍低的优先权。它允许划定一块自己的区域，并且在其中进行绘制、按钮/元素放置、侦听等操作。
 * Page的激活与否由GUI类控制。
 * @author WeAthFolD
 */
public abstract class LIGuiPage {
    
    String name;
    
    /**
     * X或Y原点坐标，代表对GUI原点（左上角）的位移。
     */
    public final float originX, originY;
    
    private Set<LIGuiPart> guiParts = new HashSet();
    
    protected GuiScreen myGui;
    
    public LIGuiPage(GuiScreen gui, String s, float oriX, float oriY) {
        name = s;
        originX = oriX;
        originY = oriY;
        myGui = gui;
        addElements(guiParts);
    }
    
    /**
     * 绘制页面的主要内容。
     */
    public abstract void drawPage();
    
    /**
     * 注册该page所有的元素。
     */
    public abstract void addElements(Set<LIGuiPart> set);
    
    /**
     * 按键侦听
     */
    public abstract void onPartClicked(LIGuiPart part, float subX, float subY);
    
    public String getUnlocalizedName() {
        return name;
    }
    
    public String getLocalizedName() {
        return StatCollector.translateToLocal(name);
    }
    
    public final Iterator<LIGuiPart> getParts() {
        return guiParts.iterator();
    }
    
    public static void drawString(FontRenderer font, String s, float x, float y, int color, float scale) {
        GL11.glPushMatrix(); {
            GL11.glTranslatef(x, y, 0);
            GL11.glScalef(scale, scale, scale);
            font.drawString(s, 0, 0, color);
        } GL11.glPopMatrix();
    }
    
}

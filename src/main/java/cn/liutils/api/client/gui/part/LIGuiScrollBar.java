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
package cn.liutils.api.client.gui.part;

import org.lwjgl.opengl.GL11;

import cn.liutils.api.client.util.HudUtils;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 *
 */


public class LIGuiScrollBar extends LIGuiPart {

    final float barWidth,
        barHeight;
    final int    barTexU,
        barTexV,
        barTexW,
        barTexH;
    
    LIGuiScrollerHorizonal scroller;
    
    /**
     * @param n
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public LIGuiScrollBar(String n, LIGuiScrollerHorizonal h2, float x, float y, float w, float h,
            float btw, float bth, int btu, int btv, int bttw, int btth) {
        super(n, x, y, w, h);
        scroller = h2;
        barWidth = btw;
        barHeight = bth;
        barTexU = btu;
        barTexV = btv;
        barTexW = bttw;
        barTexH = btth;
        doesDraw = true;
    }
    
    @Override
    public void drawAtOrigin(float mx, float my, boolean mouseHovering) {
        if(this.hasTexOverride()) RenderUtils.loadTexture(texOverride);
        GL11.glDepthFunc(GL11.GL_ALWAYS);
        float H = height - barHeight;
        float modifier = (float)scroller.progress / (scroller.maxProgress - 1);
        float h = H * modifier;
        HudUtils.drawTexturedModalRect(0, h, barTexU, barTexV, barWidth, barHeight, barTexW, barTexH);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
    }

}

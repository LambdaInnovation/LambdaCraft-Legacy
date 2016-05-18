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
public abstract class LIGuiProgressBar extends LIGuiPart {

    boolean isVertical = true;
    
    /**
     * @param n
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public LIGuiProgressBar(String n, float x, float y, float w, float h, int u, int v, int texW, int texH) {
        super(n, x, y, w, h);
        this.setTexSize(texW, texH);
        this.setTextureCoords(u, v);
    }
    
    public LIGuiProgressBar setVertical(boolean b) {
        isVertical = b;
        return this;
    }
    
    /**
     * from 0.0 to 1.0
     * @return
     */
    public abstract float getProgress();
    
    @Override
    public void drawAtOrigin(float mx, float my, boolean mouseHovering) {
        if(this.hasTexOverride())
            RenderUtils.loadTexture(texOverride);
        float f = getProgress();
        GL11.glColor4f(1F, 1F, 1F, 1F);
        if(isVertical)
            HudUtils.drawTexturedModalRect(0F, 0F, texU, texV, width * f, height, texWidth * f, texHeight);
        else {
            HudUtils.drawTexturedModalRect(0F, 0F, texU, texV, width, height * f, texWidth, texHeight * f);
        }
    }
    
    

}

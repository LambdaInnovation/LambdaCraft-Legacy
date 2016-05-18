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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.opengl.GL11;

import cn.liutils.api.client.gui.LIGuiPage;
import cn.liutils.api.client.util.HudUtils;
import cn.liutils.api.util.GenericUtils;

/**
 * @author WeAthFolD
 *
 */
public abstract class LIGuiScrollerHorizonal extends LIGuiPart {
    
    public static abstract class ScrollerEntry extends LIGuiPage {
        public int u, v, texWidth, texHeight;
        
        public ScrollerEntry(int u, int v, int texW, int texH) {
            super(null, "", 0, 0);
            this.u = u;
            this.v = v;
            this.texWidth = texW;
            this.texHeight = texH;
        }
        
        public void drawEntry(float width, float height, boolean mouseHovering) {
            HudUtils.drawTexturedModalRect(0, 0, u, v, width, height, texWidth, texHeight);
        }
        
        @Override
        public final void drawPage() {
            throw new UnsupportedOperationException();
        }
    }
    
    protected List<ScrollerEntry> entryList = new ArrayList();
    
    int progress = 0;
    int entriesPerPage;
    float entryHeight;
    int maxProgress;

    public LIGuiScrollerHorizonal(String n, float x, float y, float w, float h, float entryHeight) {
        super(n, x, y, w, h);
        this.entryHeight = entryHeight;
        entriesPerPage = (int) (this.height / entryHeight);
        doesDraw = true;
    }
    
    public void initList() {
        maxProgress = Math.max(0, entryList.size() - entriesPerPage + 1);
    }
    
    public void roll(boolean dir) {
        progress += dir ? 1 : -1;
        
        if(progress < 0) progress = 0;
        if(progress >= maxProgress) progress = maxProgress - 1;
    }
    
    private ScrollerEntry getEntry(int id) {
        return GenericUtils.safeFetchFrom(entryList, id);
    }
    
    @Override
    public boolean onPartClicked(float x, float y) {
        //System.out.println("OnPartClicked" + y);
        int id = (int) (y / entryHeight);
        if(id >= entriesPerPage) return true;
        id += progress;
        y %= entryHeight;
        ScrollerEntry ent = getEntry(id);
        if(ent != null) {
            Iterator<LIGuiPart> iter = ent.getParts();
            while(iter.hasNext()) {
                LIGuiPart pt = iter.next();
                if(x >= pt.posX && y >= pt.posY && x <= pt.posX + pt.width && y <= pt.posY + pt.height)
                    if(pt.onPartClicked(x, y))
                        ent.onPartClicked(pt, x, y);
            }
        }
        return true;
    }
    
    @Override
    public void drawAtOrigin(float mx, float my, boolean mouseHovering) {
        //Translate to Scroller-Relatived coords
        int id = (int) (my / entryHeight);
        id += progress;
        if(mx < 0 || mx >= this.width) id = -1;
        
        for(int i = 0; i < entriesPerPage; i++) {
            int x = i + progress;
            ScrollerEntry ent = getEntry(x);
            if(ent != null) {
                GL11.glPushMatrix(); {
                    GL11.glTranslatef(0F, i * entryHeight, 0F);
                    ent.drawEntry(width, entryHeight, x == id);
                } GL11.glPopMatrix();
            }
        }
    }

}

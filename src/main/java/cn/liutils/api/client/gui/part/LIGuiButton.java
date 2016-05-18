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
package cn.liutils.api.client.gui.part;

import cn.liutils.api.client.util.HudUtils;
import cn.liutils.api.client.util.RenderUtils;

/**
 * GUI按钮类，和CBCGuiContainer配合使用
 * 
 * @author WeAthFolD
 * 
 */
public class LIGuiButton extends LIGuiPart {

    /**
     * texture UVs.
     */
    public int downTexU, downTexV, invaildTexU, invaildTexV;
    
    public boolean isInvalid = false;

    public LIGuiButton(String name, float x, float y, float width, float height) {
        super(name, x, y, width, height);
    }

    /**
     * set texture coords when button is down.
     * @param u
     * @param v
     * @return 当前按钮
     */
    public LIGuiButton setDownCoords(int u, int v) {
        downTexU = u;
        downTexV = v;
        doesDraw = true;
        return this;
    }

    /**
     * set texture coords when button is invalid.
     * @param u
     * @param v
     * @return 当前按钮
     */
    public LIGuiButton setInvaildCoords(int u, int v) {
        invaildTexU = u;
        invaildTexV = v;
        doesDraw = true;
        return this;
    }
    
    @Override
    public boolean onPartClicked(float x, float y) {
        if(isInvalid) return false;
        return true;
    }
    
    @Override
    public void drawAtOrigin(float mx, float my, boolean mouseHovering) {
        int texU = 0, texV = 0;

        if (isInvalid) {
            texU = this.invaildTexU;
            texV = this.invaildTexV;
        } else if(mouseHovering) {
            texU = this.downTexU;
            texV = this.downTexV;
        } else {
            texU = this.texU;
            texV = this.texV;
        }
        
        if(this.hasTexOverride())
            RenderUtils.loadTexture(texOverride);
        HudUtils.drawTexturedModalRect(0F, 0F, texU, texV, width, height, texWidth, texHeight);
    }

}

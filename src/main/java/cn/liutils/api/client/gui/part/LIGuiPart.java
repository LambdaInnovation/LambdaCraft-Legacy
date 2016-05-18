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

import cn.liutils.api.client.gui.IGuiTip;
import cn.liutils.api.client.util.HudUtils;
import cn.liutils.api.client.util.RenderUtils;
import net.minecraft.util.ResourceLocation;

/**
 * LambdaCraft GUI元素。
 * 
 * @author WeAthFolD
 * 
 */
public class LIGuiPart {

    /**
     * 该GUI元素的名称。
     */
    public String name;

    /**
     * GUI元素位置。
     */
    public float posX, posY;

    /**
     * GUI元素的大小。
     */
    public float width, height;
    
    public float texWidth, texHeight;

    public int texU, texV;

    public boolean doesDraw;

    public IGuiTip tip;
    
    public ResourceLocation texOverride = null;

    /**
     * 
     */
    public LIGuiPart(String n, float x, float y, float w, float h) {
        name = n;
        posX = x;
        posY = y;
        texWidth = width = w;
        texHeight = height = h;
        doesDraw = false;
    }

    /**
     * 设置按钮是否被渲染。
     * 
     * @param b
     * @return
     */
    public LIGuiPart setDraw(boolean b) {
        this.doesDraw = b;
        return this;
    }

    /**
     * 设置GUI元素空闲时的U、V位置（像素，左上角）
     * 
     * @param u
     * @param v
     * @return 当前按钮
     */
    public LIGuiPart setTextureCoords(int u, int v) {
        texU = u;
        texV = v;
        doesDraw = true;
        return this;
    }
    
    public LIGuiPart setTextureOverride(ResourceLocation r) {
        texOverride = r;
        return this;
    }
    
    public LIGuiPart setTexSize(float u, float v) {
        texWidth = u;
        texHeight = v;
        return this;
    }
    
    public boolean hasTexOverride() {
        return texOverride != null;
    }
    
    /**
     * 当被点击时调用。返回false以阻止进一步的信息发送。
     * @return
     */
    public boolean onPartClicked(float x, float y) {
        return true;
    }

    /**
     * 判断是否拥有鼠标移上去时提示。
     * 
     * @return
     */
    public boolean hasToolTip() {
        return this.tip != null;
    }
    
    public LIGuiPart setTip(IGuiTip tip) {
        this.tip = tip;
        return this;
    }
    
    /**
     * 这里的mx和my是相对于part自身的坐标。
     * @param mx
     * @param my
     * @param mouseHovering
     */
    public void drawAtOrigin(float mx, float my, boolean mouseHovering) {
        if(this.hasTexOverride())
            RenderUtils.loadTexture(texOverride);
        HudUtils.drawTexturedModalRect(0F, 0F, texU, texV, width, height, texWidth, texHeight);
    }

}

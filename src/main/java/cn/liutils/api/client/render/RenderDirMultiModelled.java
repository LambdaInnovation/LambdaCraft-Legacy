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
package cn.liutils.api.client.render;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import cn.liutils.api.block.BlockDirectionedMulti;
import cn.liutils.api.client.model.ITileEntityModel;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 *
 */
public class RenderDirMultiModelled extends RenderTileDirMulti {
    
    protected ITileEntityModel theModel;
    protected boolean isTechne = false;

    /**
     * 
     */
    public RenderDirMultiModelled(ITileEntityModel mdl) {
        theModel = mdl;
    }
    
    public RenderDirMultiModelled setTechne(boolean b) {
        isTechne = b;
        return this;
    }
    
    @Override
    protected void renderAtOrigin(TileEntity te) {
        int meta = ((BlockDirectionedMulti)te.getBlockType())
                .getMetadata(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
        ResourceLocation tex = getTexture(te);
        Block blockType = te.getBlockType();
        if(tex != null) RenderUtils.loadTexture(tex);
        GL11.glPushMatrix(); {
            if(isTechne) {
                GL11.glScalef(-0.0625F, -0.0625F, 0.0625F);
            } else {
                GL11.glScalef(scale, scale, scale);
            }
            theModel.render(te, 0F, 0F);
        } GL11.glPopMatrix();
    }

}

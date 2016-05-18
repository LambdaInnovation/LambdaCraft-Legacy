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
package cn.liutils.api.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.IModelCustom;

/**
 * @author WeAthFolD
 *
 */
public class ItemModelCustom implements IItemModel {

    private IModelCustom theModel;
    
    /**
     * 
     */
    public ItemModelCustom(IModelCustom model) {
        theModel = model;
    }

    /* (non-Javadoc)
     * @see cn.liutils.api.client.model.IItemModel#render(net.minecraft.item.ItemStack, float, float)
     */
    @Override
    public void render(ItemStack is, float scale, float f) {
        GL11.glScalef(scale, scale, scale);
        theModel.renderAll();
    }

    /* (non-Javadoc)
     * @see cn.liutils.api.client.model.IItemModel#setRotationAngles(net.minecraft.item.ItemStack, double, double, double, float)
     */
    @Override
    public void setRotationAngles(ItemStack is, double posX, double posY,
            double posZ, float f) {
    }

}

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

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.IModelCustom;

/**
 * Simple wrapper of IModelCustom.
 * @author WeAthFolD
 *
 */
public class TileEntityModelCustom implements ITileEntityModel {
    
    IModelCustom theModel;

    /**
     * 
     */
    public TileEntityModelCustom(IModelCustom model) {
        theModel = model;
    }

    @Override
    public void render(TileEntity is, float f1, float f) {
        theModel.renderAll();
    }

    @Override
    public void renderPart(TileEntity te, String name, float f1, float f) {
        theModel.renderPart(name);
    }

}

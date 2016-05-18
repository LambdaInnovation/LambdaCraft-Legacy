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
package cn.liutils.api.block;

import cn.liutils.core.LIUtils;
import cn.liutils.core.network.MsgTileDirMulti;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Handles the metadata in TileEntity so we can bypass the byte-value size limit of blockMetadata.
 * @author WeAthFolD
 */
public class TileDirectionedMulti extends TileEntity implements IMetadataProvider {

    private boolean synced = false;
    private int ticksUntilReq = 4;
    int metadata = -1;
    
    @Override
    public void updateEntity() {
        if(metadata == -1) {
            metadata = this.getBlockMetadata();
        }
        if(worldObj.isRemote && !synced && ++ticksUntilReq == 5) {
            ticksUntilReq = 0;
            LIUtils.netHandler.sendToServer(new MsgTileDirMulti.Request(this));
        }
    }
    
    @Override
    public void setMetadata(int meta) {
        synced = true;
        metadata = meta;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        metadata = nbt.getInteger("meta");
        super.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("meta", metadata);
        super.writeToNBT(nbt);
    }

    @Override
    public int getMetadata() {
        if(metadata == -1)
            metadata = this.getBlockMetadata();
        return metadata;
    }

}

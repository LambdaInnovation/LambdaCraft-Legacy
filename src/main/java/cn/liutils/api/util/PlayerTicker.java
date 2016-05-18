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
package cn.liutils.api.util;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Simple wrapping of EntityPlayer NBT. Used for tick counting&checking.
 * @author WeAthFolD
 */
public class PlayerTicker {

    public static void updateTicker(EntityPlayer player, String channel) {
        player.getEntityData().setInteger("tick_" + channel, player.ticksExisted);
    }
    
    public static int getDeltaTick(EntityPlayer player, String channel) {
        int dt = player.getEntityData().getInteger("tick_" + channel) - player.ticksExisted;
        if(dt < 0) dt = 0;
        return dt;
    }
    
    public static int getDeltaTick(EntityPlayer player, String channel, int DEFAULT_VALUE) {
        int dt = player.getEntityData().getInteger("tick_" + channel) - player.ticksExisted;
        if(dt < 0) dt = DEFAULT_VALUE;
        return dt;
    }

}

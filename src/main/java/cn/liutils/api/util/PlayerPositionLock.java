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

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

/**
 * PlayerPositionLock is a class that interrupt player's normal movements and
 * take over it only in here.
 * Still developing.
 * @author WeAthFolD
 */
public class PlayerPositionLock {

    private static class Entry extends Pair<EntityPlayer, Motion3D> {

        public Entry(EntityPlayer k, Motion3D v) {
            super(k, v);
        }
        
    }
    
    static Set<Entry> 
        lockedPlayers_client = new HashSet(),
        lockedPlayers_server = new HashSet();
    
    public static void onTick(EntityPlayer player) {
        
        Entry ent = linearSearch(player);
        if(ent == null) return;
        
        if(ent.first.isDead) {
            unlockPlayer(ent.first);
            return;
        }
        ent.first.onGround = false;
        //System.out.println("[" + ent.first.posX + ", " + ent.first.posY + ", " + ent.first.posZ + "] TO \n"
        //        + "[" + ent.second.posX + ", " + ent.second.posY + ", " + ent.second.posZ + "]");
        
        ent.first.posX = ent.second.posX;
        ent.first.posY = ent.second.posY;
        ent.first.posZ = ent.second.posZ;
        ent.first.setPosition(ent.second.posX, ent.second.posY, ent.second.posZ);
        ent.first.motionX = ent.first.motionY = ent.first.motionZ = 0D;
    }
    
    public static void lockPlayer(EntityPlayer player) {
//        System.out.println("locked playersss");
        getSet(player.worldObj.isRemote).add(new Entry(player, 
                new Motion3D(player)));
        player.moveEntity(0D, 0.1D, 0D);
        player.onGround = false;
    }
    
    public static void lockPlayer(EntityPlayer player, Motion3D initPos) {
//        System.out.println("locked playerwww");
        getSet(player.worldObj.isRemote).add(new Entry(player, initPos));
    }

    public static void unlockPlayer(EntityPlayer player) {
        getSet(player.worldObj.isRemote).remove(linearSearch(player));
    }
    
    public static void movePlayerTo(EntityPlayer player, double x, double y, double z) {
        Entry ent = linearSearch(player);
        if(ent == null) return;
        ent.second.posX = x;
        ent.second.posY = y;
        ent.second.posZ = z;
    }
    
    public static void applyVelocity(EntityPlayer player, Vec3 vel) {
        Entry ent = linearSearch(player);
        if(ent == null) return;
//        System.out.println("SUCCESS");
        ent.second.posX += vel.xCoord;
        ent.second.posY += vel.yCoord;
        ent.second.posZ += vel.zCoord;
    }
    
    public static boolean isPlayerLocked(EntityPlayer player) {
        return linearSearch(player) != null;
    }
    
    private static Entry linearSearch(EntityPlayer player) {
        Set<Entry> set = getSet(player.worldObj.isRemote);
        for(Entry ent : set) {
            if(ent.first.equals(player))
                return ent;
        }
        return null;
    }
    
    private static Set<Entry> getSet(boolean isRemote) {
        return isRemote ? lockedPlayers_client : lockedPlayers_server;
    }
}

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
package cn.weaponmod.api.action;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
public class ActionBuckshot extends ActionShoot {

    int buck = 6;
    
    /**
     * @param dmg
     * @param snd
     */
    public ActionBuckshot(int dmg, String snd) {
        super(dmg, snd);
    }

    /**
     * @param dmg
     * @param scat
     * @param snd
     */
    public ActionBuckshot(int dmg, float scat, String snd) {
        super(dmg, scat, snd);
    }
    
    public ActionBuckshot setBucks(int i) {
        buck = i;
        return this;
    }
    
    @Override
    protected void doSpawnEntity(World world, EntityPlayer player) {
        for(int i = 0; i < buck; i++) {
            Entity bullet = getProjectileEntity(world, player);
            world.spawnEntityInWorld(bullet);
        }
    }

}

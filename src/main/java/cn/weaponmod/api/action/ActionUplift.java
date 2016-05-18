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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.core.client.UpliftHandler;
import cn.weaponmod.core.proxy.WMClientProxy;

/**
 * Weapon uplift action. Usually goes with ActionShoot.
 * Simple wrapper fot WMClientTickHandler.
 * @author WeAthFolD
 */
public class ActionUplift extends Action {
    
    private float
        uplift_radius = UpliftHandler.DEFAULT_UPLIFT_RADIUS,
        uplift_speed = UpliftHandler.DEFAULT_UPLIFT_SPEED,
        recover_speed = UpliftHandler.DEFAULT_RECOVER_SPEED,
        max_uplift = uplift_radius * 4.3F;
    
    public ActionUplift() {
        super(0, "uplift");
    }
    
    public ActionUplift(float uplift_rad, float uplift_spd, float recover_spd, float max_rad) {
        this();
        uplift_radius = uplift_rad;
        uplift_speed = uplift_spd;
        recover_speed = recover_spd;
        max_uplift = max_rad;
    }
    
    @Override
    public boolean onActionTick(World world, EntityPlayer player, InfWeapon inf) {
        return true;
    }
    
    @Override
    public boolean onActionEnd(World world, EntityPlayer player, InfWeapon information) {
        return false;
    }

    /* (non-Javadoc)
     * @see cn.weaponmod.api.action.Action#getPriority()
     */
    @Override
    public int getPriority() {
        return 0;
    }

    /* (non-Javadoc)
     * @see cn.weaponmod.api.action.Action#doesConcurrent(cn.weaponmod.api.action.Action)
     */
    @Override
    public boolean doesConcurrent(Action other) {
        return true;
    }
    
    @Override
    public boolean onActionBegin(World world, EntityPlayer player,
            InfWeapon information) {
        if(world.isRemote) {
            WMClientProxy.upliftHandler.setProperties(uplift_radius, uplift_speed, recover_speed, max_uplift);
            WMClientProxy.upliftHandler.doUplift();
        }
        return true;
    }

    @Override
    public int getRenderPriority() {
        return -1;
    }

}

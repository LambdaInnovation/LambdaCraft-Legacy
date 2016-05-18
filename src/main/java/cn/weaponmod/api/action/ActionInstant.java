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

/**
 * Instantanious action. Simple wrapping.
 * BTW: You usually don't have to create one instance at a time for those actions
 */
public abstract class ActionInstant extends Action {

    /**
     * @param ticks
     * @param name
     */
    public ActionInstant(String name) {
        super(0, name);
    }
    
    @Override
    public final boolean onActionBegin(World world, EntityPlayer player, InfWeapon information) { 
        return onActionExecute(world, player, information);
    }
    
    public abstract boolean onActionExecute(World world, EntityPlayer player, InfWeapon information);

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean doesConcurrent(Action other) {
        return false;
    }

}

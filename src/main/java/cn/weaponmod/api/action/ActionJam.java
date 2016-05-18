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
 * @author WeAthFolD
 *
 */
public class ActionJam extends Action {

    String sound = "";
    public int interval = 10; //jamsound every 0.5s
    
    public static final String TICKER_CHANNEL = "jam";
    
    /**
     * @param ticks
     * @param name
     */
    public ActionJam(int interval, String snd) {
        super(300, "jam");
        sound = snd;
        this.interval = interval;
    }

    /* (non-Javadoc)
     * @see cn.weaponmod.api.action.Action#getPriority()
     */
    @Override
    public int getPriority() {
        return 0;
    }
    
    @Override
    public boolean onActionBegin(World world, EntityPlayer player, InfWeapon inf) {
        player.playSound(sound, 0.5F, 1.0F);
        return true;
    }
    
    @Override
    public boolean onActionTick(World world, EntityPlayer player, InfWeapon inf) {
        int tt = inf.getTicker(TICKER_CHANNEL);
        if(tt == 0 || (inf.getTicksExisted() - tt) % interval == 0) {
            player.playSound(sound, 0.5F, 1.0F);
            inf.updateTicker(TICKER_CHANNEL);
        }
        return true;
    }

    /* (non-Javadoc)
     * @see cn.weaponmod.api.action.Action#doesConcurrent(cn.weaponmod.api.action.Action)
     */
    @Override
    public boolean doesConcurrent(Action other) {
        return true;
    }

    @Override
    public int getRenderPriority() {
        return -1;
    }

}

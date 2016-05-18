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
package cn.weaponmod.api.information;

import cn.weaponmod.api.action.ActionShoot;

/**
 * @author WeAthFOlD
 *
 */
public class InfUtils {

    public static int getMuzzleTick(InfWeapon information) {
        return getDeltaTick(information, ActionShoot.DEFAULT_TICKER_CHANNEL);
    }
    
    public static int getDeltaTick(InfWeapon information) {
        return information.ticksExisted - information.getLastActionTick();
    }
    
    public static int getDeltaTick(InfWeapon information, String channel) {
        return information.ticksExisted - information.getTicker(channel);
    }
}

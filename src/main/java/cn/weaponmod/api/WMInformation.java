/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.weaponmod.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cn.weaponmod.api.information.InfWeapon;

/**
 * Pool and processor of weapon informations.
 * Usual implementations don't need to invoke this, use the WeaponGeneral methods
 * @author WeAthFolD
 */
public class WMInformation {
    
    public static WMInformation instance = new WMInformation();
    private static WMInformation origin = instance;

    private Map<EntityPlayer, InfWeapon> infPool_client = new HashMap();
    private Map<EntityPlayer, InfWeapon> infPool_server = new HashMap();
    private final Random RNG = new Random();
    
    public synchronized InfWeapon getInformation(EntityPlayer player) {
        Map<EntityPlayer, InfWeapon> map = player.worldObj.isRemote ? infPool_client : infPool_server;
        InfWeapon inf = map.get(player);
        if(inf == null || inf.owner != player) {
//            WeaponMod.log.info("Created InfWeapon instance for " + player.getCommandSenderName()
//                    + " in " + player.worldObj.isRemote + " side");
            inf = new InfWeapon(player);
            map.put(player, inf);
        }
        return inf;
    }
    
    /**
     * Black techonology, don't use it!!
     */
    public synchronized static void switchPool(WMInformation fake) {
        instance = fake;
    }
    
    public synchronized static void restorePool() {
         instance = origin;
    }
    
    public void updateTick(World world) {
        Map<EntityPlayer, InfWeapon> pool = world.isRemote ? infPool_client : infPool_server;
        for(InfWeapon inf : pool.values()) {
            inf.updateTick();
        }
    }

}

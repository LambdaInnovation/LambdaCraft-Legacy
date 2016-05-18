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

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InfUtils;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.api.weapon.WeaponGenericBase;

/**
 * @author WeAthFolD
 *
 */
public class ActionMultipleReload extends Action {
    
    public String 
        reload_snd = "",
        finish_snd = "";
    
    public int rate = 10;

    /**
     * @param ticks
     * @param name
     */
    public ActionMultipleReload(int ticks, int maxTime) {
        super(maxTime, "reload_multiple");
        rate = ticks;
    }
    
    public ActionMultipleReload setSound(String s) {
        reload_snd = s;
        return this;
    }
    
    public ActionMultipleReload setSoundFinish(String s) {
        finish_snd = s;
        return this;
    }
    
    @Override
    public boolean onActionBegin(World world, EntityPlayer player, InfWeapon inf) { 
        ItemStack stack = player.getCurrentEquippedItem();
        if(stack == null || !(stack.getItem() instanceof WeaponGenericBase))
            return false;
        WeaponGenericBase wpn = (WeaponGenericBase) stack.getItem();
        if(wpn.getAmmo(stack) == stack.getMaxDamage() || !WeaponHelper.hasAmmo(wpn, player)) {
            return false;
        }
        inf.setLastActionTick();
        return true;
    }
    
    @Override
    public boolean onActionEnd(World world, EntityPlayer player, InfWeapon inf) {
        return false;
    }
    
    @Override
    public boolean onActionTick(World world, EntityPlayer player, InfWeapon inf) {
        
        if(inf.getTicksExisted() % this.rate == 0) {
            if(attemptReload(player, player.getCurrentEquippedItem())) {
                player.playSound(reload_snd, 0.5F, 1.0F);
                return true;
            } else {
                player.playSound(finish_snd, 0.5F, 1.0F);
                inf.removeAction(player, this);
            }
        }
        return true;
    }
    
    private boolean attemptReload(EntityPlayer player, ItemStack weapon) {
        WeaponGenericBase wpn = (WeaponGenericBase) weapon.getItem();
        if(player.capabilities.isCreativeMode) {
            wpn.setAmmo(weapon, wpn.getMaxDamage());
            return false;
        }
        if(wpn.getAmmo(weapon) == wpn.getMaxDamage()) return false;
        int i = WeaponHelper.consumeAmmo(player, wpn, 1);
        if(i == 1) return false;
        
        wpn.setAmmo(weapon, wpn.getAmmo(weapon) + 1);
        
        return true;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean doesConcurrent(Action other) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderPriority() {
        return 2;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void applyRenderEffect(World world, EntityPlayer player, InfWeapon inf, boolean first) {
        float prog = (float)InfUtils.getDeltaTick(inf) / this.rate;
        if(prog < 0.1F) {
            prog /= 0.1F;
        } else prog = 1F;
        GL11.glRotatef(80F * prog, 0F, 1F, 0F);
    }

}

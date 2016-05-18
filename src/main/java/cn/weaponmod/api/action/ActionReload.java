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
import cn.weaponmod.api.weapon.WeaponGeneric;
import cn.weaponmod.api.weapon.WeaponGenericBase;

/**
 * Action available only for WeaponGeneral subclasses.
 * @author WeAthFolD
 */
public class ActionReload extends Action {
    
    public String sound = "", soundFinish = "";
    
    public static ActionReload INSTANCE = new ActionReload(20, "", "");

    public ActionReload(int ticks, String snd, String sndFinish) {
        super(ticks, "reload");
        sound = snd;
        soundFinish = sndFinish;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean doesConcurrent(Action other) {
        return false; //高独占动作
    }
    
    @Override
    public boolean onActionBegin(World world, EntityPlayer player, InfWeapon inf) {
        ItemStack stack = player.getCurrentEquippedItem();
        if(stack == null || !(stack.getItem() instanceof WeaponGeneric))
            return false;
        player.playSound(sound, 0.5F, 1.0F);
        WeaponGeneric wpn = (WeaponGeneric) stack.getItem();
        return canReload(player, stack) && WeaponHelper.hasAmmo(stack.getItem(), player);
    }
    
    @Override
    public boolean onActionTick(World world, EntityPlayer player, InfWeapon inf) {
        return true;
    }
    
    @Override
    public boolean onActionEnd(World world, EntityPlayer player, InfWeapon inf) {
        ItemStack curItem = player.getCurrentEquippedItem();
        WeaponGenericBase wpnType = (WeaponGenericBase) curItem.getItem();
        int ammo = wpnType.getAmmo(curItem),
                maxAmmo = wpnType.getMaxDamage();
        if (ammo == maxAmmo) {
            return true;
        }
        int toConsume = maxAmmo - ammo;
        wpnType.setAmmo(curItem, ammo + toConsume - WeaponHelper.consumeAmmo(player, wpnType, toConsume));
        player.playSound(soundFinish, 0.5F, 1.0F);
        return true;
    }

    @Override
    public int getRenderPriority() {
        return 2;
    }
    
    protected boolean canReload(EntityPlayer player, ItemStack stack) {
        WeaponGeneric wpn = (WeaponGeneric) stack.getItem();
        int ammo = wpn.getAmmo(stack) ;
        return ammo < stack.getMaxDamage() && WeaponHelper.getAmmoCapacity(wpn, player.inventory) > 0;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void applyRenderEffect(World world, EntityPlayer player, InfWeapon inf, boolean first) {
        float prog = (float)InfUtils.getDeltaTick(inf) / this.maxTick;
        if(prog < 0.2f) {
            prog /= 0.2f;
        } else if(prog < 0.8f) {
            prog = 1.0f;
        } else {
            prog = (1 - prog) / 0.2f;
        }
        GL11.glRotatef(80F * prog, 0F, 1F, 0F);
    }

}

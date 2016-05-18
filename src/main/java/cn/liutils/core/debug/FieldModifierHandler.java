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
package cn.liutils.core.debug;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import cn.liutils.api.util.GenericUtils;

/**
 * @author WeAthFolD
 *
 */
public class FieldModifierHandler {
    
    private static int[] keyTicks = new int[6];

    public static List<IModifierProvider> all = new ArrayList();
    static {
        all.add(new ModifierProviderModelRender());
    }
    
    public static IModifierProvider currentProvider = null;
    public static int modifierID = 0;

    public FieldModifierHandler() {
    }
    
    public static void sendInfo(EntityPlayer player) {
        if(currentProvider == null) {
            player.addChatMessage(new ChatComponentTranslation("provider doesn't exist."));
        } else {
            for(FieldModifier f : currentProvider.getModifiers())
                player.addChatMessage(new ChatComponentTranslation(f.getName() + ": " + f.toInfo(currentProvider.extractInstance())));
        }
    }
    
    public static String getProviderName() {
        return currentProvider == null ?  "NULL" : currentProvider.getName();
    }
    
    public static void registerModifierProvider(IModifierProvider mod) {
        all.add(mod);
    }
    
    public static FieldModifier getCurModifier() {
        return currentProvider == null ? null : GenericUtils.safeFetchFrom(currentProvider.getModifiers(), modifierID);
    }
    
    public static boolean setProvider(int id) {
        IModifierProvider provider = GenericUtils.safeFetchFrom(all, id);
        if(provider != null) {
            currentProvider = provider;
            modifierID = 0;
            return true;
        }
        return false;
    }
    
    public static void reset(boolean dir, int dim) {
        keyTicks[getID(dir, dim)] = 0;
    }
    
    public static void directSet(String str) {
        FieldModifier modder = getCurModifier();
        Object instance = currentProvider.extractInstance();
        if(modder != null && instance != null) modder.directSet(instance, str);
    }
    
    public static void onKeyTick(boolean dir, int dim) {
        ++keyTicks[getID(dir, dim)];
        if(currentProvider != null) {
            FieldModifier modder = GenericUtils.safeFetchFrom(currentProvider.getModifiers(), modifierID);
            Object instance = currentProvider.extractInstance();
            try {
                //System.out.println("Aplying " + dir + " " + getKeyTick(dir, dim));
            if(modder != null && instance != null) 
                modder.applyModification(instance, dir, getKeyTick(dir, dim), 
                        dim >= modder.getRequiredDimensions() ? modder.getRequiredDimensions() - 1 : dim);
            } catch(Exception e) {
                System.err.println("Something bad occured while processing modification");
                e.printStackTrace();
            }
        }
    }
    
    private static int getKeyTick(boolean dir, int dim) {
        return keyTicks[getID(dir, dim)];
    }
    
    private static int getID(boolean dir, int dim) {
        return (dim << 1) + (dir ? 1 : 0);
    }

}

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

import cn.liutils.api.client.render.RenderModelItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;

/**
 * @author WeAthFolD
 *
 */
public class ModifierProviderModelRender implements IModifierProvider {

    /**
     * 
     */
    public ModifierProviderModelRender() {
        // TODO Auto-generated constructor stub
    }
    
    private static class ModVec3 extends Vec3Modifier {
        
        String my;

        public ModVec3(String name) throws NoSuchFieldException, SecurityException {
            super(RenderModelItem.class.getDeclaredField(name));
            my = name;
        }

        @Override
        public String getName() {
            return my;
        }
    }
    
    private static class ModDouble extends DoubleModifier {
        
        String my;
        
        public ModDouble(String name) throws NoSuchFieldException, SecurityException {
            super(RenderModelItem.class.getDeclaredField(name));
            my = name;
        }

        @Override
        public String getName() {
            return my;
        }
    }
    
    private static class ModVec2 extends Vec2fModifier {
        String my;
        
        public ModVec2(String name) throws NoSuchFieldException, SecurityException {
            super(RenderModelItem.class.getDeclaredField(name));
            my = name;
        }

        @Override
        public String getName() {
            return my;
        }
    }

    /* (non-Javadoc)
     * @see cn.liutils.core.debug.IModifierProvider#extractInstance()
     */
    @Override
    public Object extractInstance() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if(player != null) {
            ItemStack stack = player.getCurrentEquippedItem();
            if(stack != null) {
                IItemRenderer renderer = MinecraftForgeClient.getItemRenderer(stack, ItemRenderType.EQUIPPED_FIRST_PERSON);
                if(renderer != null && renderer instanceof RenderModelItem) {
                    return renderer;
                }
            }
        }
        return null;
    }
    
    private static List<FieldModifier> list = new ArrayList();
    static {
        try {
            list.add(new ModVec3("stdOffset"));
            list.add(new ModVec3("stdRotation"));
            list.add(new ModDouble("scale"));
            list.add(new ModVec3("equipOffset"));
            list.add(new ModVec3("equipRotation"));
            list.add(new ModDouble("invScale"));
            list.add(new ModVec2("invOffset").setScale(.2));
            list.add(new ModVec3("invRotation"));
        } catch(Exception e) {
            //NOPE
        }
    }
    
    

    /* (non-Javadoc)
     * @see cn.liutils.core.debug.IModifierProvider#getModifiers()
     */
    @Override
    public List<FieldModifier> getModifiers() {
        return list;
    }

    @Override
    public String getName() {
        return "Model Renderer";
    }

}

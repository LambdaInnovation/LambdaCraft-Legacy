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
package cn.weaponmod.api.client.render;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cn.liutils.api.client.model.IItemModel;
import cn.liutils.api.client.render.RenderModelItem;
import cn.weaponmod.api.weapon.WeaponGeneric;

/**
 * 
 * @author WeAthFolD
 *
 */
public class RendererModelBulletWeapon extends RendererBulletWeaponBase {
    
    public RenderModelItem mdlRenderer;
    
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        // TODO Auto-generated method stub
        switch (type) {
        case EQUIPPED:
        case EQUIPPED_FIRST_PERSON:
            return true;
        case ENTITY:
            return mdlRenderer.renderEntityItem;
        default:
            return false;
        }

    }
    
    @Override
    public void renderEntityItem(RenderBlocks a, EntityItem b) {
        mdlRenderer.renderEntityItem(a, b);
    }
    
    /**
     * @param mdl
     * @param texture
     */
    public RendererModelBulletWeapon(IItemModel mdl, WeaponGeneric type, ResourceLocation texture) {
        super(type);
        mdlRenderer = new RenderModelItem(mdl, texture);
        setInvRotation(0F, 0F, 37.5F);
    }
    
    public RendererModelBulletWeapon setInformationFrom(RendererModelBulletWeapon a) {
        mdlRenderer.setInformationFrom(a.mdlRenderer);
        return this;
    }
    
    public RendererModelBulletWeapon setRenderInventory(boolean b) {
        mdlRenderer.setRenderInventory(b);
        return this;
    }
    
    public RendererModelBulletWeapon setRenderEntityItem(boolean b) {
        mdlRenderer.setRenderEntityItem(b);
        return this;
    }
    
    public RendererModelBulletWeapon setInventorySpin(boolean b) {
        mdlRenderer.setInventorySpin(b);
        return this;
    }
    
    public RendererModelBulletWeapon setStdRotation(float x, float y, float z) {
        mdlRenderer.setStdRotation(x, y, z);
        return this;
    }
    
    public RendererModelBulletWeapon setEquipRotation(float x, float y, float z) {
        mdlRenderer.setEquipRotation(x, y, z);
        return this;
    }
    
    public RendererModelBulletWeapon setInvRotation(float x, float y, float z) {
        mdlRenderer.setInvRotation(x, y, z);
        return this;
    }
    
    public RendererModelBulletWeapon setEntityItemRotation(float b0, float b1, float b2) {
        mdlRenderer.setEntityItemRotation(b0, b1, b2);
        return this;
    }
    
    public RendererModelBulletWeapon setScale(double s) {
        mdlRenderer.setScale(s);
        return this;
    }
    
    public RendererModelBulletWeapon setInvScale(double s) {
        mdlRenderer.setInvScale(s);
        return this;
    }
    
    public RendererModelBulletWeapon setOffset(float offsetX, float offsetY, float offsetZ) {
        mdlRenderer.setOffset(offsetX, offsetY, offsetZ);
        return this;
    }
    
    public RendererModelBulletWeapon setInvOffset(float offsetX, float offsetY) {
        mdlRenderer.setInvOffset(offsetX, offsetY);
        return this;
    }
    
    public RendererModelBulletWeapon setEquipOffset(double b0, double b1, double b2) {
        mdlRenderer.setEquipOffset(b0, b1, b2);
        return this;
    }

    @Override
    protected void renderWeapon(ItemStack stack, EntityPlayer pl,
            ItemRenderType type) {
        mdlRenderer.renderEquipped(stack, null, pl, type);
    }

}

/**
 * 
 */
package cn.weaponmod.api.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.weapon.WeaponDualWield;
import cn.weaponmod.api.weapon.WeaponGeneric;
import cn.weaponmod.core.proxy.WMClientProxy;

/**
 * @author WeathFolD
 *
 */
public class RendererDualWield extends RendererBulletWeaponBase {

    RendererBulletWeaponBase template;

    public RendererDualWield(WeaponGeneric type, RendererBulletWeaponBase template) {
        super(type);
        this.template = template;
    }
    
    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
        case EQUIPPED:
            template.renderEquipped(item, (RenderBlocks) data[0], (EntityLivingBase) data[1], type);
            break;
        case EQUIPPED_FIRST_PERSON:
            GL11.glPushMatrix();
            GL11.glTranslated(0, 0, -1.95);
            GL11.glScalef(1, 1, -1);
            template.renderEquipped(item, (RenderBlocks) data[0], (EntityLivingBase) data[1], type);
            GL11.glPopMatrix();
            renderRight(item, data[0], data[1]);
            break;
        case ENTITY:
            template.renderEntityItem((RenderBlocks)data[0], (EntityItem)data[1]);
            break;
        default:
            break;
        }

    }

    private synchronized void renderRight(ItemStack item, Object... data) {
        WMInformation.switchPool(WeaponDualWield.fakePool);
        WMClientProxy.switchUpl(WeaponDualWield.fakeUpl);
        GL11.glPushMatrix();
        template.renderEquipped(item, (RenderBlocks) data[0], (EntityLivingBase) data[1],  ItemRenderType.EQUIPPED_FIRST_PERSON);
        GL11.glPopMatrix();
        WMInformation.restorePool();
        WMClientProxy.restoreUpl();
    }
    
    @Override
    protected void renderWeapon(ItemStack stack, EntityPlayer pl,
            ItemRenderType type) {}

}

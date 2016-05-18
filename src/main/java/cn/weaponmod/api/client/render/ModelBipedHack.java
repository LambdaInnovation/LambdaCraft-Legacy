/**
 * 
 */
package cn.weaponmod.api.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.information.InfUtils;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.api.weapon.WeaponGeneric;

/**
 * @author WeathFolD
 *
 */
public class ModelBipedHack extends ModelBiped {

    public ModelBipedHack() {
    }

    public ModelBipedHack(float par1) {
        super(par1);
    }

    public ModelBipedHack(float par1, float par2, int par3, int par4) {
        super(par1, par2, par3, par4);
    }
    
    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity ent)
    {    
        EntityPlayer player = (EntityPlayer) ent;
        InfWeapon inf = WMInformation.instance.getInformation(player);
        ItemStack st1 = inf.getLastStack(),
                st2 = player.getCurrentEquippedItem();
        if(st1 != null && st2 != null && st1.getItem() == st2.getItem() && (st1.getItem() instanceof WeaponGeneric)) {
            WeaponGeneric weapon = (WeaponGeneric) st1.getItem();
            if(inf.isActionPresent(weapon.actionShoot.name) || InfUtils.getDeltaTick(inf, weapon.actionShoot.name) < 40)
                this.aimedBow = true;
        }
        
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, ent);
    }

}

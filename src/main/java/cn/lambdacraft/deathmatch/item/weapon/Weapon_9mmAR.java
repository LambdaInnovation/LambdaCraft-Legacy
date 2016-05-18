package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entity.EntityARGrenade;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionShoot;
import cn.weaponmod.api.information.InfWeapon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 9mm Assault Rifle class. Mode I : Bullet, II : AR Grenade.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_9mmAR extends Weapon_9mmAR_Raw {
    
    public static Action actionGrenade = new ActionShoot(0, 0, "lambdacraft:weapons.glauncher") {
        
        {
            setShootRate(17);
            ticker_channel = "grenade";
        }
        
        @Override
        protected boolean consumeAmmo(EntityPlayer player, ItemStack stack, int amount) {
            return WeaponHelper.consumeInventoryItem(player.inventory.mainInventory, CBCItems.ammo_argrenade, amount) == 0;
        }
        
        @Override
        protected Entity getProjectileEntity(World world, EntityPlayer player) {
            return world.isRemote ? null : new EntityARGrenade(world, player);
        }
        
    };

    public Weapon_9mmAR() {
        super();
    }
    
    @Override
    public void onItemClick(World world, EntityPlayer player, ItemStack stack, int keyid) {
        super.onItemClick(world, player, stack, keyid);
        if(keyid == 1) {
            InfWeapon inf = loadInformation(player);
            inf.executeAction(actionGrenade);
        }
    }
    
    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World,
            Entity par3Entity, int par4, boolean par5) {
        super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    }
    

    @Override
    @SideOnly(Side.CLIENT)
    public IHudTip[] getHudTip(ItemStack itemStack, EntityPlayer player) {
        IHudTip[] tips = new IHudTip[2];
        tips[1] = new IHudTip() {

            @Override
            public IIcon getRenderingIcon(ItemStack itemStack,
                    EntityPlayer player) {
                if(ammoItem != null){
                    return ammoItem.getIconIndex(itemStack);
                }
                return null;
            }

            @Override
            public String getTip(ItemStack itemStack, EntityPlayer player) {
                return getAmmo(itemStack) + "|" + WeaponHelper.getAmmoCapacity(ammoItem, player.inventory);
            }

            @Override
            public int getTextureSheet(ItemStack itemStack) {
                return itemStack.getItemSpriteNumber();
            }
            
        };
        
        tips[0] = new IHudTip() {

            @Override
            public IIcon getRenderingIcon(ItemStack itemStack,
                    EntityPlayer player) {
                return CBCItems.ammo_argrenade.getIconIndex(itemStack);
            }

            @Override
            public String getTip(ItemStack itemStack, EntityPlayer player) {
                return String.valueOf(WeaponHelper.getAmmoCapacity(CBCItems.ammo_argrenade, player.inventory));
            }

            @Override
            public int getTextureSheet(ItemStack itemStack) {
                return itemStack.getItemSpriteNumber();
            }
            
        };
        return tips;
    }
    
}

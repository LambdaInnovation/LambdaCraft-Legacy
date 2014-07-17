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
import cn.weaponmod.api.information.InformationBullet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 9mm Assault Rifle class. Mode I : Bullet, II : AR Grenade.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_9mmAR extends Weapon_9mmAR_Raw {

	public Weapon_9mmAR() {
		super();
	}

	@Override
	public boolean canShoot(EntityPlayer player, ItemStack is, boolean side) {
		InformationBullet inf = (InformationBullet) this.getInformation(is, player.worldObj);
		return side ? super.canShoot(player, is, side) : (player.capabilities.isCreativeMode || WeaponHelper.hasAmmo(CBCItems.ammo_argrenade, player));
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
	protected Entity getBulletEntity(ItemStack stack, World world, EntityPlayer player, boolean left) {
		return left ? super.getBulletEntity(stack, world, player, left) : world.isRemote ? null : new EntityARGrenade(world, player);
	}

	@Override
	public String getSoundShoot(boolean left) {
		return left ? "lambdacraft:weapons.hksa" : (itemRand.nextFloat() * 2 > 1 ? "lambdacraft:weapons.glauncher" : "lambdacraft:weapons.glauncherb");
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 4 : 20;
	}
	
	@Override
	public boolean onConsumeAmmo(ItemStack stack, World world, EntityPlayer player, InformationBullet inf, boolean left) {
		if(!left) {
			WeaponHelper.consumeInventoryItem(player.inventory.mainInventory, CBCItems.ammo_argrenade, 1);
			return true;
		}
		return false;
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
				return (itemStack.getMaxDamage() - Weapon_9mmAR.this.getWpnStackDamage(itemStack)  - 1) + "|" + WeaponHelper.getAmmoCapacity(ammoItem, player.inventory);
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

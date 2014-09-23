package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.api.util.GenericUtils;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionBuckshot;
import cn.weaponmod.api.action.ActionMultipleReload;
import cn.weaponmod.core.proxy.WMClientProxy;

public class Weapon_Shotgun extends WeaponGeneralBullet_LC {

	public Weapon_Shotgun() {

		super(CBCItems.ammo_shotgun);

		setIAndU("weapon_shotgun");
		setMaxDamage(9);
		setCreativeTab(CBCMod.cct);

	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4,
				par5);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer,
				par4);
	}
	
	public Action getActionShoot() {
		return new ActionBuckshot(2, 5, "lambdacraft:weapons.sbarrela").setShootRate(20);
	}
	
	public Action getActionReload() {
		return new ActionMultipleReload(15, 300).setSound("lambdacraft:weapons.reload");
	}

}

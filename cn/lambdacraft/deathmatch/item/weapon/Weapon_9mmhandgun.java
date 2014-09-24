package cn.lambdacraft.deathmatch.item.weapon;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_9mmAR.ActionGrenade;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.information.InfWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * 9mm Handgun. Mode I : low speed, high accuracy Mode II : high speed, low
 * accuracy
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_9mmhandgun extends Weapon_9mmhandgun_Raw {

	public Weapon_9mmhandgun() {
		super();
	}
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, int keyid) {
		InfWeapon inf = loadInformation(stack, player);
		switch(keyid) {
		case 0: //LMOUSE
			if(!canShoot(player, stack)) {
				inf.executeAction(player, getActionJam());
			} else {
				inf.executeAction(player, this.getActionShoot());
			}
			break;
		case 1:
			//RMOUSE
			inf.executeAction(player, new ActionAutomaticShoot(300, 7, 3, 5, "lambdacraft:weapons.plgun_c")
				.setMuzzleflash(ClientProps.MUZZLEFLASH));
			break;
		case 2: //Reload
			inf.executeAction(player, this.getActionReload());
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onItemRelease(World world, EntityPlayer pl, ItemStack stack,
			int keyid) {
		InfWeapon inf = loadInformation(stack, pl);
		switch(keyid) {
		case 0: //LMOUSE
		case 1: //RMOUSE
			inf.removeAction(pl, "shoot_auto");
			break;
		default:
			break;
		}
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

}

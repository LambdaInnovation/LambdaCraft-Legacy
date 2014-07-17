package cn.lambdacraft.deathmatch.item.weapon;

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

	@Override
	public int getShootTime(boolean left) {
		return  left ? 10 : 5;
	}

	@Override
	public int getOffset(boolean left) {
		return left ? 1 : 8;
	}

}

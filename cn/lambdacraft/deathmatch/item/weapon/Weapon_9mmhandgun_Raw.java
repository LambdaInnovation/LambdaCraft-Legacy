package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;

public class Weapon_9mmhandgun_Raw extends WeaponGeneralBullet_LC {

	public Weapon_9mmhandgun_Raw() {

		super(CBCItems.ammo_9mm);

		setUnlocalizedName("weapon_9mmhandgun");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(18);
		iconName = "weapon_9mmhandgun";
		setNoRepair();

		setReloadTime(60);
		setJamTime(10);
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
	public String getSoundShoot(boolean left) {
		return "lambdacraft:weapons.plgun_c";
	}

	@Override
	public String getSoundJam(boolean left) {
		return "lambdacraft:weapons.gunjam_a";
	}

	@Override
	public String getSoundReload() {
		return "lambdacraft:weapons.nmmclipa";
	}

	@Override
	public int getShootTime(boolean left) {
		return  left ? 10 : 0;
	}

	@Override
	public int getWeaponDamage(boolean left) {
		return 3;
	}

	@Override
	public int getOffset(boolean left) {
		return 1;
	}

}

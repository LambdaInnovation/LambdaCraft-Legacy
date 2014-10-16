package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.action.ActionJam;
import cn.weaponmod.api.action.ActionReload;
import cn.weaponmod.api.action.ActionShoot;

public class Weapon_9mmhandgun_Raw extends WeaponGenericLC {

	public Weapon_9mmhandgun_Raw() {

		super(CBCItems.ammo_9mm);

		setUnlocalizedName("weapon_9mmhandgun");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(18);
		iconName = "weapon_9mmhandgun";
		setNoRepair();
		
		actionShoot = new ActionAutomaticShoot(300, 10, 2, 5, "lambdacraft:weapons.plgun_c")
		.setMuzzleflash(ClientProps.MUZZLEFLASH);
		actionReload = new ActionReload(35, "lambdacraft:weapons.nmmclipa", "");
		actionJam = new ActionJam(10, "lambdacraft:weapons.gunjam_a");
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

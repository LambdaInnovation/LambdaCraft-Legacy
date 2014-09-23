package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionJam;
import cn.weaponmod.api.action.ActionReload;
import cn.weaponmod.api.action.ActionShoot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * .357 Magnum weapon class.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_357 extends WeaponGeneralBullet_LC {

	public Weapon_357() {

		super(CBCItems.ammo_357);

		setIAndU("weapon_357");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(7);
		setNoRepair();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:weapon_357");
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4,
				par5);
	}
	
	public Action getActionShoot() {
		return new ActionShoot(7, 5, "lambdacraft:weapons.pyt_shota").setShootRate(20);
	}
	
	public Action getActionReload() {
		return new ActionReload(70, "lambdacraft:weapons.pyt_reloada", "");
	}
	
	public Action getActionJam() {
		return new ActionJam(20, "lambdacraft:weapons.gunjam_a");
	}

}
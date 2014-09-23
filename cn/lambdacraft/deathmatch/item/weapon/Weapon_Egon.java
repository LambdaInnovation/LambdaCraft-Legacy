package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.ISpecialCrosshair;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entity.EntityBulletEgon;
import cn.lambdacraft.deathmatch.entity.fx.EntityEgonRay;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionJam;
import cn.weaponmod.api.information.InfWeapon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Egon energy weapon.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_Egon extends WeaponGeneralBullet_LC implements ISpecialCrosshair {

	public static String SND_WINDUP = "lambdacraft:weapons.egon_windup",
			SND_RUN = "lambdacraft:weapons.egon_run", SND_OFF = "lambdacraft:weapons.egon_off";

	public IIcon iconEquipped;

	public class ActionEgonShoot extends Action {
		
		//shootRate = 2

		public ActionEgonShoot() {
			super(200, "");
		}
		
		public boolean onActionBegin(World world, EntityPlayer player, InfWeapon information) { 
			//Spawn egon
			return false;
		}
		
		public boolean onActionTick(World world, EntityPlayer player, InfWeapon inf) {
			//Continous entityBullet && Sound
			return false;
		}

		@Override
		public int getPriority() {
			return 0;
		}

		@Override
		public boolean doesConcurrent(Action other) {
			return false;
		}

		@Override
		public int getRenderPriority() {
			return 0;
		}
		
	}
	
	public Weapon_Egon() {
		super(CBCItems.ammo_uranium);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("weapon_egon");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		iconEquipped = reg.registerIcon("lambdacraft:weapon_egon0");
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world,
			EntityPlayer player, int par4) {
		if (canShoot(player, stack))
			world.playSoundAtEntity(player, SND_OFF, 0.5F, 1.0F);
	}
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, int keyid) {
		InfWeapon inf = loadInformation(stack, player);
		switch(keyid) {
		case 0: //LMOUSE
			if(!canShoot(player, stack)) {
				//Sound playing
				inf.executeAction(player, getActionJam());
			} else {
				inf.executeAction(player, this.getActionShoot());
			}
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
			inf.removeAction(pl, name_shoot);
			break;
		default:
			break;
		}
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
	public Action getActionShoot() {
		return new ActionEgonShoot();
	}

	@Override
	public Action getActionJam() {
		return new ActionJam(20, "cbc.weapons.gunjam_a");
	}

	@Override
	public int getHalfWidth() {
		return 8;
	}

	@Override
	public int getCrosshairID(ItemStack is) {
		return -1;
	}

}

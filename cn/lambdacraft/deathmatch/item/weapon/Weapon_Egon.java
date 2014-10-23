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
import cn.liutils.api.entity.EntityBullet;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.action.ActionJam;
import cn.weaponmod.api.action.ActionShoot;
import cn.weaponmod.api.action.ActionUplift;
import cn.weaponmod.api.information.InfUtils;
import cn.weaponmod.api.information.InfWeapon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Egon energy weapon.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_Egon extends WeaponGenericLC implements ISpecialCrosshair {

	public static String
		SND_WINDUP = "lambdacraft:weapons.egon_windup",
		SND_RUN = "lambdacraft:weapons.egon_run",
		SND_OFF = "lambdacraft:weapons.egon_off";

	public IIcon iconEquipped;
	
	private class ActionShooter extends ActionShoot {

		public ActionShooter() {
			super(10, 2, "");
		}
		
		protected Entity getProjectileEntity(World world, EntityPlayer player) {
			return world.isRemote ? null : new EntityBulletEgon(world, player);
		}
		
	}
	
	private class ActionEgonShoot extends ActionAutomaticShoot {

		public ActionEgonShoot() {
			super(new ActionShooter(), 4, 300);
		}
		
		@Override
		public boolean onActionBegin(World world, EntityPlayer player, InfWeapon information) {
			world.spawnEntityInWorld(new EntityEgonRay(world, player));
			return super.onActionBegin(world, player, information);
		}
		
		@Override
		public boolean onActionTick(World world, EntityPlayer player, InfWeapon inf) {
			int ticks = inf.getTickLeft(this);
			if(ticks == 1) {
				player.playSound(SND_WINDUP, 0.5F, 1.0F);
			} else if((ticks - 79) % 43 == 0) {
				player.playSound(SND_RUN, 0.5F, 1.0F);
			}
			return super.onActionTick(world, player, inf);
		}
		
		@Override
		public boolean onActionEnd(World world, EntityPlayer player, InfWeapon information) {
			player.playSound(SND_OFF, 0.5F, 1.0F);
			return false;
		}
		
	}
	
	public Weapon_Egon() {
		super(CBCItems.ammo_uranium);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("weapon_egon");
		iconName = "weapon_egon";
		
		actionUplift = new ActionUplift(0, 0, 0, 0);
		actionShoot = new ActionEgonShoot();
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
				inf.executeAction(player, actionJam);
			} else {
				inf.executeAction(player, actionShoot);
			}
			break;
		case 1: //RMOUSE, MISTY ACTION
			if(InfUtils.getDeltaTick(inf, "nico") >= 20) {
				inf.updateTicker("nico");
				player.playSound(SND_OFF, 0.5F, 1.0F);
			}
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
			inf.removeAction(actionShoot.name);
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
	public int getHalfWidth() {
		return 8;
	}

	@Override
	public int getCrosshairID(ItemStack is) {
		return -1;
	}

}

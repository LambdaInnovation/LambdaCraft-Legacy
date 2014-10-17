/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.deathmatch.entity.EntityHornet;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.action.ActionJam;
import cn.weaponmod.api.action.ActionReload;
import cn.weaponmod.api.action.ActionShoot;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.core.event.ItemControlHandler;

/**
 * @author WeAthFolD.
 * 
 */
public class Weapon_Hornet extends WeaponGenericLC {
	
	public static class HornetShoot extends ActionShoot {

		boolean side;
		
		public HornetShoot(boolean b) {
			super(0, 0, "lambdacraft:weapons.ag_firea");
			side = b;
			setShootRate(b ? 5 : 3);
		}
		
		protected Entity getProjectileEntity(World world, EntityPlayer player) {
			return world.isRemote ? null : new EntityHornet(world, player, side);
		}
		
	}

	public static final int RECOVER_TIME = 10;
	
	private Action actionAutomShoot;

	public Weapon_Hornet() {
		super(null);
		setMaxDamage(8);
		setCreativeTab(CBCMod.cct);
		setIAndU("weapon_hornet");
		actionShoot = new ActionAutomaticShoot(new HornetShoot(true), 5, 300);
		actionAutomShoot = new ActionAutomaticShoot(new HornetShoot(false), 3, 300);
		actionJam = new ActionJam(20, "");
		actionReload = null;
	}
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, int keyid) {
		InfWeapon inf = loadInformation(player);
		super.onItemClick(world, player, stack, keyid);
		if(keyid == 1) {
			inf.executeAction(actionAutomShoot);
		}
	}
	
	@Override
	public void onItemRelease(World world, EntityPlayer pl, ItemStack stack,
			int keyid) {
		super.onItemRelease(world, pl, stack, keyid);
		if(keyid == 1)
			loadInformation(pl).removeAction("shoot_auto");
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		InfWeapon inf = onWpnUpdate(par1ItemStack, par2World,
				par3Entity, par4, par5);
		if (inf == null)
			return;
		
		int dt = inf.ticksExisted;
		
		EntityPlayer player = (EntityPlayer) par3Entity;
		
		if (dt % RECOVER_TIME == 0 
				&& !ItemControlHandler.isKeyDown(player, 0) 
				&& !ItemControlHandler.isKeyDown(player, 1)) {
			if (this.getAmmo(par1ItemStack) < this.getMaxDamage()) {
				this.setAmmo(par1ItemStack, this.getAmmo(par1ItemStack) + 1);
			}
		}
	}
}

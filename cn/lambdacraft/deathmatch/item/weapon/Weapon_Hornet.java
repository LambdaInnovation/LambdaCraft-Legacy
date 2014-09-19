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
import cn.weaponmod.api.action.ActionReload;
import cn.weaponmod.api.action.ActionShoot;
import cn.weaponmod.api.information.InfWeapon;

/**
 * @author WeAthFolD.
 * 
 */
public class Weapon_Hornet extends WeaponGeneralBullet_LC {
	
	public static class HornetShoot extends ActionShoot {

		boolean side;
		
		public HornetShoot(boolean b) {
			super(0, 0, "lambdacraft:weapons.ag_firea");
			side = b;
			setShootRate(b ? 5 : 3);
		}
		
		protected Entity getProjectileEntity(World world, EntityPlayer player) {
			return new EntityHornet(world, player, side);
		}
		
	}

	public static final int RECOVER_TIME = 10;

	public Weapon_Hornet() {
		super(null);
		setMaxDamage(9);
		setCreativeTab(CBCMod.cct);
		setIAndU("weapon_hornet");
	}
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, int keyid) {
		InfWeapon inf = loadInformation(stack, player);
		super.onItemClick(world, player, stack, keyid);
		if(keyid == 1) {
			inf.executeAction(player, new HornetShoot(false));
		}
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
		
		if (dt % RECOVER_TIME == 0 && !(this.canShoot(player, par1ItemStack) && !inf.isActionPresent("shoot"))) {
			if (this.getAmmo(par1ItemStack) < this.getMaxDamage()) {
				this.setAmmo(par1ItemStack, this.getAmmo(par1ItemStack) + 1);
			}
		}
	}
	
	@Override
	public Action getActionReload() {
		return new ActionReload(0, "", "");
	}
	
	@Override
	public Action getActionShoot() {
		return new HornetShoot(true);
	}

}

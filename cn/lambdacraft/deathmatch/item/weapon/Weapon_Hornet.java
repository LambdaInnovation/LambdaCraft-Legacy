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
import cn.lambdacraft.core.LCMod;
import cn.lambdacraft.deathmatch.entity.EntityHornet;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.events.ItemControlHandler;

/**
 * @author WeAthFolD.
 * 
 */
public class Weapon_Hornet extends WeaponGeneralBullet_LC {

	public static final int RECOVER_TIME = 10;

	public Weapon_Hornet() {
		super(null);
		setMaxDamage(9);
		setCreativeTab(LCMod.cct);
		setIAndU("weapon_hornet");
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		InformationBullet inf = (InformationBullet) onWpnUpdate(par1ItemStack, par2World,
				par3Entity, par4, par5);
		if (inf == null)
			return;
		int dt = inf.ticksExisted;
		EntityPlayer player = (EntityPlayer) par3Entity;
		if (dt % RECOVER_TIME == 0 && !(this.canShoot(player, par1ItemStack, true) && (ItemControlHandler.getUsingTickLeft(player, true) > 0) || ItemControlHandler.getUsingTickLeft(player, false) > 0)) {
			if (this.getWpnStackDamage(par1ItemStack) > 0) {
				this.setWpnStackDamage(par1ItemStack, this.getWpnStackDamage(par1ItemStack) - 1);
			}
		}
	}
	
	@Override
	protected Entity getBulletEntity(ItemStack is, World world, EntityPlayer player, boolean left) {
		return world.isRemote ? null : new EntityHornet(world, player, left);
	}

	@Override
	public void onBulletWpnReload(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information) {}
	
	@Override
	public int getWeaponDamage(boolean left) {
		return 4;
	}

	@Override
	public int getOffset(boolean left) {
		return 0;
	}

	@Override
	public String getSoundShoot(boolean left) {
		int random = (int) (itemRand.nextFloat() * 3);
		return random == 0 ? "lambdacraft:weapons.ag_firea"
				: (random == 1 ? "lambdacraft:weapons.ag_fireb"
						: "lambdacraft:weapons.ag_firec");
	}

	@Override
	public String getSoundJam(boolean left) {
		return "";
	}

	@Override
	public String getSoundReload() {
		return "";
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 5 : 3;
	}

}

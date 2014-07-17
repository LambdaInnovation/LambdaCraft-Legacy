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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.deathmatch.item.weapon;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.core.LCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entity.EntityRocket;
import cn.lambdacraft.deathmatch.util.InformationRPG;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.events.ItemControlHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_RPG_Raw extends WeaponGeneralBullet_LC {


	public Weapon_RPG_Raw() {
		super(CBCItems.ammo_rpg);

		setIAndU("weapon_rpg");
		setCreativeTab(LCMod.cct);
		this.hasSubtypes = true;

		setJamTime(20);
		setLiftProps(20, 2);
		this.reloadTime = 45;
	}
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {

		InformationRPG inf = (InformationRPG) super.onWpnUpdate(
				par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information, boolean left) {
		
		information.setLastTick(left);
		if (this.canShoot(par3Entity, par1ItemStack, left)) {
			if(!par2World.isRemote) {
				par2World.playSoundAtEntity(par3Entity, getSoundShoot(left), 0.5F,
						1.0F);
				par2World.spawnEntityInWorld(new EntityRocket(par2World,
						par3Entity, par1ItemStack));
				WeaponHelper.consumeAmmo(par3Entity, this, 1);
			}
			ItemControlHandler.stopUsingItem(par3Entity, true);
			ItemControlHandler.stopUsingItem(par3Entity, false);
			information.isReloading = true;
			information.setLastTick(false);
			information.setLastTick(true);
		}
	}
	
	@Override
	public void onBulletWpnReload(ItemStack stack, World world, EntityPlayer entity, InformationBullet inf) {
		//DO NOTHING
		inf.isReloading = false;
		inf.setLastTick(false);
	}

	@Override
	public boolean canShoot(EntityPlayer player, ItemStack is, boolean left) {
		return WeaponHelper.hasAmmo(this, player) || player.capabilities.isCreativeMode;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer,
				par4);
	}

	@Override
	public String getSoundShoot(boolean side) {
		return "lambdacraft:weapons.rocketfire";
	}

	@Override
	public String getSoundJam(boolean side) {
		return "lambdacraft:weapons.gunjam_a";
	}

	@Override
	public String getSoundReload() {
		return "";
	}

	@Override
	public int getShootTime(boolean side) {
		return side ? 40 : 0;
	}

	@Override
	public int getWeaponDamage(boolean side) {
		return 0;
	}

	@Override
	public int getOffset(boolean side) {
		return 0;
	}

	@Override
	public InformationRPG getInformation(ItemStack itemStack, World world) {
		return (InformationRPG)WMInformation.getInformation(itemStack, world);
	}

	@Override
	public InformationRPG loadInformation(ItemStack stack,
			EntityPlayer player) {

		InformationRPG inf = getInformation(stack, player.worldObj);
		if (inf != null)
			return inf;
		inf = new InformationRPG(stack);
		WMInformation.register(stack, inf, player.worldObj);
		return inf;

	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IHudTip[] getHudTip(ItemStack itemStack, EntityPlayer player) {
		IHudTip[] tips = new IHudTip[1];
		tips[0] = new IHudTip() {

			@Override
			public IIcon getRenderingIcon(ItemStack itemStack,
					EntityPlayer player) {
				if(ammoItem != null){
					return ammoItem.getIconIndex(itemStack);
				}
				return null;
			}

			@Override
			public String getTip(ItemStack itemStack, EntityPlayer player) {
				return String.valueOf(WeaponHelper.getAmmoCapacity(ammoItem, player.inventory));
			}

			@Override
			public int getTextureSheet(ItemStack itemStack) {
				return itemStack.getItemSpriteNumber();
			}
			
		};
		return tips;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * 获取上弹时武器的旋转角度（0.0F~1.0F)
	 * @param itemStack
	 * @return
	 */
	public float getRotationForReload(ItemStack itemStack) {
		InformationBullet inf = (InformationBullet) WMInformation.getInformation(itemStack, true);
		int dt = inf.getDeltaTick(false);
		float changeTime = reloadTime / 5F;
		float rotation = 1.0F;
		if (dt < changeTime/2F) {
			rotation = MathHelper.sin((dt / changeTime) * 2F * (float) Math.PI * 0.5F);
		} else if (dt > reloadTime - changeTime) {
			rotation = MathHelper.sin((reloadTime - dt) / changeTime
					* (float) Math.PI * 0.5F);
		}
		return rotation;
	}

}

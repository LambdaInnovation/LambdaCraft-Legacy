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
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entity.EntityRPGDot;
import cn.lambdacraft.deathmatch.entity.EntityRocket;
import cn.liutils.api.entity.EntityBullet;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionDummy;
import cn.weaponmod.api.action.ActionJam;
import cn.weaponmod.api.action.ActionReload;
import cn.weaponmod.api.action.ActionShoot;
import cn.weaponmod.api.feature.IModdable;
import cn.weaponmod.api.weapon.WeaponGeneral;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_RPG_Raw extends WeaponGeneralBullet_LC {

	public static class ActionRocketShoot extends ActionShoot {

		public ActionRocketShoot() {
			super(0, "lambdacraft:weapons.rocketfire");
			setShootRate(40);
		}
		
		@Override
		protected Entity getProjectileEntity(World world, EntityPlayer player) {
			ItemStack is = player.getCurrentEquippedItem();
			return world.isRemote ? null : new EntityRocket(world, player, is);
		}
		
		@Override
		protected boolean consumeAmmo(EntityPlayer player, ItemStack stack, int amount) {
			return WeaponHelper.consumeInventoryItem(player.inventory.mainInventory, CBCItems.ammo_rpg, 1) == 0;
		}
		
	}
	

	public Weapon_RPG_Raw() {
		super(CBCItems.ammo_rpg);

		setIAndU("weapon_rpg");
		setCreativeTab(CBCMod.cct);
		this.hasSubtypes = true;
		
		actionReload = new ActionReload(40, "", "");
		actionJam = new ActionJam(40, "lambdacraft:weapons.gunjam_a");
		actionShoot = new ActionRocketShoot();
	}
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}

	@Override
	public boolean canShoot(EntityPlayer player, ItemStack is) {
		return WeaponHelper.hasAmmo(this, player) || player.capabilities.isCreativeMode;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer,
				par4);
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

}

package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.api.util.GenericUtils;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.proxy.WMClientProxy;

public class Weapon_Shotgun extends WeaponGeneralBullet_LC {

	public Weapon_Shotgun(int par1) {

		super(par1, CBCItems.ammo_shotgun.itemID);

		setIAndU("weapon_shotgun");
		setMaxDamage(9);
		setCreativeTab(CBCMod.cct);
		setReloadTime(10);
		setJamTime(20);
		setLiftProps(18F, 1.5F);

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
	public void onBulletWpnReload(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationBullet information) {
		int dmg = getWpnStackDamage(par1ItemStack);
		if (dmg <= 0) {
			information.setLastTick(false);
			information.isReloading = false;
			return;
		}

		this.setWpnStackDamage(par1ItemStack,dmg  - 1 + WeaponHelper.consumeAmmo(player, this, 1));
		if(getWpnStackDamage(par1ItemStack) <= 0 || WeaponHelper.getAmmoCapacity(ammoID, player.inventory) == 0) {
			information.isReloading = false;
			player.playSound(this.getSoundReloadFinish(), 0.5F, 1.0F);
		} else {
			player.playSound(this.getSoundReload(), 0.5F, 1.0F);
			information.isReloading = true;
		}
		information.setLastTick(false);
		return;
	}

	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information, boolean left) {
		int count = left ? 8 : 16;
		for (int i = 0; i < count; i++) {
			par2World.spawnEntityInWorld(new EntityBullet(par2World, par3Entity, getWeaponDamage(left), left ? 5 : 10));
		}
		if (!par2World.isRemote) {
			par2World.playSoundAtEntity(par3Entity, getSoundShoot(left), 0.5F, 1.0F);
		} else 
			WMClientProxy.cth.setUplift(left ? 1.8F : 3.0F);

		if (par3Entity instanceof EntityPlayer) {
			if (!par3Entity.capabilities.isCreativeMode) {
				int dmg = this.getWpnStackDamage(par1ItemStack) + (left ? 1 : 2);
				this.setWpnStackDamage(par1ItemStack, dmg);
			}
		}

		information.setLastTick_Shoot(left);
		information.setMuzzleTick(left);
	}

	@Override
	public String getSoundShoot(boolean left) {
		return left ? "lambdacraft:weapons.sbarrela" : "lambdacraft:weapons.sbarrelb";
	}

	@Override
	public String getSoundJam(boolean left) {
		return "lambdacraft:weapons.scocka";
	}

	@Override
	public String getSoundReload() {
		return GenericUtils.getRandomSound("lambdacraft:weapons.reload", 3);
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 20 : 35;
	}

	@Override
	public int getWeaponDamage(boolean left) {
		return 2;
	}

	@Override
	public int getOffset(boolean left) {
		return left ? 8 : 17;
	}
	
	@Override
	public float getRotationForReload(ItemStack itemStack) {
		InformationBullet inf = (InformationBullet)WMInformation.getInformation(itemStack, true);
		int dt = inf.getDeltaTick(false), dt2 = inf.getDeltaTick(true);
		float changeTime = reloadTime / 5F;
		float rotation = 1.0F;
		if(dt2 < 10 || dt2 > this.reloadTime * 7)
		if(dt < changeTime) {
			rotation = MathHelper.sin(dt / changeTime * (float)Math.PI * 0.5F);
		} else if(dt > reloadTime - changeTime) {
			rotation = MathHelper.sin((reloadTime - dt) / changeTime * (float)Math.PI * 0.5F);
		}
		return rotation;
	}

}

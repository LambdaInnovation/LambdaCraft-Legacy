package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.ISpecialCrosshair;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entity.EntityBulletGauss;
import cn.lambdacraft.deathmatch.entity.EntityBulletGaussSec;
import cn.lambdacraft.deathmatch.entity.EntityBulletGaussSec.EnumGaussRayType;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.api.util.GenericUtils;
import cn.liutils.api.util.Motion3D;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionJam;
import cn.weaponmod.api.action.ActionShoot;
import cn.weaponmod.api.information.InfUtils;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.core.event.ItemControlHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 喜闻乐见的Gauss！有穿墙和击飞效果喔~
 * @author WeAthFolD
 *
 */
public class Weapon_Gauss extends WeaponGeneralBullet_LC implements ISpecialCrosshair {

	
	public static String SND_CHARGE_PATH = "lambdacraft:weapons.gauss_charge",
			SND_CHARGEA_PATH[] = { "lambdacraft:weapons.gauss_windupa",
					"lambdacraft:weapons.gauss_windupb", "lambdacraft:weapons.gauss_windupc",
					"lambdacraft:weapons.gauss_windupd" },
			SND_SHOOT_PATH = "lambdacraft:weapons.gaussb";
	
	public class ActionCharge extends ActionShoot {
		
		final int strengh;
		
		public ActionCharge(int str) {
			super(0, "charge");
			strengh = str;
		}
		
		@Override
		public Entity getProjectileEntity(World world, EntityPlayer player) {
			return new EntityBulletGauss(world, player, player.getCurrentEquippedItem(), strengh);
		}
	}

	public Weapon_Gauss() {
		super(CBCItems.ammo_uranium);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("weapon_gauss");
		
		actionShoot = new ActionShoot(0, this.SND_SHOOT_PATH) {
			protected Entity getProjectileEntity(World world, EntityPlayer player) {
				return new EntityBulletGaussSec(EnumGaussRayType.NORMAL, world, player, player.getCurrentEquippedItem(), null, null, 8);
			}
		};
		actionJam = new ActionJam(20, "lambdacraft:weapons.gunjam_a");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:weapon_gauss");
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {

		InfWeapon inf = onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		
		if (inf == null)
			return;
		EntityPlayer player = (EntityPlayer) par3Entity;
		/*
		if(!player.isUsingItem() && inf.rotationVelocity > 0)
			inf.rotationVelocity -= 0.5;
		inf.rotationAngle += inf.rotationVelocity;*/
	}

	public void onChargeModeUpdate(InfWeapon inf,
			ItemStack par1ItemStack, World par2World, EntityPlayer player,
			int par4, boolean par5) {
		/*TODO:Transfer to ActionCharge
		final int OVER_CHARGE_LIMIT = 160;
		final int CHARGE_TIME_LIMIT = 41;

		int ticksChange = InfUtils.getDeltaTick(inf, "charge");
		inf.chargeTime++;
		
		if(inf.rotationVelocity <= 15)
			inf.rotationVelocity += 1;

		Boolean canUse = this.canShoot(player, par1ItemStack, false);
		Boolean ignoreAmmo = player.capabilities.isCreativeMode;

		if (canUse && inf.chargeTime < CHARGE_TIME_LIMIT)
			inf.charge++;

		if (!canUse)
			player.stopUsingItem();

		if (!ignoreAmmo && inf.ticksExisted <= CHARGE_TIME_LIMIT
				&& inf.chargeTime % 4 == 0)
			WeaponHelper.consumeAmmo(player, this, 1);

		// OverCharge!
		if (inf.chargeTime > OVER_CHARGE_LIMIT) {
			player.stopUsingItem();
			player.playSound(GenericUtils.getRandomSound("cbc.weapons.electro", 3), 0.5F, 1.0F);
			player.attackEntityFrom(DamageSource.causeMobDamage(player), 10);
			inf.resetState();
		}

		int i = -1;
		if (inf.ticksExisted == 9) 
			i = 1;
		else if (inf.ticksExisted == 18) 
			i = 2;
		else if (inf.ticksExisted == 27) 
			i = 3;
		if (i > 0) 
			par2World.playSoundAtEntity(player, SND_CHARGEA_PATH[i], 0.5F, 1.0F);

		if (inf.chargeTime >= 30 && inf.chargeTime % 15 == 0) {
			inf.setLastTick(false);
			par2World.playSoundAtEntity(player, SND_CHARGE_PATH, 0.5F, 1.0F);
		}
	*/
	}
	
	public void onItemRelease(World world, EntityPlayer player, ItemStack stack, int keyid) {
		super.onItemRelease(world, player, stack, keyid);
		
		InfWeapon inf = loadInformation(stack, player);
		if (inf == null || keyid != 0)
			return;

		// Do the charge attack part
		int charge = (InfUtils.getDeltaTick(inf) > 60 ? 60 : InfUtils.getDeltaTick(inf));
		if (charge <= 6)
			return;
		int damage = charge * 2 / 3;
		double vel = charge / 14.0;

		Motion3D var0 = new Motion3D(player, true);
		double dx = var0.motionX * vel, dy = var0.motionY * vel, dz = var0.motionZ
				* vel;

		player.addVelocity(-dx, -dy, -dz);
		if (!world.isRemote) {
			world.playSoundAtEntity(player, SND_SHOOT_PATH, 0.5F,
					1.0F);
			world.spawnEntityInWorld(new EntityBulletGauss(world, player, stack, damage));
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 500;
	}

	@Override
	public int getHalfWidth() {
		return 8;
	}

	@Override
	public int getCrosshairID(ItemStack itemStack) {
		return -1;
	}
	
	@SideOnly(Side.CLIENT)
	public float getRotationForStack(ItemStack stack, EntityPlayer ent) {
		return this.loadInformation(stack, ent).infData.getInteger("charge");
	}

}

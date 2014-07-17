package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.ISpecialCrosshair;
import cn.lambdacraft.core.LCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entity.EntityBulletGauss;
import cn.lambdacraft.deathmatch.entity.EntityBulletGaussSec;
import cn.lambdacraft.deathmatch.entity.EntityBulletGaussSec.EnumGaussRayType;
import cn.liutils.api.util.GenericUtils;
import cn.liutils.api.util.Motion3D;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.events.ItemControlHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 喜闻乐见的Gauss！有穿墙和击飞效果喔~
 * @author WeAthFolD
 *
 */
public class Weapon_Gauss extends WeaponGeneralEnergy_LC implements ISpecialCrosshair {

	
	public static String SND_CHARGE_PATH = "lambdacraft:weapons.gauss_charge",
			SND_CHARGEA_PATH[] = { "lambdacraft:weapons.gauss_windupa",
					"lambdacraft:weapons.gauss_windupb", "lambdacraft:weapons.gauss_windupc",
					"lambdacraft:weapons.gauss_windupd" },
			SND_SHOOT_PATH = "lambdacraft:weapons.gaussb";

	public Weapon_Gauss() {
		super(CBCItems.ammo_uranium);
		setCreativeTab(LCMod.cct);
		setUnlocalizedName("weapon_gauss");
		setJamTime(20);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:weapon_gauss");
	}
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, boolean left) {
		super.onItemClick(world, player, stack, left);
		InformationEnergy inf = loadInformation(stack, player);
		if (left) {
			inf.rotationVelocity = 15.0F;
		} else {
			inf.resetState();
			if (canShoot(player, stack, left))
				world.playSoundAtEntity(player, SND_CHARGEA_PATH[0], 0.5F, 1.0F);
		}
	}
	

	@Override
	public void onItemUsingTick(World world, EntityPlayer player, ItemStack stack, boolean type, int tickLeft) {
    	InformationEnergy inf = loadInformation(stack, player);
    	super.onItemUsingTick(world, player, stack, type, tickLeft);
		if(!type)
			onChargeModeUpdate(inf, stack, player.worldObj, player, 0, true);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {

		InformationEnergy inf = (InformationEnergy) onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		
		if (inf == null)
			return;
		EntityPlayer player = (EntityPlayer) par3Entity;
		if(!player.isUsingItem() && inf.rotationVelocity > 0)
			inf.rotationVelocity -= 0.5;
		inf.rotationAngle += inf.rotationVelocity;
	}

	@Override
	public boolean doesShoot(InformationEnergy inf, EntityPlayer player, ItemStack itemStack, boolean side) {
		return side && super.doesShoot(inf, player, itemStack, side);
	}

	public void onChargeModeUpdate(InformationEnergy inf,
			ItemStack par1ItemStack, World par2World, EntityPlayer player,
			int par4, boolean par5) {

		final int OVER_CHARGE_LIMIT = 160;
		final int CHARGE_TIME_LIMIT = 41;

		int ticksChange = inf.getDeltaTick(false);
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

	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, int par4) {
		InformationEnergy inf = getInformation(par1ItemStack, par2World);
		boolean left = ItemControlHandler.getUsingTickLeft(player, true) > 0;
		if (inf == null)
			return;

		if (left) {
			super.onPlayerStoppedUsing(par1ItemStack, par2World,
					player, par4);
			return;
		}

		// Do the charge attack part
		int charge = (inf.charge > 60 ? 60 : inf.charge);
		if (charge <= 6)
			return;
		int damage = charge * 2 / 3;
		double vel = charge / 14.0;

		Motion3D var0 = new Motion3D(player, true);
		double dx = var0.motionX * vel, dy = var0.motionY * vel, dz = var0.motionZ
				* vel;

		player.addVelocity(-dx, -dy, -dz);
		if (!par2World.isRemote) {
			par2World.playSoundAtEntity(player, SND_SHOOT_PATH, 0.5F,
					1.0F);
			par2World.spawnEntityInWorld(new EntityBulletGauss(par2World, player, par1ItemStack, damage));
		}

	}

	@Override
	public void onEnergyWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationEnergy information, boolean side) {
		if(!par2World.isRemote)
			par2World.spawnEntityInWorld(new EntityBulletGaussSec(EnumGaussRayType.NORMAL, par2World, player,
				par1ItemStack, null, null, getWeaponDamage(side)));
		par2World.playSoundAtEntity(player, getSoundShoot(side), 0.5F, 1.0F);
		WeaponHelper.consumeAmmo(player, this, 2);
		information.setLastTick(side);
		return;
	}
	
	@SideOnly(Side.CLIENT)
	public float getRotationForStack(ItemStack is, EntityLivingBase living) {
		if(!(living instanceof EntityPlayer))
			return 0.0F;
		InformationEnergy inf = loadInformation(is, (EntityPlayer) living);
		if(inf == null)
			return 0;
		return inf.rotationAngle / 0.01745329252F;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 500;
	}

	@Override
	public int getShootTime(boolean left) {
		return 5;
	}

	@Override
	public String getSoundShoot(boolean left) {
		return left ? SND_SHOOT_PATH : "";
	}

	@Override
	public String getSoundJam(boolean left) {
		return "lambdacraft:weapons.gunjam_a";
	}

	@Override
	public int getWeaponDamage(boolean left) {
		return 8;
	}

	@Override
	public int getOffset(boolean left) {
		return 0;
	}

	@Override
	public int getHalfWidth() {
		return 8;
	}

	@Override
	public int getCrosshairID(ItemStack itemStack) {
		return -1;
	}

}

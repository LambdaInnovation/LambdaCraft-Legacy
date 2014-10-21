package cn.lambdacraft.deathmatch.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cn.lambdacraft.deathmatch.entity.EntityBulletGaussSec.EnumGaussRayType;
import cn.lambdacraft.deathmatch.entity.fx.EntityGaussRay;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Gauss;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.api.util.Motion3D;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.api.weapon.WeaponGenericBase;

/**
 * 高斯枪蓄力射击的判断实体。
 * 
 * @author WeAthFolD
 */
public class EntityBulletGauss extends EntityBullet {

	private static final float CHARGE_DAMAGE_SCALE = 2.0F;

	private Weapon_Gauss item;
	private InfWeapon inf;
	protected ItemStack itemStack;

	public enum EnumSubPlain {
		PLAIN_X, PLAIN_Y, PLAIN_Z;
	}

	public EntityBulletGauss(World world) {
		super(world);
	}

	public EntityBulletGauss(World par1World, EntityPlayer entityPlayer, int damage) {
		super(par1World, entityPlayer, damage);
		motion = new Motion3D(entityPlayer, true);
		itemStack = entityPlayer.getCurrentEquippedItem();
		
		if(itemStack == null) { //for safety
			System.out.println("Unexpected error in " + par1World.isRemote);
			this.setDead();
			return;
		}
		
		item = (Weapon_Gauss) itemStack.getItem();
		inf = item.loadInformation(entityPlayer);
		worldObj.spawnEntityInWorld(new EntityGaussRay(new Motion3D(this, true), worldObj));
		if (par1World.isRemote)
			this.setDead();
	}

	@Override
	public void entityInit() {
	}

	@Override
	protected void onImpact(MovingObjectPosition par1) {
		if (worldObj.isRemote)
			return;
		switch (par1.typeOfHit) {
		case BLOCK:
			doBlockCollision(par1);
			this.setDead();
			break;

		case ENTITY:
			doEntityCollision(par1);
			break;
		default:
			break;
		}

	}

	@Override
	protected void doBlockCollision(MovingObjectPosition result) {
		doWallPenetrate(result);
	}

	@Override
	public void doEntityCollision(MovingObjectPosition result) {
		doChargeAttack(result, inf, item);
	}

	/**
	 * 进行蓄力射击的穿墙。
	 * 
	 * @param result
	 *            RayTrace result.
	 */
	private void doWallPenetrate(MovingObjectPosition result) {

		if (result.sideHit == -1)
			return;

		// do reflection and decay damage
		int damage = doReflection(result);

		int blockFront = getFrontBlockCount(result);

		if (blockFront >= 3) // Too thick,cannot penetrate
			return;
		worldObj.spawnEntityInWorld(new EntityBulletGaussSec(EnumGaussRayType.PENETRATION, worldObj,
				getThrower(), itemStack, result, motion, damage));

		double radius = Math.round(0.3 * damage); // Max: 5.28
		damage = (int) Math.round(damage * (1.0 - 0.33 * blockFront)); // Decay
																		// based
																		// on
																		// blockFront

		AxisAlignedBB box = getPenetratingBox(radius, result);
		if (box == null)
			return;

		List var1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, box);
		Entity var2;

		for (int i = 0; i < var1.size(); i++) {
			var2 = (Entity) var1.get(i);
			if (result.entityHit == null)
				continue;
			if (!(result.entityHit instanceof EntityLiving
					|| result.entityHit instanceof EntityDragonPart || result.entityHit instanceof EntityEnderCrystal))
				continue;
			// Calculate distance & damage, damage entities.
			double distance = Math.sqrt(Math.pow(
					(result.hitVec.xCoord - var2.posX), 2)
					+ Math.pow((result.hitVec.yCoord - var2.posY), 2)
					+ Math.pow((result.hitVec.zCoord - var2.posZ), 2));
			int dmg = (int) Math
					.round((damage
							* Math.pow(1.0 - distance / (radius * 1.732), 2) * CHARGE_DAMAGE_SCALE));
			double var0 = dmg / 20;
			double dx = motion.motionX * var0, dy = motion.motionY * var0, dz = motion.motionZ
					* var0;
			WeaponHelper.doEntityAttack(result.entityHit,
					DamageSource.causeMobDamage(getThrower()), dmg, dx, dy, dz);
		}

	}

	/**
	 * @param result
	 *            RayTrace result
	 * @return 衰减后的伤害
	 */
	private int doReflection(MovingObjectPosition result) {

		double sin = getSinBySideAndMotion(result.sideHit);
		if (sin == -2)
			return getChargeDamage();
		double sin45 = 0.7071067812;
		int damage = 0;
		if (-sin45 < sin && sin < sin45) {
			damage = (int) Math.round(Math.sqrt(1 - sin * sin)
					* getChargeDamage());
			worldObj.spawnEntityInWorld(new EntityBulletGaussSec(EnumGaussRayType.REFLECTION, worldObj,
					getThrower(), itemStack, result, motion, damage));
		}
		return getChargeDamage() - damage;

	}

	/**
	 * 实际进行蓄力伤害计算。
	 * 
	 * @param result
	 * @param information
	 * @param item
	 */
	private void doChargeAttack(MovingObjectPosition result,
			InfWeapon information, WeaponGenericBase item) {

		int damage = getChargeDamage();
		double var0 = damage / 20;
		double dx = motion.motionX * var0, dy = motion.motionY * var0, dz = motion.motionZ
				* var0;
		if (!(result.entityHit instanceof EntityLivingBase
				|| result.entityHit instanceof EntityDragonPart || result.entityHit instanceof EntityEnderCrystal))
			return;
		WeaponHelper.doEntityAttack(result.entityHit, DamageSource.causeMobDamage(getThrower()), damage, dx, dy, dz);

	}

	/**
	 * 获得RayTrace结果方向前的方块数。(粗略估算)
	 */
	private int getFrontBlockCount(MovingObjectPosition result) {

		Vec3 side = getSideByMotion();
		return 1 + (worldObj.getBlock(result.blockX + (int) side.xCoord,
				result.blockY + (int) side.yCoord, result.blockZ
						+ (int) side.zCoord) != Blocks.air ? 1 : 0);

	}

	/**
	 * Get player rough viewing side by calculating its rotationPitch and
	 * rotationYaw. returns: int[0]:X direction facing; int[1] : Y direction
	 * facing; int[2] : Z direction facing; value : +1, -1, 0
	 */
	private Vec3 getSideByMotion() {

		int dx = 0, dy, dz = 0;
		if (rotationPitch > -45 && rotationPitch < 45) {
			dy = 0;
		} else if (rotationPitch > -65 && rotationPitch < 65) {
			dy = rotationPitch > 0 ? -1 : 1;
		} else {
			dy = rotationPitch > 0 ? -2 : 2;
		}
		if (2 != Math.abs(dy)) {
			if (rotationYaw > 315 || rotationYaw < 45)
				dz = 1;
			else if (rotationYaw < 225 || rotationYaw > 135)
				dz = -1;
			else
				dx = rotationYaw < 180 ? -1 : 1;
		} else
			dy = (dy > 0 ? 1 : -1);

		return Vec3.createVectorHelper(dx, dy, dz);
	}

	/**
	 * 获取高斯穿墙计算伤害的碰撞箱。
	 * 
	 * @param radius
	 *            计算半径
	 * @param result
	 * @return
	 */
	private AxisAlignedBB getPenetratingBox(double radius,
			MovingObjectPosition result) {
		if (radius <= 0.0)
			return null;

		double dx, dy, dz;
		// center is inside the hitting block.
		if (radius > 5)
			radius = 5;
		switch (result.sideHit) {
		case -1:
			return null;
		case 0:
		case 1:
			dy = (motionY > 0) ? radius + 1 : -radius - 1;
			dx = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_X)
					* radius * (motionY > 0 ? 1 : -1);
			dz = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_Z)
					* radius * (motionY > 0 ? 1 : -1);
			break;
		case 2:
		case 3:
			dz = (motionZ > 0) ? radius + 1 : -radius - 1;
			dx = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_X)
					* radius * (motionZ > 0 ? 1 : -1);
			dy = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_Y)
					* radius * (motionZ > 0 ? 1 : -1);
			break;
		case 4:
		case 5:
			dx = (motionX > 0) ? radius + 1 : -radius - 1;
			dy = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_Y)
					* radius * (motionX > 0 ? 1 : -1);
			dz = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_Z)
					* radius * (motionX > 0 ? 1 : -1);
			break;
		default:
			return null;
		}
		Vec3 center = result.hitVec.addVector(dx, dy, dz);
		return AxisAlignedBB.getBoundingBox(center.xCoord - radius,
				center.yCoord - radius, center.zCoord - radius, center.xCoord
						+ radius, center.yCoord + radius, center.zCoord
						+ radius);

	}

	/**
	 * get the tangent value of the motion in a specific subplain.
	 * 
	 * @param sideHit
	 *            HitVec sidehit
	 * @param subPlain
	 *            judging with sideHit determines a single plain,such as XoZ
	 * @return tangent value, could be lower than 0
	 */
	private double getTanBySideAndMotion(int sideHit, EnumSubPlain subPlain) {
		double a = 0.0;
		if (subPlain == EnumSubPlain.PLAIN_X)
			a = motionX;
		if (subPlain == EnumSubPlain.PLAIN_Y)
			a = motionY;
		if (subPlain == EnumSubPlain.PLAIN_Z)
			a = motionZ;

		double b = 0.0;
		switch (sideHit) {
		case 0:
		case 1:
			b = motionY;
			break;
		case 2:
		case 3:
			b = motionZ;
			break;
		case 4:
		case 5:
			b = motionX;
			break;
		default:
			return -2;
		}

		double tan = a / b;
		return tan;
	}

	/**
	 * 根据碰撞方向判断入射角正弦值。
	 */
	private double getSinBySideAndMotion(int sideHit) {
		double a = Math.sqrt(motionX * motionX + motionY * motionY + motionZ
				* motionZ);
		double sin = 0.0; // 入射角正弦值
		double b = 0.0;

		switch (sideHit) {
		case 0:
		case 1:
			b = motionY;
			break;
		case 2:
		case 3:
			b = motionZ;
			break;
		case 4:
		case 5:
			b = motionX;
			break;
		default:
			return -2;
		}

		sin = b / a;
		return sin;
	}

	private int getChargeDamage() {
		return Math.round(damage);
	}

}

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
package cn.lambdacraft.deathmatch.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.deathmatch.entity.fx.EntityGaussRay;
import cn.lambdacraft.deathmatch.entity.fx.EntityGaussRayColored;
import cn.lambdacraft.deathmatch.entity.fx.GaussParticleFX;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.api.util.Motion3D;
import cn.weaponmod.api.WeaponHelper;

/**
 * 非蓄力射击的高斯枪射击判断实体。
 * 
 * @author WeAthFolD
 */
public class EntityBulletGaussSec extends EntityBullet {

	public int damage;

	public enum EnumGaussRayType {
		PENETRATION, REFLECTION, NORMAL;
	}

	public EntityBulletGaussSec(World world) {
		super(world);
	}

	public EntityBulletGaussSec(EnumGaussRayType typeOfRay, World worldObj,
			EntityLivingBase entityLivingBase, ItemStack itemStack,
			MovingObjectPosition result, Motion3D motion, int dmg) {

		super(worldObj, entityLivingBase, dmg);
		Motion3D real = null;

		if (typeOfRay == EnumGaussRayType.NORMAL) {
			damage = dmg;
			worldObj.spawnEntityInWorld(new EntityGaussRayColored(
					new Motion3D(this, true), worldObj));
			return;
		} else {
			real = new Motion3D(result.hitVec, motion.motionX, motion.motionY,
					motion.motionZ);
			ForgeDirection[] v = ForgeDirection.values();
			ForgeDirection d = v[result.sideHit];
			double dx = d.offsetX, dy = d.offsetY, dz = d.offsetZ;
			worldObj.spawnEntityInWorld(new GaussParticleFX(worldObj, real.posX
					+ dx * 0.03F, real.posY + dy * 0.03F, real.posZ + dz
					* 0.03F));
		}
		damage = dmg;

		if (typeOfRay == EnumGaussRayType.PENETRATION) {
			double du = 0.0;
			du = getMiniumUpdate(result, real);
			real.move(du);
		} else if (typeOfRay == EnumGaussRayType.REFLECTION) {

			switch (result.sideHit) {
			case 0:
			case 1:
				real.motionY = -real.motionY;
				break;
			case 2:
			case 3:
				real.motionZ = -real.motionZ;
				break;
			case 4:
			case 5:
				real.motionX = -real.motionX;
				break;
			default:
				this.setDead();
			}

		}

		motionX = real.motionX;
		motionY = real.motionY;
		motionZ = real.motionZ;
		real.move(0.001);
		setPosition(real.posX, real.posY, real.posZ);
		this.setThrowableHeading(motionX, motionY, motionZ,
				this.func_70182_d(), 1.0F);
		if (!worldObj.isRemote) {
			worldObj.spawnEntityInWorld(new EntityGaussRay(new Motion3D(this, true), worldObj));
		}
	}

	@Override
	public void entityInit() {
	}

	/**
	 * Get the minium updateMotion radius for penetrating an block.
	 * 
	 * @return du = updateMotionRadius
	 */
	private double getMiniumUpdate(MovingObjectPosition result, Motion3D real) {

		double du = 0.0;
		double dx = -result.hitVec.xCoord + result.blockX, dy = -result.hitVec.yCoord
				+ result.blockY, dz = -result.hitVec.zCoord + result.blockZ;
		if (real.motionX > 0)
			dx = 1 + dx;
		if (real.motionY > 0)
			dy = 1 + dy;
		if (real.motionZ > 0)
			dz = 1 + dz;

		dx = dx / real.motionX;
		dy = dy / real.motionY;
		dz = dz / real.motionZ;
		if (dx < 0)
			dx = 10000;
		if (dy < 0)
			dy = 10000;
		if (dz < 0)
			dz = 10000;
		return Math.min(Math.min(dx, dy), dz);

	}

	@Override
	public void doBlockCollision(MovingObjectPosition r) {
		ForgeDirection[] v = ForgeDirection.values();
		ForgeDirection d = v[r.sideHit];
		double dx = d.offsetX, dy = d.offsetY, dz = d.offsetZ;
		worldObj.spawnEntityInWorld(new GaussParticleFX(worldObj,
				r.hitVec.xCoord + dx * 0.03F, r.hitVec.yCoord + dy * 0.03F,
				r.hitVec.zCoord + dz * 0.03F));
	}

	@Override
	public void doEntityCollision(MovingObjectPosition result) {

		double var0 = damage / 20;
		double dx = motion.motionX * var0, dy = motion.motionY * var0, dz = motion.motionZ
				* var0;

		WeaponHelper.doEntityAttack(result.entityHit, DamageSource.causeMobDamage(getThrower()), damage, dx, dy, dz);

	}

}

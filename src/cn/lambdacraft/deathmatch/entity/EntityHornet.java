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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.liutils.api.entity.EntityTrailFX;
import cn.liutils.api.util.GenericUtils;
import cn.weaponmod.api.WeaponHelper;

/**
 * 蜂巢枪实体。
 * 
 * @author WeAthFolD
 * 
 */
public class EntityHornet extends EntityThrowable {

	private boolean searchForPlayer = false;
	private EntityLivingBase currentTarget;
	public static final double RAD = 8.0, TURNING_SPEED = 0.5;

	public EntityHornet(World par1World) {
		super(par1World);
		if (worldObj.isRemote)
			worldObj.spawnEntityInWorld(new EntityTrailFX(worldObj, this)
					.setSampleFreq(1).setTrailWidth(0.1F)
					.setTextures(ClientProps.HORNET_TRAIL_PATH, null)
					.setDecayTime(20).setHasLight(false));
	}

	public EntityHornet(World par1World, EntityPlayer player, boolean doSearch) {
		super(par1World, player);
		searchForPlayer = doSearch;
	}

	@Override
	protected void entityInit() {}

	public void setHornetHeading(double par1, double par3, double par5,
			float par7) {
		float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5
				* par5);
		par1 /= f2;
		par3 /= f2;
		par5 /= f2;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		if (Math.abs(this.motionX - par1) < TURNING_SPEED)
			;
		double dx = par1 - motionX, dy = par3 - motionY, dz = par5 - motionZ;
		float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);

		if (Math.abs(dx) < TURNING_SPEED)
			this.motionX = par1;
		else
			this.motionX += (dx > 0) ? TURNING_SPEED : -TURNING_SPEED;
		if (Math.abs(dy) < TURNING_SPEED)
			this.motionY = par3;
		else
			this.motionY += (dy > 0) ? TURNING_SPEED : -TURNING_SPEED;
		if (Math.abs(dz) < TURNING_SPEED)
			this.motionZ = par5;
		else
			this.motionZ += (dz > 0) ? TURNING_SPEED : -TURNING_SPEED;

		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1,
				par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3,
				f3) * 180.0D / Math.PI);
	}

	public void setDoSearch(boolean b) {
		searchForPlayer = b;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (worldObj.isRemote && this.ticksExisted > 120)
			this.setDead();
		if (!this.searchForPlayer)
			return;
		if (currentTarget != null) {
			double dx = currentTarget.posX - this.posX, dy = currentTarget.posY
					- this.posY, dz = currentTarget.posZ - this.posZ;
			this.setHornetHeading(dx, dy, dz, func_70182_d());
		} else if (ticksExisted % 5 == 0) {
			this.searchTarget();
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition m) {
		if (this.ticksExisted > 200)
			this.setDead();
		if (m.typeOfHit == EnumMovingObjectType.TILE) {
			int bID = worldObj.getBlockId(m.blockX, m.blockY, m.blockZ);
			if(Block.blocksList[bID].getCollisionBoundingBoxFromPool(worldObj, m.blockX, m.blockY, m.blockZ) != null) {
				switch (m.sideHit) {
				case 0:
					this.motionY = -0.05F;
					break;
				case 1:
					this.motionY = 0.05F;
					break;
				case 2:
					this.motionZ = -0.05F;
					break;
				case 3:
					this.motionZ = 0.05F;
					break;
				case 4:
					this.motionX = -0.05F;
					break;
				case 5:
					this.motionX = 0.05F;
					break;
			}
			this.setThrowableHeading(motionX, motionY, motionZ,
					this.func_70182_d(), 0.0F);
			}
			if (searchForPlayer)
				searchTarget();
		} else {
			if (!(m.entityHit instanceof EntityLiving
					|| m.entityHit instanceof EntityDragonPart || m.entityHit instanceof EntityEnderCrystal))
				return;
			WeaponHelper.doEntityAttack(m.entityHit,
					DamageSource.causeMobDamage(getThrower()),
					DMItems.weapon_hornet.getWeaponDamage(searchForPlayer));
			this.setDead();
		}
	}

	@Override
	public float getGravityVelocity() {
		return 0.0F;
	}

	private void searchTarget() {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(posX - RAD,
				posY - RAD, posZ - RAD, posX + RAD, posY + RAD, posZ + RAD);
		List<EntityLivingBase> list = worldObj
				.getEntitiesWithinAABBExcludingEntity(getThrower(), box, GenericUtils.selectorLiving);
		float distance = 10000.0F;
		EntityLivingBase target = null;
		for (EntityLivingBase e : list) {
			float d = e.getDistanceToEntity(this);
			if (d < distance) {
				distance = d;
				target = e;
			}
		}
		if (target != null)
			this.currentTarget = target;
		else
			currentTarget = null;
	}

}

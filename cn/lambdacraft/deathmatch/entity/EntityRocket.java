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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cn.lambdacraft.core.proxy.LCClientProps;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.liutils.api.entity.EntityTrailFX;
import cn.weaponmod.api.WeaponHelper;

/**
 * RPG火箭弹实体。
 * 
 * @author WeAthFolD
 * 
 */
public class EntityRocket extends EntityThrowable {

	private EntityRPGDot dot;
	public static double TURNING_SPEED = 0.6;

	public EntityRocket(World par1World, EntityPlayer player, ItemStack is) {
		super(par1World, player);
		rotationPitch = player.rotationPitch;
		rotationYaw = player.rotationYaw;
		worldObj.playSoundAtEntity(this, "cbc.weapons.rocket", 0.3F, 1.0F);
		this.dot = DMItems.weapon_RPG.getRPGDot(is, par1World, player);
	}

	public EntityRocket(World world) {
		super(world);
		worldObj.spawnEntityInWorld(new EntityTrailFX(worldObj, this)
				.setSampleFreq(1)
				.setTrailWidth(0.4F)
				.setTextures(LCClientProps.RPG_TRAIL_PATH[0],
						LCClientProps.RPG_TRAIL_PATH[1]).setDoesRenderEnd(true)
				.setDecayTime(20));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.isBurning())
			Explode();
		if (ticksExisted % 45 == 0)
			worldObj.playSoundAtEntity(this, "cbc.weapons.rocket", 0.5F, 1.0F);
		if (dot == null || this.isDead)
			return;
		if (dot.isDead) {
			dot = null;
			return;
		}

		double dx = dot.posX - this.posX, dy = dot.posY - this.posY, dz = dot.posZ
				- this.posZ;
		this.setRocketHeading(dx, dy, dz, this.func_70182_d());
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	public void setRocketHeading(double par1, double par3, double par5,
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

		if (Math.sqrt(dx * dx + dy * dy + dz * dz) > 8 * TURNING_SPEED)
			return;

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

	@Override
	protected void onImpact(MovingObjectPosition var1) {
		Explode();
	}

	private void Explode() {
		WeaponHelper.Explode(worldObj, this, 4.5F, 5.0F, posX, posY, posZ, 35);
		this.setDead();
	}

	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

	@Override
	protected float func_70182_d() {
		return 4.0F;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

}

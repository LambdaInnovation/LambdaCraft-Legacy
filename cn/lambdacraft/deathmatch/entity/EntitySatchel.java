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

import cn.liutils.api.entity.EntityProjectile;
import cn.weaponmod.api.WeaponHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * 遥控炸弹实体。
 * 
 * @author WeAthFolD
 * 
 */
public class EntitySatchel extends EntityProjectile {

	public static double HEIGHT = 0.083, WIDTH1 = 0.2, WIDTH2 = 0.15;

	/**
	 * 被击中时的tick数。
	 */
	public int tickHit = 0;

	/**
	 * 实体的旋转情况。
	 */
	public float rotationFactor;

	public EntitySatchel(World par1World, EntityPlayer par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}

	public EntitySatchel(World world) {
		super(world);
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public void Explode() {

		WeaponHelper.Explode(worldObj, this, 3.0F, 4.0F, posX, posY, posZ, 35);
		this.setDead();

	}

	@Override
	protected float getGravityVelocity() {
		return 0.025F;
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return AxisAlignedBB.getBoundingBox(-WIDTH1, -HEIGHT, -WIDTH2, WIDTH1,
				HEIGHT, WIDTH2);
	}

	@Override
	protected float getHeadingVelocity() {
		return 0.7F;
	}

	@Override
	protected float getMotionYOffset() {
		return 0;
	}

	@Override
	protected void entityInit() {
		rotationFactor = 0.0F;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (worldObj.isRemote)
			return;
		if (getThrower() == null) {
			this.setDead();
			return;
		}
		boolean doesExplode = getThrower().getEntityData().getBoolean(
				"doesExplode");
		if (doesExplode || isBurning())
			Explode();
		if (this.onGround) {
			rotationFactor += 0.01F;
		} else
			rotationFactor += 3.0F;
		if (rotationFactor > 360.0F)
			rotationFactor = 0.0F;
	}

	@Override
	protected void onCollide(MovingObjectPosition result) {
		switch (result.sideHit) {
		case 1:
			motionY = -0.5 * motionY;
			return;
		case 2:
		case 3:
			motionZ = -0.6 * motionZ;
			return;
		case 4:
		case 5:
			motionX = -0.6 * motionX;
			return;
		default:
			this.onGround = true;
			posY = result.hitVec.yCoord + 1.0;
			motionY = 0.0;
			return;
		}
	}

}

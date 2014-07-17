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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开原协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.deathmatch.entity.fx;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cn.liutils.api.util.Motion3D;

/**
 * 高斯枪光束渲染的实用类。
 * 
 * @author WeAthFolD
 */
public class EntityGaussRay extends Entity {

	public double distanceToRender = 50;
	private Motion3D motion = null;
	
	public EntityGaussRay(Motion3D begin, World par1World) {

		super(par1World);
		this.posX = begin.posX;
		this.posY = begin.posY;
		this.posZ = begin.posZ;
		this.motionX = begin.motionX;
		this.motionY = begin.motionY;
		this.motionZ = begin.motionZ;
		this.setRayHeading(motionX, motionY, motionZ, 1.0F, 1.0F);
		
	}

	public EntityGaussRay(World world) {
		super(world);
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	public void setRayHeading(double par1, double par3, double par5,
			float par7, float par8) {
		float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5
				* par5);
		par1 /= f2;
		par3 /= f2;
		par5 /= f2;
		par1 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
		par3 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
		par5 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1,
				par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3,
				f3) * 180.0D / Math.PI);
	}

	@Override
	public void onUpdate() {
		if(motion == null)
			motion = new Motion3D(this, true);
		MovingObjectPosition trace = this.worldObj.rayTraceBlocks(motion
				.asVec3(this.worldObj),
				motion.move(100.0F).asVec3(this.worldObj));
		Vec3 end = (trace == null) ? motion.asVec3(this.worldObj) : trace.hitVec;
		double dx = end.xCoord - this.posX;
		double dy = end.yCoord - this.posY;
		double dz = end.zCoord - this.posZ;
		distanceToRender = Math.sqrt(dx * dx + dy * dy + dz * dz);
		if(distanceToRender < 3)
			distanceToRender = 50;
		if (this.ticksExisted > 2)
			this.setDead();
	}

	@Override
	protected void entityInit() {
		ignoreFrustumCheck = true;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}

}

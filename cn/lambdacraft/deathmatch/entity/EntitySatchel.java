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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cn.weaponmod.api.WeaponHelper;

/**
 * 遥控炸弹实体。
 * 
 * @author WeAthFolD
 * 
 */
public class EntitySatchel extends EntityThrowable {

	public static double HEIGHT = 0.083, WIDTH1 = 0.2, WIDTH2 = 0.15;
	
	public boolean still;
	public double stlX, stlY, stlZ;

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
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(9,  Byte.valueOf((byte) 0));
		dataWatcher.addObject(10, Float.valueOf(0));
		dataWatcher.addObject(11, Float.valueOf(0));
		dataWatcher.addObject(12, Float.valueOf(0));
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
		return still ? 0F : 0.025F;
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return AxisAlignedBB.getBoundingBox(-WIDTH1, -HEIGHT, -WIDTH2, WIDTH1,
				HEIGHT, WIDTH2);
	}

	@Override
	protected float func_70182_d() {
		return 0.7F;
	}

	@Override
	public void onUpdate() {
		attemptUpdate();
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
	protected void onImpact(MovingObjectPosition result) {
		if(result.typeOfHit != MovingObjectType.BLOCK || 
				still || !acceptible(result.blockX, result.blockY, result.blockZ)) return;
		ForgeDirection dir = ForgeDirection.values()[result.sideHit];
		double offX = dir.offsetX, offY = dir.offsetY, offZ = dir.offsetZ;
		if(result.sideHit == 0 || result.sideHit == 1) {
			offY *= 0.1F;
		} else {
			offX *= 0.1F;
			offZ *= 0.1F;
		}
		
		this.still = true;
		stlX = result.hitVec.xCoord + offX;
		stlY = result.hitVec.yCoord + offY;
		stlZ = result.hitVec.zCoord + offZ;
		attemptUpdate();
	}
	
	private void attemptUpdate() {
		if(worldObj.isRemote) {
			still = dataWatcher.getWatchableObjectByte(9) == 1;
			stlX = dataWatcher.getWatchableObjectFloat(10);
			stlY = dataWatcher.getWatchableObjectFloat(11);
			stlZ = dataWatcher.getWatchableObjectFloat(12);
		} else {
			dataWatcher.updateObject(9, still ? (byte)1 : (byte)0);
			if(still) {
				dataWatcher.updateObject(10, (float)stlX);
				dataWatcher.updateObject(11, (float)stlY);
				dataWatcher.updateObject(12, (float)stlZ);
			}
		}
		
		if(still) {
			motionX = motionY = motionZ = 0;
			posX = stlX;
			posY = stlY;
			posZ = stlZ;
			
			if(!acceptible((int)posX, (int)posY, (int)posZ)) still = false;
		}
	}
	
	private boolean acceptible(int x, int y, int z) {
		return worldObj.getBlock(x, y, z).getCollisionBoundingBoxFromPool(worldObj, x, y, z) != null;
	}

}

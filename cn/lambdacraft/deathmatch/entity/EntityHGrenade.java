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

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import cn.weaponmod.api.WeaponHelper;

/**
 * 手雷实体。
 * 
 * @author Administrator
 * 
 */
public class EntityHGrenade extends EntityThrowable {

	int delay;
	int lastBounceTime;

	public EntityHGrenade(World world, EntityLivingBase player, int fuse) {
		super(world, player);
		delay = 60 - fuse;
		lastBounceTime = 0;

	}

	public EntityHGrenade(World world) {
		super(world);
	}

	@Override
	protected void onImpact(MovingObjectPosition par1) {
		
		if(par1.typeOfHit == MovingObjectType.BLOCK) {
			//System.out.println("set" + worldObj.isRemote);
			lastBounceTime = ticksExisted;
		}
		
		if (worldObj.isRemote)
			return;

		Block block = this.worldObj.getBlock(par1.blockX, par1.blockY, par1.blockZ);
		
		double collideStrengh = 1.0;
		// 碰撞代码
		if (par1.typeOfHit == MovingObjectType.BLOCK) {
			if (!block.isCollidable())
				return;
			switch (par1.sideHit) {

			case 0:
			case 1:
				collideStrengh = Math.abs(motionY) * 0.3;
				this.motionY = 0.01 * -motionY;
				this.motionX = 0.8 * motionX;
				this.motionZ = 0.8 * motionZ;
				break;

			case 2:
			case 3:
				this.motionZ = 0.6 * -motionZ;
				collideStrengh = Math.abs(motionZ);
				break;

			case 4:
			case 5:
				this.motionX = 0.6 * -motionX;
				collideStrengh = Math.abs(motionX);
				break;

			default:
				break;
			}
		}
		if(collideStrengh > 1.0) collideStrengh = 1.0;
//		System.out.println(collideStrengh);
		if (lastBounceTime == 0 || ticksExisted - lastBounceTime > 20) { // 最小时间间隔1s
			this.playSound("lambdacraft:weapons.hgrenadebounce", (float) (5F * collideStrengh), 1.0F);
		}

	}

	private void Explode() {
		if (worldObj.isRemote)
			return;
		WeaponHelper.Explode(worldObj, this, 3.0F, 3.5F, posX, posY, posZ, 35);
		this.setDead();
	}

	@Override
	public void onUpdate() {
		double lastPosY = posY;
		super.onUpdate();
		if(worldObj.isRemote) {
			if(lastBounceTime != 0 && ticksExisted - lastBounceTime < 10 && posY - lastPosY < 0.02) {
				posY = lastPosY;
			}
		}
		
		if (this.ticksExisted >= delay || this.isBurning()) // Time to explode
			Explode();											// >)
			
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	protected float getGravityVelocity() {
		return 0.025F;
	}

	@Override
	protected float func_70182_d() {
		return 0.7F;
	}

}

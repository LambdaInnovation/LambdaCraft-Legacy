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
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import cn.lambdacraft.deathmatch.entity.fx.EntityCrossbowStill;
import cn.liutils.api.util.Motion3D;
import cn.weaponmod.api.WeaponHelper;

/**
 * 十字弩的爆炸弩箭实体。
 * 
 * @author WeAthFolD
 * 
 */
public class EntityCrossbowArrow extends EntityThrowable {

	public boolean isExplosive;
	public int damage;
	
	public EntityCrossbowArrow(World par1World, EntityLivingBase par2EntityLiving, boolean explode) {
		super(par1World, par2EntityLiving);
		isExplosive = explode;
		damage = 20;
	}

	public EntityCrossbowArrow(World world) {
		super(world);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {
		if(!worldObj.isRemote) {
			if(isExplosive)
				Explode();
			else {
				if(var1.typeOfHit == MovingObjectType.ENTITY) {
					if(damage <= 3)
						setDead();
					WeaponHelper.doEntityAttack(var1.entityHit, DamageSource.causeMobDamage(getThrower()), damage);
					if(var1.entityHit instanceof EntityLivingBase) {
						EntityLivingBase living = (EntityLivingBase) var1.entityHit;
						living.setArrowCountInEntity(living.getArrowCountInEntity() + 1);
					}
					damage *= 0.6;
				}
				else {
					Block block = worldObj.getBlock(var1.blockX, var1.blockY, var1.blockZ);
					if(block == Blocks.glass_pane || block == Blocks.glass) {
						//worldObj.destroyBlock(var1.blockX, var1.blockY, var1.blockZ, false);
						//TODO:destroy it!
					} else { 
						this.setDead();
						worldObj.spawnEntityInWorld(
							new EntityCrossbowStill(worldObj, new Motion3D(var1.hitVec.xCoord, var1.hitVec.yCoord, var1.hitVec.zCoord, motionX, motionY, motionZ),
									this.rotationYaw, this.rotationPitch));
					}
				}
			}
		}
	}

	private void Explode() {
		WeaponHelper.Explode(worldObj, this, 1.0F, 3.0F, posX, posY, posZ, 30);
		this.setDead();
	}

	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

	@Override
	protected float func_70182_d() {
		return 5.0F;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

}

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.api.util.GenericUtils;

/**
 * 随机破坏方块+范围攻击
 * @author WeAthFolD
 */
public class EntityBulletEgon extends EntityBullet {

	public EntityBulletEgon(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving, DMItems.weapon_egon.getWeaponDamage(true));
	}

	public EntityBulletEgon(World world) {
		super(world);
	}
	
	@Override
	protected void doBlockCollision(MovingObjectPosition result) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(result.hitVec.xCoord - 2, result.hitVec.yCoord - 2, result.hitVec.zCoord - 2,
				result.hitVec.xCoord + 2, result.hitVec.yCoord + 2, result.hitVec.zCoord + 2);
		List<Entity> ents = worldObj.getEntitiesWithinAABBExcludingEntity(this, box, GenericUtils.selectorLiving);
		for(Entity e : ents) {
			double distance = e.getDistance(result.hitVec.xCoord, result.hitVec.yCoord, result.hitVec.zCoord);
			int damage = (int) (7.0 / distance);
			e.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), damage);
		}
		if(rand.nextFloat() < 0.1)  {
			Block blockID = worldObj.getBlock(result.blockX, result.blockY, result.blockZ);
			//检查以防破坏基岩等重要方块
			if(blockID != null && blockID != Blocks.air) {
				float hardness = blockID.getBlockHardness(worldObj, result.blockX, result.blockY, result.blockZ);
				//if(hardness > 0.0F && hardness < 5.0F) TODO:orz
				//	worldObj.destroyBlock(result.blockX, result.blockY, result.blockZ, false);
			} else worldObj.setBlock(result.blockX, result.blockY, result.blockZ, Blocks.air, 0, 3);
		}
		this.setDead();
	}

}

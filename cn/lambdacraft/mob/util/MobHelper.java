package cn.lambdacraft.mob.util;

import java.util.List;

import cn.lambdacraft.api.entity.IEntityLink;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class MobHelper {

	/**
	 * WeAthFolD: 把一个奇怪的生物生成到世界中。
	 * 
	 * mkpoli: 喂 变态 哪里奇怪了
	 * 
	 */
	public static Entity spawnCreature(World par0World,
			Class<? extends Entity> c, EntityLivingBase thrower, boolean front) {

		Entity entity = null;

		try {
			entity = c.getConstructor(World.class).newInstance(par0World);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (entity != null && entity instanceof EntityLivingBase) {
			EntityLiving entityliving = (EntityLiving) entity;
			float f = 0.4F;
			Vec3 lookVec = thrower.getLookVec();
			if(front)
			entityliving.setLocationAndAngles(thrower.posX + lookVec.xCoord * 2,
					thrower.posY, thrower.posZ + lookVec.zCoord * 2,
					thrower.rotationYawHead, 0.0F);
			else entityliving.setLocationAndAngles(thrower.posX,
					thrower.posY, thrower.posZ,
					thrower.rotationYawHead, 0.0F);
			entityliving.rotationYawHead = entityliving.rotationYaw;
			entityliving.renderYawOffset = entityliving.rotationYaw;
			if (entityliving instanceof IEntityLink) {
				((IEntityLink) entityliving).setLinkedEntity(thrower);
			}
            entityliving.onSpawnWithEgg((EntityLivingData)null);
			par0World.spawnEntityInWorld(entity);
			entityliving.playLivingSound();
		}

		return entity;
	}
	
    public static Entity spawnCreature(World par0World, EntityPlayer thrower, Class<? extends Entity> c, double par2, double par4, double par6)
    {
            Entity entity = null;

    		try {
    			entity = c.getConstructor(World.class).newInstance(par0World);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
            
            for (int j = 0; j < 1; ++j)
            {

                if (entity != null && entity instanceof EntityLivingBase)
                {
                	EntityLiving entityliving = (EntityLiving)entity;
                    entity.setLocationAndAngles(par2, par4, par6, MathHelper.wrapAngleTo180_float(par0World.rand.nextFloat() * 360.0F), 0.0F);
                    entityliving.rotationYawHead = entityliving.rotationYaw;
                    entityliving.renderYawOffset = entityliving.rotationYaw;
                    entityliving.onSpawnWithEgg((EntityLivingData)null);
                    if (entityliving instanceof IEntityLink && thrower != null) {
        				((IEntityLink) entityliving).setLinkedEntity(thrower);
        			}
                    par0World.spawnEntityInWorld(entity);
                    entityliving.playLivingSound();
                }
            }

            return entity;
    }

	private static void setEntityHeading(Entity ent, double par1, double par3,
			double par5, double moveSpeed) {
		float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5
				* par5);
		par1 /= f2;
		par3 /= f2;
		par5 /= f2;
		par1 *= moveSpeed;
		par3 *= moveSpeed;
		par5 *= moveSpeed;
		ent.motionX = par1;
		ent.motionY = par3;
		ent.motionZ = par5;
		float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		ent.prevRotationYaw = ent.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
		ent.prevRotationPitch = ent.rotationPitch = (float) (Math.atan2(par3,
				f3) * 180.0D / Math.PI);
	}
	
	public static Entity getNearestTargetWithinAABB(World world, double x, double y, double z, float range, IEntitySelector selector, Entity... exclusion) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range, y + range, z + range);
		List<Entity> entList = world.getEntitiesWithinAABBExcludingEntity(null, box, selector);
		double distance = range + 10000.0;
		Entity target = null;
		for(Entity e : entList) {
			for(Entity ex : exclusion)
				if(e == ex) continue;
			double d = e.getDistanceSq(x, y, z);
			if(d < distance) {
				target = e;
				distance = d;
			}
		}
		return target;
	}

}

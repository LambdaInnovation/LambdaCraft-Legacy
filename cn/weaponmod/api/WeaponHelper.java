/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.weaponmod.api;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.api.util.LIExplosion;
import cn.weaponmod.api.weapon.WeaponGeneral;
import cn.weaponmod.network.NetExplosion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class WeaponHelper {

	/**
	 * Tries to consume a specific amount of ammo in player's inventory.
	 * 
	 * @return how many are left to be consumed
	 */
	public static int consumeAmmo(EntityPlayer player, WeaponGeneral item, int amount) {
		return tryConsume(player, item.ammoID, amount);
	}

	/**
	 * Tries to consume one specific item in player's inventory.
	 * 
	 * @return how many are left to be consumed
	 */
	public static int tryConsume(EntityPlayer player, int itemID, int amount) {

		int left = amount;
		ItemStack is;
		if (Item.itemsList[itemID].getItemStackLimit() > 1) {

			for (int i = 0; i < player.inventory.mainInventory.length; i++) {
				is = player.inventory.mainInventory[i];
				if (is != null && is.itemID == itemID) {
					if (is.stackSize > left) {
						player.inventory.decrStackSize(i, left);
						return 0;
					} else {
						left -= is.stackSize;
						player.inventory.decrStackSize(i, is.stackSize);
					}
				}
			}
			return left;

		} else {

			for (int i = 0; i < player.inventory.mainInventory.length; i++) {
				is = player.inventory.mainInventory[i];
				int stackCap;
				if (is != null && is.itemID == itemID) {
					stackCap = is.getMaxDamage() - is.getItemDamage() - 1;
					if (stackCap > left) {
						is.damageItem(left, player);
						return 0;
					} else {
						left -= stackCap;
						is.setItemDamage(is.getMaxDamage() - 1);
					}
				}
			}
			return left;

		}
	}

	/**
	 * determine if player have any ammo for reloading/energy weapon shooting.
	 */
	public static boolean hasAmmo(WeaponGeneral is, EntityPlayer player) {
		for (ItemStack i : player.inventory.mainInventory) {
			if (i == null)
				continue;
			if (i.itemID == is.ammoID) {
				if (i.isStackable())
					return true;
				else if (i.getItemDamage() < i.getMaxDamage() - 1)
					return true;
			}
		}
		return false;
	}
	
	public static int getAmmoCapacity(int itemID, InventoryPlayer inv) {
		int cnt = 0;
		for(ItemStack s : inv.mainInventory) {
			if(s != null && s.itemID == itemID) {
				cnt += s.getMaxStackSize() == 1 ? s.getMaxDamage() - s.getItemDamage() - 1 : s.stackSize;
			}
		}
		return cnt;
	}

	public static boolean hasAmmo(int itemID, EntityPlayer player) {
		return player.inventory.hasItem(itemID);
	}

	public static int consumeInventoryItem(ItemStack[] inv, int itemID,
			int count) {
		int left = count;
		ItemStack is;
		if (Item.itemsList[itemID].getItemStackLimit() > 1) {

			for (int i = 0; i < inv.length; i++) {
				is = inv[i];
				if (is != null && is.itemID == itemID) {
					if (is.stackSize > left) {
						inv[i].splitStack(left);
						return 0;
					} else {
						left -= is.stackSize;
						inv[i] = null;
					}
				}
			}
			return left;

		} else
			return left;
	}

	public static int consumeInventoryItem(ItemStack[] inv, int itemID,
			int count, int startFrom) {
		int left = count;
		ItemStack is;
		if (Item.itemsList[itemID].getItemStackLimit() > 1) {

			for (int i = startFrom; i < inv.length; i++) {
				is = inv[i];
				if (is != null && is.itemID == itemID) {
					if (is.stackSize > left) {
						inv[i].splitStack(left);
						return 0;
					} else {
						left -= is.stackSize;
						inv[i] = null;
					}
				}
			}
			return left;

		} else
			return left;
	}
	
	private static final double BB_SIZE = 0.5D;
	private static final int ENTITY_TRACE_RANGE = 128;

	public static void Shoot(EntityLivingBase par3Entity, World worldObj, int damage) {
		worldObj.spawnEntityInWorld(new EntityBullet(worldObj, par3Entity, damage));
	}
	
	public static void Shoot(int damage, EntityLivingBase entityPlayer,
			World worldObj) {
		worldObj.spawnEntityInWorld(new EntityBullet(worldObj, entityPlayer, damage));
	}

	/**
	 * 意义何在？
	 * @param ent
	 * @param ds
	 * @param damage
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	public static void doEntityAttack(Entity ent, DamageSource ds, int damage, double dx, double dy, double dz) {
		ent.attackEntityFrom(ds, damage);
	}

	public static void doEntityAttack(Entity ent, DamageSource ds, int damage) {
		doEntityAttack(ent, ds, damage, 0, 0, 0);
	}

	public static void Explode(World world, Entity entity, float strengh,
			double radius, double posX, double posY, double posZ,
			int additionalDamage) {
		Explode(world, entity, strengh, radius, posX, posY, posZ, additionalDamage, 1.0, 1.0F);
	}
	
	public static MovingObjectPosition rayTraceBlocksAndEntities(World world, Vec3 vec1, Vec3 vec2) {
		MovingObjectPosition mop = rayTraceEntities(null, world, vec1, vec2);
		if(mop == null)
			return world.clip(vec1, vec2);
		return mop;
	}
	
	public static MovingObjectPosition rayTraceBlocksAndEntities(IEntitySelector selector, World world, Vec3 vec1, Vec3 vec2, Entity... exclusion) {
		MovingObjectPosition mop = rayTraceEntities(selector, world, vec1, vec2, exclusion);
		if(mop == null)
			return world.clip(vec1, vec2);
		return mop;
	}
	
	public static MovingObjectPosition traceBetweenEntities(Entity e1, Entity e2) {
		if(e1.worldObj != e2.worldObj) return null;
		Vec3 v1 = e1.worldObj.getWorldVec3Pool().getVecFromPool(e1.posX, e1.posY, e1.posZ),
				v2 = e2.worldObj.getWorldVec3Pool().getVecFromPool(e2.posX, e2.posY, e2.posZ);
		MovingObjectPosition mop = e1.worldObj.clip(v1, v2);
		return mop;
	}
	
	public static MovingObjectPosition rayTraceEntities(IEntitySelector selector, World world, Vec3 vec1, Vec3 vec2, Entity... exclusion) {
        Entity entity = null;
        AxisAlignedBB boundingBox = getBoundingBox(vec1, vec2);
        List list = world.getEntitiesWithinAABBExcludingEntity(null, boundingBox.expand(1.0D, 1.0D, 1.0D), selector);
        double d0 = 0.0D;

        for (int j = 0; j < list.size(); ++j)
        {
            Entity entity1 = (Entity)list.get(j);

            Boolean b = entity1.canBeCollidedWith();
            if(!b)
            	continue;
            for(Entity e : exclusion) {
            	if(e == entity1)
            		b = false;
            }
            if (b && entity1.canBeCollidedWith())
            {
                float f = 0.3F;
                AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f, f, f);
                MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec1, vec2);

                if (movingobjectposition1 != null)
                {
                    double d1 = vec1.distanceTo(movingobjectposition1.hitVec);

                    if (d1 < d0 || d0 == 0.0D)
                    {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        if (entity != null)
        {
            return new MovingObjectPosition(entity);
        }
        return null;
	}
	
	public static AxisAlignedBB getBoundingBox(Vec3 vec1, Vec3 vec2) {
		double minX = 0.0, minY = 0.0, minZ = 0.0, maxX = 0.0, maxY = 0.0, maxZ = 0.0;
		if(vec1.xCoord < vec2.xCoord) {
			minX = vec1.xCoord;
			maxX = vec2.xCoord;
		} else {
			minX = vec2.xCoord;
			maxX = vec1.xCoord;
		}
		if(vec1.yCoord < vec2.yCoord) {
			minY = vec1.yCoord;
			maxY = vec2.yCoord;
		} else {
			minY = vec2.yCoord;
			maxY = vec1.yCoord;
		}
		if(vec1.zCoord < vec2.zCoord) {
			minZ = vec1.zCoord;
			maxZ = vec2.zCoord;
		} else {
			minZ = vec2.zCoord;
			maxZ = vec1.zCoord;
		}
		return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	public static AxisAlignedBB getBoundingBox(double minX, double minY,double minZ,double maxX,double maxY, double maxZ) {
		double i;
		if(minX > maxX) {
			i = maxX;
			maxX = minX;
			minX = i;
		}
		if(minY > maxY) {
			i = maxY;
			maxY = minY;
			minY = i;
		}
		if(minZ > maxZ) {
			i = maxZ;
			maxZ = minZ;
			minZ = i;
		}
		return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public static void Explode(World world, Entity entity, float strengh,
			double radius, double posX, double posY, double posZ,
			int additionalDamage, double velocityRadius, float soundRadius) {

		LIExplosion explosion = new LIExplosion(world, entity, posX, posY,
				posZ, strengh).setSoundFactor(soundRadius).setVelocityFactor(
				velocityRadius);
		explosion.isSmoking = true;
		explosion.isFlaming = false;

		explosion.doExplosionA();
		explosion.doExplosionB(true);
		
		NetExplosion.sendNetPacket(world, (float) posX, (float) posY, (float) posZ, strengh);
		if (additionalDamage <= 0)
			return;

		doRangeDamage(world, DamageSource.setExplosionSource(explosion
				.asDefaultExplosion()), world.getWorldVec3Pool().getVecFromPool(posX, posY, posZ), additionalDamage, radius, entity);
		
	}
	
	public static void doRangeDamage(World world, DamageSource src, Vec3 pos, float strengh, double radius, Entity... exclusion) {
		AxisAlignedBB par2 = AxisAlignedBB.getBoundingBox(pos.xCoord - 4, pos.yCoord - 4,
				pos.zCoord - 4, pos.xCoord + 4, pos.yCoord + 4, pos.zCoord + 4);
		List entitylist = world
				.getEntitiesWithinAABBExcludingEntity(null, par2);
		if (entitylist.size() > 0) {
			for (int i = 0; i < entitylist.size(); i++) {
				Entity ent = (Entity) entitylist.get(i);
				if (ent instanceof EntityLiving) {
					double distance = pos.distanceTo(world.getWorldVec3Pool().getVecFromPool(ent.posX, ent.posY, ent.posZ));
					int damage = (int) ((1 - distance / 6.928) * strengh);
					ent.attackEntityFrom(src , damage);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static void clientExplode(World world, float strengh, double posX,
			double posY, double posZ) {
		LIExplosion explosion = new LIExplosion(world, null, posX, posY,
				posZ, strengh);
		explosion.isSmoking = true;
		explosion.isFlaming = false;

		explosion.doExplosionA();
		explosion.doExplosionB(true);
	}

}

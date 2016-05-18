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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import cn.liutils.api.util.GenericUtils;
import cn.liutils.api.util.Motion3D;
import cn.weaponmod.api.weapon.WeaponGenericBase;

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
    public static int consumeAmmo(EntityPlayer player, WeaponGenericBase item, int amount) {
        return tryConsume(player, item.ammoItem, amount);
    }

    /**
     * Tries to consume one specific item in player's inventory.
     * 
     * @return how many are left to be consumed
     */
    public static int tryConsume(EntityPlayer player, Item item, int amount) {

        int left = amount;
        ItemStack is;
        if (item.getItemStackLimit() > 1) {

            for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                is = player.inventory.mainInventory[i];
                if (is != null && is.getItem() == item) {
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
                if (is != null && is.getItem() == item) {
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
    public static boolean hasAmmo(WeaponGenericBase is, EntityPlayer player) {
        for (ItemStack i : player.inventory.mainInventory) {
            if (i == null)
                continue;
            if (i.getItem() == is.ammoItem) {
                if (i.isStackable())
                    return true;
                else if (i.getItemDamage() < i.getMaxDamage() - 1)
                    return true;
            }
        }
        return false;
    }
    
    public static int getAmmoCapacity(Item item, InventoryPlayer inv) {
        int cnt = 0;
        for(ItemStack s : inv.mainInventory) {
            if(s != null && s.getItem() == item) {
                cnt += s.getMaxStackSize() == 1 ? s.getMaxDamage() - s.getItemDamage() - 1 : s.stackSize;
            }
        }
        return cnt;
    }

    public static boolean hasAmmo(Item itemID, EntityPlayer player) {
        return player.inventory.hasItem(itemID);
    }

    public static int consumeInventoryItem(ItemStack[] inv, Item item,
            int count) {
        int left = count;
        ItemStack is;
        if (item.getItemStackLimit() > 1) {

            for (int i = 0; i < inv.length; i++) {
                is = inv[i];
                if (is != null && is.getItem() == item) {
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

    public static int consumeInventoryItem(ItemStack[] inv, Item item,
            int count, int startFrom) {
        int left = count;
        ItemStack is;
        if (item.getItemStackLimit() > 1) {

            for (int i = startFrom; i < inv.length; i++) {
                is = inv[i];
                if (is != null && is.getItem() == item) {
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
    
    /**
     * 意义何在？
     * @param ent
     * @param ds
     * @param damage
     * @param dx
     * @param dy
     * @param dz
     */
    public static void doEntityAttack(Entity ent, DamageSource ds, float damage, double dx, double dy, double dz) {
        ent.attackEntityFrom(ds, damage);
    }

    public static void doEntityAttack(Entity ent, DamageSource ds, float damage) {
        doEntityAttack(ent, ds, damage, 0, 0, 0);
    }
    
    public static void doPlayerAttack(EntityPlayer player, float damage) {
        Motion3D mot = new Motion3D(player, true);
        MovingObjectPosition mop = GenericUtils.rayTraceBlocksAndEntities(GenericUtils.selectorLiving,
                player.worldObj, mot.getPosVec(player.worldObj), mot.move(1.4).getPosVec(player.worldObj), player);
        if(mop != null && mop.typeOfHit == MovingObjectType.ENTITY) {
            mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
        }
    }
    
    public static void Explode(World world, Entity entity, float strengh,
            double radius, double posX, double posY, double posZ,
            int additionalDamage) {
        Explode(world, entity, strengh, radius, posX, posY, posZ, additionalDamage, 1.0, 1.0F);
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
        if(world.isRemote) //Ignore client operations
            return;
        
        Explosion explosion = world.createExplosion(entity, posX, posY, posZ, strengh, true);
        
        if (additionalDamage <= 0)
            return;

        doRangeDamage(world, DamageSource.setExplosionSource(explosion), Vec3.createVectorHelper(posX, posY, posZ), additionalDamage, radius, entity);
        
    }
    
    public static void doRangeDamage(World world, DamageSource src, Vec3 pos, float strengh, double radius, Entity... exclusion) {
        AxisAlignedBB par2 = AxisAlignedBB.getBoundingBox(pos.xCoord - 4, pos.yCoord - 4,
                pos.zCoord - 4, pos.xCoord + 4, pos.yCoord + 4, pos.zCoord + 4);
        List entitylist = world
                .getEntitiesWithinAABBExcludingEntity(null, par2);
        if (entitylist.size() > 0) {
            for (int i = 0; i < entitylist.size(); i++) {
                Entity ent = (Entity) entitylist.get(i);
                if (ent instanceof EntityLivingBase) {
                    double distance = pos.distanceTo(Vec3.createVectorHelper(ent.posX, ent.posY, ent.posZ));
                    int damage = (int) ((1 - distance / 6.928) * strengh);
//                    System.out.println("Attacking " + ent + " " + damage);
                    ent.attackEntityFrom(src , damage);
                }
            }
        }
    }
}

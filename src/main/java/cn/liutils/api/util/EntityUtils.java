/**
 * Code by Lambda Innovation, 2013.
 */
package cn.liutils.api.util;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
public class EntityUtils {

    public static int getSlotByStack(ItemStack item, EntityPlayer player) {
        InventoryPlayer inv = player.inventory;
        for(int i = 0; i < inv.mainInventory.length; i++) {
            ItemStack is = inv.mainInventory[i];
            if(is != null && item == is)
                return i;
        }
        return -1;
    }
    
    /**
     * Returns the distance of e1-e2 in plane XZ.
     * @param e1
     * @param e2
     * @return
     */
    public static double getDistanceSqFlat(Entity e1, Entity e2) {
        double x1 = e1.posX - e2.posX, x2 = e1.posZ - e2.posZ;
        return x1 * x1 + x2 * x2;
    }
    
    public static List<Entity> getEntitiesAround(Entity e, double rad) {
        return getEntitiesAround(e.worldObj, e.posX, e.posY, e.posZ, rad, null);
    }
    
    /**
     * Get all entities around e within distance rad.
     * @param e
     * @param rad
     * @param selector
     * @return
     */
    public static List<Entity> getEntitiesAround(Entity e, double rad, IEntitySelector selector) {
        return getEntitiesAround(e.worldObj, e.posX, e.posY, e.posZ, rad, selector);
    }
    
    /**
     * Getting all entites around ent while checking if ent can see it.
     * @param ent
     * @param rad
     * @param selector
     * @return
     */
    public static List<Entity> getEntitiesAround_CheckSight(EntityLivingBase ent, double rad, IEntitySelector selector) {
        List<Entity> l = getEntitiesAround(ent.worldObj, ent.posX, ent.posY, ent.posZ, rad, selector);
        if(l != null)
            for(int i = 0; i < l.size(); i++) {
                Entity e = l.get(i);
                if(!ent.canEntityBeSeen(ent))
                    l.remove(i);
            }
        return l;
    }
    
    public static Entity getNearestEntityTo(Entity pos, List<Entity> list) {
        Entity ent = null;
        double dist = Double.MAX_VALUE;
        if(list != null)
        for(Entity e : list) {
            double d = e.getDistanceSqToEntity(pos);
            if(d < dist) {
                ent = e;
                dist = d;
            }
        }
        return ent;
    }
    
    public static List<Entity> getEntitiesAround(World world, double x, double y, double z, double rad, IEntitySelector selector, Entity...exclusions) {
        rad *= 0.5;
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x - rad, y - rad, z - rad, x + rad, y + rad, z + rad);
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(exclusions.length > 0 ? exclusions[0] : null, box, selector);
        return list;
    }
    
    public static void applyEntityToPos(Entity entity, Vec3 vec3) {
        entity.setPosition(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }

    public static float getEntityVolume(Entity e) {
        return e.width * e.width * e.height;
    }

}

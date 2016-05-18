package cn.liutils.api.util;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cn.liutils.api.util.selector.EntitySelectorLiving;
import cn.liutils.api.util.selector.EntitySelectorPlayer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * All sorts of utility functions.
 * @author WeAthFolD
 */
public class GenericUtils {

    public static IEntitySelector selectorLiving = new EntitySelectorLiving(),
            selectorPlayer = new EntitySelectorPlayer();
    
    private static Random RNG = new Random();
    
    public static float wrapYawAngle(float f) {
        if(f > 180.0F)
            f %= 360F;
        else if(f < -180.0F)
            f = (360.0F - f) % 360F;
        return f;
    }
    
    public static MovingObjectPosition rayTraceBlocksAndEntities(World world, Vec3 vec1, Vec3 vec2) {
        MovingObjectPosition mop = rayTraceEntities(null, world, vec1, vec2);
        if(mop == null)
            return world.rayTraceBlocks(vec1, vec2);
        return mop;
    }
    
    public static MovingObjectPosition rayTraceBlocksAndEntities(IEntitySelector selector, World world, Vec3 vec1, Vec3 vec2, Entity... exclusion) {
        MovingObjectPosition mop = rayTraceEntities(selector, world, vec1, vec2, exclusion);
        if(mop == null)
            return world.rayTraceBlocks(vec1, vec2);
        return mop;
    }
    
    public static MovingObjectPosition traceBetweenEntities(Entity e1, Entity e2) {
        if(e1.worldObj != e2.worldObj) return null;
        Vec3 v1 = Vec3.createVectorHelper(e1.posX, e1.posY + e1.getEyeHeight(), e1.posZ),
                v2 = Vec3.createVectorHelper(e2.posX, e2.posY + e2.getEyeHeight(), e2.posZ);
        MovingObjectPosition mop = e1.worldObj.rayTraceBlocks(v1, v2);
        return mop;
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
    
    /**
     * 获取一个区域内所有的方块信息。
     * @param world
     * @param box
     * @return
     */
    public static Set<BlockPos> getBlocksWithinAABB(World world, AxisAlignedBB box) {
        Set<BlockPos> set = new HashSet();
        int minX = MathHelper.floor_double(box.minX), minY = MathHelper.floor_double(box.minY), minZ = MathHelper.floor_double(box.minZ),
            maxX = MathHelper.ceiling_double_int(box.maxX), maxY = MathHelper.ceiling_double_int(box.maxY), maxZ = MathHelper.ceiling_double_int(box.maxZ);
        for(int x = minX; x <= maxX; x++) {
            for(int y = minY; y <= maxY; y++) {
                for(int z = minZ; z <= maxZ; z++) {
                    Block id = world.getBlock(x, y, z);
                    if(id != Blocks.air) {
                        set.add(new BlockPos(x, y, z, id));
                    }
                }
            }
        }
        return set;
    }
    
    public static NBTTagCompound loadCompound(ItemStack stack) {
        if(stack.stackTagCompound == null)
            stack.stackTagCompound = new NBTTagCompound();
        return stack.stackTagCompound;
    }
    
    /**
     * Acquire a random sound string. the pathname goes like "soundname[a, b, c, d, ...]"
     * @param sndPath
     * @param countSounds
     * @return
     */
    public static String getRandomSound(String sndPath, int countSounds) {
        int a = RNG.nextInt(countSounds);
        return sndPath.concat(String.valueOf((char)('a' + a)));
    }
    
    /**
     * get MC-namespace splitted string.
     * @param str
     * @param isNamespace
     * @return splitted string
     */
    public static String splitString(String str, boolean isNamespace) {
        String[] strs = str.split(":");
        if(strs.length < 2) return str;
        return isNamespace ? strs[0] : strs[1];
    }
    
    public static <T> T safeFetchFrom(List<T> list, int id) {
        if(id >= 0 && id < list.size())
            return list.get(id);
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * Judge if the player is playing the client game and isn't opening any GUI.
     * @return
     */
    public static boolean isPlayerInGame() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        return player != null && Minecraft.getMinecraft().currentScreen == null;
    }
    
    public static Vec3 multiply(Vec3 vec, double factor) {
        return Vec3.createVectorHelper(vec.xCoord * factor, vec.yCoord * factor, vec.zCoord * factor);
    }
    
    public static int mini(int... arr) {
        int min = Integer.MAX_VALUE;
        for(int i : arr) 
            if(i < min) min = i;
        return min;
    }
}

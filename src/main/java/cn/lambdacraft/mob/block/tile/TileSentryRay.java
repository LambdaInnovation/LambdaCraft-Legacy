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
package cn.lambdacraft.mob.block.tile;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.mob.ModuleMob;
import cn.lambdacraft.mob.block.BlockSentryRay;
import cn.lambdacraft.mob.entity.EntitySentry;
import cn.lambdacraft.mob.network.MsgSentrySync;
import cn.liutils.api.command.LICommandBase;
import cn.liutils.api.util.BlockPos;
import cn.liutils.api.util.GenericUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 */
public class TileSentryRay extends TileEntity {

    public TileSentryRay linkedBlock;
    public boolean isLoaded;

    private int tickSinceLastUpdate = 0;

    public boolean isActivated;

    private int linkedX = 0, linkedY = 0, linkedZ = 0;
    
    private String placerName = "";

    public IEntitySelector selector = new IEntitySelector() {

        @Override
        public boolean isEntityApplicable(Entity entity) {
            boolean b = entity instanceof EntitySentry;
            if(b) {
                return ((EntitySentry)entity).placerName.equals(placerName);
            } else return false;
        }
        
    };
    
    private int tickSinceLastActivate = 0;
    
    private static ForgeDirection dirs[] = ForgeDirection.values();

    public void connectWith(TileSentryRay another) {
        this.linkedBlock = another;
    }

    public void noticeSentry() {
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(xCoord - 7.5, yCoord - 7.5, zCoord - 7.5, xCoord + 8.5, yCoord + 8.5, zCoord + 8.5);
        List<EntitySentry> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, box, selector);
        for(EntitySentry e : list) {
            e.activate();
        }
    }

    @Override
    public void updateEntity() {
        if (linkedBlock != null && linkedBlock.isInvalid()) {
            linkedBlock = null;
        }
        
        if (worldObj.isRemote)
            return;
        
        ++tickSinceLastActivate;
        if (linkedX != 0 || linkedY != 0 || linkedZ != 0) {
            TileEntity te = worldObj.getTileEntity(linkedX, linkedY, linkedZ);
            if (te != null && te instanceof TileSentryRay) {
                linkedBlock = (TileSentryRay) te;
                CBCMod.netHandler.sendToDimension(
                        new MsgSentrySync(this), worldObj.provider.dimensionId);
            }
            linkedX = linkedY = linkedZ = 0;
        }
        
        //Check if touched, and notice the entity to activate
        if(!isLoaded) {
            BlockPos bp = new BlockPos(this);
            if(ModuleMob.placeMap.containsKey(bp)) {
                EntityPlayer player = ModuleMob.placeMap.get(bp);
                placerName = player.getCommandSenderName();
                TileSentryRay t = ModuleMob.tileMap.get(player); //Aquire the last ray block that player places
                if(t == null) { 
                    ModuleMob.tileMap.put(player, this);
                    isActivated = false;
                    LICommandBase.sendChat(player, "sentry.another.name");
                } else {
                    if(t.worldObj.equals(worldObj)) {
                        ForgeDirection dir1 = dirs[this.blockMetadata], dir2 = dirs[t.blockMetadata];
                        Vec3 v1 = Vec3.createVectorHelper(xCoord + .5, yCoord + .5, zCoord + .5)
                                .addVector(dir1.offsetX, dir1.offsetY, dir1.offsetZ),
                                v2 = Vec3.createVectorHelper(t.xCoord + .5, t.yCoord + .5, t.zCoord + .5)
                                .addVector(dir2.offsetX, dir2.offsetY, dir2.offsetZ);
                        MovingObjectPosition pos = worldObj.rayTraceBlocks(v1, v2); //Peform a raytrace to see if there are blocks in the path
                        if(t.getDistanceFrom(xCoord, yCoord, zCoord) <= 400.0 && pos == null) { //Maxium 20 blocks away
                            linkedBlock = t;
                            ModuleMob.tileMap.remove(player);
                            LICommandBase.sendChat(player, "sentry.successful.name");
                            CBCMod.netHandler.sendToDimension(
                                    new MsgSentrySync(this), worldObj.provider.dimensionId);
                            isActivated = true;
                        } else {
                            LICommandBase.sendChat(player, "sentry.toofar.name");
                            ModuleMob.tileMap.remove(player);
                        }
                    } else {
                        ModuleMob.tileMap.put(player, this);
                        LICommandBase.sendChat(player, "sentry.diffdim.name");
                    }
                }
            } else {
                isActivated = false;
            }
            isLoaded = true;
        }

        if (linkedBlock != null) {
            Vec3 offset = getRayOffset();
            Vec3 vec0 = Vec3.createVectorHelper(xCoord, yCoord, zCoord).addVector(offset.xCoord, offset.yCoord, offset.zCoord);
            offset = linkedBlock.getRayOffset();
            Vec3 vec1 = Vec3.createVectorHelper(linkedBlock.xCoord, linkedBlock.yCoord,
                            linkedBlock.zCoord)
                    .addVector(offset.xCoord, offset.yCoord, offset.zCoord);
            MovingObjectPosition result = GenericUtils.rayTraceEntities( GenericUtils.selectorLiving, worldObj, vec0, vec1);
            if (result != null) {
                this.noticeSentry();
            }
        }
        if (++tickSinceLastUpdate > 20) {
            tickSinceLastUpdate = 0;
            CBCMod.netHandler.sendToDimension(
                    new MsgSentrySync(this), worldObj.provider.dimensionId);
        }
    }

    public Vec3 getRayOffset() {
        double x = 0.0, y = 0.0, z = 0.0;
        float var6 = BlockSentryRay.HEIGHT;
        float var7 = BlockSentryRay.WIDTH;
        switch (this.blockMetadata) {
        case 5:
            y += 0.5;
            z += 0.5;
            x += 2 * var6;
            break;
        case 4:
            y += 0.5;
            z += 0.5;
            x += 1 - 2 * var6;
            break;
        case 3:
            y += 0.5;
            x += 0.5;
            z += 2 * var6;
            break;
        case 2:
            y += 0.5;
            x += 0.5;
            z += 1 - 2 * var6;
            break;
        case 1:
            x += 0.5;
            z += 0.5;
            y += 2 * var6;
            break;
        case 0:
            x += 0.5;
            z += 0.5;
            y += 1 - 2 * var6;
            break;
        default:
            break;
        }
        return Vec3.createVectorHelper(x, y, z);
    }

    protected void sendChatToPlayer(EntityPlayer player, EntitySentry sentry) {
        if (worldObj.isRemote)
            return;
        StringBuilder sb = new StringBuilder(StatCollector.translateToLocal("sentry.head.name"));
        sb.append(EnumChatFormatting.RED).append(StatCollector.translateToLocal("sentry.id.name")).append(" : ")
                .append(EnumChatFormatting.RED).append(sentry.getEntityId())
                .append("\n").append(EnumChatFormatting.GREEN)
                .append(StatCollector.translateToLocal("sentry.raydep.name"));
        LICommandBase.sendChat(player, sb.toString());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        AxisAlignedBB bb = INFINITE_EXTENT_AABB;
        return bb;
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        linkedX = nbt.getInteger("linkX");
        linkedY = nbt.getInteger("linkY");
        linkedZ = nbt.getInteger("linkZ");
        isLoaded = nbt.getBoolean("isLoaded");
        isActivated = nbt.getBoolean("isActivated");
        placerName = nbt.getString("placerName");
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (linkedBlock != null) {
            nbt.setInteger("linkX", linkedBlock.xCoord);
            nbt.setInteger("linkY", linkedBlock.yCoord);
            nbt.setInteger("linkZ", linkedBlock.zCoord);
        }
        nbt.setBoolean("isLoaded", isLoaded);
        nbt.setBoolean("isActivated", isActivated);
        nbt.setString("placerName", placerName);
    }

}

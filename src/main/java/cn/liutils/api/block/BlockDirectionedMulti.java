/**
 * Copyright (C) Lambda-Innovation, 2013-2014
 * This code is open-source. Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 */
package cn.liutils.api.block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.liutils.api.util.GenericUtils;
import cn.liutils.core.proxy.LIClientProps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * A class of block that can be placed to player's facing direction, also it contains multiblock structure. <br/>
 * This block depends <strong>on only its main block(meta 0)</strong> to render, and you ought to provide the
 * offset for render (of each direction maybe) by functions provided <code>abstract</code>. <br/><br/>
 * You should define the sub blocks in your Ctor, 
 * using {@link #addSubBlock(int, int, int)} <br/>
 * It is usual (almost all the time) that we use <code>TileEntityRenderer</code> and disable normal renderers.
 * Render template is provided via <code>RenderTileDirMulti</code>,
 * a corresponding model-renderer implementation is <code>RenderDirMultiModelled</code>. </br>
 * Because MC use bytes to store metadata we often find the value overflows. Thus,
 * A <code>IMetadataProvider</code> interface is used upon block's TileEntity to override metadata.
 * You can use <code>TileDirectionedMulti</code> for fast impl.<br/>
 * BTW, Z+ is the facing direction~
 * @author WeAthFolD
 * @see cn.liutils.api.block.TileDirectionedMulti
 * @see cn.liutils.api.client.render.RenderTileDirMulti
 * @see cn.liutils.api.client.render.RenderDirMultiModelled
 * @see cn.liutils.api.block.IMetadataProvider
 * @see cn.liutils.api.block.TileDirectionedMulti
 */
public abstract class BlockDirectionedMulti extends Block implements ITileEntityProvider {
    /**
     * A list for all the subblocks. Automatically stores id0
     */
    private List<SubBlockPos> subBlocks = new ArrayList();
    
    /**
     * Determine if we use built-in offset-rotation for the renderer.
     */
    protected boolean useRotation = true;

    public BlockDirectionedMulti(Material mat) {
        super(mat);
        addSubBlock(0, 0, 0);
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * Get the render offset for front-view direction(Z+). Rotations will be automatically applied for others.
     * Sometimes you have to use getOffsetRotated(int) for better control.
     */
    public abstract Vec3 getRenderOffset();
    
    @SideOnly(Side.CLIENT)
    /**
     * Return the resulting offset vec for direction value dir. you can override this to get better control.
     * @param dir
     * @return
     */
    public Vec3 getOffsetRotated(int dir) {
        if(!useRotation) return getRenderOffset();
        Vec3 pos = getRenderOffset();
        if(dir == 3) 
            return Vec3.createVectorHelper(pos.xCoord, pos.yCoord, pos.zCoord);
        if(dir == 4)
            return Vec3.createVectorHelper(pos.zCoord, pos.yCoord, pos.xCoord);
        if(dir == 2)
            return Vec3.createVectorHelper(0, pos.yCoord, pos.zCoord);
        return Vec3.createVectorHelper(pos.zCoord, pos.yCoord, 0);
    }
    
    /**
     * Get the block facing direction via its metadata.
     */
    public static ForgeDirection getFacingDirection(int metadata) {
        return ForgeDirection.values()[dirMap[metadata & 0x03]];
    }
    
    /**
     * Return a array of size 3 containg the origin coordinate of the block.
     */
    protected int[] getOrigin(World world, int x, int y, int z, int meta) {
        SubBlockPos pos2 = applyRotation(getSubBlockByMeta(meta),
                getFacingDirection(meta).ordinal());
        if(pos2 == null)
            return null;
        return new int[] { x - pos2.offX, y - pos2.offY, z - pos2.offZ };
    }
    
    /**
     * Rotate the specific subBlock to a certain direction and return a instance representing the result.
     * <br/>Dosen't change the <code>subBlocks</code> list.
     */
    public SubBlockPos applyRotation(SubBlockPos pos, int dir) {
        if(pos == null)
            return null;
        if(dir == 3) 
            return new SubBlockPos(pos.offX, pos.offY, pos.offZ, pos.id);
        if(dir == 4)
            return new SubBlockPos(-pos.offZ, pos.offY, pos.offX, pos.id);
        if(dir == 2)
            return new SubBlockPos(-pos.offX, pos.offY, -pos.offZ, pos.id);
        return new SubBlockPos(pos.offZ, pos.offY, -pos.offX, pos.id);
    }
    
    /**
     * Get an array of 3 ints representing the subblock #id's coordinates, where the id is implied by meta.
     * @param x
     * @param y
     * @param z
     * @param meta
     * @return
     */
    protected int[] offset(int x, int y, int z, int meta) {
        SubBlockPos pos2 = applyRotation(subBlocks.get(meta >> 2), getFacingDirection(meta).ordinal());
        return new int[] {
                x + pos2.offX,
                y + pos2.offY,
                z + pos2.offZ
        };
    }
    
    /**
     * Get the real metadata of the multiBlock. (Sometimes overrided by Tile implementing IMetadataProvider)
     */
    public int getMetadata(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof IMetadataProvider) {
//            System.out.println("Get by te");
            return ((IMetadataProvider)te).getMetadata();
        }
//        System.out.println("Get by normal");
        return world.getBlockMetadata(x, y, z);
    }
    
    public void setMetadata(World world, int x, int y, int z, int meta) {
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof IMetadataProvider) {
            ((IMetadataProvider)te).setMetadata(meta);
            return;
        }
        world.setBlockMetadataWithNotify(x, y, z, meta, 0x03);
    }
    
    /**
     * Define a SubBlock.
     * Note that we don't process overlap situations, therefore you might wanna keep it safe yourself.
     */
    protected void addSubBlock(int ox, int oy, int oz) {
        SubBlockPos res = new SubBlockPos(ox, oy, oz, subBlocks.size());
        subBlocks.add(res);
    }
    
    /**
     * Get a SubBlock instance by its id.
     */
    protected SubBlockPos getSubBlock(int id) {
        return GenericUtils.safeFetchFrom(subBlocks, id);
    }
    
    /**
     * Get a SubBlock instance by a metadata of a block.
     */
    protected SubBlockPos getSubBlockByMeta(int meta) {
        return getSubBlock(meta >> 2);
    }
    
    /**
     * Get a block's subBlock id by its metadata.
     */
    protected int getSubBlockIdByMeta(int meta) {
        return meta >> 2;
    }
    
    protected List<SubBlockPos> getSubBlocks() {
        return subBlocks;
    }
    
    //---------------------Internal Impl.---------------------------
    private static final int[] dirMap = { 2, 5, 3, 4 };
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack)
    {
//        System.out.println(Item.getItemFromBlock(Blocks.bed));
//        System.out.println(Item.getItemFromBlock(Blocks.piston));
        int l = MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int metadata = l;
        world.setBlockMetadataWithNotify(x, y, z, metadata, 0x03);
        
        //if(world.isRemote)
        // w    return;
       
        int dir = getFacingDirection(metadata).ordinal();
        SubBlockPos arr[] = new SubBlockPos[subBlocks.size()];
        for(int i = 1; i < subBlocks.size(); ++i) {
            SubBlockPos bp = subBlocks.get(i);
            SubBlockPos bp2 = this.applyRotation(bp, dir);
            Block block = world.getBlock(x + bp2.offX, y + bp2.offY, z + bp2.offZ);
            if(!block.isReplaceable(world, x + bp2.offX, y + bp2.offY, z + bp2.offZ)) {
                this.dropBlockAsItem(world, x, y, z, new ItemStack(this));
                world.setBlockToAir(x, y, z);
//                System.out.println("Removed");
                return;
            }
            arr[i] = bp2;
        }
        
        for(int i = 1; i < subBlocks.size(); ++i) {
//            System.out.println("Set");
            arr[i].setMe(world, x, y, z, metadata | (arr[i].id << 2), this);
        }
    }
    
    private void clearAll(World world, int x, int y, int z, int dir) {
        Iterator<SubBlockPos> iter = subBlocks.iterator();
        iter.next();
        while(iter.hasNext()) {
            SubBlockPos pos0 = applyRotation(iter.next(), dir);
            pos0.destroyMe(world, x, y, z);
        }
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileDirectionedMulti)
            metadata = this.getMetadata(world, x, y, z);
        int[] crds = this.getOrigin(world, x, y, z, metadata);
        if(crds != null) {
            x = crds[0];
            y = crds[1];
            z = crds[2];
            int dir = BlockDirectionedMulti.getFacingDirection(metadata).ordinal();
//            System.out.println(dir);
            for(SubBlockPos bp : this.subBlocks) {
                SubBlockPos bp2 = this.applyRotation(bp, dir);
                bp2.destroyMe(world, x, y, z);
            }
        }
        super.breakBlock(world, x, y, z, block, metadata);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return LIClientProps.RENDER_TYPE_EMPTY;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public class SubBlockPos {
        public final int offX, offY, offZ; //in origin rotation
        public final int id;
        
        public SubBlockPos(int x, int y, int z, int id) {
            offX = x;
            offY = y;
            offZ = z;
            this.id = id;
        }
        
        public void destroyMe(World wrld, int x, int y, int z) {
            Block cmp = wrld.getBlock(x + offX, y + offY, z + offZ);
//            System.out.println(cmp);
            if(cmp == BlockDirectionedMulti.this)
                wrld.setBlockToAir(x + offX, y + offY, z + offZ);
        }
        
        public void setMe(World world, int x, int y, int z, int meta, Block block) {
            world.setBlock(x + offX, y + offY, z + offZ, block, meta, 0x03);
            setMetadata(world, x + offX, y + offY, z + offZ, meta);
        }
        
        public boolean meThere(World wrld, int x, int y, int z) {
            return wrld.getBlock(x + offX, y + offY,  z + offZ) == BlockDirectionedMulti.this;
        }
    }
    
    @Override 
    public int getMobilityFlag()
    {
        return 1;
    }
}

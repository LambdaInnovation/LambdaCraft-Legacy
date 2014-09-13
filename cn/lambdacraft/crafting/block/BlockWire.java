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
package cn.lambdacraft.crafting.block;

import java.util.List;

import cn.lambdacraft.core.block.BlockElectricalBase;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.crafting.block.tile.TileWire;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 * 
 */
public class BlockWire extends BlockElectricalBase {
	
	public static final float  WIDTH = 0.16666666F;

	public BlockWire() {
		super(Material.cake);
		this.setBlockTextureName("lambdacraft:wire");
		this.setStepSound(Block.soundTypeCloth);
		this.setBlockName("wire");
		this.setHardness(1.0F);
		this.setGuiId(-1);
		this.setTileType(TileWire.class);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return ClientProps.RENDER_TYPE_EMPTY;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	
    @Override
	public void addCollisionBoxesToList(World world, int par2, int par3, int par4, AxisAlignedBB aabb, List list, Entity par7Entity)
    {
    	//super.addCollisionBoxesToList(world, par2, par3, par4, aabb, list, par7Entity);
    	TileWire tile = (TileWire) world.getTileEntity(par2, par3, par4);
    	boolean[] arr = tile.renderSides;
    	float minA = 0.5F - WIDTH, maxA = 0.5F + WIDTH;
    	float
    	minX = arr[4] ? 0.0F : minA,
    	minY = arr[0] ? 0.0F : minA,
    	minZ = arr[2] ? 0.0F : minA,
    	maxX = arr[5] ? 1.0F : maxA,
    	maxY = arr[1] ? 1.0F : maxA,
    	maxZ = arr[3] ? 1.0F : maxA;
    	
    	setBlockBounds(minA, minY, minA, maxA, maxY, maxA);
    	super.addCollisionBoxesToList(world, par2, par3, par4, aabb, list, par7Entity);
    	setBlockBounds(minX, minA, minA, maxX, maxA, maxA);
    	super.addCollisionBoxesToList(world, par2, par3, par4, aabb, list, par7Entity);
    	setBlockBounds(minA, minA, minZ, maxA, maxA, maxZ);
    	super.addCollisionBoxesToList(world, par2, par3, par4, aabb, list, par7Entity);
    }
    
    
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess ws, int x, int y, int z) {
    	TileWire te = (TileWire) ws.getTileEntity(x, y, z);
    	boolean[] arr = te.renderSides;
    	float minA = 0.5F - WIDTH, maxA = 0.5F + WIDTH;
    			minX = arr[4] ? 0.0F : minA;
    	    	minY = arr[0] ? 0.0F : minA;
    	    	minZ = arr[2] ? 0.0F : minA;
    	    	maxX = arr[5] ? 1.0F : maxA;
    	    	maxY = arr[1] ? 1.0F : maxA;
    	    	maxZ = arr[3] ? 1.0F : maxA;
    }
	
}

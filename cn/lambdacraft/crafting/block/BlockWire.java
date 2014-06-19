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
import cn.lambdacraft.core.proxy.ClientProps;
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

	public BlockWire(int par1) {
		super(par1, Material.cake);
		this.setIconName("wire");
		this.setStepSound(Block.soundClothFootstep);
		this.setUnlocalizedName("wire");
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
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
    	super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    	TileWire tile = (TileWire) par1World.getBlockTileEntity(par2, par3, par4);
    	float minA = 0.5F - WIDTH, maxA = 0.5F + WIDTH;
    	float minX = 0.5F - WIDTH,
    	minY = 0.5F - WIDTH,
    	minZ = 0.5F - WIDTH,
    	maxX = 0.5F + WIDTH,
    	maxY = 0.5F + WIDTH,
    	maxZ = 0.5F + WIDTH;
    	
    	//X方向
    	boolean[] arr = tile.renderSides;
    	if(arr[5]) 
    		maxA = 1.0F;
    	if(arr[4])
    		minA = 0.0F;
    	if(arr[5] || arr[4]) {
    		setBlockBounds(minA, minY, minZ, maxA, maxY, maxZ);
    		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    	}
    	
    	if(arr[3])
    		maxA = 1.0F;
    	else maxA = 0.5F + WIDTH;
    	if(arr[2])
    		minA = 0.0F;
    	else minA = 0.5F - WIDTH;
    	if(arr[3] || arr[2]) {
    		setBlockBounds(minX, minY, minA, maxX, maxY, maxA);
    		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    	}
    	
    	if(arr[1])
    		maxA = 1.0F;
    	else maxA = 0.5F + WIDTH;
    	if(arr[0])
    		minA = 0.0F;
    	else minA = 0.5F - WIDTH;
    	if(arr[1] || arr[0]) {
    		setBlockBounds(minX, minA, minZ, maxX, maxA, maxZ);
    		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    	}
    }
    
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
    	minX = 0.5 - WIDTH;
    	minY = 0.5 - WIDTH;
    	minZ = 0.5 - WIDTH;
    	maxX = 0.5 + WIDTH;
    	maxY = 0.5 + WIDTH;
    	maxZ = 0.5 + WIDTH;
    }

}

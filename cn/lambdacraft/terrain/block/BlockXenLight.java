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
package cn.lambdacraft.terrain.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cn.lambdacraft.core.block.*;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.terrain.register.XenBlocks;
import cn.lambdacraft.terrain.tileentity.TileEntityXenLight;
import cn.liutils.api.block.LIBlockContainer;

/**
 * @author WeAthFolD
 *
 */
public class BlockXenLight extends LIBlockContainer {

	private boolean isBright;

	public BlockXenLight( boolean bright) {
		super(Material.cloth);
		isBright = bright;
		this.setStepSound(soundTypeCloth);
		this.setBlockName("xenLight");
		this.setBlockTextureName("lambdacraft:xenlight");
		this.setLightLevel(isBright ? 0.7F : 0.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityXenLight();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
    
    /**
     * Returns true if the given block ID is equivalent to this one. Example: redstoneTorchOn matches itself and
     * redstoneTorchOff, and vice versa. Most blocks only match themselves.
     */
    @Override
	public boolean isAssociatedBlock(Block par1)
    {
        return par1 == XenBlocks.light_on || par1 == XenBlocks.light_off;
    }
    
    @Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(XenBlocks.light_on);
    }
    
    @Override
	public int getRenderType()
    {
        return ClientProps.RENDER_TYPE_EMPTY;
    }
    
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
    	this.setBlockBounds(0.35F, 0.0F, 0.35F, 0.65F, isBright ? 1.0F : 0.4F, 0.65F);
    }
    
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
    public void onNeighborBlockChange(World world, int par2, int par3, int par4, Block par5) {
    	if(world.getBlock(par2, par3 - 1, par4) == Blocks.air){
    		world.setBlockToAir(par2, par3, par4);
    	}
    }

}

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
package cn.lambdacraft.xen.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cn.lambdacraft.core.block.CBCBlockContainer;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.xen.tileentity.TileEntityXenAmethyst;

/**
 * @author WeAthFolD
 *
 */
public class BlockXenAmethyst extends CBCBlockContainer {

	/**
	 * @param par1
	 * @param par2Material
	 */
	public BlockXenAmethyst(int par1) {
		super(par1, Material.rock);
		setHardness(1.0F);
		this.setLightValue(0.7F);
		setUnlocalizedName("xenAmethyst");
		setIconName("amethyst");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityXenAmethyst();
	}
	
	@Override
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
    	return this.canPlaceBlockAt(par1World, par2, par3, par4) && par5 == 0;
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
     * The type of render function that is called for this block
     */
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
    	this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
    }


}

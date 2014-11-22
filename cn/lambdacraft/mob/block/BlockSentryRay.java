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
package cn.lambdacraft.mob.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.mob.block.tile.TileSentryRay;
import cn.lambdacraft.mob.register.CBCMobItems;
import cn.liutils.api.block.LIBlockContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class BlockSentryRay extends LIBlockContainer {

	public static final float HEIGHT = 0.1F, WIDTH = 0.2F;
			
	/**
	 * @param par1
	 * @param par2Material
	 */
	public BlockSentryRay() {
		super(Material.rock);
		this.setCreativeTab(null);
		this.setHardness(1.0F);
		setBlockTextureName("lambdacraft:ss_1");
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4) {

		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		float var6 = HEIGHT;
		float var7 = WIDTH;
		if (var5 == 5) // X+
		{
			this.setBlockBounds(0.0F, 0.5F - var7, 0.5F - var7, var6 * 2.0F,
					0.5F + var7, 0.5F + var7); // (0, 0.5) (0.3, 0.7), (0.2,
												// 0.8)
		} else if (var5 == 4) // X-
		{
			this.setBlockBounds(1.0F - var6 * 2.0F, 0.5F - var7, 0.5F - var7,
					1.0F, 0.5F + var7, 0.5F + var7);
		} else if (var5 == 3) // Z+
		{
			this.setBlockBounds(0.5F - var7, 0.5F - var7, 0.0F, 0.5F + var7,
					0.5F + var7, var6 * 2.0F);
		} else if (var5 == 2) // Z-
		{
			this.setBlockBounds(0.5F - var7, 0.5F - var7, 1.0F - var6 * 2.0F,
					0.5F + var7, 0.5F + var7, 1.0F);
		} else if(var5 == 1) {//Y+
			this.setBlockBounds(0.5F - var7, 0.0F, 0.5F-var7,
					0.5F + var7, 2 * var6, 0.5F + var7);
		} else if(var5 == 0) {//Y-
			this.setBlockBounds(0.5F - var7, 1.0F - 2 * var6, 0.5F-var7,
					0.5F + var7, 1.0F, 0.5F + var7);
		}
	}
	
    @Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
    {
    	return CBCMobItems.sentrySyncer;
    }

	/* (non-Javadoc)
	 * @see net.minecraft.block.ITileEntityProvider#createNewTileEntity(net.minecraft.world.World)
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileSentryRay();
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
	public void onNeighborBlockChange(World world, int par2, int par3,
			int par4, Block par5) {
		super.onNeighborBlockChange(world, par2, par3, par4, par5);
		int meta = world.getBlockMetadata(par2, par3, par4); //ForgeDirecton真的是个好东西~ 
		ForgeDirection dir = ForgeDirection.values()[meta].getOpposite();
		if(!world.isBlockNormalCubeDefault(par2 + dir.offsetX, par3 + dir.offsetY, par4 + dir.offsetZ, false))
			world.destroyBlockInWorldPartially(par2, par3, par4, 0, 0);
	}

}

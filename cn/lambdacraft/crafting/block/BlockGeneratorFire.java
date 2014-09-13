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

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.core.block.BlockElectricalBase;
import cn.lambdacraft.core.prop.GeneralProps;
import cn.lambdacraft.crafting.block.tile.TileGeneratorFire;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 * 
 */
public class BlockGeneratorFire extends BlockElectricalBase {

	public IIcon iconSide, iconTop, iconBottom, iconMain;
	private ForgeDirection[] dirs = ForgeDirection.values();

	/**
	 * @param par1
	 * @param mat
	 */
	public BlockGeneratorFire() {
		super(Material.rock);
		setHardness(2.0F);
		setTileType(TileGeneratorFire.class);
		setGuiId(GeneralProps.GUI_ID_GENFIRE);
		setBlockName("genFire");
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
		int l = MathHelper
				.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l == 0) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
		}

		if (l == 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
		}

		if (l == 2) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
		}

		if (l == 3) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		iconSide = par1IconRegister.registerIcon("lambdacraft:genfire_side");
		iconTop = par1IconRegister.registerIcon("lambdacraft:genfire_top");
		iconBottom = par1IconRegister
				.registerIcon("lambdacraft:crafter_bottom");
		iconMain = par1IconRegister.registerIcon("lambdacraft:genfire_back");
		blockIcon = iconTop;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2) {
		if (par1 < 1)
			return iconBottom;
		if (par1 < 2)
			return iconTop;
		if (par2 == 0 || par1 == par2)
			return iconMain;
		return iconSide;
	}

}

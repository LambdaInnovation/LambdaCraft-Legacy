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
import net.minecraft.util.IIcon;
import cn.lambdacraft.core.block.BlockElectricalBase;
import cn.lambdacraft.core.prop.GeneralProps;
import cn.lambdacraft.crafting.block.tile.TileGeneratorSolar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 * 
 */
public class BlockGeneratorSolar extends BlockElectricalBase {

	public IIcon iconSide, iconTop, iconBottom;

	public BlockGeneratorSolar() {
		super(Material.rock);
		this.setHardness(2.0F);
		setTileType(TileGeneratorSolar.class);
		setGuiId(GeneralProps.GUI_ID_GENSOLAR);
		setBlockName("genSolar");
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		iconSide = par1IconRegister.registerIcon("lambdacraft:gensolar_main");
		iconTop = par1IconRegister.registerIcon("lambdacraft:gensolar_top");
		iconBottom = par1IconRegister
				.registerIcon("lambdacraft:crafter_bottom");
		blockIcon = iconTop;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2) {
		if (par1 < 1)
			return iconBottom;
		if (par1 < 2)
			return iconTop;
		return iconSide;
	}

}

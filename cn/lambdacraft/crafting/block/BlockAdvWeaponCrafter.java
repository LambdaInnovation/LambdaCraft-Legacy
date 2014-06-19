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

import cn.lambdacraft.crafting.block.tile.TileWeaponCrafter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * 高级武器合成机！
 * 
 * @author WeAthFolD
 * 
 */
public class BlockAdvWeaponCrafter extends BlockWeaponCrafter {

	public BlockAdvWeaponCrafter(int par1) {
		super(par1);
		setUnlocalizedName("advcrafter");
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		iconSide = par1IconRegister.registerIcon("lambdacraft:advcrafter_side");
		iconTop = par1IconRegister.registerIcon("lambdacraft:advcrafter_top");
		iconBottom = par1IconRegister.registerIcon("lambdacraft:advcrafter_bottom");
		iconMain = par1IconRegister.registerIcon("lambdacraft:advcrafter_main");
		blockIcon = iconTop;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2) {
		if (par1 < 1)
			return iconBottom;
		if (par1 < 2)
			return iconTop;
		if (par1 == par2)
			return iconMain;
		return iconSide;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileWeaponCrafter().setAdvanced(true);
	}

}

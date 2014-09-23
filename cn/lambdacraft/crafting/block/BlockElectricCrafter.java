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

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.prop.GeneralProps;
import cn.lambdacraft.crafting.block.tile.TileElCrafter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 * 
 */
public class BlockElectricCrafter extends BlockWeaponCrafter {

	public BlockElectricCrafter() {
		super();
		setBlockName("elcrafter");
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		iconSide = par1IconRegister.registerIcon("lambdacraft:ec_side");
		iconTop = par1IconRegister.registerIcon("lambdacraft:ec_top");
		iconBottom = par1IconRegister
				.registerIcon("lambdacraft:crafter_bottom");
		iconMain = par1IconRegister.registerIcon("lambdacraft:ec_main");
		blockIcon = iconTop;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2) {
		if (par1 < 1)
			return iconBottom;
		if (par1 < 2)
			return iconTop;
		if (par1 == par2)
			return iconMain;
		return iconSide;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		player.openGui(CBCMod.instance, GeneralProps.GUI_ID_EL_CRAFTER, world,
				x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileElCrafter();
	}

}

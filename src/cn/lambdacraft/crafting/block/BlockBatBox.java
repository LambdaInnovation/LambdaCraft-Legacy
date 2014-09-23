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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.block.BlockElectricalBase;
import cn.lambdacraft.core.prop.GeneralProps;
import cn.lambdacraft.crafting.block.tile.TileBatBox;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 * 
 */
public class BlockBatBox extends BlockElectricalBase {

	public IIcon iconSide, iconTop, iconBottom, iconMain;

	private final int type;

	/**
	 * @param par1
	 * @param mat
	 */
	public BlockBatBox(int typ) {
		super(Material.rock);
		this.setBlockName("batbox" + typ);
		type = typ;
		this.setGuiId(GeneralProps.GUI_ID_BATBOX);
		if (type != 0 && type != 1)
			throw new RuntimeException();

	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float what, float these, float are) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (player.isSneaking()) {
			world.setBlockMetadataWithNotify(x, y, z, side, 2);
			return true;
		}
		if (guiId == -1 || tileEntity == null)
			return false;
		player.openGui(CBCMod.instance, guiId, world, x, y, z);
		return true;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
		int l = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int pitch = Math.round(par5EntityLiving.rotationPitch);

		if (pitch >= 65) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
			return;
		} else if (pitch <= -65) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);
			return;
		}
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
	public TileEntity createNewTileEntity(World world, int i) {
		return (type == 1 ? new TileBatBox.TileBoxLarge()
				: new TileBatBox.TileBoxSmall());
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		if (type == 0) {
			iconSide = par1IconRegister.registerIcon("lambdacraft:batbox_side");
			iconTop = par1IconRegister.registerIcon("lambdacraft:batbox_top_s");
			iconBottom = par1IconRegister.registerIcon("lambdacraft:crafter_bottom");
			iconMain = par1IconRegister.registerIcon("lambdacraft:batbox_main_s");
		} else {
			iconSide = par1IconRegister.registerIcon("lambdacraft:batbox_large_side");
			iconTop = par1IconRegister.registerIcon("lambdacraft:batbox_top");
			iconBottom = par1IconRegister.registerIcon("lambdacraft:crafter_bottom");
			iconMain = par1IconRegister.registerIcon("lambdacraft:batbox_main");
		}
		blockIcon = iconTop;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2) {
		if (par1 == par2)
			return iconMain;
		if (par1 < 1)
			return iconBottom;
		if (par1 < 2)
			return iconTop;
		if(par1 == 0)
			return iconMain;
		return iconSide;
	}

}

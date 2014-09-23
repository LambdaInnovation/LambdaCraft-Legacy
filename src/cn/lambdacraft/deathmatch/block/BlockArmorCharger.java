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
package cn.lambdacraft.deathmatch.block;

import cn.lambdacraft.api.tile.IUseable;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.block.CBCBlockContainer;
import cn.lambdacraft.core.client.key.UsingUtils;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.core.prop.GeneralProps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 */
public class BlockArmorCharger extends CBCBlockContainer implements IUseable {

	protected final float WIDTH = 0.3F, HEIGHT = 0.4F, LENGTH = 0.08F;

	/**
	 * @param par1
	 * @param par2Material
	 */
	public BlockArmorCharger() {
		super(Material.rock);
		this.setBlockName("armorcharger");
		this.setBlockTextureName("charger");
		this.setHardness(2.0F);
		this.setGuiId(GeneralProps.GUI_ID_CHARGER);
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, Block par5) {
		TileArmorCharger te = (TileArmorCharger) par1World.getTileEntity(
				par2, par3, par4);
		if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
			te.isRSActivated = true;
		} else {
			te.isRSActivated = false;
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		player.openGui(CBCMod.instance, GeneralProps.GUI_ID_CHARGER, world, x,
				y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || !(tileEntity instanceof TileArmorCharger)) {
			return;
		}
		TileArmorCharger inventory = (TileArmorCharger) tileEntity;
		dropItems(world, x, y, z, inventory.slots);
		super.breakBlock(world, x, y, z, par5, par6);
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
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4) {

		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		float var6 = HEIGHT;
		float var7 = WIDTH;
		if (var5 == 5) // X+
		{
			this.setBlockBounds(0.0F, 0.5F - var6, 0.5F - var7, LENGTH * 2.0F,
					0.5F + var6, 0.5F + var7); // (0, 0.5) (0.3, 0.7), (0.2,
												// 0.8)
		} else if (var5 == 4) // X-
		{
			this.setBlockBounds(1.0F - LENGTH * 2.0F, 0.5F - var6, 0.5F - var7,
					1.0F, 0.5F + var6, 0.5F + var7);
		} else if (var5 == 3) // Z+
		{
			this.setBlockBounds(0.5F - var7, 0.5F - var6, 0.0F, 0.5F + var7,
					0.5F + var6, LENGTH * 2.0F);
		} else if (var5 == 2) // Z-
		{
			this.setBlockBounds(0.5F - var7, 0.5F - var6, 1.0F - LENGTH * 2.0F,
					0.5F + var7, 0.5F + var6, 1.0F);
		}

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
	public void onBlockUse(World world, EntityPlayer player, int bx, int by,
			int bz) {

		TileEntity te = world.getTileEntity(bx, by, bz);
		if (te == null)
			return;
		TileArmorCharger te2 = (TileArmorCharger) te;
		String path = te2.currentEnergy > 0 ? "lambdacraft:entities.suitchargeok"
				: "lambdacraft:entities.suitchargeno";
		world.playSoundAtEntity(player, path, 0.5F, 1.0F);
		UsingUtils.setBlockInUse(player, bx, by, bz);
		if (te2.currentEnergy > 0)
			te2.startUsing(player);

	}

	@Override
	public void onBlockStopUsing(World world, EntityPlayer player, int bx,
			int by, int bz) {
		TileEntity te = world.getTileEntity(bx, by, bz);
		if (te == null)
			return;
		TileArmorCharger te2 = (TileArmorCharger) te;
		te2.stopUsing(player);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileArmorCharger();
	}

}

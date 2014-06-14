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

import java.util.Random;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.block.CBCBlockContainer;
import cn.lambdacraft.core.proxy.GeneralProps;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 * 
 */
public class BlockMedkitFiller extends CBCBlockContainer {

	private Icon iconTop, iconBottom;

	/**
	 * @param par1
	 * @param par2Material
	 */
	public BlockMedkitFiller(int par1) {
		super(par1, Material.rock);
		this.setUnlocalizedName("medkitfiller");
		setHardness(2.0F);
		setCreativeTab(CBCMod.cct);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister
				.registerIcon("lambdacraft:medfiller_side");
		this.iconTop = par1IconRegister
				.registerIcon("lambdacraft:medfiller_top");
		this.iconBottom = par1IconRegister
				.registerIcon("lambdacraft:crafter_bottom");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2) {
		if (par1 < 1)
			return iconBottom;
		if (par1 < 2)
			return iconTop;
		return this.blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileMedkitFiller();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}

	private void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();

		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (!(tileEntity instanceof TileMedkitFiller)) {
			return;
		}
		TileMedkitFiller inventory = (TileMedkitFiller) tileEntity;

		for (ItemStack item : inventory.slots) {

			if (item != null && item.stackSize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z
						+ rz, new ItemStack(item.itemID, item.stackSize,
						item.getItemDamage()));

				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound(
							(NBTTagCompound) item.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		player.openGui(CBCMod.instance, GeneralProps.GUI_ID_MEDFILLER, world,
				x, y, z);
		return true;
	}

}

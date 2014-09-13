package cn.lambdacraft.crafting.block;

import java.util.Random;

import net.minecraft.block.Block;
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
import cn.lambdacraft.core.block.CBCBlockContainer;
import cn.lambdacraft.core.prop.GeneralProps;
import cn.lambdacraft.crafting.block.tile.TileWeaponCrafter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWeaponCrafter extends CBCBlockContainer {

	public IIcon iconSide, iconTop, iconBottom, iconMain;

	public enum CrafterIconType {

		CRAFTING, NOMATERIAL, NONE;
	}

	public BlockWeaponCrafter() {
		super(Material.iron);
		setBlockName("crafter");
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
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
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		iconSide = par1IconRegister.registerIcon("lambdacraft:crafter_side");
		iconTop = par1IconRegister.registerIcon("lambdacraft:crafter_top");
		iconBottom = par1IconRegister
				.registerIcon("lambdacraft:crafter_bottom");
		iconMain = par1IconRegister.registerIcon("lambdacraft:crafter_main");
		blockIcon = iconTop;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		player.openGui(CBCMod.instance, GeneralProps.GUI_ID_CRAFTER, world, x,
				y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
		TileWeaponCrafter te = (TileWeaponCrafter) world.getTileEntity(x, y, z);
		dropItems(world, x, y, z, te.inventory);
		
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@SideOnly(Side.CLIENT)
	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3,
			int par4, Random par5Random) {
		TileWeaponCrafter te = (TileWeaponCrafter) par1World
				.getTileEntity(par2, par3, par4);
		if (te.isBurning) {
			int l = par1World.getBlockMetadata(par2, par3, par4);
			float f = par2 + 0.5F;
			float f1 = par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
			float f2 = par4 + 0.5F;
			float f3 = 0.52F;
			float f4 = par5Random.nextFloat() * 0.6F - 0.3F;

			if (l == 4) {
				par1World.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D,
						0.0D, 0.0D);
				par1World.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D,
						0.0D, 0.0D);
			} else if (l == 5) {
				par1World.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D,
						0.0D, 0.0D);
				par1World.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D,
						0.0D, 0.0D);
			} else if (l == 2) {
				par1World.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D,
						0.0D, 0.0D);
				par1World.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D,
						0.0D, 0.0D);
			} else if (l == 3) {
				par1World.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D,
						0.0D, 0.0D);
				par1World.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D,
						0.0D, 0.0D);
			}
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
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileWeaponCrafter();
	}

}

/**
 * 
 */
package cn.lambdacraft.deathmatch.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.prop.ClientProps;
import cn.liutils.api.util.Motion3D;
import cn.weaponmod.api.WeaponHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Tripmine Weapon block.
 * 
 * @author WeAthFolD
 */
public class BlockTripmine extends BlockContainer {

	public static final float HEIGHT = 0.15F, WIDTH = 0.28F, RAY_RAD = 0.025F;

	public BlockTripmine() {
		super(Material.circuits);
		setCreativeTab(CBCMod.cct);
		setTickRandomly(true);
		setBlockName("tripmine");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister
				.registerIcon("lambdacraft:blockTripmine");
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(World par1World) {
		return 10;
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4,
			Random par5Random) {

	}

	public void updateRayRange(World par1World, int par2, int par3, int par4) {
		Motion3D begin = new Motion3D(par2, par3, par4, 0, 0, 0);
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		TileTripmine tileEntity = (TileTripmine) par1World.getTileEntity(par2, par3, par4);
		if (tileEntity == null)
			return;
		if (meta == 5)
			begin.motionX = 1.0;
		else if (meta == 4)
			begin.motionX = -1.0;
		else if (meta == 3)
			begin.motionZ = 1.0;
		else
			begin.motionZ = -1.0;
		Motion3D end = new Motion3D(begin).move(20.0);
		Vec3 vec1 = begin.getPosVec(par1World).addVector(0.0, 0.5, 0.0), vec2 = end
				.getPosVec(par1World).addVector(0.0, 0.5, 0.0);
		MovingObjectPosition result = par1World.rayTraceBlocks(vec1, vec2);
		if (result == null) {
			tileEntity.setEndCoords((int) end.posX, (int) end.posY,
					(int) end.posZ);
		} else {
			tileEntity
					.setEndCoords(result.blockX, result.blockY, result.blockZ);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {

	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, Block par5) {

		int var7 = par1World.getBlockMetadata(par2, par3, par4);
		Boolean var8 = false;
		// Check if the block still could exist
		if (!par1World.isSideSolid(par2 - 1, par3, par4, ForgeDirection.SOUTH)
				&& var7 == 5)
			var8 = true;
		if (!par1World.isSideSolid(par2 + 1, par3, par4, ForgeDirection.NORTH)
				&& var7 == 4)
			var8 = true;
		if (!par1World.isSideSolid(par2, par3, par4 - 1, ForgeDirection.EAST)
				&& var7 == 3)
			var8 = true;
		if (!par1World.isSideSolid(par2, par3, par4 + 1, ForgeDirection.WEST)
				&& var7 == 2)
			var8 = true;
		if (var8) {
			par1World.setBlockToAir(par2, par3, par4);
			return;
		}

	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			Block par5, int par6) {
		int var10 = par1World.getBlockMetadata(par2, par3, par4) & 3;
		int i = (var10 == 3 || var10 == 1) ? par2 : par4;
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		par1World.setBlockToAir(par2, par3, par4);
		WeaponHelper.Explode(par1World, null, 2.5F, 3.5F, par2, par3, par4,
				40, 0.5D, 1.0F);
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	@Override
	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3,
			int par4, int par5) {
		ForgeDirection dir = ForgeDirection.getOrientation(par5);
		return (dir == ForgeDirection.NORTH && par1World.isSideSolid(par2, par3,
				par4 + 1, ForgeDirection.NORTH))
				|| (dir == ForgeDirection.SOUTH && par1World.isSideSolid(par2, par3,
						par4 - 1, ForgeDirection.SOUTH))
				|| (dir == ForgeDirection.WEST && par1World.isSideSolid(par2 + 1, par3,
						par4, ForgeDirection.WEST))
				|| (dir == ForgeDirection.EAST && par1World.isSideSolid(par2 - 1, par3,
						par4, ForgeDirection.EAST));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4) {

		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		float var6 = HEIGHT;
		float var7 = WIDTH;
		if (var5 == 5) // X+
		{
			this.setBlockBounds(0.0F, 0.5F - var6, 0.5F - var7, var6 * 2.0F,
					0.5F + var6, 0.5F + var7); // (0, 0.5) (0.3, 0.7), (0.2,
												// 0.8)
		} else if (var5 == 4) // X-
		{
			this.setBlockBounds(1.0F - var6 * 2.0F, 0.5F - var6, 0.5F - var7,
					1.0F, 0.5F + var6, 0.5F + var7);
		} else if (var5 == 3) // Z+
		{
			this.setBlockBounds(0.5F - var7, 0.5F - var6, 0.0F, 0.5F + var7,
					0.5F + var6, var6 * 2.0F);
		} else if (var5 == 2) // Z-
		{
			this.setBlockBounds(0.5F - var7, 0.5F - var6, 1.0F - var6 * 2.0F,
					0.5F + var7, 0.5F + var6, 1.0F);
		}

	}

	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4,
			int par5, float par6, float par7, float par8, int par9) {
		byte var10 = 0;

		if (par5 == 2
				&& par1World.isSideSolid(par2, par3, par4 + 1, ForgeDirection.WEST,
						true)) {
			var10 = 2;
		}

		if (par5 == 3
				&& par1World.isSideSolid(par2, par3, par4 - 1, ForgeDirection.EAST,
						true)) {
			var10 = 3;
		}

		if (par5 == 4
				&& par1World.isSideSolid(par2 + 1, par3, par4, ForgeDirection.NORTH,
						true)) {
			var10 = 4;
		}

		if (par5 == 5 && par1World.isSideSolid(par2 - 1, par3, par4, ForgeDirection.SOUTH,
						true)) {
			var10 = 5;
		}

		return var10;
	}

	@Override
	public void onPostBlockPlaced(World par1World, int par2, int par3,
			int par4, int par5) {
		this.updateRayRange(par1World, par2, par3, par4);
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
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileTripmine();
	}

}

package cn.lambdacraft.deathmatch.block;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.liutils.api.util.GenericUtils;
import cn.liutils.api.util.Motion3D;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileTripmine extends TileEntity {

	public int endX, endY, endZ;
	private int ticksExisted;
	
	public TileTripmine() {
	}

	public void setEndCoords(int x, int y, int z) {
		endX = x;
		endY = y;
		endZ = z;
	}

	@Override
	public void updateEntity() {
		BlockTripmine blockType = (BlockTripmine) DMBlocks.blockTripmine;
		if (ticksExisted % 5 == 0)
			blockType.updateRayRange(worldObj, xCoord, yCoord, zCoord);
		if(++ticksExisted < 20 || worldObj.isRemote)
			return;
		Motion3D begin = new Motion3D(xCoord, yCoord, zCoord, 0, 0, 0);
		int meta = this.blockMetadata;
		Motion3D end = new Motion3D(endX, endY, endZ, 0, 0, 0);
		double minX, minY, minZ, maxX, maxY, maxZ;
		if (meta == 0)
			return;
		if (end.posX > begin.posX) {
			minX = begin.posX + 0.5;
			maxX = end.posX + 0.5;
		} else {
			minX = end.posX + 0.5;
			maxX = begin.posX + 0.5;
		}
		if (end.posY > begin.posY) {
			minY = begin.posY + 0.5;
			maxY = end.posY + 0.5;
		} else {
			minY = end.posY + 0.5;
			maxY = begin.posY + 0.5;
		}
		if (end.posZ > begin.posZ) {
			minZ = begin.posZ + 0.5;
			maxZ = end.posZ + 0.5;
		} else {
			minZ = end.posZ + 0.5;
			maxZ = begin.posZ + 0.5;
		}

		float RAY_RAD = BlockTripmine.RAY_RAD;
		minY = minY - RAY_RAD;
		maxY = maxY + RAY_RAD;
		if (meta == 5 || meta == 4) { // X
			minZ = minZ - RAY_RAD;
			maxZ = maxZ + RAY_RAD;
		} else {
			minX = minX - RAY_RAD;
			maxX = maxX + RAY_RAD;
		}
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(minX, minY, minZ,
				maxX, maxY, maxZ);
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(null, box,
				GenericUtils.selectorLiving);
		if (list != null && list.size() == 1) {
			getBlockType().breakBlock(worldObj, xCoord, yCoord, zCoord, getBlockType(), meta);
		}
	}

	public double getRayDistance() {
		double dx = xCoord - endX, dz = zCoord - endZ;
		return Math.sqrt(dx * dx + dz * dz);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public double getMaxRenderDistanceSquared() {
		return 4096.0D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
}

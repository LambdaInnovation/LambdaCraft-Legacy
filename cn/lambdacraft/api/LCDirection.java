package cn.lambdacraft.api;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public enum LCDirection {

	XN(0),

	XP(1),

	YN(2),

	YP(3),

	ZN(4),

	ZP(5);

	private int dir;
	public static final LCDirection[] directions;

	private LCDirection(int dir) {
		this.dir = dir;
	}

	public TileEntity applyToTileEntity(TileEntity tileEntity) {
		int[] coords = { tileEntity.xCoord, tileEntity.yCoord,
				tileEntity.zCoord };

		coords[(this.dir / 2)] += getSign();

		World world = tileEntity.getWorldObj();
		if ((world != null)
				&& (world.blockExists(coords[0], coords[1],
						coords[2]))) {
			return world.getTileEntity(coords[0], coords[1],
					coords[2]);
		}
		return null;
	}

	public LCDirection getInverse() {
		int inverseDir = this.dir - getSign();

		for (LCDirection direction : directions) {
			if (direction.dir == inverseDir)
				return direction;
		}

		return this;
	}

	public int toSideValue() {
		return (this.dir + 4) % 6;
	}

	private int getSign() {
		return this.dir % 2 * 2 - 1;
	}

	public ForgeDirection toForgeDirection() {
		return ForgeDirection.getOrientation(toSideValue());
	}

	static {
		directions = values();
	}
}
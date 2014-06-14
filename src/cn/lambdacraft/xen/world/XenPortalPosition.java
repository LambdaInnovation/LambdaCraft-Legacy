package cn.lambdacraft.xen.world;

import net.minecraft.util.ChunkCoordinates;
/**
 * 传送需要的一个类
 * @author F
 *
 */
public class XenPortalPosition extends ChunkCoordinates{
	
	public long along;
	final TeleporterXen teleporterXen;
	
	public XenPortalPosition(TeleporterXen teleporterXen, int par2, int par3, int par4, long par5)
	{
		super(par2, par3, par4);
		this.teleporterXen = teleporterXen;
		this.along = par5;
	}
}


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
package cn.lambdacraft.deathmatch.client.renderer;

import cn.lambdacraft.core.prop.ClientProps;
import cn.liutils.api.client.render.RendererSidedCube;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 * @author WeAthFolD
 * 
 */
public class RenderTileHeCharger extends RendererSidedCube {

	public RenderTileHeCharger(Block block) {
		super(block);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer#
	 * renderTileEntityAt(net.minecraft.tileentity.TileEntity, double, double,
	 * double, float)
	 */
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,
			double z, float f) {
		this.doRender(tileentity, x, y, z, f);
	}

	@Override
	public ResourceLocation getTexture(TileEntity te, int side, int metadata) {
		if (side == 0 || side == 1)
			return ClientProps.HECHARGER_TD_PATH;
		if (side == metadata)
			return ClientProps.HECHARGER_MAIN_PATH;
		if (metadata == 5 || metadata == 3)
			if (side == metadata - 2)
				return ClientProps.HECHARGER_BACK_PATH;
		return ClientProps.HECHARGER_SIDE_PATH;
	}

}

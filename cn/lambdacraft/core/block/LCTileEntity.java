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
package cn.lambdacraft.core.block;

import cn.lambdacraft.core.proxy.LCGeneralProps;
import net.minecraft.tileentity.TileEntity;

/**
 * @author WeAthFolD
 * 
 */
public abstract class LCTileEntity extends TileEntity {

	protected int lastTick;
	private int updateFreq = LCGeneralProps.updateRate;

	@Override
	public boolean canUpdate() {
		return true;
	}

	protected void setUpdateFreq(int freq) {
		updateFreq = freq;
	}

	@Override
	public void updateEntity() {
		if (++this.lastTick > updateFreq) {
			lastTick = 0;
			this.frequentUpdate();
			//this.onInventoryChanged();
			//TODO:寻找替代方法
		}
	}

	public void frequentUpdate() {

	}

	@Override
	public void onChunkUnload() {
		this.onTileUnload();
		super.onChunkUnload();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		onTileUnload();
	}

	public void onTileUnload() {
	};
}

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

import cn.lambdacraft.core.prop.GeneralProps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * @author WeAthFolD
 * 
 */
public abstract class CBCTileEntity extends TileEntity {

	protected int lastTick;
	private int updateFreq = GeneralProps.updateRate;
	

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
	
	protected void storeInventory(NBTTagCompound nbt, ItemStack[] inv, String key) {
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] == null)
				continue;
			nbt.setShort(key + "_id" + i, (short) Item.getIdFromItem(inv[i].getItem()));
			nbt.setByte(key + "_count" + i, (byte) inv[i].stackSize);
			nbt.setShort(key + "_damage" + i, (short) inv[i].getItemDamage());
		}
	}
	
	protected ItemStack[] restoreInventory(NBTTagCompound nbt, String key, int len) {
		ItemStack[] inv = new ItemStack[len];
		for (int i = 0; i < len; i++) {
			short id = nbt.getShort(key + "_id" + i), 
				damage = nbt.getShort(key + "_damage" + i);
			byte count = nbt.getByte(key + "_count" + i);
			if (id == 0)
				continue;
			ItemStack is = new ItemStack(Item.getItemById(id), count, damage);
			inv[i] = is;
		}
		return inv;
	}
}

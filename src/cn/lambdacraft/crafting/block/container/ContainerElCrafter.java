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
package cn.lambdacraft.crafting.block.container;

import cn.lambdacraft.crafting.block.BlockWeaponCrafter.CrafterIconType;
import cn.lambdacraft.crafting.block.tile.TileElCrafter;
import cn.lambdacraft.crafting.block.tile.TileWeaponCrafter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

/**
 * @author WeAthFolD
 * 
 */
public class ContainerElCrafter extends ContainerWeaponCrafter {

	protected TileElCrafter tileEntity;

	/**
	 * @param inventoryPlayer
	 * @param te
	 */
	public ContainerElCrafter(InventoryPlayer inventoryPlayer, TileElCrafter te) {
		super(inventoryPlayer, te);
		this.tileEntity = te;
	}

	@Override
	protected void addSlots(TileWeaponCrafter te) {
		// Crafting recipe slot
		for (int i = 0; i < 3; i++) {
			// output:0 4 8
			Slot s = addSlotToContainer(new SlotOutput(te, 9 + i, 63,
					14 + 18 * i));
			// input :123 567 9.10.11
			for (int j = 0; j < 3; j++) {
				addSlotToContainer(new SlotLocked(te, j + i * 3, 6 + 18 * j,
						14 + 18 * i));
			}

		}

		addSlotToContainer(new Slot(te, 13, 95, 50));
		addSlotToContainer(new SlotResult(te, 12, 95, 14));
		// Block Storage
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(te, 14 + 9 * i + j, 6 + 18 * j,
						72 + 18 * i));
			}
		}
	}

	@Override
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						6 + j * 18, 112 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 6 + i * 18, 170));
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			icrafting.sendProgressBarUpdate(this, 0, tileEntity.page);
			icrafting.sendProgressBarUpdate(this, 1,
					tileEntity.iconType.ordinal());
			icrafting.sendProgressBarUpdate(this, 2,
					tileEntity.currentEnergy / 3);
			icrafting.sendProgressBarUpdate(this, 3, tileEntity.heat);
			if (tileEntity.currentRecipe != null) {
				icrafting.sendProgressBarUpdate(this, 4,
						tileEntity.currentRecipe.heatRequired);
			} else
				icrafting.sendProgressBarUpdate(this, 4, 0);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			tileEntity.page = Math.abs(par2);
		} else if (par1 == 1) {
			tileEntity.iconType = CrafterIconType.values()[par2];
		} else if (par1 == 2) {
			tileEntity.currentEnergy = par2 * 3;
		} else if (par1 == 3) {
			tileEntity.heat = par2;
		} else if (par1 == 4)
			tileEntity.heatRequired = par2;
	}

}

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

import cn.lambdacraft.crafting.block.tile.TileBatBox;
import cn.lambdacraft.crafting.block.tile.TileElCrafter;
import cn.lambdacraft.crafting.block.tile.TileGeneratorFire;
import cn.lambdacraft.crafting.block.tile.TileGeneratorLava;
import cn.lambdacraft.crafting.block.tile.TileGeneratorSolar;
import cn.lambdacraft.crafting.block.tile.TileWeaponCrafter;
import cn.lambdacraft.crafting.client.gui.GuiBatBox;
import cn.lambdacraft.crafting.client.gui.GuiElectricCrafter;
import cn.lambdacraft.crafting.client.gui.GuiGenFire;
import cn.lambdacraft.crafting.client.gui.GuiGenLava;
import cn.lambdacraft.crafting.client.gui.GuiGenSolar;
import cn.lambdacraft.crafting.client.gui.GuiWeaponCrafter;
import cn.liutils.api.register.IGuiElement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 * 
 */
public class CRGuiElements {
	public static class ElementCrafter implements IGuiElement {

		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerWeaponCrafter(player.inventory,
					(TileWeaponCrafter) world.getTileEntity(x, y, z));
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiWeaponCrafter(player.inventory,
					(TileWeaponCrafter) world.getTileEntity(x, y, z));
		}
	}

	public static class ElementElCrafter implements IGuiElement {

		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerElCrafter(player.inventory,
					(TileElCrafter) world.getTileEntity(x, y, z));
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiElectricCrafter(player.inventory,
					(TileElCrafter) world.getTileEntity(x, y, z));
		}
	}

	public static class ElementGenFire implements IGuiElement {

		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerGenerator(
					(TileGeneratorFire) world.getTileEntity(x, y, z),
					player.inventory);
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiGenFire((TileGeneratorFire) world.getTileEntity(
					x, y, z), player.inventory);
		}
	}

	public static class ElementGenLava implements IGuiElement {

		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerGeneratorLava(
					(TileGeneratorLava) world.getTileEntity(x, y, z),
					player.inventory);
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiGenLava((TileGeneratorLava) world.getTileEntity(
					x, y, z), player.inventory);
		}
	}

	public static class ElementGenSolar implements IGuiElement {

		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerGeneratorSolar(
					(TileGeneratorSolar) world.getTileEntity(x, y, z),
					player.inventory);
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiGenSolar(
					(TileGeneratorSolar) world.getTileEntity(x, y, z),
					player.inventory);
		}
	}

	public static class ElementBatbox implements IGuiElement {

		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerBatBox((TileBatBox) world.getTileEntity(x,
					y, z), player.inventory);
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiBatBox(
					(TileBatBox) world.getTileEntity(x, y, z),
					player.inventory);
		}

	}

}

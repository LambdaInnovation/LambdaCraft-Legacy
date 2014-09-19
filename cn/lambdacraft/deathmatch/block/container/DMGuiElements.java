package cn.lambdacraft.deathmatch.block.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cn.lambdacraft.deathmatch.block.TileArmorCharger;
import cn.lambdacraft.deathmatch.block.TileHealthCharger;
import cn.lambdacraft.deathmatch.client.gui.GuiArmorCharger;
import cn.lambdacraft.deathmatch.client.gui.GuiHealthCharger;
import cn.liutils.api.register.IGuiElement;

public class DMGuiElements {

	public static class ElementArmorCharger implements IGuiElement {
		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerArmorCharger(
					(TileArmorCharger) world.getTileEntity(x, y, z),
					player.inventory);
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiArmorCharger(
					(TileArmorCharger) world.getTileEntity(x, y, z),
					new ContainerArmorCharger((TileArmorCharger) world
							.getTileEntity(x, y, z), player.inventory));
		}
	}

	public static class ElementHealthCharger implements IGuiElement {
		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerHealthCharger(
					(TileHealthCharger) world.getTileEntity(x, y, z),
					player.inventory);
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiHealthCharger(
					(TileHealthCharger) world.getTileEntity(x, y, z),
					new ContainerHealthCharger(
							(TileHealthCharger) world.getTileEntity(x, y,
									z), player.inventory));
		}
	}
}

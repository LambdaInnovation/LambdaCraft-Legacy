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
package cn.lambdacraft.crafting.client.gui;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.LCMod;
import cn.lambdacraft.core.proxy.LCClientProps;
import cn.lambdacraft.crafting.block.container.ContainerElCrafter;
import cn.lambdacraft.crafting.block.tile.TileElCrafter;
import cn.lambdacraft.crafting.network.MessageCrafter;
import cn.lambdacraft.crafting.recipe.RecipeWeapons;
import cn.liutils.api.client.gui.LIGuiButton;
import cn.liutils.api.client.gui.LIGuiContainer;
import cn.liutils.api.client.gui.LIGuiPart;
import cn.liutils.api.client.gui.IGuiTip;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

/**
 * @author WeAthFolD
 * 
 */
public class GuiElectricCrafter extends LIGuiContainer {

	public TileElCrafter tileEntity;

	public GuiElectricCrafter(InventoryPlayer inventoryPlayer,
			TileElCrafter tile) {
		super(new ContainerElCrafter(inventoryPlayer, tile));
		this.tileEntity = tile;
		xSize = 173;
		ySize = 192;
	}

	protected class TipEnergy implements IGuiTip {

		@Override
		public String getHeadText() {
			return EnumChatFormatting.RED
					+ StatCollector.translateToLocal("gui.curenergy.name");
		}

		@Override
		public String getTip() {
			return tileEntity.currentEnergy + "/" + TileElCrafter.MAX_STORAGE
					+ " EU";
		}

	}

	public class TipBehavior implements IGuiTip {

		@Override
		public String getHeadText() {
			return EnumChatFormatting.RED
					+ StatCollector.translateToLocal("gui.curtask.name");
		}

		@Override
		public String getTip() {
			switch (tileEntity.iconType) {
			case CRAFTING:
				return tileEntity.currentRecipe == null ? "" : 
					StatCollector.translateToLocal("gui.crafting.name") + tileEntity.currentRecipe.toString();
			case NOMATERIAL:
				return StatCollector.translateToLocal("gui.nomaterial.name");
			case NONE:
				return StatCollector.translateToLocal("gui.idle.name");
			default:
				return "";
			}
		}

	}

	public class TipHeat implements IGuiTip {

		@Override
		public String getHeadText() {
			return EnumChatFormatting.RED
					+ StatCollector.translateToLocal("gui.curheat.name");
		}

		@Override
		public String getTip() {
			return tileEntity.heatForRendering + "/" + tileEntity.maxHeat + " "
					+ StatCollector.translateToLocal("gui.heat.name");
		}

	}

	@Override
	public void initGui() {
		super.initGui();
		LIGuiPart up = new LIGuiButton("up", 85, 16, 4, 3), down = new LIGuiButton(
				"down", 85, 61, 4, 3), left = new LIGuiButton("left", 6, 6, 3,
				4), right = new LIGuiButton("right", 158, 6, 3, 4), heat = new LIGuiPart(
				"heat", 138, 17, 6, 46), energy = new LIGuiPart("energy", 116,
				17, 6, 46), behavior = new LIGuiPart("behavior", 124, 16, 6, 8);
		addElements(up, down, left, right, heat, energy, behavior);
		this.setElementTip("heat", new TipHeat());
		this.setElementTip("energy", new TipEnergy());
		this.setElementTip("behavior", new TipBehavior());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		String currentPage = StatCollector.translateToLocal(RecipeWeapons
				.getDescription(tileEntity.page));
		fontRendererObj.drawString(currentPage,
				85 - fontRendererObj.getStringWidth(currentPage) / 2, 3, 0xff9843);
		super.drawGuiContainerForegroundLayer(par1, par2);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(LCClientProps.GUI_ELCRAFTER_PATH);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		int height = tileEntity.heatForRendering * 46 / tileEntity.maxHeat;
		if (height > 0) {
			drawTexturedModalRect(x + 138, y + 63 - height, 181, 0, 6, height);
		}
		height = tileEntity.currentEnergy * 46 / TileElCrafter.MAX_STORAGE;
		if (height > 0) {
			drawTexturedModalRect(x + 116, y + 63 - height, 174, 0, 6, height);
		}
		if (tileEntity.isCrafting && tileEntity.currentRecipe != null) {
			if (tileEntity.heatRequired > 0) {
				height = tileEntity.heatRequired * 46 / tileEntity.maxHeat;
				drawTexturedModalRect(x + 136, y + 63 - height, 207, 1, 6, 3);
			}
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	public void onButtonClicked(LIGuiButton button) {
		if (button.name == "up" || button.name == "down") {
			boolean isDown = button.name == "down" ? true : false;
			LCMod.netHandler.sendToServer(new MessageCrafter(tileEntity, 0, isDown));
			return;
		}
		if (button.name == "left" || button.name == "right") {
			boolean isForward = button.name == "right" ? true : false;
			LCMod.netHandler.sendToServer(new MessageCrafter(tileEntity, 1, isForward));
			return;
		}
	}

}

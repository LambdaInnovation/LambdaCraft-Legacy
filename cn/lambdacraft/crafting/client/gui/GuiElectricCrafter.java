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

import java.util.Set;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.crafting.block.container.ContainerElCrafter;
import cn.lambdacraft.crafting.block.tile.TileElCrafter;
import cn.lambdacraft.crafting.network.MsgCrafterClient;
import cn.lambdacraft.crafting.recipe.RecipeWeapons;
import cn.liutils.api.client.gui.GuiContainerSP;
import cn.liutils.api.client.gui.IGuiTip;
import cn.liutils.api.client.gui.part.LIGuiButton;
import cn.liutils.api.client.gui.part.LIGuiPart;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 * 
 */
public class GuiElectricCrafter extends GuiContainerSP {

	public TileElCrafter tileEntity;

	public GuiElectricCrafter(InventoryPlayer inventoryPlayer,
			TileElCrafter tile) {
		super(172, 192, new ContainerElCrafter(inventoryPlayer, tile));
		this.tileEntity = tile;
	}

	protected class TipEnergy implements IGuiTip {

		@Override
		public String getHeader() {
			return EnumChatFormatting.RED
					+ StatCollector.translateToLocal("gui.curenergy.name");
		}

		@Override
		public String getText() {
			return tileEntity.currentEnergy + "/" + TileElCrafter.MAX_STORAGE
					+ " EU";
		}

	}
	
	public class TipHeat implements IGuiTip {

		@Override
		public String getHeader() {
			return EnumChatFormatting.RED
					+ StatCollector.translateToLocal("gui.curheat.name");
		}

		@Override
		public String getText() {
			return tileEntity.heatForRendering + "/" + tileEntity.maxHeat + " "
					+ StatCollector.translateToLocal("gui.heat.name");
		}

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
		RenderUtils.loadTexture(ClientProps.GUI_ELCRAFTER_PATH);
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
	public void onPartClicked(LIGuiPart button, float mx, float my) {
		if (button.name == "up" || button.name == "down") {
			boolean isDown = button.name == "down" ? true : false;
			CBCMod.netHandler.sendToServer(new MsgCrafterClient(0, tileEntity, isDown));
			return;
		}
		if (button.name == "left" || button.name == "right") {
			boolean isForward = button.name == "right" ? true : false;
			CBCMod.netHandler.sendToServer(new MsgCrafterClient(1, tileEntity, isForward));
			return;
		}
	}

	@Override
	protected void addElements(Set<LIGuiPart> set) {
		
		set.add(new LIGuiButton("up", 85, 16, 4, 3));
		set.add(new LIGuiButton("down", 85, 61, 4, 3));
		set.add(new LIGuiButton("left", 6, 6, 3,4));
		set.add(new LIGuiButton("right", 158, 6, 3, 4));
		set.add(new LIGuiPart("heat", 138, 17, 6, 46).setTip(new TipHeat()));
		set.add(new LIGuiPart("energy", 116, 17, 6, 46).setTip(new TipEnergy()));
		set.add(new LIGuiPart("behavior", 124, 16, 6, 8));
		
	}

}

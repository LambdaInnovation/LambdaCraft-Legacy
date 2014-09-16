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

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.crafting.block.container.ContainerBatBox;
import cn.lambdacraft.crafting.block.tile.TileBatBox;
import cn.liutils.api.client.gui.GuiContainerSP;
import cn.liutils.api.client.gui.IGuiTip;
import cn.liutils.api.client.gui.part.LIGuiPart;

/**
 * @author WeAthFolD
 * 
 */
public class GuiBatBox extends GuiContainerSP {

	private TileBatBox te;

	/**
	 * @param par1Container
	 */
	public GuiBatBox(TileBatBox box, InventoryPlayer inv) {
		super(173, 165, new ContainerBatBox(box, inv));
		te = box;
	}

	private class TipEnergy implements IGuiTip {

		@Override
		public String getHeader() {
			return EnumChatFormatting.RED + StatCollector.translateToLocal("gui.curenergy.name");
		}

		@Override
		public String getText() {
			return te.currentEnergy + "/" + te.maxStorage + " EU";
		}

	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		String guiName = StatCollector.translateToLocal(te.getInventoryName());
		this.fontRendererObj.drawString(guiName, 7, 7, 0xdadada);
		super.drawGuiContainerForegroundLayer(par1, par2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.client.gui.inventory.GuiContainer#
	 * drawGuiContainerBackgroundLayer(float, int, int)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(ClientProps.GUI_BATBOX_PATH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		if (te.currentEnergy > 0) {
			int len = te.currentEnergy * 68 / te.maxStorage;
			this.drawTexturedModalRect(x + 53, y + 38, 173, 10, len, 7);
		}

		this.drawElements(i, j);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	protected void addElements(Set<LIGuiPart> set) {
		LIGuiPart energy = new LIGuiPart("energy", 53, 38, 68, 7);
		energy.setTip(new TipEnergy());
		set.add(energy);
	}

	@Override
	protected void onPartClicked(LIGuiPart part, float mx, float my) {
		
	}

}

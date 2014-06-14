/** 
4 * Copyright (c) LambdaCraft Modding Team, 2013
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
package cn.lambdacraft.deathmatch.client.gui;

import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.deathmatch.block.TileMedkitFiller;
import cn.lambdacraft.deathmatch.network.NetMedFillerClient;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.liutils.api.client.gui.LIGuiButton;
import cn.liutils.api.client.gui.LIGuiContainer;
import cn.liutils.api.client.gui.LIGuiPart;
import cn.liutils.api.client.gui.IGuiTip;


/**
 * @author WeAthFolD
 * 
 */
public class GuiMedFiller extends LIGuiContainer {

	TileMedkitFiller te;

	/**
	 * @param par1Container
	 */
	public GuiMedFiller(TileMedkitFiller t, Container par1Container) {
		super(par1Container);
		this.xSize = 200;
		this.ySize = 207;
		te = t;
	}

	class TipEnergy implements IGuiTip {

		@Override
		public String getHeadText() {
			return "";
		}

		@Override
		public String getTip() {
			return StatCollector.translateToLocal("gui.curenergy.name") + ": "
					+ te.getCurrentEnergy() + "/" + te.getMaxEnergy() + " EU";
		}

	}

	class TipBehavior implements IGuiTip {

		@Override
		public String getHeadText() {
			return EnumChatFormatting.RED
					+ StatCollector.translateToLocal("gui.rsbehavior.name");
		}

		@Override
		public String getTip() {
			return StatCollector
					.translateToLocal(te.currentBehavior.toString());
		}

	}

	@Override
	public void initGui() {
		super.initGui();
		LIGuiPart energy = new LIGuiPart("energy", 112, 22, 7, 51), behavior = new LIGuiButton(
				"behavior", 171, 15, 23, 10).setDownCoords(223, 57)
				.setTextureCoords(200, 57);
		this.addElement(energy);
		this.addElement(behavior);
		this.setElementTip("energy", new TipEnergy());
		this.setElementTip("behavior", new TipBehavior());
	}

	/**
	 * Called when the mouse is clicked.
	 */
	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.lambdacraft.core.gui.CBCGuiContainer#onButtonClicked(cn.lambdacraft.core.gui
	 * .CBCGuiButton)
	 */
	@Override
	public void onButtonClicked(LIGuiButton button) {
		if (button.name == "behavior") {
			NetMedFillerClient.sendPacket(te);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		// String blockName = DMBlocks.medkitFiller.getLocalizedName();
		String blockName = DMBlocks.medkitFiller.getLocalizedName();
		fontRenderer.drawString(EnumChatFormatting.RED + blockName,
				100 - fontRenderer.getStringWidth(blockName) / 2, 2, 0xffffff);
		blockName = StatCollector.translateToLocal("container.inventory");
		fontRenderer.drawString(EnumChatFormatting.DARK_GRAY + blockName, 8,
				85, 0xffffff);
		super.drawGuiContainerForegroundLayer(par1, par2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.client.gui.inventory.GuiContainer#
	 * drawGuiContainerBackgroundLayer(float, int, int)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int a, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.renderEngine.bindTexture(ClientProps.GUI_MEDFILLER_PATH);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int height = te.currentEnergy * 51 / te.maxEnergy;
		this.drawTexturedModalRect(x + 112, y + 73 - height, 200, 57 - height,
				7, height);

		for (int i = 0; i < 3; i++) {
			int length = te.progresses[i] * 17 / TileMedkitFiller.CRAFT_LIMIT;
			this.drawTexturedModalRect(x + 29 * (i + 1), y + 77, 200, 3,
					length, 3);
		}
		this.drawElements();
	}
}

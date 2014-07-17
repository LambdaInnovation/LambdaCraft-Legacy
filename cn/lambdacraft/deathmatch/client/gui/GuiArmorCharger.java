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
package cn.lambdacraft.deathmatch.client.gui;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.LCMod;
import cn.lambdacraft.core.proxy.LCClientProps;
import cn.lambdacraft.deathmatch.block.TileArmorCharger;
import cn.lambdacraft.deathmatch.network.MessageCharger;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.liutils.api.client.gui.LIGuiButton;
import cn.liutils.api.client.gui.LIGuiContainer;
import cn.liutils.api.client.gui.LIGuiPart;
import cn.liutils.api.client.gui.IGuiTip;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

/**
 * @author Administrator
 * 
 */
public class GuiArmorCharger extends LIGuiContainer {

	TileArmorCharger te;

	/**
	 * @param par1Container
	 */
	public GuiArmorCharger(TileArmorCharger t, Container par1Container) {
		super(par1Container);
		this.xSize = 176;
		this.ySize = 166;
		te = t;
	}

	class TipEnergy implements IGuiTip {

		@Override
		public String getHeadText() {
			return StatCollector.translateToLocal("gui.curenergy.name");
		}

		@Override
		public String getTip() {
			return te.currentEnergy + "/" + TileArmorCharger.ENERGY_MAX + " EU";
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
		LIGuiPart behavior = new LIGuiPart("behavior", 80, 28, 64, 10)
				.setDraw(false), redstone = new LIGuiButton("redstone", 153,
				5, 19, 10).setDownCoords(180, 13).setTextureCoords(153, 5);
		this.addElement(behavior);
		this.addElement(redstone);
		this.setElementTip("behavior", new TipEnergy());
		this.setElementTip("redstone", new TipBehavior());
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
		if (button.name == "redstone") {
			te.nextBehavior();
			LCMod.netHandler.sendToServer(new MessageCharger(te));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		String blockName = EnumChatFormatting.DARK_GRAY
				+ StatCollector.translateToLocal(DMBlocks.armorCharger
						.getLocalizedName());
		fontRendererObj.drawString(blockName,
				88 - fontRendererObj.getStringWidth(blockName) / 2, 5, 0x969494);
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
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(LCClientProps.GUI_ARMORCHARGER_PATH);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		this.drawElements();

		int length = te.currentEnergy * 64 / TileArmorCharger.ENERGY_MAX;
		this.drawTexturedModalRect(x + 80, y + 28, 176, 0, length, 10);

		if (te.isCharging) {
			int height = (int) (te.getWorldObj().getWorldTime() % 43);
			this.drawTexturedModalRect(x + 29, y + 21, 176, 56, 43, height);
		}
	}

}

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

import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.crafting.block.container.ContainerGenerator;
import cn.lambdacraft.crafting.block.tile.TileGeneratorFire;
import cn.lambdacraft.crafting.register.CBCBlocks;
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
public class GuiGenFire extends LIGuiContainer {

	TileGeneratorFire te;

	private class TipEnergy implements IGuiTip {

		@Override
		public String getHeadText() {
			return EnumChatFormatting.RED + StatCollector.translateToLocal("gui.curenergy.name");
		}

		@Override
		public String getTip() {
			return te.currentEnergy + "/" + te.maxStorage + " EU";
		}

	}

	public GuiGenFire(TileGeneratorFire gen, InventoryPlayer inv) {
		super(new ContainerGenerator(gen, inv));
		te = gen;
		this.xSize = 173;
		this.ySize = 178;
	}

	@Override
	public void initGui() {
		super.initGui();
		LIGuiPart energy = new LIGuiPart("energy", 75, 15, 14, 50);
		this.addElement(energy);
		this.setElementTip("energy", new TipEnergy());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		String guiName = CBCBlocks.genFire.getLocalizedName();
		this.fontRenderer.drawString(guiName, 7, 7, 0xff9944);
		super.drawGuiContainerForegroundLayer(par1, par2);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(ClientProps.GUI_GENFIRE_PATH);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		int len = 0;
		if (te.maxBurnTime > 0) {
			len = te.tickLeft * 39 / te.maxBurnTime;
			this.drawTexturedModalRect(x + 109, y + 52, 173, 0, len, 3);
		}
		len = te.currentEnergy * 50 / te.maxStorage;
		if (len > 0)
			this.drawTexturedModalRect(x + 75, y + 65 - len, 173, 55 - len, 14,
					len);
		this.drawElements();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	public void onButtonClicked(LIGuiButton button) {
	}

}

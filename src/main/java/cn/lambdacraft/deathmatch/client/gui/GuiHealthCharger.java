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

import java.util.Set;

import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.deathmatch.block.TileHealthCharger;
import cn.liutils.api.client.gui.GuiContainerSP;
import cn.liutils.api.client.gui.IGuiTip;
import cn.liutils.api.client.gui.part.LIGuiPart;


/**
 * @author WeAthFolD
 * 
 */
public class GuiHealthCharger extends GuiContainerSP {

	TileHealthCharger te;

	/**
	 * @param par1Container
	 */
	public GuiHealthCharger(TileHealthCharger t, Container par1Container) {
		super(176, 166, par1Container);
		te = t;
	}

	class TipEnergy implements IGuiTip {

		@Override
		public String getHeader() {
			return "";
		}

		@Override
		public String getText() {
			return StatCollector.translateToLocal("gui.curenergy.name") + ": "
					+ te.currentEnergy + "/" + TileHealthCharger.ENERGY_MAX
					+ " EU";
		}

	}

	class TipMain implements IGuiTip {
		@Override
		public String getHeader() {
			return EnumChatFormatting.RED
					+ StatCollector.translateToLocal("gui.hemain.name");
		}

		@Override
		public String getText() {
			return te.mainEff + "/" + TileHealthCharger.HEALTH_MAX + " HP";
		}
	}

	class TipSide implements IGuiTip {
		@Override
		public String getHeader() {
			return EnumChatFormatting.RED
					+ StatCollector.translateToLocal("gui.heside.name");
		}

		@Override
		public String getText() {
			return te.sideEff / 20.0F + "/" + TileHealthCharger.EFFECT_MAX
					/ 20.0F + " s";
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		mc.renderEngine.bindTexture(ClientProps.GUI_HECHARGER_PATH);
		if (te.currentEnergy == 0) {
			this.drawTexturedModalRect(9, 7, 190, 0, 60, 72);
		} else super.drawGuiContainerForegroundLayer(par1, par2);
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
		mc.renderEngine.bindTexture(ClientProps.GUI_HECHARGER_PATH);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		this.drawElements(i, j);

		if (te.currentEnergy > 0) {
			int len = te.currentEnergy * 48 / TileHealthCharger.ENERGY_MAX;
			this.drawTexturedModalRect(x + 154, y + 55 - len, 176, 93 - len, 5,
					len);
		}
		if (te.mainEff > 0) {
			int len = te.mainEff * 46 / TileHealthCharger.HEALTH_MAX;
			this.drawTexturedModalRect(x + 20, y + 54 - len, 176, 46 - len, 14,
					len);
		}
		if (te.sideEff > 0) {
			int len = te.sideEff * 46 / TileHealthCharger.EFFECT_MAX;
			this.drawTexturedModalRect(x + 42, y + 54 - len, 176, 140 - len,
					14, len);
		}
		if (te.prgAddMain > 0) {
			int len = te.prgAddMain * 17 / TileHealthCharger.PROGRESS_TIME;
			this.drawTexturedModalRect(x + 36, y + 77 - len, 176, 157 - len,
					14, len);
		}
		if (te.prgAddSide > 0) {
			int len = te.prgAddSide * 17 / TileHealthCharger.PROGRESS_TIME;
			this.drawTexturedModalRect(x + 58, y + 77 - len, 176, 157 - len,
					14, len);
		}
	}

	@Override
	protected void addElements(Set<cn.liutils.api.client.gui.part.LIGuiPart> set) {
		LIGuiPart 
		behavior = new LIGuiPart("behavior", 154, 8, 5, 48).setTip(new TipEnergy()), 
		main = new LIGuiPart("main", 20, 8, 14, 46).setTip(new TipMain()), 
		side = new LIGuiPart("side", 42, 8,14, 46).setTip(new TipSide());
		set.add(behavior);
		set.add(main);
		set.add(side);
	}

	@Override
	protected void onPartClicked(cn.liutils.api.client.gui.part.LIGuiPart part,
			float mx, float my) {
		// TODO Auto-generated method stub
		
	}

}
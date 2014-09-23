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
import cn.lambdacraft.crafting.block.BlockWeaponCrafter.CrafterIconType;
import cn.lambdacraft.crafting.block.container.ContainerWeaponCrafter;
import cn.lambdacraft.crafting.block.tile.TileWeaponCrafter;
import cn.lambdacraft.crafting.network.MsgCrafterClient;
import cn.lambdacraft.crafting.recipe.RecipeWeapons;
import cn.liutils.api.client.gui.GuiContainerSP;
import cn.liutils.api.client.gui.IGuiTip;
import cn.liutils.api.client.gui.part.LIGuiButton;
import cn.liutils.api.client.gui.part.LIGuiPart;
import cn.liutils.api.client.util.HudUtils;
import cn.liutils.api.client.util.RenderUtils;


/**
 * 武器合成机和高级武器合成机的Gui类。
 * 
 * @author WeAthFolD
 */
public class GuiWeaponCrafter extends GuiContainerSP {

	public TileWeaponCrafter te;
	public InventoryPlayer inv;

	public class TipHeat implements IGuiTip {

		@Override
		public String getHeader() {
			return EnumChatFormatting.RED
					+ StatCollector.translateToLocal("gui.curheat.name");
		}

		@Override
		public String getText() {
			return te.heatForRendering + "/" + te.maxHeat + " "
					+ StatCollector.translateToLocal("gui.heat.name");
		}

	}

	public class TipBehavior implements IGuiTip {

		@Override
		public String getHeader() {
			return EnumChatFormatting.RED
					+ StatCollector.translateToLocal("gui.curtask.name");
		}

		@Override
		public String getText() {
			switch (te.iconType) {
			case CRAFTING:
				return StatCollector.translateToLocal("gui.crafting.name")
						+ te.currentRecipe.toString();
			case NOMATERIAL:
				return StatCollector.translateToLocal("gui.nomaterial.name");
			case NONE:
				return StatCollector.translateToLocal("gui.idle.name");
			default:
				return "";
			}
		}

	}

	public GuiWeaponCrafter(InventoryPlayer inventoryPlayer,
			TileWeaponCrafter tileEntity) {
		super(200, 250, new ContainerWeaponCrafter(inventoryPlayer, tileEntity));
		te = tileEntity;
		inv = inventoryPlayer;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		String storage = StatCollector
				.translateToLocal("gui.crafter_storage.name");
		String currentPage = StatCollector.translateToLocal(RecipeWeapons
				.getDescription(te.page));
		this.fontRendererObj.drawString(storage, 8, 88, 4210752);
		fontRendererObj.drawString(currentPage,
				100 - fontRendererObj.getStringWidth(currentPage) / 2, 1, 4210752);
		super.drawGuiContainerForegroundLayer(par1, par2);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		if (!this.te.isLoad)
			return;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtils.loadTexture(ClientProps.GUI_WEAPONCRAFTER_PATH);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		HudUtils.setTextureResolution(256, 256);
		this.drawElements(i, j);
		int dy = 0;
		if (te.iconType == CrafterIconType.NOMATERIAL)
			dy = 60;
		else if (te.iconType == CrafterIconType.CRAFTING)
			dy = 16;
		else
			dy = 38;
		drawTexturedModalRect(x + 160, y + 16, 232, dy, 8, 18);

		int height = te.heatForRendering * 64 / te.maxHeat;
		if (height > 0) {
			drawTexturedModalRect(x + 174, y + 78 - height, 232, 150 - height,
					8, height);
		}
		if (te.heatRequired > 0) {
			if (te.currentRecipe != null) {
				height = te.currentRecipe.heatRequired * 64 / te.maxHeat;
				drawTexturedModalRect(x + 173, y + 77 - height, 201, 1, 6, 3);
			}
		}
		if (te.maxBurnTime != 0) {
			height = te.burnTimeLeft * 22 / te.maxBurnTime;
			if (height > 0)
				drawTexturedModalRect(x + 156, y + 78 - height, 211,
						101 - height, 16, height);
		}
	}

	// top 1, bottom -1, neither 0
	public int isAtTopOrBottom() {
		if (te.scrollFactor == RecipeWeapons.getRecipeLength(te.page) - 3)
			return -1;
		if (te.scrollFactor == 0)
			return 1;
		return 0;
	}

	@Override
	public void onPartClicked(LIGuiPart button, float a, float b) {
		if (button.name == "up" || button.name == "down") {
			boolean isDown = button.name == "down" ? true : false;
			te.addScrollFactor(isDown);
			CBCMod.netHandler.sendToServer(new MsgCrafterClient(0, te, isDown));
			return;
		}
		if (button.name == "left" || button.name == "right") {
			boolean isForward = button.name == "right" ? true : false;
			te.addPage(isForward);
			CBCMod.netHandler.sendToServer(new MsgCrafterClient(1, te, isForward));
			return;
		}
	}

	@Override
	protected void addElements(Set<LIGuiPart> set) {
		LIGuiPart 
		up = new LIGuiButton("up", 111, 19, 7, 6)
			.setDownCoords(220, 13).setInvaildCoords(220, 6)
			.setTextureCoords(208, 13),
		down = new LIGuiButton("down", 111, 74, 7, 6).setDownCoords(220, 43)
			.setInvaildCoords(208, 6).setTextureCoords(208, 43),
		left = new LIGuiButton("left", 5, 2, 5, 6).setDownCoords(220, 53)
			.setInvaildCoords(245, 53).setTextureCoords(210, 53),
		right = new LIGuiButton("right", 190, 2, 5, 6).setDownCoords(220, 63)
			.setInvaildCoords(245, 63).setTextureCoords(210, 63),
		heat = new LIGuiPart("heat", 175, 15, 6, 63).setTip(new TipHeat()),
		behavior = new LIGuiPart("behavior", 160, 16, 8, 18).setTip(new TipBehavior());
		
		set.add(up);
		set.add(down);
		set.add(left);
		set.add(right);
		set.add(heat);
		set.add(behavior);
	}

}

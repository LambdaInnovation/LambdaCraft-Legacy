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
package cn.lambdacraft.deathmatch.client;

import java.util.HashMap;

import javax.swing.Icon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.hud.IHudTipProvider;
import cn.lambdacraft.api.hud.ISpecialCrosshair;
import cn.lambdacraft.core.LCClientPlayer;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.deathmatch.item.ArmorHEV;
import cn.liutils.api.client.util.HudUtils;
import cn.liutils.api.client.util.RenderUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
@SideOnly((Side.CLIENT))
public class HEVRenderingUtils {

	private static HashMap<IHudTipProvider, IHudTip[]> tipPool = new HashMap();
	
	private static final int TEX_WIDTH = 640, TEX_HEIGHT = 128;
	
	public static void drawPlayerHud(EntityPlayer player, ScaledResolution resolution, float partialTickTime) {
		
		int k = resolution.getScaledWidth();
        int l = resolution.getScaledHeight();
        int i2 = k / 2 - 91;
        int k2 = l - 32 + 3;
        Minecraft mc = Minecraft.getMinecraft();
        TextureManager engine = mc.renderEngine;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.6F);
        engine.bindTexture(ClientProps.HEV_HUD_PATH);
        
        GL11.glPushMatrix();
        
        double scale = 0.00026 * mc.displayWidth + 0.3;
        
        float xOffset, yOffset;
        xOffset = 10;
    	yOffset = l - 20;
    	GL11.glTranslatef(xOffset, yOffset, 0.0F);
    	
        GL11.glScaled(scale, scale, 1.0);
        //Health Section
        HudUtils.setTextureResolution(TEX_WIDTH, TEX_HEIGHT);
    	
        GL11.glColor4f(0.7F, 0.7F, 0.7F, 0.6F);
        HudUtils.drawTexturedModalRect(0 , 0 , 64, 64, 24, 24, 64, 64);
        GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.6F);
        int h = (int) (player.getHealth() * 16 / 20);
        HudUtils.drawTexturedModalRect(0 , 24 - (int)(h * 1.5), 192, 128 - 4 * h, 24, (int) (1.5 * h), 64, 4 * h);
        if(player.getHealth() <= 5)
        	GL11.glColor4f(0.9F, 0.1F, 0.1F, 0.6F);
        drawNumberAt((byte) (player.getHealth() * 5), 18, 0);
        GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.9F);
        
        //Armor Section
        GL11.glColor4f(0.7F, 0.7F, 0.7F, 0.6F);
        HudUtils.drawTexturedModalRect(70 , 0 , 0, 64, 24, 24, 64, 64);
        GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.6F);
        h = player.getTotalArmorValue() * 16 / 20;
        if(h > 16)
        	h = 16;
        HudUtils.drawTexturedModalRect(70, 24 - (int)(h * 1.5), 128, 128 - 4 * h, 24, (int)(h * 1.5), 64, 4 * h);
        
        drawNumberAt(player.getTotalArmorValue() * 5, 70 + 12, 0 );
        
        GL11.glPopMatrix();
        
        //Other section
        drawArmorTip(player, engine, k, l);
        if(LCClientPlayer.drawArmorTip)
        	drawWeaponTip(player, engine, k, l);
 
        engine.bindTexture(engine.getResourceLocation(1)); 
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.7F);
	}
	
	public static void drawCrosshair(ItemStack stack, int k, int l) {
		String xhairPath = null;
		TextureManager engine = Minecraft.getMinecraft().renderEngine;
		int h = 12;
		if(stack != null) {
			if(stack.getItem() instanceof ISpecialCrosshair) {
				h = ((ISpecialCrosshair)stack.getItem()).getHalfWidth();
				int i = ((ISpecialCrosshair)stack.getItem()).getCrosshairID(stack);
				if(i >= 0)
					xhairPath = ClientProps.xhair_path + "xhair" +  i + ".png";
			} else
				xhairPath = ClientProps.getCrosshairPath(stack.getItem().getUnlocalizedName(stack));
			
			if(xhairPath == null)
				xhairPath = ClientProps.DEFAULT_XHAIR_PATH;
		} else {
			xhairPath = ClientProps.DEFAULT_XHAIR_PATH;
		}
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int par1 = k / 2 - h, par2 = l / 2 - h;
		RenderUtils.loadTexture(xhairPath);
		Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.setColorRGBA(ClientProps.xHairR, ClientProps.xHairG, ClientProps.xHairB, 255);
        t.addVertexWithUV(par1 + 0, par2 + 2*h, -90, 0, 1);
        t.addVertexWithUV(par1 + 2*h, par2 + 2*h, -90, 1, 1);
        t.addVertexWithUV(par1 + 2*h, par2 + 0, -90, 1, 0);
        t.addVertexWithUV(par1 + 0, par2 + 0, -90, 0, 0);
        t.draw();
        
        //TODO: maybe buggy here
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.7F);
        GL11.glPopMatrix();
        engine.bindTexture(Gui.icons);
	}
	
	private static void drawArmorTip(EntityPlayer player,TextureManager renderEngine, int k, int l) {
		int tx = k - 26, tx2 = k - 10;
		for(int i = 0, xOffset = -10 ; i < 4; i ++) {
			ItemStack is = player.inventory.armorInventory[i];
			ArmorHEV hev;
			if(is != null && is.getItem() instanceof ArmorHEV) {
				hev = (ArmorHEV) is.getItem();
				int energy = hev.getManager(is).discharge(is, Integer.MAX_VALUE, 0, true, true);
				int heightToDraw = energy * 16 / is.getMaxDamage();
				int height = l - 65 - i * 16;
				if (is.getItemSpriteNumber() == 0) {
					renderEngine.bindTexture(renderEngine.getResourceLocation(0)); 
				} else {
					renderEngine.bindTexture(renderEngine.getResourceLocation(1)); 
				}
				//xOffset = (int) (xOffset * 0.7);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
				HudUtils.drawTexturedModelRectFromIcon(tx + xOffset, height, hev.getIcon(is, 0), 16, 16);
				renderEngine.bindTexture(ClientProps.HEV_HUD_PATH);
				
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
				HudUtils.drawTexturedModalRect(tx2 + xOffset, height + 16 - heightToDraw, 286, 64, 8, 16, 34, 64); //overlay
				GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.75F);
				HudUtils.drawTexturedModalRect(tx2 + xOffset, height + 16 - heightToDraw, 350, 128 - 4 * heightToDraw, 8, heightToDraw, 34, heightToDraw * 4); //actual
			}
		}
	}
	
	private static void drawWeaponTip(EntityPlayer player,TextureManager renderEngine, int k, int l) {
		ItemStack item = player.getCurrentEquippedItem();
		if(item == null)
			return;
		if(item.getItem() instanceof IHudTipProvider) {
			IHudTip[] st = tipPool.get(item.getItem());
			if(st == null) {
				st = ((IHudTipProvider)item.getItem()).getHudTip(item, player);
				tipPool.put((IHudTipProvider) item.getItem(), st);
			}
			drawTips(st, renderEngine, item, player, k, l);
		}
	}
	
	private static void drawTips(IHudTip[] tips, TextureManager engine, ItemStack itemStack, EntityPlayer player, int k, int l) {
		int startHeight = l - 5 - 24 * tips.length;
		for(int i = 0; i < tips.length; i ++) {
			String s = tips[i].getTip(itemStack, player);
			int width = k - 32 - getStringLength(s);
			IIcon icon = tips[i].getRenderingIcon(itemStack, player);
			if(icon != null) {
				int sheetIndex = tips[i].getTextureSheet(itemStack);
				if (sheetIndex == 0)
					engine.bindTexture(engine.getResourceLocation(0)); 
				else if(sheetIndex != 5)
					engine.bindTexture(engine.getResourceLocation(1)); 
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.7F);
				HudUtils.drawTexturedModelRectFromIcon(k - 30, startHeight, icon, 20, 20);
				GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.6F);
				//Bind the texture by Rikka0_0
				RenderUtils.loadTexture(ClientProps.HEV_HUD_PATH);
			}
			drawTipStringAt(s, width, startHeight);
			startHeight += 18;
		}
	}
	
	private static int getStringLength(String s) {
		int count = 0;
		for(char c : s.toCharArray()) {
			if(Character.isDigit(c))
				count += 12;
			else count += 7;
		}
		return count;
	}
	
	private static void drawNumberAt(int number, int x, int y) {
		String s = String.valueOf(number);
		drawTipStringAt(s, x, y);
	}

	static void drawTipStringAt(String s, int x, int y) {
		int lastLength = 0;
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			boolean b = Character.isDigit(c);
			if(b) {
				int number = Integer.valueOf(String.valueOf(c));
				drawSingleNumberAt(number, x + lastLength, y);
			} else {
				HudUtils.drawTexturedModalRect(x + lastLength - 5, y, 608, 64, 12, 24, 32, 64);
			}
			lastLength += b? 12 : 7; 
		}
	}
	
	private static void drawSingleNumberAt(int number, int x, int y) {
		HudUtils.drawTexturedModalRect(x, y, 64 * number + 32, 0, 24, 24, 64, 64);
	}

}

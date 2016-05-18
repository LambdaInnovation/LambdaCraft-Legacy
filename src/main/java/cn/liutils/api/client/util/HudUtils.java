package cn.liutils.api.client.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

/**
 * Utilities about hud drawing.
 */
public class HudUtils {

    private static float SCALE_X = 1.0F, SCALE_Y = 1.0F;
    public static double zLevel = -90D;
    
    /**
     * Called to set the scale before any drawing call.
     * @param x
     * @param y
     */
    public static void setTextureResolution(int x, int y) {
        SCALE_X = 1.0F/x;
        SCALE_Y = 1.0F/y;
    }
    
    public static void setZLevel(double z) {
        zLevel = z;
    }
    
    /**
     * Draws a textured rectangle at the stored z-value. Mapping the full texture to the rect.
     * Args: x, y, width, height
     */
    public static void drawTexturedModalRect(float x, float y, float width, float height)
    {
        float f = SCALE_X;
        float f1 = SCALE_Y;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, zLevel, 0, 1);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, 1, 1);
        tessellator.addVertexWithUV(x + width, y + 0, zLevel, 1, 0);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, 0, 0);
        tessellator.draw();
    }
    
    
    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public static void drawTexturedModalRect(float par1, float par2, int par3, int par4, float par5, float par6)
    {
        float f = SCALE_X;
        float f1 = SCALE_Y;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(par1 + 0, par2 + par6, zLevel, (par3 + 0) * f, (par4 + par6) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + par6, zLevel, (par3 + par5) * f, (par4 + par6) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + 0, zLevel, (par3 + par5) * f, (par4 + 0) * f1);
        tessellator.addVertexWithUV(par1 + 0, par2 + 0, zLevel, (par3 + 0) * f, (par4 + 0) * f1);
        tessellator.draw();
    }
    
    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height, texWidth, texHeight
     */
    public static void drawTexturedModalRect(float par1, float par2, int par3, int par4, float par5, float par6, float par7, float par8)
    {
        float f = SCALE_X;
        float f1 = SCALE_Y;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(par1 + 0, par2 + par6, zLevel, (par3 + 0) * f, (par4 + par8) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + par6, zLevel, (par3 + par7) * f, (par4 + par8) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + 0, zLevel, (par3 + par7) * f, (par4 + 0) * f1);
        tessellator.addVertexWithUV(par1 + 0, par2 + 0, zLevel, (par3 + 0) * f, (par4 + 0) * f1);
        tessellator.draw();
    }
    
    public static void drawTexturedModelRectFromIcon(int x, int y, IIcon icon, int width, int height)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, zLevel, icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV(x + width, y + height, zLevel, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(x + width, y + 0, zLevel, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, icon.getMinU(), icon.getMinV());
        tessellator.draw();
    }
}

/**
 * 
 */
package cn.liutils.api.client.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

/**
 * Utilities of generic rendering.
 * P1.Texture Binding and Vertex plotting
 * P2.
 * @author WeAthFolD
 */
@SideOnly(Side.CLIENT)
public class RenderUtils {

    private static Tessellator t = Tessellator.instance;
    private static Map<String, ResourceLocation> srcMap  = Maps.newHashMap();
    private static int textureState = -1;
    
    public static void pushTextureState() {
        if(textureState != -1) {
            System.err.println("RenderUtils:Texture State Overflow");
            return;
        }
        textureState = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
    }
    
    public static void popTextureState() {
        if(textureState == -1) {
            System.err.println("RenderUtils:Texture State Underflow");
            return;
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureState);
        textureState = -1;
    }
    
    /**
     * Add a vertex to the tessellator with UV coords.
     * @param vec3
     * @param texU
     * @param texV
     */
    public static void addVertex(Vec3 vec3, double texU, double texV) {
        t.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, texU, texV);
    }

    /**
     * Add a vertex to the tessellator without UV coords.
     * @param vec3
     */
    public static void addVertex(Vec3 vec3) {
        t.addVertex(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
    

    /**
     * Load a texture by reading its namespace path.
     * This method is low in efficiency, use ResourceLocation directly
     * @param path The namespace path of the texture
     */
    @Deprecated
    public static void loadTexture(String path) {
        ResourceLocation src = srcMap.get(path);
        if(src == null) {
            src = new ResourceLocation(path);
            srcMap.put(path, src);
        }
        loadTexture(src);
    }
    
    public static void loadTexture(ResourceLocation src) {
        
        Minecraft.getMinecraft().renderEngine.bindTexture(src);
    }

    
    public static void renderItemIn2d(ItemStack stackToRender, double w) {
        renderItemIn2d(stackToRender, w, null);
    }

    /**
     * 将Item渲染成一个有厚度的薄片。
     * @param w
     *            宽度
     * @param t
     */
    public static void renderItemIn2d(ItemStack stackToRender,
            double w, IIcon specialIcon) {
        Vec3 a1 = newV3(0, 0, w), a2 = newV3(1, 0, w), a3 = newV3(1, 1, w), a4 = newV3(
                0, 1, w), a5 = newV3(0, 0, -w), a6 = newV3(1, 0, -w), a7 = newV3(
                1, 1, -w), a8 = newV3(0, 1, -w);

        IIcon icon = stackToRender.getIconIndex();
        if (specialIcon != null)
            icon = specialIcon;

        Minecraft mc = Minecraft.getMinecraft();

        if (icon == null) {
            return;
        }

        mc.renderEngine.bindTexture(mc.renderEngine.getResourceLocation(stackToRender.getItemSpriteNumber()));

        float u1 = 0.0F, v1 = 0.0F, u2 = 0.0F, v2 = 0.0F;
        u1 = icon.getMinU();
        v1 = icon.getMinV();
        u2 = icon.getMaxU();
        v2 = icon.getMaxV();

        Tessellator t;
        t = Tessellator.instance;
        GL11.glPushMatrix();
        t.startDrawingQuads();
        t.setNormal(0.0F, 0.0F, 1.0F);
        addVertex(a1, u2, v2);
        addVertex(a2, u1, v2);
        addVertex(a3, u1, v1);
        addVertex(a4, u2, v1);
        t.draw();

        t.startDrawingQuads();
        t.setNormal(0.0F, 0.0F, -1.0F);
        addVertex(a8, u2, v1);
        addVertex(a7, u1, v1);
        addVertex(a6, u1, v2);
        addVertex(a5, u2, v2);
        t.draw();

        int var7;
        float var8;
        float var9;
        float var10;
        
        /*
         * Gets the width/16 of the currently bound texture, used to fix the
         * side rendering issues on textures != 16
         */
        int tileSize = 32;
        float tx = 1.0f / (32 * tileSize);
        float tz = 1.0f / tileSize;

        t.startDrawingQuads();
        t.setNormal(-1.0F, 0.0F, 0.0F);
        for (var7 = 0; var7 < tileSize; ++var7) {
            var8 = (float) var7 / tileSize;
            var9 = u2 - (u2 - u1) * var8 - tx;
            var10 = 1.0F * var8;
            t.addVertexWithUV(var10, 0.0D, -w, var9, v2);
            t.addVertexWithUV(var10, 0.0D, w, var9, v2);
            t.addVertexWithUV(var10, 1.0D, w, var9, v1);
            t.addVertexWithUV(var10, 1.0D, -w, var9, v1);

            t.addVertexWithUV(var10, 1.0D, w, var9, v1);
            t.addVertexWithUV(var10, 0.0D, w, var9, v2);
            t.addVertexWithUV(var10, 0.0D, -w, var9, v2);
            t.addVertexWithUV(var10, 1.0D, -w, var9, v1);
        }
        t.draw();

        GL11.glPopMatrix();
    }
    
    public static void renderItemIn2d(double w, ResourceLocation front, ResourceLocation back) {
        Vec3 a1 = newV3(0, 0, w), a2 = newV3(1, 0, w), a3 = newV3(1, 1, w), a4 = newV3(
                0, 1, w), a5 = newV3(0, 0, -w), a6 = newV3(1, 0, -w), a7 = newV3(
                1, 1, -w), a8 = newV3(0, 1, -w);

        Minecraft mc = Minecraft.getMinecraft();


        float u1 = 0.0F, v1 = 0.0F, u2 = 1.0F, v2 = 1.0F;

        Tessellator t;
        t = Tessellator.instance;
        GL11.glPushMatrix();
        
        RenderUtils.loadTexture(back);
        t.startDrawingQuads();
        t.setNormal(0.0F, 0.0F, 1.0F);
        addVertex(a1, u2, v2);
        addVertex(a2, u1, v2);
        addVertex(a3, u1, v1);
        addVertex(a4, u2, v1);
        t.draw();

        RenderUtils.loadTexture(front);
        t.startDrawingQuads();
        t.setNormal(0.0F, 0.0F, -1.0F);
        addVertex(a8, u2, v1);
        addVertex(a7, u1, v1);
        addVertex(a6, u1, v2);
        addVertex(a5, u2, v2);
        t.draw();

        int var7;
        float var8;
        float var9;
        float var10;
        
        /*
         * Gets the width/16 of the currently bound texture, used to fix the
         * side rendering issues on textures != 16
         */
        int tileSize = 32;
        float tx = 1.0f / (32 * tileSize);
        float tz = 1.0f / tileSize;

        t.startDrawingQuads();
        t.setNormal(-1.0F, 0.0F, 0.0F);
        for (var7 = 0; var7 < tileSize; ++var7) {
            var8 = (float) var7 / tileSize;
            var9 = u2 - (u2 - u1) * var8 - tx;
            var10 = 1.0F * var8;
            t.addVertexWithUV(var10, 0.0D, -w, var9, v2);
            t.addVertexWithUV(var10, 0.0D, w, var9, v2);
            t.addVertexWithUV(var10, 1.0D, w, var9, v1);
            t.addVertexWithUV(var10, 1.0D, -w, var9, v1);

            t.addVertexWithUV(var10, 1.0D, w, var9, v1);
            t.addVertexWithUV(var10, 0.0D, w, var9, v2);
            t.addVertexWithUV(var10, 0.0D, -w, var9, v2);
            t.addVertexWithUV(var10, 1.0D, -w, var9, v1);
        }
        t.draw();

        GL11.glPopMatrix();
    }
    
    private static ResourceLocation src_glint = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    
    public static void renderOverlay_Equip(ResourceLocation src) {
        GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        loadTexture(src);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        float f7 = 0.76F;
        //GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPushMatrix();
        float f8 = 0.125F;
        GL11.glScalef(f8, f8, f8);
        float f9 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
        GL11.glTranslatef(f9, 0.0F, 0.0F);
        GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(f8, f8, f8);
        f9 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
        GL11.glTranslatef(-f9, 0.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
    }
    
    public static void renderEnchantGlint_Equip() {
        GL11.glColor3f(0.301F, 0.78F, 1.0F);
        renderOverlay_Equip(src_glint);
    }
    
    public static void renderOverlay_Inv(ResourceLocation src) {
        GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        loadTexture(src);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        float f7 = 0.76F;
        //GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPushMatrix();
        float f8 = 0.125F;
        GL11.glScalef(f8, f8, f8);
        float f9 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
        GL11.glTranslatef(f9, 0.0F, 0.0F);
        GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        t.startDrawingQuads();
        t.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        t.addVertexWithUV(0.0, 16.0, 0.0, 0.0, 1.0);
        t.addVertexWithUV(16.0, 16.0, 0.0, 1.0, 1.0);
        t.addVertexWithUV(16.0, 0.0, 0.0, 1.0, 0.0);
        t.draw();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(f8, f8, f8);
        f9 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
        GL11.glTranslatef(-f9, 0.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        t.startDrawingQuads();
        t.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        t.addVertexWithUV(0.0, 16.0, 0.0, 0.0, 1.0);
        t.addVertexWithUV(16.0, 16.0, 0.0, 1.0, 1.0);
        t.addVertexWithUV(16.0, 0.0, 0.0, 1.0, 0.0);
        t.draw();
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
    }
    
    public static void renderSimpleOverlay_Inv(ResourceLocation src) {
        //GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        RenderUtils.loadTexture(src);
         t.startDrawingQuads();
            t.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
            t.addVertexWithUV(0.0, 16.0, 0.0, 0.0, 1.0);
            t.addVertexWithUV(16.0, 16.0, 0.0, 1.0, 1.0);
            t.addVertexWithUV(16.0, 0.0, 0.0, 1.0, 0.0);
           t.draw();
           GL11.glEnable(GL11.GL_LIGHTING);
         GL11.glMatrixMode(GL11.GL_MODELVIEW);
         GL11.glDepthFunc(GL11.GL_LEQUAL);
    }
    
    public static void renderEnchantGlint_Inv() {
        GL11.glColor3f(0.301F, 0.78F, 1.0F);
        renderOverlay_Inv(src_glint);
    }
    
    /**
     * 直接在物品栏渲染物品icon。确认你已经绑定好贴图。
     * @param item
     */
    public static void renderItemInventory(ItemStack item) {
        IIcon icon = item.getIconIndex();
        renderItemInventory(icon);
    }
    
    /**
     * 直接在物品栏渲染物品icon。确认你已经绑定好贴图。
     * @param item
     */
    public static void renderItemInventory(IIcon icon) {
        if(icon != null) {
            t.startDrawingQuads();
            t.addVertexWithUV(0.0, 0.0, 0.0, icon.getMinU(), icon.getMinV());
            t.addVertexWithUV(0.0, 16.0, 0.0, icon.getMinU(), icon.getMaxV());
            t.addVertexWithUV(16.0, 16.0, 0.0, icon.getMaxU(), icon.getMaxV());
            t.addVertexWithUV(16.0, 0.0, 0.0, icon.getMaxU(), icon.getMinV());
            t.draw();
        }
    }

    /**
     * 创建一个新的Vec3顶点。
     * 
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static Vec3 newV3(double x, double y, double z) {
        return Vec3.createVectorHelper(x, y, z);
    }
    
    
    public static void drawHoveringText(List par1List, int par2, int par3, FontRenderer font, int width, int height)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            float zLevel = -90.0F;
            Iterator iterator = par1List.iterator();

            while (iterator.hasNext())
            {
                String s = (String)iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (i1 + k > width)
            {
                i1 -= 28 + k;
            }

            if (j1 + k1 + 6 > height)
            {
                j1 = height - k1 - 6;
            }

            zLevel = 300.0F;
            int l1 = -267386864;
            drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, i1, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
    
    public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = (par5 >> 24 & 255) / 255.0F;
        float f1 = (par5 >> 16 & 255) / 255.0F;
        float f2 = (par5 >> 8 & 255) / 255.0F;
        float f3 = (par5 & 255) / 255.0F;
        float f4 = (par6 >> 24 & 255) / 255.0F;
        float f5 = (par6 >> 16 & 255) / 255.0F;
        float f6 = (par6 >> 8 & 255) / 255.0F;
        float f7 = (par6 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex(par3, par2, -90D);
        tessellator.addVertex(par1, par2, -90D);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex(par1, par4, -90D);
        tessellator.addVertex(par3, par4, -90D);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

}

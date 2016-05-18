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
package cn.liutils.api.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.liutils.api.client.util.RenderUtils;


/**
 * A special TileEntityRenderer that handles multiple-sided non-cube block rendering.
 * extend it to provide texture informations
 * @author WeAthFolD
 */
public abstract class RendererSidedCube extends TileEntitySpecialRenderer {

    /** The minimum X value for rendering (default 0.0). */
    private double minX;

    /** The maximum X value for rendering (default 1.0). */
    private double maxX;

    /** The minimum Y value for rendering (default 0.0). */
    private double minY;

    /** The maximum Y value for rendering (default 1.0). */
    private double maxY;

    /** The minimum Z value for rendering (default 0.0). */
    private double minZ;

    /** The maximum Z value for rendering (default 1.0). */
    private double maxZ;

    /**
     * 这货一定要在构造器里被加载。
     */
    protected Block block;

    public RendererSidedCube(Block blockType) {
        block = blockType;
    }

    public static void addVertex(Vec3 vec3, double texU, double texV) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
                texU, texV);
    }

    protected void setBound(Block block) {
        minX = block.getBlockBoundsMinX();
        minY = block.getBlockBoundsMinY();
        minZ = block.getBlockBoundsMinZ();
        maxX = block.getBlockBoundsMaxX();
        maxY = block.getBlockBoundsMaxY();
        maxZ = block.getBlockBoundsMaxZ();
    }

    public abstract ResourceLocation getTexture(TileEntity te, int side, int metadata);

    public void doRender(TileEntity tileEntity, double x, double y, double z,
            float f) {
        Tessellator t = Tessellator.instance;
        int var5 = tileEntity.getBlockMetadata();
        block.setBlockBoundsBasedOnState(tileEntity.getWorldObj(),tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        setBound(block);

        Vec3 v1, v2, v3, v4, v5, v6, v7, v8;
        v1 = RenderUtils.newV3(minX, minY, minZ);
        v2 = RenderUtils.newV3(minX, minY, maxZ);
        v3 = RenderUtils.newV3(minX, maxY, maxZ);
        v4 = RenderUtils.newV3(minX, maxY, minZ);

        v5 = RenderUtils.newV3(maxX, minY, minZ);
        v6 = RenderUtils.newV3(maxX, minY, maxZ);
        v7 = RenderUtils.newV3(maxX, maxY, maxZ);
        v8 = RenderUtils.newV3(maxX, maxY, minZ);

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        RenderUtils.loadTexture(getTexture(tileEntity, 4, var5));
        t.startDrawingQuads();
        t.setNormal(-1, 0, 0);
        addVertex(v1, 0, 1);
        addVertex(v2, 1, 1);
        addVertex(v3, 1, 0);
        addVertex(v4, 0, 0);
        t.draw();

        RenderUtils.loadTexture(getTexture(tileEntity, 5, var5));
        t.startDrawingQuads();
        t.setNormal(1, 0, 0);
        addVertex(v8, 1, 0);
        addVertex(v7, 0, 0);
        addVertex(v6, 0, 1);
        addVertex(v5, 1, 1);
        t.draw();

        RenderUtils.loadTexture(getTexture(tileEntity, 2, var5));
        t.startDrawingQuads();
        t.setNormal(0, 0, -1);
        addVertex(v4, 1, 0);
        addVertex(v8, 0, 0);
        addVertex(v5, 0, 1);
        addVertex(v1, 1, 1);
        t.draw();

        RenderUtils.loadTexture(getTexture(tileEntity, 3, var5));
        t.startDrawingQuads();
        t.setNormal(0, 0, 1);
        addVertex(v3, 0, 0);
        addVertex(v2, 0, 1);
        addVertex(v6, 1, 1);
        addVertex(v7, 1, 0);
        t.draw();

        RenderUtils.loadTexture(getTexture(tileEntity, 1, var5));
        t.startDrawingQuads();
        t.setNormal(0, 1, 0);
        addVertex(v3, 0, 0);
        addVertex(v7, 1, 0);
        addVertex(v8, 1, 1);
        addVertex(v4, 0, 1);
        t.draw();

        RenderUtils.loadTexture(getTexture(tileEntity, 0, var5));
        t.startDrawingQuads();
        t.setNormal(0, -1, 0);
        addVertex(v1, 0, 1);
        addVertex(v5, 1, 1);
        addVertex(v6, 1, 0);
        addVertex(v2, 0, 0);
        t.draw();

        GL11.glPopMatrix();
    }

}

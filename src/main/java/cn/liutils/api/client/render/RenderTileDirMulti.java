/**
 * 
 */
package cn.liutils.api.client.render;

import org.lwjgl.opengl.GL11;

import cn.liutils.api.block.BlockDirectionedMulti;
import cn.liutils.api.client.ITextureProvider;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

/**
 * @author WeathFolD
 *
 */
public abstract class RenderTileDirMulti extends TileEntitySpecialRenderer {

    protected float scale = 0.0625F;
    protected double offX, offY, offZ;
    protected ResourceLocation texture;

    public RenderTileDirMulti() {}
    
    public RenderTileDirMulti setScale(float f) {
        scale = f;
        return this;
    }
    
    public RenderTileDirMulti setOffset(double x, double y, double z) {
        offX = x;
        offY = y;
        offZ = z;
        return this;
    }
    
    public RenderTileDirMulti setModelTexture(ResourceLocation t) {
        texture = t;
        return this;
    }
    
    public ResourceLocation getTexture(TileEntity te) {
        ResourceLocation tex;
        if(te.getBlockType() instanceof ITextureProvider)
            tex = ((ITextureProvider)te.getBlockType()).getTexture();
        else tex = this.texture;
        return tex;
    }
    
    protected float rotations[] = { 180, 90, 0, -90 };
    @Override
    public void renderTileEntityAt(TileEntity var1, double var2, double var4,
            double var6, float var8) {
        if(!(var1.getBlockType() instanceof BlockDirectionedMulti))
            return;
        int meta = ((BlockDirectionedMulti)var1.getBlockType())
                .getMetadata(var1.getWorldObj(), var1.xCoord, var1.yCoord, var1.zCoord);
        if(meta >> 2 != 0) return;
        Vec3 rotate = ((BlockDirectionedMulti)var1.getBlockType()).getOffsetRotated(BlockDirectionedMulti.getFacingDirection(meta).ordinal());
        GL11.glPushMatrix(); {
            GL11.glTranslated(var2 + offX + rotate.xCoord, var4 + offY + rotate.yCoord, var6 + offZ + rotate.zCoord);
            GL11.glRotatef(rotations[meta], 0F, 1F, 0F);
            renderAtOrigin(var1);
        } GL11.glPopMatrix();
    }
    
    
    protected abstract void renderAtOrigin(TileEntity te);

}

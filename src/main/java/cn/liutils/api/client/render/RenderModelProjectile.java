package cn.liutils.api.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cn.liutils.api.util.Motion3D;

public class RenderModelProjectile extends Render {

    private ModelBase model;
    
    protected ResourceLocation TEXTURE_PATH;
    
    protected float offsetX, offsetY, offsetZ;
    protected float scale;
    
    public RenderModelProjectile(ModelBase mdl, String texturePath) {
        TEXTURE_PATH = new ResourceLocation(texturePath);
        model = mdl;
    }
    
    public RenderModelProjectile(ModelBase mdl, ResourceLocation texturePath) {
        TEXTURE_PATH = texturePath;
        model = mdl;
    }
    
    public RenderModelProjectile setOffset(float x, float y, float z) {
        offsetX = x;
        offsetY = y;
        offsetZ = z;
        return this;
    }
    
    public RenderModelProjectile setScale(float s) {
        scale = s;
        return this;
    }

    @Override
    public void doRender(Entity ent, double par2, double par4,
            double par6, float par8, float par9) {
        Motion3D motion = new Motion3D(ent);
        
        GL11.glPushMatrix(); {
            bindTexture(TEXTURE_PATH);
            GL11.glTranslatef((float) par2, (float) par4, (float) par6);
            GL11.glRotatef(180.0F - ent.rotationYaw, 0.0F, -1.0F, 0.0F); // 左右旋转
            GL11.glRotatef(ent.rotationPitch, 1.0F, 0.0F, 0.0F); // 上下旋转
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            model.render(ent, (float)par2, (float)par4, (float)par6, par8, par9, 0.0625F);
        } GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }


}

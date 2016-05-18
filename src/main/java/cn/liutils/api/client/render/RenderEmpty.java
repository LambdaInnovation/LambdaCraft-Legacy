package cn.liutils.api.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Pure empty render.
 */
public class RenderEmpty extends Render {
    
    @Override
    public void doRender(Entity par1Entity, double par2, double par4,
            double par6, float par8, float par9) {}

    @Override
    protected ResourceLocation getEntityTexture(Entity var1) {
        return null;
    }
    
}

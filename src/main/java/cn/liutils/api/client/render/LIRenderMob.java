package cn.liutils.api.client.render;

import cn.liutils.api.entity.LIEntityMob;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LIRenderMob extends RenderLiving {

    public LIRenderMob(ModelBase par1ModelBase, float par2) {
        super(par1ModelBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity == null || !(entity instanceof LIEntityMob)) {
            return null;
        }
        LIEntityMob e = (LIEntityMob) entity;
        return e.getTexture();
    }

}

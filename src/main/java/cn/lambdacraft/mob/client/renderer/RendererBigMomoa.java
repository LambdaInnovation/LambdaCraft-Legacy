/**
 * 
 */
package cn.lambdacraft.mob.client.renderer;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.mob.entity.EntityBigMomoa;
import cn.liutils.api.client.util.RenderUtils;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

/**
 * @author WeathFolD
 */
public class RendererBigMomoa extends RenderEntity {
    
    IModelCustom model;

    public RendererBigMomoa() {
        model = ClientProps.MDL_GONARCH;
    }

    @Override
    public void doRender(Entity ent, double x, double y, double z,
            float f1, float f2) {
        EntityBigMomoa gonarch = (EntityBigMomoa) ent;
        GL11.glPushMatrix(); {
            
            GL11.glTranslated(x, y, z);
            
            float s = 0.03F;
            GL11.glScalef(s, s, s);
            
            RenderUtils.loadTexture(ClientProps.BIG_MOMOA_MOB_PATH);
            model.renderAll();
            
        } GL11.glPopMatrix();
        //debug use
        super.doRender(ent, x, y, z, f1, f2);
    }

    /* (non-Javadoc)
     * @see net.minecraft.client.renderer.entity.Render#getEntityTexture(net.minecraft.entity.Entity)
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity var1) {
        return null;
    }

}

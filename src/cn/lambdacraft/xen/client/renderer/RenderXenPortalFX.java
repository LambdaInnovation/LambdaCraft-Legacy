/**
 * 
 */
package cn.lambdacraft.xen.client.renderer;

import net.minecraft.entity.Entity;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.xen.client.EntityXenPortalFX;
import cn.liutils.api.client.render.RenderIcon;

/**
 * @author Administrator
 *
 */
public class RenderXenPortalFX extends RenderIcon {

	/**
	 * @param ic
	 */
	public RenderXenPortalFX() {
		super(ClientProps.XENPORTAL_PARTICLE_PATH[0]);
		setSize(0.16F);
		this.setBlend(0.7F);
		setHasLight(false);
	}
	
	@Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		EntityXenPortalFX portal = (EntityXenPortalFX) par1Entity;
		this.icon = ClientProps.XENPORTAL_PARTICLE_PATH[((EntityXenPortalFX)par1Entity).isGreen ? 0 : 1];
		this.alpha = portal.getParticleAlpha();
		this.setSize(portal.getParticleScale());
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}

}

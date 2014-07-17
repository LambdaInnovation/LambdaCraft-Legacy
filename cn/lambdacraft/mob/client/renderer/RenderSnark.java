/**
 * 
 */
package cn.lambdacraft.mob.client.renderer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import cn.lambdacraft.core.proxy.LCClientProps;
import cn.lambdacraft.mob.client.model.ModelSnark;
import cn.liutils.api.client.render.RenderModelItem;

/**
 * @author WeAthFolD
 *
 */
public class RenderSnark extends RenderModelItem {

	/**
	 * @param mdl
	 * @param texture
	 */
	public RenderSnark() {
		super(new ModelSnark(), LCClientProps.SQUEAK_MOB_PATH);
		this.setRotation(0F, -195F, 0F);
		this.setScale(1.4F);
		this.setOffset(0F, 1.35F, -.15F);
		this.setEquipOffset(0.0F, 0.4F, 0.0F);
		this.setRenderInventory(false);
	}
	
	@Override
	protected float getModelAttribute(ItemStack item, EntityLivingBase entity) {
		return entity.ticksExisted / 10F;
	}

}

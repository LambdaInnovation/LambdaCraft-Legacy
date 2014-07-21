package cn.lambdacraft.deathmatch.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.deathmatch.client.model.ModelEgonHead;
import cn.liutils.api.client.render.RenderModelItem;

/**
 * Weapon Egon Renderer Class.
 */
public class RenderEgon extends RenderModelItem {

	public RenderEgon() {
		super(new ModelEgonHead(), ClientProps.EGON_HEAD_PATH);
		this.renderInventory = false;
		this.setRotation(0F, 179.35F, 0F);
		this.inventorySpin = false;
		this.setEquipOffset(0.438F, -0.152F, -0.172F);
		this.setOffset(0.0F, 0.2F, -0.302F);
		this.setScale(2.45F);
	}

	Tessellator t = Tessellator.instance;

}

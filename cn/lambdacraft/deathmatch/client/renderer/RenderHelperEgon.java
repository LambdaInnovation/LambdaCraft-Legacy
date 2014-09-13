/**
 * Code by Lambda Innovation, 2013.
 */
package cn.lambdacraft.deathmatch.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.deathmatch.client.model.ModelEgonBackpack;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.liutils.api.client.render.PlayerRenderHandler;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 *
 */
public class RenderHelperEgon implements PlayerRenderHandler {

	ModelEgonBackpack bp = new ModelEgonBackpack();
	
	/**
	 * 
	 */
	public RenderHelperEgon() {
	}

	/* (non-Javadoc)
	 * @see cn.liutils.api.client.render.PlayerRenderHelper#isActivated(net.minecraft.entity.player.EntityPlayer, net.minecraft.world.World)
	 */
	@Override
	public boolean isActivated(EntityPlayer player, World world) {
		ItemStack is = player.getCurrentEquippedItem();
		Minecraft mc = Minecraft.getMinecraft();
		return mc.gameSettings.thirdPersonView != 0 && is != null && is.getItem() == DMItems.weapon_egon;
	}

	/* (non-Javadoc)
	 * @see cn.liutils.api.client.render.PlayerRenderHelper#renderHead(net.minecraft.entity.player.EntityPlayer, net.minecraft.world.World)
	 */
	@Override
	public void renderHead(EntityPlayer player, World world) {

	}

	/* (non-Javadoc)
	 * @see cn.liutils.api.client.render.PlayerRenderHelper#renderBody(net.minecraft.entity.player.EntityPlayer, net.minecraft.world.World)
	 */
	@Override
	public void renderBody(EntityPlayer player, World world) {
		GL11.glPushMatrix();
		RenderUtils.loadTexture(ClientProps.EGON_BACKPACK);
		
		GL11.glTranslated(0.0F, -0.1F, -0.35F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bp.render(null, 0F, 0F, 0F, 0F, 0F, 0.12F);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

}

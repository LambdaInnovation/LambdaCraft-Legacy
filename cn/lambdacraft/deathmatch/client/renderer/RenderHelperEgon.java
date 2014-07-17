/**
 * Code by Lambda Innovation, 2013.
 */
package cn.lambdacraft.deathmatch.client.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.core.proxy.LCClientProps;
import cn.lambdacraft.deathmatch.client.model.ModelEgonBackpack;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.liutils.api.client.render.PlayerRenderHelper;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 *
 */
public class RenderHelperEgon implements PlayerRenderHelper {

	ModelEgonBackpack bp = new ModelEgonBackpack();
	
	public RenderHelperEgon() {
	}

	@Override
	public boolean isActivated(EntityPlayer player, World world) {
		ItemStack is = player.getCurrentEquippedItem();
		Minecraft mc = Minecraft.getMinecraft();
		return mc.gameSettings.thirdPersonView != 0 && is != null && is.getItem() == DMItems.weapon_egon;
	}

	@Override
	public void renderHead(EntityPlayer player, World world) {

	}

	@Override
	public void renderBody(EntityPlayer player, World world) {
		GL11.glPushMatrix();
		RenderUtils.loadTexture(LCClientProps.EGON_BACKPACK);
		
		GL11.glTranslated(0.0F, -0.1F, -0.35F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bp.render(null, 0F, 0F, 0F, 0F, 0F, 0.12F);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

}

/**
 * 
 */
package cn.lambdacraft.deathmatch.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import cn.lambdacraft.core.LCClientPlayer;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.deathmatch.client.model.ModelGauss;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class DMClientEventHandler {

	private ModelGauss model = new ModelGauss();
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderGameOverlay(RenderGameOverlayEvent event) {

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		boolean hasHEV = LCClientPlayer.armorStat[2] && LCClientPlayer.armorStat[3];
		if (event.type == ElementType.HEALTH || event.type == ElementType.ARMOR || event.type == ElementType.CROSSHAIRS) {
			if (hasHEV) 
				event.setCanceled(true);
		}
		if(hasHEV && event.type == ElementType.EXPERIENCE) {
				HEVRenderingUtils.drawPlayerHud(player, event.resolution, player.ticksExisted);
				HEVRenderingUtils.drawCrosshair(player.getCurrentEquippedItem(), event.resolution.getScaledWidth(), event.resolution.getScaledHeight());
		}
		else if(ClientProps.alwaysCustomCrossHair && event.type == ElementType.CROSSHAIRS) {
			event.setCanceled(true);
			HEVRenderingUtils.drawCrosshair(player.getCurrentEquippedItem(), event.resolution.getScaledWidth(), event.resolution.getScaledHeight());
		}
	}

}

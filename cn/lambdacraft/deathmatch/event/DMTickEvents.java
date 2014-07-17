/**
 * 
 */
package cn.lambdacraft.deathmatch.event;

import cn.lambdacraft.deathmatch.proxy.ClientProxy;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Administrator
 *
 */
public class DMTickEvents {
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event) {
		if(event.phase == Phase.END)
			ClientProxy.fth.onClientTick();
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderTick(RenderTickEvent event) {
		if(event.phase == Phase.END)
			ClientProxy.fth.onRenderTick();
	}
}

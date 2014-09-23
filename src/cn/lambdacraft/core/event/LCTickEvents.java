/**
 * 
 */
package cn.lambdacraft.core.event;

import cn.lambdacraft.deathmatch.proxy.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * @author WeathFolD
 *
 */
public class LCTickEvents {
	
	public void clientEvent(ClientTickEvent event) {
		if(event.phase == Phase.START)
			ClientProxy.cth.tickEnd(true);
	}

	public void PlayerEvent(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		
		if(event.phase == Phase.END)  {
			if(player.worldObj.isRemote) {
				cn.lambdacraft.core.proxy.ClientProxy.lcPlayer.tickEnd();
			}
			
			return;
		}
		
		if(player.worldObj.isRemote) {
			cn.lambdacraft.core.proxy.ClientProxy.lcPlayer.tickStart();
		} else {
			
		}
	}
	
}

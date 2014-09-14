/**
 * 
 */
package cn.lambdacraft.core.event;

import cn.lambdacraft.core.proxy.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * @author WeathFolD
 *
 */
public class LCTickEvents {

	public void PlayerEvent(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		
		if(event.phase == Phase.END)  {
			if(player.worldObj.isRemote) {
				ClientProxy.lcPlayer.tickEnd();
			}
			
			return;
		}
		
		if(player.worldObj.isRemote) {
			ClientProxy.lcPlayer.tickStart();
		} else {
			
		}
	}
	
}

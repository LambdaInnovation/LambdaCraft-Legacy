package cn.weaponmod.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cn.weaponmod.WeaponMod;
import cn.weaponmod.api.weapon.IZoomable;
import cn.weaponmod.proxy.WMClientProxy;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WMTickEvents {
	  @SubscribeEvent
	  public void onPlayerTick(PlayerTickEvent event) {
		  if(event.phase == Phase.START) { 
			  EntityPlayer player = event.player;
			  
			  if(!player.worldObj.isRemote) {
				  //control
				  WeaponMod.proxy.controlHandler_server.tickStart(player);
				  
				  //Zooming support
				  ItemStack curItem = player.getCurrentEquippedItem();
				  if(curItem != null && curItem.getItem() instanceof IZoomable) {
					  IZoomable tn = (IZoomable) curItem.getItem();
					  if(tn.isItemZooming(curItem, player.worldObj, player) && 
							  tn.doesSlowdown(curItem, player.worldObj, player)) 
						  player.capabilities.setPlayerWalkSpeed(0.005F);
					  else player.capabilities.setPlayerWalkSpeed(.1F);
				  } else
					  player.capabilities.setPlayerWalkSpeed(.1F);
				  
			  } else {
				  WeaponMod.proxy.controlHandler_client.tickStart(player);
			  }
			  
		  }
	  }
	  
	  @SubscribeEvent
	  @SideOnly(Side.CLIENT)
	  public void onCLientTick(ClientTickEvent event) {
		  WMClientProxy.cth.tickStart();
	  }
}

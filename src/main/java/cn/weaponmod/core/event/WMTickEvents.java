package cn.weaponmod.core.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.weaponmod.api.weapon.IZoomable;
import cn.weaponmod.core.proxy.WMClientProxy;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WMTickEvents {
    
      @SubscribeEvent
      @SideOnly(Side.CLIENT)
      public void onPlayerTick(PlayerTickEvent event) {
          if(event.phase == Phase.START) { 
              EntityPlayer player = event.player;
              
              if(!player.worldObj.isRemote) {
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
              }
              
          }
      }
      
      @SubscribeEvent
      public void OnWorldEvent(ServerTickEvent event) {
          if(event.phase == Phase.START) {
              ItemControlHandler.tickStart(false);
          }
      }
      
      @SubscribeEvent
      @SideOnly(Side.CLIENT)
      public void onClientTick(ClientTickEvent event) {
          if(event.phase == Phase.START) {
              WMClientProxy.upliftHandler.tickStart();
              ItemControlHandler.tickStart(true);
              World world = Minecraft.getMinecraft().theWorld;
          } else {
              WMClientProxy.upliftHandler.tickEnd();
          }
      }
}

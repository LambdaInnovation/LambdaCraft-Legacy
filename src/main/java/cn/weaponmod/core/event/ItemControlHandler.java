package cn.weaponmod.core.event;


import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.weaponmod.api.feature.IClickHandler;

/**
 * WeaponMod custom keys' data syncing.
 * @author WeAthFolD
 */
public class ItemControlHandler {
    
    public static final int MAX_KEYS = 4;
    
    /**
     * Check the tick use of a specified subKey.
     */
    public static int getUsingTicks(EntityPlayer player, int kid) {
        UsingStatus stat = getStatus(player);
        return stat == null ? 0 : stat.ticker[kid];
    }

    /**
     * Check if the specified key is down
     * @return true for down, false for released
     */
    public static boolean isKeyDown(EntityPlayer player, int kid) {
        UsingStatus stat = getStatus(player);
        return stat == null ? false : stat.keyDown[kid];
    }
    
    /**
     * The player control status storage.
     */
    protected static HashMap<EntityPlayer, UsingStatus> 
            usingPlayerMap_server = new HashMap(), 
            usingPlayerMap_client = new HashMap();
    
    //------------------Internal----------------------------
    
    public static void onKeyPressed(EntityPlayer player, int kid) {
        UsingStatus stat = loadStatus(player);
        stat.press(player, kid);
    }
    
    public static void onKeyReleased(EntityPlayer player, int kid) {
        UsingStatus stat = loadStatus(player);
        stat.release(player, kid);
    }

    public static void tickStart(boolean isRemote) {
        Map<EntityPlayer, UsingStatus> map = getUsingPlayerMap(isRemote);
        for(Map.Entry<EntityPlayer, UsingStatus> ent : map.entrySet()) {
            ent.getValue().attemptUpdate(ent.getKey());
        }
    }

    private static Map<EntityPlayer, UsingStatus> getUsingPlayerMap(World world) {
        return getUsingPlayerMap(world.isRemote);
    }
    
    private static Map<EntityPlayer, UsingStatus> getUsingPlayerMap(boolean b) {
        //b stands for isRemote
        Map<EntityPlayer, UsingStatus> usingPlayerMap = b ? usingPlayerMap_client : usingPlayerMap_server;
        return usingPlayerMap;
    }
    
    private static UsingStatus getStatus(EntityPlayer player) {
        return getUsingPlayerMap(player.worldObj).get(player);
    }
    
    private static UsingStatus loadStatus(EntityPlayer player) {
        Map<EntityPlayer, UsingStatus> map = getUsingPlayerMap(player.worldObj);
        UsingStatus stat = map.get(player);
        if(stat == null) {
            stat = new UsingStatus(player);
            map.put(player, stat);
        }
        return stat;
    }
    
    protected static class UsingStatus {
        
        int [] ticker = new int[4];
        boolean [] keyDown = new boolean[4];
        
        int lastSlot;
        ItemStack lastStack;
        
        EntityPlayer player;
        
        public UsingStatus(EntityPlayer player) {
            this.player = player;
            resetInfo(player.getCurrentEquippedItem(), player.inventory.currentItem);
        }
        
        public void attemptUpdate(EntityPlayer player) {
            ItemStack curItem = player.getCurrentEquippedItem();
            if(!itemUnchanged(player)) {
                resetInfo(player.getCurrentEquippedItem(), player.inventory.currentItem);
                return;
            }
            lastStack = player.getCurrentEquippedItem();
            IClickHandler handler = safe_cast(lastStack);
            
            if(handler != null)
                for(int i = 0; i < MAX_KEYS; i++) {
                    if(keyDown[i]) {
                        ++ticker[i];
                        handler.onItemUsingTick(player.worldObj, player, lastStack, i, ticker[i]);
                    }
                }
        }
        
        public void resetInfo(ItemStack stack, int id) {
            lastStack = stack;
            this.lastSlot = id;
            for(int i = 0; i < MAX_KEYS; i++) {
                ticker[i] = 0;
                keyDown[i] = false;
            }
        }
        
        private boolean itemUnchanged(EntityPlayer player) {
            ItemStack stack = player.getCurrentEquippedItem();
            if(lastStack == null)
                return stack == null;
            if(stack == null)
                return true;
            return lastStack.getItem() == stack.getItem() &&
                    player.inventory.currentItem == lastSlot;
        }
        
        public void press(EntityPlayer player, int kid) {
            if(!keyDown[kid]) {
                IClickHandler handler = safe_cast(player.getCurrentEquippedItem());
                if(handler != null)
                    handler.onItemClick(player.worldObj, player, player.getCurrentEquippedItem(), kid);
            }
            keyDown[kid] = true;
            ticker[kid] = 0;
        }
        
        public void release(EntityPlayer player, int kid) {
            if(keyDown[kid]) {
                IClickHandler handler = safe_cast(player.getCurrentEquippedItem());
                if(handler != null)
                    handler.onItemRelease(player.worldObj, player, player.getCurrentEquippedItem(), kid);
                ticker[kid] = 0;
            } 
            keyDown[kid] = false;
        }
        
        public boolean isKeyDown(int kid) {
            return keyDown[kid];
        }
        
        public int getTicksPressed(int kid) {
            return ticker[kid];
        }
        
        private IClickHandler safe_cast(ItemStack item) {
            return (IClickHandler) (item == null ? null : (item.getItem() instanceof IClickHandler ? item.getItem() : null));
        }
     }

}

package cn.weaponmod.api.feature;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * making an {@link net.minecraft.item.Item} instance able to process clicks independantly.
 * For supported keys, see the class presented below.
 * @see cn.weaponmod.core.proxy.WMClientProxy
 * @author WeAthFolD
 */
public interface IClickHandler {
    
    /**
     * Fired both client and server side, called to update clicking action.
     * @see cn.ItemControlHandler.deathmatch.utils.ItemHelper#setItemInUse
     * @param world
     * @param player
     * @param stack
     * @param isLeft
     */
    public void onItemClick(World world, EntityPlayer player, ItemStack stack, int keyid);
    
    /**
     * See description for onItemClick.
     */
    public void onItemRelease(World world, EntityPlayer pl, ItemStack stack, int keyid);
    
    public void onItemUsingTick(World world, EntityPlayer player, ItemStack stack, int keyid, int ticks);
    
}

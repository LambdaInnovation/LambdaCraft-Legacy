package cn.weaponmod.api.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Making an Item instance able to "zoom in" in certain circumstances.
 * @author WeAthFoLD
 */
public interface IZoomable {
    
    /**
     * Called when the item is EQUIPPED. Determine if it does zoom in now or not.
     */
    boolean isItemZooming(ItemStack stack, World world, EntityPlayer player);
    
    /**
     * Does item slow down player when zooming?
     */
    boolean doesSlowdown(ItemStack stack, World world, EntityPlayer player);
}

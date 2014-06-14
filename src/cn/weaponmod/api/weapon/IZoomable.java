/**
 * 
 */
package cn.weaponmod.api.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author WeAthFoLD
 *
 */
public interface IZoomable {
	boolean isItemZooming(ItemStack stack, World world, EntityPlayer player);
}

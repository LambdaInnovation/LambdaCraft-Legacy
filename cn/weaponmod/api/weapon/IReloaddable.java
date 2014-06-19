/**
 * 
 */
package cn.weaponmod.api.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * 正如其名。
 * @author WeAthFolD
 */
public interface IReloaddable {
	public boolean onSetReload(ItemStack itemStack, EntityPlayer player);
}

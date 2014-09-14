/**
 * 
 */
package cn.lambdacraft.crafting.event;

import cn.lambdacraft.core.misc.CBCAchievements;
import cn.lambdacraft.crafting.register.CBCItems;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemEvent;

/**
 * @author WeathFolD
 *
 */
public class CRTickEvents {

	public void onSmelt(ItemSmeltedEvent event) {
		EntityPlayer player = event.player;
		ItemStack item = event.smelting;
		if (!player.worldObj.isRemote) {
			if (item.getItem() == CBCItems.ingotSteel) {
				CBCAchievements.getAchievement(player,
						CBCAchievements.ohMyTeeth);
			} else if (item.getItem() == CBCItems.ingotUranium) {
				CBCAchievements.getAchievement(player,
						CBCAchievements.radioactiveBeryl);
			} else if (item.getItem() == CBCItems.halfLife01
					|| item.getItem() == CBCItems.halfLife02
					|| item.getItem() == CBCItems.halfLife03) {
				CBCAchievements.getAchievement(player, CBCAchievements.letsMoe);
			}
		}
	}
}

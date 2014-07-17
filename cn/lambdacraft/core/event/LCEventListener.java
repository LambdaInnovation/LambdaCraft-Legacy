/**
 * 
 */
package cn.lambdacraft.core.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.core.LCMod;
import cn.lambdacraft.core.LCPlayer;
import cn.lambdacraft.core.energy.EnergyNet;
import cn.lambdacraft.core.misc.LCAchievements;
import cn.lambdacraft.crafting.register.CBCItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Administrator
 *
 */
public class LCEventListener {

	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event) {
		
	}
	
	@SubscribeEvent
	public void onSmelt(ItemSmeltedEvent event) {
		EntityPlayer player = event.player;
		ItemStack item = event.smelting;
		if (!player.worldObj.isRemote) {
			if (item.getItem() == CBCItems.ingotSteel) {
				LCAchievements.getAchievement(player,
						LCAchievements.ohMyTeeth);
			} else if (item.getItem() == CBCItems.ingotUranium) {
				LCAchievements.getAchievement(player,
						LCAchievements.radioactiveBeryl);
			} else if (item.getItem() == CBCItems.halfLife01
					|| item.getItem() == CBCItems.halfLife02
					|| item.getItem() == CBCItems.halfLife03) {
				LCAchievements.getAchievement(player, LCAchievements.letsMoe);
			}
		}
	}
	
}

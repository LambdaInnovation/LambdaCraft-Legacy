/**
 * 
 */
package cn.lambdacraft.core.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.CBCPlayer;
import cn.lambdacraft.core.energy.EnergyNet;
import cn.lambdacraft.core.misc.CBCAchievements;
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
public class CBCEventListener {
	
	private CBCPlayer player = new CBCPlayer();

	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event) {
		
	}
	
	@SubscribeEvent
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
	
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		CBCMod.proxy.profilerStartSection("LambdaCraft");
		
		World world = event.world;
		
		CBCMod.proxy.profilerEndStartSection("EnergyNet");
		EnergyNet.onTick(world);
		
		CBCMod.proxy.profilerEndSection();
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event) {
		if(event.phase == Phase.START)
			player.tickStart();
		else player.tickEnd();
	}
}

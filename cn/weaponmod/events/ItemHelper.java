package cn.weaponmod.events;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.weaponmod.api.feature.ISpecialUseable;
import cn.weaponmod.network.NetKeyClicking;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * 左右键射击的底层支持。
 * @author WeAthFolD
 */
public class ItemHelper implements ITickHandler {

	protected static class UsingStatus {
		
		int useDuration;
		
		/**
		 * Using tick left for current ItemStack, stop using once the duration reaches 0.
		 */
		int useDurationLeft;
		
		ItemStack stack;
		
		public UsingStatus() {}
		
		public UsingStatus(int maxUse, boolean side, ItemStack stack) {
			resetStatus(maxUse, side, stack);
		}
		
		public boolean handleUpdate(EntityPlayer player) {
			ItemStack curItem = player.getCurrentEquippedItem(); 
			return --useDurationLeft > 0 && stack != null && curItem != null && stack.itemID == curItem.itemID;
		}
		
		public boolean isUsing(EntityPlayer player) {
			ItemStack curItem = player.getCurrentEquippedItem(); 
			return useDurationLeft > 0 && stack != null && curItem != null && stack.itemID == curItem.itemID;
		}
		
		public void resetStatus(int maxUse, boolean side, ItemStack is) {
			useDuration = useDurationLeft = maxUse;
			stack = is;
		}
		
		public void stopUsing(EntityPlayer player) {
			if(stack != null) {
				Item instance = stack.getItem();
				instance.onPlayerStoppedUsing(stack, player.worldObj, player, useDuration - useDurationLeft);
			}
			useDurationLeft = 0;
			stack = null;
		}
 	}
	
	/**
	 * 特殊的使用函数中会用到的，玩家和使用状态的映射表。
	 */
	protected static HashMap<EntityPlayer, UsingStatus> usingPlayerMap_server[] = new HashMap[2], 
			usingPlayerMap_client[] = new HashMap[2];
	
	static {
		for(int i = 0; i < 2; i ++) {
			usingPlayerMap_server[i] = new HashMap();
			usingPlayerMap_client[i] = new HashMap();
		}
	}
	
	public static void setItemInUse(EntityPlayer player, ItemStack currentStack, int maxCount, boolean side) {
		//Put the usingStatus into map, or reuse it if exists
		UsingStatus stat;
		Map<EntityPlayer, UsingStatus> usingPlayerMap = getUsingPlayerMapFor(player.worldObj, side);
		if(!usingPlayerMap.containsKey(player)) {
			stat = new UsingStatus();
			usingPlayerMap.put(player, stat);
		} else stat = usingPlayerMap.get(player);
		stat.resetStatus(maxCount, side, currentStack);
	}

	
	/**
	 * 用来确定是否在【特殊使用】一个物品。
	 * @param player 需要确认的玩家
	 * @return 如果为0则未使用，否则是剩余的usingTick
	 */
	public static int getUsingTickLeft(EntityPlayer player, boolean side) {
		Map<EntityPlayer, UsingStatus> usingPlayerMap = getUsingPlayerMapFor(player.worldObj, side);
		UsingStatus stat = usingPlayerMap.get(player);
		if(stat == null)
			return 0;
		return stat.isUsing(player) ? stat.useDurationLeft : 0;
	}
	
	public static boolean getUsingSide(EntityPlayer player) {
		Map<EntityPlayer, UsingStatus> usingPlayerMap = getUsingPlayerMapFor(player.worldObj, true);
		UsingStatus stat1 = usingPlayerMap.get(player);
		return stat1 == null ? false : stat1.isUsing(player);
	}
	
	public static void stopUsingItem(EntityPlayer player, boolean side) {
		Map<EntityPlayer, UsingStatus> usingPlayerMap = getUsingPlayerMapFor(player.worldObj, side);
		UsingStatus stat = usingPlayerMap.get(player);
		if(stat != null) {
			stat.stopUsing(player);
			if(player.worldObj.isRemote)
				NetKeyClicking.sendPacketData(side ? 2 : -2);
		}
	}


	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if(type.contains(TickType.PLAYER)) {
			EntityPlayer player = (EntityPlayer) tickData[0];
			World world = player.worldObj;
			for(int i = 0; i < 2 ; i++) {
			Map<EntityPlayer, UsingStatus> usingPlayerMap = getUsingPlayerMapFor(world, i == 0 );
			UsingStatus stat = usingPlayerMap.get(player);
			if(stat != null && stat.isUsing(player)) {
				if(player.worldObj.isRemote) {
					if(Minecraft.getMinecraft().currentScreen != null)
						stopUsingItem(player, i == 0);
				}
				if(!stat.handleUpdate(player)) {
					stat.stopUsing(player);
				} else {
					Item item = stat.stack.getItem();
					if(item instanceof ISpecialUseable) {
						((ISpecialUseable)item).onItemUsingTick(world, player, stat.stack, i == 0, stat.useDurationLeft);
					}
				}
			}
			}
		}
	}

	private static Map<EntityPlayer, UsingStatus> getUsingPlayerMapFor(World world, boolean isLeft) {
		Map<EntityPlayer, UsingStatus> usingPlayerMap[] = world.isRemote ? usingPlayerMap_client : usingPlayerMap_server;
		return usingPlayerMap[isLeft ? 0 : 1];
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {}


	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}


	@Override
	public String getLabel() {
		return "MyWeaponry Special Using";
	}

}

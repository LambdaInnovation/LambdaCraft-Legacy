/**
 * Code by Lambda Innovation, 2013.
 */
package cn.weaponmod.events;

import java.util.EnumSet;

import cn.weaponmod.api.weapon.IZoomable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * @author WeAthFolD
 *
 */
public class WMTickHandler implements ITickHandler {

	public WMTickHandler() {
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		EntityPlayer player = (EntityPlayer) tickData[0];
		if(player.worldObj.isRemote) return;
		
		ItemStack curItem = player.getCurrentEquippedItem();
		if(curItem != null && curItem.getItem() instanceof IZoomable) {
			IZoomable tn = (IZoomable) curItem.getItem();
			if(tn.isItemZooming(curItem, player.worldObj, player) && tn.doesSlowdown(curItem, player.worldObj, player)) {
				player.capabilities.setPlayerWalkSpeed(0.005F);
			} else player.capabilities.setPlayerWalkSpeed(.1F);
		} else
			player.capabilities.setPlayerWalkSpeed(.1F);
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.ITickHandler#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Weaponry ServerTickHandler";
	}

}

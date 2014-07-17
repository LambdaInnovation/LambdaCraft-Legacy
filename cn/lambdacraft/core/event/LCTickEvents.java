package cn.lambdacraft.core.event;

import net.minecraft.world.World;
import cn.lambdacraft.core.LCMod;
import cn.lambdacraft.core.LCPlayer;
import cn.lambdacraft.core.energy.EnergyNet;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LCTickEvents {
	
	private LCPlayer player = new LCPlayer();
	
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		LCMod.proxy.profilerStartSection("LambdaCraft");
		
		World world = event.world;
		
		LCMod.proxy.profilerEndStartSection("EnergyNet");
		EnergyNet.onTick(world);
		
		LCMod.proxy.profilerEndSection();
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event) {
		if(event.phase == Phase.START)
			player.tickStart();
		else player.tickEnd();
	}
}

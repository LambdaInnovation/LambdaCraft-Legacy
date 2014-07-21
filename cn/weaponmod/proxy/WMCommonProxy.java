package cn.weaponmod.proxy;

import cn.weaponmod.events.WMTickHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class WMCommonProxy {

	public void preInit() {
		TickRegistry.registerTickHandler(new WMTickHandler(), Side.CLIENT);
	}

	public void init() {}
	
}

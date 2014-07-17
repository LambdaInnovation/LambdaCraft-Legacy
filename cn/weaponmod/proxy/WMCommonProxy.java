package cn.weaponmod.proxy;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cn.weaponmod.events.ItemControlHandler;
import cn.weaponmod.events.WMEventListener;
import cn.weaponmod.events.WMTickEvents;

public class WMCommonProxy {
	
	public ItemControlHandler controlHandler_client = new ItemControlHandler(),
			controlHandler_server = new ItemControlHandler();

	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new WMEventListener());
		FMLCommonHandler.instance().bus().register(new WMTickEvents());
	}

	public void init() {}
	
}

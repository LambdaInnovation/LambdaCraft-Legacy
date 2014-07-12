package cn.weaponmod.proxy;

import cn.weaponmod.events.ItemControlHandler;

public class WMCommonProxy {
	
	public ItemControlHandler controlHandler_client = new ItemControlHandler(),
			controlHandler_server = new ItemControlHandler();

	public void preInit() {}

	public void init() {}
	
}

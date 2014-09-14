/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开原协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.core;

import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;
import cn.lambdacraft.core.network.MsgKeyUsing;
import cn.lambdacraft.core.prop.GeneralProps;
import cn.lambdacraft.crafting.recipe.RecipeWeapons;
import cn.liutils.api.register.LIGuiHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "LambdaCraft", name = "LambdaCraft Core", version = CBCMod.VERSION)
public class CBCMod {

	public static final String VERSION = "1.7.8.2";

	public static final String DEPENCY_CRAFTING = "required-after:LambdaCraft|World@" + VERSION,
			DEPENDENCY_CORE = "required-after:LambdaCraft@" + VERSION,
			DEPENDENCY_DEATHMATCH = "required-after:LambdaCraft|DeathMatch@" + VERSION,
			DEPENDENCY_MOB = "required-after:LambdaCraft|Living@" + VERSION,
			DEPENCY_XEN = "required-after:LambdaCraft|Xen@" + VERSION;

	@SideOnly(Side.CLIENT)
	private Minecraft mc;
	
	/**
	 * 日志
	 */
	public static Logger log = Logger.getLogger("LambdaCraft");

	/**
	 * 武器制作机的合成表。
	 */
	public static RecipeWeapons recipeWeapons;

	/**
	 * Creative Tab.
	 */
	public static CreativeTabs cct = new CBCCreativeTab("CBCMod");

	/**
	 * 公用设置。
	 */
	public static Configuration config;

	@Instance("LambdaCraft")
	public static CBCMod instance;

	/**
	 * 加载代理。
	 */
	@SidedProxy(clientSide = "cn.lambdacraft.core.proxy.ClientProxy", serverSide = "cn.lambdacraft.core.proxy.Proxy")
	public static cn.lambdacraft.core.proxy.Proxy proxy;

	public static boolean ic2Installed = false;
	
	public static LIGuiHandler guiHandler = new LIGuiHandler();
	
	public static SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(GeneralProps.NET_CHANNEL);

	/**
	 * 预加载（设置、世界生成、注册Event）
	 * 
	 * @param event
	 */
	@EventHandler()
	public void preInit(FMLPreInitializationEvent event) {

		log.info("Starting LambdaCraft " + CBCMod.VERSION);
		log.info("Copyright (c) Lambda Innovation, 2013");
		log.info("http://www.lambdacraft.cn");
		
		config = new Configuration(event.getSuggestedConfigurationFile());
		proxy.preInit();
		GeneralProps.loadProps(config);
		ModMetadata meta = event.getModMetadata();
		meta.logoFile = "\\lambdacraft.png";
		meta.description = "A minecraft mod re-making Half-Life worldview! Enjoy your experience" +
				"crafting HEV and gauss, battling with headcrabs, and travelling to XEN!";
		meta.url = "http://lambdacraft.cn";
		meta.version = "1.7.8";
	}

	/**
	 * 加载（方块、物品、网络处理、其他)
	 * 
	 * @param Init
	 */
	@EventHandler()
	public void init(FMLInitializationEvent Init) {
		
		log.fine("LambdaCraft IC2 Intergration Module STATE : " + ic2Installed);
		// Blocks, Items, GUI Handler,Key Process.
		NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMod", "LambdaCraft");
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMisc", "LambdaCraft-Misc");
		netHandler.registerMessage(MsgKeyUsing.Handler.class, MsgKeyUsing.class, GeneralProps.NET_ID_USE, Side.SERVER);
		proxy.init();
	}

	/**
	 * 加载后（保存设置）
	 * 
	 * @param Init
	 */
	@EventHandler()
	public void postInit(FMLPostInitializationEvent Init) {
		config.save();
	}

}

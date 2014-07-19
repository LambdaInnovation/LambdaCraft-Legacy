/** 
 * Copyright (c) LambdaCraft Modding Team, 2013-2014
 * 版权所有：LambdaCraft 制作小组， 2013-2014.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.core;

import java.util.EnumSet;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cn.lambdacraft.core.energy.EnergyNet;
import cn.lambdacraft.core.event.LCEventListener;
import cn.lambdacraft.core.network.MessageKeyUsing;
import cn.lambdacraft.core.proxy.LCGeneralProps;
import cn.lambdacraft.crafting.recipe.RecipeWeapons;
import cn.liutils.api.register.LIGuiHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "LambdaCraft", name = "LambdaCraft Core", version = LCMod.VERSION)
public class LCMod {

	public static final String VERSION = "1.7.8pre";

	public static final String DEPENCY_CRAFTING = "required-after:LambdaCraft|World@" + VERSION,
			DEPENDENCY_CORE = "required-after:LambdaCraft@" + VERSION,
			DEPENDENCY_DEATHMATCH = "required-after:LambdaCraft|DeathMatch@" + VERSION,
			DEPENDENCY_MOB = "required-after:LambdaCraft|Living@" + VERSION,
			DEPENCY_XEN = "required-after:LambdaCraft|Xen@" + VERSION;

	@SideOnly(Side.CLIENT)
	private Minecraft mc;

	public static EnergyNet energyNet = new EnergyNet();
	
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
	public static CreativeTabs cct = new LCCreativeTab("LCMod", 0),
			cctMisc = new LCCreativeTab("LCMisc", 1);

	/**
	 * 公用设置。
	 */
	public static Configuration config;

	@Instance("LambdaCraft")
	public static LCMod instance;

	/**
	 * 加载代理。
	 */
	@SidedProxy(clientSide = "cn.lambdacraft.core.proxy.LCClientProxy", serverSide = "cn.lambdacraft.core.proxy.LCCommonProxy")
	public static cn.lambdacraft.core.proxy.LCCommonProxy proxy;
	
	public static LIGuiHandler guiHandler = new LIGuiHandler();
	
	public static SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(LCGeneralProps.NET_CHANNEL);
	private static int nextNetID = 0;

	/**
	 * 预加载（设置、世界生成、注册Event）
	 * 
	 * @param event
	 */
	@EventHandler()
	public void preInit(FMLPreInitializationEvent event) {

		log.info("Starting LambdaCraft " + LCMod.VERSION);
		log.info("Copyright (c) Lambda Innovation, 2013-2014");
		log.info("http://lambdacraft.half-life.cn/");
		

		config = new Configuration(event.getSuggestedConfigurationFile());
		EnergyNet.initialize();
		proxy.preInit();
		
		LCGeneralProps.loadProps(config);
		
	}

	/**
	 * 加载（方块、物品、网络处理、其他)
	 * 
	 * @param Init
	 */
	@EventHandler()
	public void init(FMLInitializationEvent Init) {
		// Blocks, Items, GUI Handler,Key Process.
		NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
		LanguageRegistry.instance().addStringLocalization("itemGroup.LCMod", "LambdaCraft");
		LanguageRegistry.instance().addStringLocalization("itemGroup.LCMisc", "LambdaCraft:Misc");

		netHandler.registerMessage(MessageKeyUsing.Handler.class, MessageKeyUsing.class, ++nextNetID, Side.SERVER);
		
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

	/**
	 * 服务器加载（注册指令）
	 * 
	 * @param event
	 */
	@EventHandler()
	public void serverStarting(FMLServerStartingEvent event) {
		CommandHandler commandManager = (CommandHandler) event.getServer()
				.getCommandManager();
	}
	
	public static int getUniqueNetChannel() {
		return nextNetID++;
	}
}

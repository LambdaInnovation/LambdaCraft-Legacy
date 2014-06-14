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

import java.util.EnumSet;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import cn.lambdacraft.core.energy.EnergyNet;
import cn.lambdacraft.core.misc.CBCNetHandler;
import cn.lambdacraft.core.network.NetKeyUsing;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.crafting.recipe.RecipeWeapons;
import cn.lambdacraft.intergration.ic2.IC2Module;
import cn.liutils.api.register.LIGuiHandler;
import cn.liutils.core.register.Config;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "LambdaCraft", name = "LambdaCraft Core", version = CBCMod.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, 
clientPacketHandlerSpec = @SidedPacketHandler(channels = { GeneralProps.NET_CHANNEL_CLIENT }, packetHandler = CBCNetHandler.class), 
serverPacketHandlerSpec = @SidedPacketHandler(channels = { GeneralProps.NET_CHANNEL_SERVER }, packetHandler = CBCNetHandler.class))
public class CBCMod implements ITickHandler {

	public static final String VERSION = "1.7.73exp";

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
	public static CreativeTabs cct = new CBCCreativeTab("CBCMod", 0),
			cctMisc = new CBCCreativeTab("CBCMisc", 1);

	/**
	 * 公用设置。
	 */
	public static Config config;

	@Instance("LambdaCraft")
	public static CBCMod instance;

	/**
	 * 加载代理。
	 */
	@SidedProxy(clientSide = "cn.lambdacraft.core.proxy.ClientProxy", serverSide = "cn.lambdacraft.core.proxy.Proxy")
	public static cn.lambdacraft.core.proxy.Proxy proxy;

	public static boolean ic2Installed = true;
	
	public static LIGuiHandler guiHandler = new LIGuiHandler();

	/**
	 * 预加载（设置、世界生成、注册Event）
	 * 
	 * @param event
	 */
	@EventHandler()
	public void preInit(FMLPreInitializationEvent event) {

		log.setParent(FMLLog.getLogger());
		log.info("Starting LambdaCraft " + CBCMod.VERSION);
		log.info("Copyright (c) Lambda Innovation, 2013");
		log.info("http://www.lambdacraft.cn");
		

		config = new Config(event.getSuggestedConfigurationFile());
		EnergyNet.initialize();
		proxy.preInit();
		TickRegistry.registerTickHandler(this, Side.CLIENT);
		TickRegistry.registerTickHandler(this, Side.SERVER);
		GeneralProps.loadProps(config);
		
	}

	/**
	 * 加载（方块、物品、网络处理、其他)
	 * 
	 * @param Init
	 */
	@EventHandler()
	public void init(FMLInitializationEvent Init) {
		ic2Installed = IC2Module.init();
		log.fine("LambdaCraft IC2 Intergration Module STATE : " + ic2Installed);
		// Blocks, Items, GUI Handler,Key Process.
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMod",
				"LambdaCraft");
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMisc",
				"LambdaCraft:Misc");
		CBCNetHandler.addChannel(GeneralProps.NET_ID_USE, new NetKeyUsing());
		proxy.init();
	}

	/**
	 * 加载后（保存设置）
	 * 
	 * @param Init
	 */
	@EventHandler()
	public void postInit(FMLPostInitializationEvent Init) {
		config.SaveConfig();

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

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (type.contains(TickType.WORLD)) {
			
			proxy.profilerStartSection("LambdaCraft");
			
			World world = (World) tickData[0];
			
			proxy.profilerEndStartSection("EnergyNet");
			if(!ic2Installed)
				EnergyNet.onTick(world);
			
			proxy.profilerEndSection();
			
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.WORLD);
	}

	@Override
	public String getLabel() {
		return "LambdaCraft ticks";
	}


}

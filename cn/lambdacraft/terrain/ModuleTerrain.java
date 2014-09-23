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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.terrain;

import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.mob.entity.EntityAlienSlave;
import cn.lambdacraft.mob.entity.EntityBarnacle;
import cn.lambdacraft.mob.entity.EntityHeadcrab;
import cn.lambdacraft.mob.entity.EntityHoundeye;
import cn.lambdacraft.terrain.register.XenBlocks;
import cn.lambdacraft.terrain.world.WorldProviderXenContinent;
import cn.lambdacraft.terrain.world.biome.MainBiomes;
import cn.liutils.api.LIGeneralRegistry;
import cn.liutils.api.register.Configurable;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

/**
 * @author mkpoli F
 *
 */
@Mod(modid = "LambdaCraft|Terrain", name = "LambdaCraft Terrain", version = CBCMod.VERSION, dependencies = CBCMod.DEPENDENCY_CORE)
public class ModuleTerrain {
	
	public static final String DEPENCY_TERRAIN = "required-after:LambdaCraft|World@" + CBCMod.VERSION;
	
	@Configurable(key = "xenContinentDimensionID", defValue = "5")
	public static int xenContinentDimensionID;
	
	@Configurable(key = "xenIslandDimensionID", defValue = "6")
	public static int xenIslandDimensionID;
	
	@Configurable(key = "xenHillBiomeId", defValue = "30")
	public static int xenHillBiomeId;
	
	@Configurable(key = "xenPlainBiomeId", defValue = "31")
	public static int xenPlainBiomeId;
	
	@Configurable(key = "xenVoidBiomeId", defValue = "32")
	public static int xenVoidBiomeId;
	
	@Configurable(key = "xenBrokenBiomeId", defValue = "33")
	public static int xenBrokenBiomeId;
	
	@Configurable(key = "xenStonePlainBiomeId", defValue = "34")
	public static int xenStonePlainBiomeId;
	
	@Configurable(key = "xenPlainInDefaultWorldBiomeId", defValue = "35")
	public static int xenPlainInDefaultWorldBiomeId;
	
	@SidedProxy(clientSide = "cn.lambdacraft.terrain.proxy.ClientProxy", serverSide = "cn.lambdacraft.terrain.proxy.Proxy")
	public static cn.lambdacraft.terrain.proxy.Proxy proxy;
	
	@Instance("LambdaCraft|Terrain")
	public static ModuleTerrain instance;
	
	@EventHandler()
	public void preInit(FMLPreInitializationEvent Init) {
	}
	
	@EventHandler()
	public void Init(FMLInitializationEvent Init) {
		loadProps(CBCMod.config);
		XenBlocks.init(CBCMod.config);
		proxy.init();
		DimensionManager.registerProviderType(xenContinentDimensionID, WorldProviderXenContinent.class, true);
		DimensionManager.registerDimension(xenContinentDimensionID, xenContinentDimensionID);
		EntityRegistry.addSpawn(EntityHoundeye.class, 25, 15, 15, EnumCreatureType.monster, MainBiomes.xenHill, MainBiomes.xenPlain);
		EntityRegistry.addSpawn(EntityHeadcrab.class, 25, 15, 15, EnumCreatureType.monster, MainBiomes.xenHill,  MainBiomes.xenPlain);
		EntityRegistry.addSpawn(EntityAlienSlave.class, 10, 15, 15, EnumCreatureType.monster, MainBiomes.xenHill);
		EntityRegistry.addSpawn(EntityBarnacle.class, 6, 15, 15, EnumCreatureType.monster, MainBiomes.xenHill,  MainBiomes.xenPlain);
	}
	
	@EventHandler()
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	@EventHandler()
	public void serverStarting(FMLServerStartingEvent event) {
	}
	
	private static void loadProps(Configuration config) {
		LIGeneralRegistry.loadConfigurableClass(CBCMod.config, ModuleTerrain.class);
	}
}

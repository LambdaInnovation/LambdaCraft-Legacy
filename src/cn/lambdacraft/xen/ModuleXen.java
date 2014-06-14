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
package cn.lambdacraft.xen;

import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.DimensionManager;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.mob.entity.EntityAlienSlave;
import cn.lambdacraft.mob.entity.EntityBarnacle;
import cn.lambdacraft.mob.entity.EntityHeadcrab;
import cn.lambdacraft.mob.entity.EntityHoundeye;
import cn.lambdacraft.xen.register.XENBlocks;
import cn.lambdacraft.xen.world.WorldProviderXen;
import cn.lambdacraft.xen.world.biome.MainBiomes;
import cn.liutils.api.register.Configurable;
import cn.liutils.core.register.Config;
import cn.liutils.core.register.ConfigHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;

/**
 * @author mkpoli F
 *
 */
@Mod(modid = "LambdaCraft|Xen", name = "LambdaCraft Xen", version = CBCMod.VERSION, dependencies = CBCMod.DEPENDENCY_CORE)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ModuleXen {
	
	public static final String DEPENCY_XEN = "required-after:LambdaCraft|World@" + CBCMod.VERSION;
	
	@Configurable(key = "xenDimensionID", defValue = "5")
	public static int dimensionId;
	
	
	@SidedProxy(clientSide = "cn.lambdacraft.xen.proxy.ClientProxy", serverSide = "cn.lambdacraft.xen.proxy.Proxy")
	public static cn.lambdacraft.xen.proxy.Proxy proxy;
	
	@Instance("LambdaCraft|Xen")
	public static ModuleXen instance;
	
	@EventHandler()
	public void preInit(FMLPreInitializationEvent Init) {
	}
	
	@EventHandler()
	public void Init(FMLInitializationEvent Init) {
		loadProps(CBCMod.config);
		XENBlocks.init(CBCMod.config);
		proxy.init();
		DimensionManager.registerProviderType(dimensionId, WorldProviderXen.class, true);
		DimensionManager.registerDimension(dimensionId, dimensionId);
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
	
	private static void loadProps(Config config) {
		ConfigHandler.loadConfigurableClass(CBCMod.config, ModuleXen.class);
	}
}

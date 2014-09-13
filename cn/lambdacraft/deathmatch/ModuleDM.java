package cn.lambdacraft.deathmatch;

import net.minecraftforge.common.MinecraftForge;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.misc.CBCNetHandler;
import cn.lambdacraft.core.prop.GeneralProps;
import cn.lambdacraft.deathmatch.block.container.DMGuiElements;
import cn.lambdacraft.deathmatch.entity.EntityARGrenade;
import cn.lambdacraft.deathmatch.entity.EntityBattery;
import cn.lambdacraft.deathmatch.entity.EntityCrossbowArrow;
import cn.lambdacraft.deathmatch.entity.EntityHGrenade;
import cn.lambdacraft.deathmatch.entity.EntityHornet;
import cn.lambdacraft.deathmatch.entity.EntityMedkit;
import cn.lambdacraft.deathmatch.entity.EntityRPGDot;
import cn.lambdacraft.deathmatch.entity.EntityRocket;
import cn.lambdacraft.deathmatch.entity.EntitySatchel;
import cn.lambdacraft.deathmatch.entity.fx.EntityCrossbowStill;
import cn.lambdacraft.deathmatch.entity.fx.EntityEgonRay;
import cn.lambdacraft.deathmatch.entity.fx.EntityGaussRay;
import cn.lambdacraft.deathmatch.entity.fx.EntityGaussRayColored;
import cn.lambdacraft.deathmatch.entity.fx.GaussParticleFX;
import cn.lambdacraft.deathmatch.network.NetChargerClient;
import cn.lambdacraft.deathmatch.network.NetMedFillerClient;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMEventHandler;
import cn.lambdacraft.deathmatch.register.DMItems;
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

@Mod(modid = "LambdaCraft|DeathMatch", name = "LambdaCraft DeathMatch", version = CBCMod.VERSION, dependencies = CBCMod.DEPENCY_CRAFTING)
public class ModuleDM {

	@Instance("LambdaCraft|DeathMatch")
	public static ModuleDM instance;

	@SidedProxy(clientSide = "cn.lambdacraft.deathmatch.proxy.ClientProxy", serverSide = "cn.lambdacraft.deathmatch.proxy.Proxy")
	public static cn.lambdacraft.deathmatch.proxy.Proxy proxy;

	@EventHandler()
	public void preInit(FMLPreInitializationEvent Init) {
		MinecraftForge.EVENT_BUS.register(new DMEventHandler());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_CHARGER_CL, new NetChargerClient());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_MEDFILLER_CL, new NetMedFillerClient());
		CBCMod.guiHandler.addGuiElement(GeneralProps.GUI_ID_CHARGER, new DMGuiElements.ElementArmorCharger());
		CBCMod.guiHandler.addGuiElement(GeneralProps.GUI_ID_HEALTH, new DMGuiElements.ElementHealthCharger());
		CBCMod.guiHandler.addGuiElement(GeneralProps.GUI_ID_MEDFILLER, new DMGuiElements.ElementMedFiller());
		proxy.preInit();
	}

	@EventHandler()
	public void init(FMLInitializationEvent Init) {
		DMItems.init(CBCMod.config);
		DMBlocks.init(CBCMod.config);
		
		EntityRegistry.registerModEntity(EntityGaussRay.class, "gauss",
				GeneralProps.ENT_ID_GAUSS1, CBCMod.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityGaussRayColored.class, "gauss2",
				GeneralProps.ENT_ID_GAUSS2, CBCMod.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityEgonRay.class, "egonray",
				GeneralProps.ENT_ID_EGON_RAY, CBCMod.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityARGrenade.class, "argrenade",
				GeneralProps.ENT_ID_ARGRENADE, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHGrenade.class, "hgrenade",
				GeneralProps.ENT_ID_HGRENADE, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHornet.class, "hornet",
				GeneralProps.ENT_ID_HORNET, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityRocket.class, "rocket",
				GeneralProps.ENT_ID_ROCKET, CBCMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityRPGDot.class, "dot",
				GeneralProps.ENT_ID_DOT, CBCMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntitySatchel.class, "satchel",
				GeneralProps.ENT_ID_SATCHEL, CBCMod.instance, 32, 2, true);
		EntityRegistry.registerModEntity(EntityCrossbowArrow.class, "arrow",
				GeneralProps.ENT_ID_ARROW, CBCMod.instance, 32, 2, true);
		EntityRegistry.registerModEntity(EntityMedkit.class, "medkit",
				GeneralProps.ENT_ID_MEDKIT, CBCMod.instance, 32, 5, true);
		EntityRegistry.registerModEntity(EntityBattery.class, "battery",
				GeneralProps.ENT_ID_BATTERY, CBCMod.instance, 32, 5, true);
		EntityRegistry.registerModEntity(GaussParticleFX.class, "gaussp",
				GeneralProps.ENT_ID_GAUSS_PARTICLE, CBCMod.instance, 32, 5, true);
		EntityRegistry.registerModEntity(EntityCrossbowStill.class, "still",
				GeneralProps.ENT_ID_BOW_STILL, CBCMod.instance, 16, 5, false);

		proxy.init();
	}

	@EventHandler()
	public void postInit(FMLPostInitializationEvent Init) {
	}

	@EventHandler()
	public void serverStarting(FMLServerStartingEvent event) {
	}

}

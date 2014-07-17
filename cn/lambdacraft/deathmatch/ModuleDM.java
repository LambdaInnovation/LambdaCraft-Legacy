package cn.lambdacraft.deathmatch;

import net.minecraftforge.common.MinecraftForge;
import cn.lambdacraft.core.LCMod;
import cn.lambdacraft.core.proxy.LCGeneralProps;
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
import cn.lambdacraft.deathmatch.event.DMEventListener;
import cn.lambdacraft.deathmatch.network.MessageCharger;
import cn.lambdacraft.deathmatch.network.MessageMedFiller;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMItems;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "LambdaCraft|DeathMatch", name = "LambdaCraft DeathMatch", version = LCMod.VERSION, dependencies = LCMod.DEPENCY_CRAFTING)
public class ModuleDM {

	@Instance("LambdaCraft|DeathMatch")
	public static ModuleDM instance;

	@SidedProxy(clientSide = "cn.lambdacraft.deathmatch.proxy.ClientProxy", serverSide = "cn.lambdacraft.deathmatch.proxy.Proxy")
	public static cn.lambdacraft.deathmatch.proxy.Proxy proxy;

	@EventHandler()
	public void preInit(FMLPreInitializationEvent Init) {
		
		LCMod.netHandler.registerMessage(MessageCharger.Handler.class, MessageCharger.class, LCMod.getUniqueNetChannel(), Side.SERVER);
		LCMod.netHandler.registerMessage(MessageMedFiller.Handler.class, MessageMedFiller.class, LCMod.getUniqueNetChannel(), Side.SERVER);
		LCMod.guiHandler.addGuiElement(LCGeneralProps.GUI_ID_CHARGER, new DMGuiElements.ElementArmorCharger());
		LCMod.guiHandler.addGuiElement(LCGeneralProps.GUI_ID_HEALTH, new DMGuiElements.ElementHealthCharger());
		LCMod.guiHandler.addGuiElement(LCGeneralProps.GUI_ID_MEDFILLER, new DMGuiElements.ElementMedFiller());
		proxy.preInit();
	}

	@EventHandler()
	public void init(FMLInitializationEvent Init) {
		DMItems.init(LCMod.config);
		DMBlocks.init(LCMod.config);
		
		EntityRegistry.registerModEntity(EntityGaussRay.class, "gauss",
				LCGeneralProps.ENT_ID_GAUSS1, ModuleDM.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityGaussRayColored.class, "gauss2",
				LCGeneralProps.ENT_ID_GAUSS2, ModuleDM.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityEgonRay.class, "egonray",
				LCGeneralProps.ENT_ID_EGON_RAY, ModuleDM.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityARGrenade.class, "argrenade",
				LCGeneralProps.ENT_ID_ARGRENADE, ModuleDM.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHGrenade.class, "hgrenade",
				LCGeneralProps.ENT_ID_HGRENADE, ModuleDM.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHornet.class, "hornet",
				LCGeneralProps.ENT_ID_HORNET, ModuleDM.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityRocket.class, "rocket",
				LCGeneralProps.ENT_ID_ROCKET, ModuleDM.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityRPGDot.class, "dot",
				LCGeneralProps.ENT_ID_DOT, ModuleDM.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntitySatchel.class, "satchel",
				LCGeneralProps.ENT_ID_SATCHEL, ModuleDM.instance, 32, 2, true);
		EntityRegistry.registerModEntity(EntityCrossbowArrow.class, "arrow",
				LCGeneralProps.ENT_ID_ARROW, ModuleDM.instance, 32, 2, true);
		EntityRegistry.registerModEntity(EntityMedkit.class, "medkit",
				LCGeneralProps.ENT_ID_MEDKIT, ModuleDM.instance, 32, 5, true);
		EntityRegistry.registerModEntity(EntityBattery.class, "battery",
				LCGeneralProps.ENT_ID_BATTERY, ModuleDM.instance, 32, 5, true);
		EntityRegistry.registerModEntity(GaussParticleFX.class, "gaussp",
				LCGeneralProps.ENT_ID_GAUSS_PARTICLE, ModuleDM.instance, 32, 5, true);
		EntityRegistry.registerModEntity(EntityCrossbowStill.class, "still",
				LCGeneralProps.ENT_ID_BOW_STILL, ModuleDM.instance, 16, 5, false);

		proxy.init();
	}

	@EventHandler()
	public void postInit(FMLPostInitializationEvent Init) {
	}

	@EventHandler()
	public void serverStarting(FMLServerStartingEvent event) {
	}

}

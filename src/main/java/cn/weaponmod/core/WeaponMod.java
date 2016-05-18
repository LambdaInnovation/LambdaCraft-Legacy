package cn.weaponmod.core;

import net.minecraft.command.CommandHandler;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Logger;

import cn.liutils.core.LIUtils;
import cn.weaponmod.core.event.WMEventListener;
import cn.weaponmod.core.event.WMTickEvents;
import cn.weaponmod.core.network.MessageWMKey;
import cn.weaponmod.core.proxy.WMCommonProxy;
import cn.weaponmod.core.proxy.WMGeneralProps;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * MyWeaponary is a Forge mod API that adds utility functions about weapons.
 * It provides many low-level supports such as key listening, muzzleflash rendering,
 * enabling programmers to concentrate more on concrete action and implementations of the weapon.
 * Implementing WeaponGeneralBullet class and custom your weapon easily!
 * MyWeaponry also provides lots of utility functions about weapons, such as ammo indication, HUD drawing.
 * @author WeAthFolD
 */
@Mod(modid = "LIUtils-Weapons", name = "LIUtils-MyWeaponry", version = LIUtils.VERSION, dependencies = LIUtils.DEPENDENCY)
public class WeaponMod {
    
    public static final String DEPENDENCY = "required-after:LIUtils-Weapons@" + LIUtils.VERSION;
    
    @SidedProxy(serverSide = "cn.weaponmod.core.proxy.WMCommonProxy", clientSide = "cn.weaponmod.core.proxy.WMClientProxy")
    public static WMCommonProxy proxy;
    
    public static Logger log = FMLLog.getLogger();
    
    public static SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(WMGeneralProps.NET_CHANNEL);

    @EventHandler()
    public void preInit(FMLPreInitializationEvent event) {
        
        log.info("Starting LIUtils:MyWeaponary " + LIUtils.VERSION);
        
        //Network Registration
        netHandler.registerMessage(MessageWMKey.Handler.class, MessageWMKey.class, 1, Side.SERVER);
        MinecraftForge.EVENT_BUS.register(new WMEventListener());
        FMLCommonHandler.instance().bus().register(new WMTickEvents());
        
        proxy.preInit();
    }

    @EventHandler()
    public void init(FMLInitializationEvent Init) {
        proxy.init();
    }
    
    @EventHandler()
    public void postInit(FMLPostInitializationEvent Init) { 
        proxy.postInit();
    }
    
    @EventHandler()
    public void serverStarting(FMLServerStartingEvent event) {
        CommandHandler cm = (CommandHandler) event.getServer().getCommandManager();
    }

}

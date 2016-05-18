
package cn.liutils.core;

import net.minecraft.command.CommandHandler;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Logger;

import cn.liutils.core.energy.EnergyNet;
import cn.liutils.core.event.LIEventListener;
import cn.liutils.core.network.MsgTileDirMulti;
import cn.liutils.core.proxy.LICommonProxy;
import cpw.mods.fml.common.FMLLog;
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
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * LIUtils is a core support mod, written by Lambda Innovation. <br/>
 * It's contents involve rendering, key listening, debugging and other many stuffs. <br/>
 * <code>cn.liutils.api</code> is what user should invoke. It contains all the main functions.
 * <code>cn.liutils.core</code> is core registration module, and should not be modified or invoked normally.
 * @author WeAthFolD
 */
@Mod(modid = "LIUtils", name = "LIUtils", version = LIUtils.VERSION)
public class LIUtils {
    
    /**
     * Version Number.
     */
    public static final String VERSION = "1.7.10.000";
    
    /**
     * The mod dependency. put this in your mod's dependency if you want to use LIUtils.
     */
    public static final String DEPENDENCY = "required-after:LIUtils@" + VERSION;
    
    /**
     * Does open debug mode. turn to false when compiling.
     */
    public static final boolean DEBUG = true;
    
    @Instance("LIutils")
    public static LIUtils instance;
    
    @SidedProxy(serverSide = "cn.liutils.core.proxy.LICommonProxy", clientSide = "cn.liutils.core.proxy.LIClientProxy")
    public static LICommonProxy proxy;
    
    public static Logger log = FMLLog.getLogger();
    
    public static SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel("LIUtils");

    public static boolean ic2Exists = false;
    
    @EventHandler()
    public void preInit(FMLPreInitializationEvent event) {

        log.info("Starting LIUtils " + VERSION);
        log.info("Copyright (c) Lambda Innovation, 2013-2014");
        log.info("http://www.lambdacraft.cn");
        
        //Try and see if IC2 implementation exists
        {
            Class cl = null;
            try {
                cl = Class.forName("ic2.core.IC2");
            } catch(Exception e) {
            } finally {
                ic2Exists = cl != null;
            }
        }
        
        if(DEBUG)
            log.info("IC2 internal network state: " + ic2Exists);
        
        if(!ic2Exists)
            EnergyNet.initialize();
        
        netHandler.registerMessage(MsgTileDirMulti.Handler.class, MsgTileDirMulti.class, 0, Side.CLIENT);
        netHandler.registerMessage(MsgTileDirMulti.Request.Handler.class, MsgTileDirMulti.Request.class, 1, Side.SERVER);
        
        MinecraftForge.EVENT_BUS.register(new LIEventListener());
        proxy.preInit();
    }
    
    @EventHandler()
    public void init(FMLInitializationEvent Init) {
        proxy.init();
    }
    
    @EventHandler()
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
    
    @EventHandler()
    public void serverStarting(FMLServerStartingEvent event) {
        CommandHandler cm = (CommandHandler) event.getServer().getCommandManager();
        proxy.cmdInit(cm);
    }
    
    private void registerEntity(Class<? extends Entity> cl, String name, int id) {
        registerEntity(cl, name, id, 32, 3, true);
    }
    
    private void registerEntity(Class<? extends Entity> cl, String name, int id, int trackRange, int freq, boolean updateVel) {
        EntityRegistry.registerModEntity(cl, name, id, instance, trackRange, freq, updateVel);
    }
    
}

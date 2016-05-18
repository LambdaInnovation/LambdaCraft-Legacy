/**
 * 
 */
package cn.liutils.core.proxy;

import net.minecraft.command.CommandHandler;
import net.minecraft.entity.Entity;

import org.lwjgl.input.Keyboard;

import cn.liutils.api.client.render.RenderCrossedProjectile;
import cn.liutils.api.client.render.RenderEmptyBlock;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.api.entity.EntityTrailFX;
import cn.liutils.core.LIUtils;
import cn.liutils.core.client.register.LIKeyProcess;
import cn.liutils.core.client.render.RenderPlayerHelper;
import cn.liutils.core.client.render.RenderTrail;
import cn.liutils.core.debug.CommandModifier;
import cn.liutils.core.debug.KeyModifier;
import cn.liutils.core.debug.KeyShowInfo;
import cn.liutils.core.entity.EntityPlayerDaemon;
import cn.liutils.core.event.LIEventListener;
import cn.liutils.core.event.LITickEvents;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

/**
 * @author WeAthFolD
 *
 */
public class LIClientProxy extends LICommonProxy {

    public static LIEventListener clientTickHandler = new LIEventListener();
    public static LIKeyProcess keyProcess;
    
    @Override
    public void init() {
        super.init();
        RenderingRegistry.registerBlockHandler(new RenderEmptyBlock());
        
        RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, 
                new RenderCrossedProjectile(0.45, 0.03, 1F, 0.96F, 0.722F) {
            @Override
            public void doRender(Entity entity, double par2, double par4,
                    double par6, float par8, float par9) {
                EntityBullet bullet = (EntityBullet) entity;
                fpOffsetZ = bullet.renderFromLeft ? 0.2 : -0.2;
                tpOffsetZ = bullet.renderFromLeft ? 0.4 : -0.4;
                super.doRender(entity, par2, par4, par6, par8, par9);
            }
        }
        .setIgnoreLight(true));
        
        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerDaemon.class, new RenderPlayerHelper());
        RenderingRegistry.registerEntityRenderingHandler(EntityTrailFX.class, new RenderTrail());
        
        if(LIUtils.DEBUG) {
            LIKeyProcess.addKey("deb_0", Keyboard.KEY_NUMPAD8, false, new KeyModifier(0, true));
            LIKeyProcess.addKey("deb_1", Keyboard.KEY_NUMPAD2, false, new KeyModifier(0, false));
            LIKeyProcess.addKey("deb_2", Keyboard.KEY_NUMPAD4, false, new KeyModifier(1, true));
            LIKeyProcess.addKey("deb_3", Keyboard.KEY_NUMPAD6, false, new KeyModifier(1, false));
            LIKeyProcess.addKey("deb_4", Keyboard.KEY_NUMPAD7, false, new KeyModifier(2, true));
            LIKeyProcess.addKey("deb_5", Keyboard.KEY_NUMPAD9, false, new KeyModifier(2, false));
            LIKeyProcess.addKey("deb_6", Keyboard.KEY_NUMPAD5, false, new KeyShowInfo());
        }
    }
    
    @Override
    public void preInit() {
        super.preInit();
        FMLCommonHandler.instance().bus().register(new LITickEvents());
    }
    
    @Override
    public void postInit() {
        super.postInit();
        keyProcess = new LIKeyProcess();
    }
    
    @Override
    public void cmdInit(CommandHandler handler) {
        if(LIUtils.DEBUG) {
            handler.registerCommand(new CommandModifier());
        }
    }
}

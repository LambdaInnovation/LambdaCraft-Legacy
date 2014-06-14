package cn.weaponmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cn.liutils.api.debug.KeyMoving;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.core.client.register.LIKeyProcess;
import cn.liutils.core.client.render.RenderBullet;
import cn.weaponmod.WeaponMod;
import cn.weaponmod.client.WMClientTickHandler;
import cn.weaponmod.client.keys.Debug_ProcessorWeapon;
import cn.weaponmod.client.keys.KeyClicking;
import cn.weaponmod.client.keys.KeyReload;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class WMClientProxy extends WMCommonProxy{

	Minecraft mc = Minecraft.getMinecraft();
	public static WMClientTickHandler cth = new WMClientTickHandler();
	
	
	@Override
	public void preInit() { 
		super.preInit();
	}
	
	@Override
	public void init() {
		
		LIKeyProcess.addKey(mc.gameSettings.keyBindAttack, false, new KeyClicking(true));
		LIKeyProcess.addKey(mc.gameSettings.keyBindUseItem, false, new KeyClicking(false));
		LIKeyProcess.addKey(new KeyBinding("reload", Keyboard.KEY_R), false, new KeyReload());
		if(WeaponMod.DEBUG) {
			KeyMoving.addProcess(new Debug_ProcessorWeapon());
		}
		TickRegistry.registerTickHandler(cth, Side.CLIENT);
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet(0.5, 0.015, 1.0F, 1.0F, 1.0F).setIgnoreLight(true));
		
		super.init();
	}
	
}

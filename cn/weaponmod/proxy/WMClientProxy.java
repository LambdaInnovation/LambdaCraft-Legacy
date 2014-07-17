package cn.weaponmod.proxy;

import net.minecraft.client.Minecraft;

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

public class WMClientProxy extends WMCommonProxy{

	Minecraft mc = Minecraft.getMinecraft();
	public static WMClientTickHandler cth = new WMClientTickHandler();
	
	
	@Override
	public void preInit() { 
		super.preInit();
		LIKeyProcess.addKey(mc.gameSettings.keyBindAttack, false, new KeyClicking(true));
		LIKeyProcess.addKey(mc.gameSettings.keyBindUseItem, false, new KeyClicking(false));
		LIKeyProcess.addKey("reload", Keyboard.KEY_R, false, new KeyReload());
	}
	
	@Override
	public void init() {
		
		if(WeaponMod.DEBUG) {
			KeyMoving.addProcess(new Debug_ProcessorWeapon());
		}
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet(0.5, 0.015, 1.0F, 1.0F, 1.0F).setIgnoreLight(true));
		
		super.init();
	}
	
}

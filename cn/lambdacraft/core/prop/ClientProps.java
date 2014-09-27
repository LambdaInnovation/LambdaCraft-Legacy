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
package cn.lambdacraft.core.prop;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cn.lambdacraft.core.CBCMod;
import cn.liutils.api.LIGeneralRegistry;
import cn.liutils.api.register.Configurable;

import com.google.common.base.Charsets;

import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * 客户端的一些信息。（贴图和渲染器)
 * 
 * @author WeAthFolD
 */
public class ClientProps {
	
	@Configurable(category = "graphics", key = "alwaysCustomCrosshair", comment = "Always draw custom crosshair regardless of player wearing HEV or not.", defValue = "false")
	public static boolean alwaysCustomCrossHair = false;

	public static final int RENDER_TYPE_TRIPMINE = RenderingRegistry.getNextAvailableRenderId();
	public static final int RENDER_TYPE_EMPTY = RenderingRegistry.getNextAvailableRenderId(),
			RENDER_ID_XENPORTAL = RenderingRegistry.getNextAvailableRenderId();

	@Configurable(category = "graphics", key = "CrossHair_R", comment = "The R color value of your custom crosshair.", defValue = "255")
	public static int xHairR = 255;

	@Configurable(category = "graphics", key = "CrossHair_G", comment = "The G color value of your custom crosshair.", defValue = "255")
	public static int xHairG = 255;

	@Configurable(category = "graphics", key = "CrossHair_B", comment = "The B color value of your custom crosshair.", defValue = "255")
	public static int xHairB = 255;
	
	@Configurable(category = "graphics", key = "Spray_R", comment = "The R color value of your custom spray.", defValue = "255")
	public static int sprayR = 255;

	@Configurable(category = "graphics", key = "Spray_G", comment = "The G color value of your custom spray.", defValue = "255")
	public static int sprayG = 255;

	@Configurable(category = "graphics", key = "Spray_B", comment = "The B color value of your custom spray.", defValue = "255")
	public static int sprayB = 255;
	
	@Configurable(category = "graphics", key = "Spray_A", comment = "The alpha value of your custom spray.", defValue = "255")
	public static int sprayA = 255;

	@Configurable(category = "graphics", key = "Spray_ID", comment = "The id value of your custom spray, ranging from 0-9.", defValue = "0")
	private static int sprayID = 0;
	
	public static Properties crosshairProps;
	public static Properties sprayProps;
	public static final String NAMESPACE = "lambdacraft:";
	
	private static final Random RNG = new Random();
	

	public static final ResourceLocation 
			GAUSS_BEAM_PATH = src("lambdacraft:textures/entities/gaussbeam.png"),
			CROSSBOW_PATH = src("lambdacraft:textures/entities/crossbow.png"),
			TRIPMINE_FRONT_PATH = src("lambdacraft:textures/blocks/tripmine_front.png"),
			TRIPMINE_SIDE_PATH = src("lambdacraft:textures/blocks/tripmine_side.png"),
			TRIPMINE_TOP_PATH = src("lambdacraft:textures/blocks/tripmine_top.png"),
			TRIPMINE_RAY_PATH = src("lambdacraft:textures/blocks/tripmine_beam.png"),
			HEVCHARGER_MAIN = src("lambdacraft:textures/blocks/ac_main.png"),
			HEVCHARGER_SIDE = src("lambdacraft:textures/blocks/ac_side.png"),
			HEVCHARGER_TD = src("lambdacraft:textures/blocks/ac_td.png"),
			HEVCHARGER_BACK = src("lambdacraft:textures/blocks/ac_back.png"),
			SATCHEL_TOP_PATH = src("lambdacraft:textures/entities/satchel_top.png"),
			SATCHEL_BOTTOM_PATH = src("lambdacraft:textures/entities/satchel_bottom.png"),
			SATCHEL_SIDE_PATH = src("lambdacraft:textures/entities/satchel_side.png"),
			SATCHEL_SIDE2_PATH = src("lambdacraft:textures/entities/satchel_side2.png"),
			AR_GRENADE_PATH = src("lambdacraft:textures/entities/grenade.png"),
			RPG_ROCKET_PATH = src("lambdacraft:textures/entities/rpgrocket.png"),
			SHOTGUN_SHELL_PATH = src("lambdacraft:textures/entities/shotgun_shell.png"),
			CROSSBOW_BOW_PATH = src("lambdacraft:textures/entities/steelbow.png"),
			RED_DOT_PATH = src("lambdacraft:textures/entities/reddot.png"),
			GUI_ARMORCHARGER_PATH = src("lambdacraft:textures/gui/armor_charger.png"),
			GUI_WEAPONCRAFTER_PATH = src("lambdacraft:textures/gui/crafter.png"),
			GUI_MEDFILLER_PATH = src("lambdacraft:textures/gui/medfiller.png"),
			GUI_GENFIRE_PATH = src("lambdacraft:textures/gui/genfire.png"),
			GUI_GENSOLAR_PATH = src("lambdacraft:textures/gui/gensolar.png"),
			GUI_GENLAVA_PATH = src("lambdacraft:textures/gui/genlava.png"),
			GUI_HECHARGER_PATH = src("lambdacraft:textures/gui/hecharger.png"),
			GUI_BATBOX_PATH = src("lambdacraft:textures/gui/batbox.png"),
			GUI_ELCRAFTER_PATH = src("lambdacraft:textures/gui/elcrafter.png"),
			HORNET_TRAIL_PATH = src("lambdacraft:textures/entities/ag_trail.png"),
			BATTERY_PATH = src("lambdacraft:textures/entities/battery.png"),
			SQUEAK_MOB_PATH = src("lambdacraft:textures/entities/squeak.png"),
			WIRE_SIDE_PATH = src("lambdacraft:textures/blocks/wire_side.png"),
			WIRE_MAIN_PATH = src("lambdacraft:textures/blocks/wire_main.png"),
			WIRE_SIDE_PATH2 = src("lambdacraft:textures/blocks/wire_side2.png"),
			HECHARGER_MAIN_PATH = src("lambdacraft:textures/blocks/hecharger_main.png"),
			HECHARGER_SIDE_PATH = src("lambdacraft:textures/blocks/hecharger_side.png"),
			HECHARGER_TD_PATH = src("lambdacraft:textures/blocks/hecharger_td.png"),
			HECHARGER_BACK_PATH = src("lambdacraft:textures/blocks/hecharger_back.png"),
			AC_NOENERGY = src("lambdacraft:textures/blocks/ac_noenergy.png"),
			LONGJUMP_ARMOR_PATH = src("lambdacraft:textures/armor/longjump.png"),
			HOUNDEYE_PATH = src("lambdacraft:textures/entities/houndeye.png"),
			MEDKIT_ENT_PATH = src("lambdacraft:textures/entities/medkit.png"),
			GLOW_PATH = src("lambdacraft:textures/entities/glow.png"),
			HEV_MASK_PATH = src("lambdacraft:textures/gui/hud_mask.png"),
			HEV_HUD_PATH = src("lambdacraft:textures/gui/hev_hud.png"),
			HEADCRAB_MOB_PATH = src("lambdacraft:textures/entities/headcrab.png"),
			BARNACLE_PATH = src("lambdacraft:textures/entities/barnacle.png"),
			BARNACLE_TENTACLE_PATH = src("lambdacraft:textures/entities/barnacle_tentacle.png"),
			LIGHT_BALL_PATH = src("lambdacraft:textures/entities/lightball.png"),
			ZOMBIE_MOB_PATH = src("lambdacraft:textures/entities/zombie.png"),
		    BULL_MOB_PATH = src("lambdacraft:textures/entities/bull.png"),
			BIG_MOMOA_MOB_PATH = src("lambdacraft:textures/entities/bigmomoa.png"),
			TURRET_PATH = src("lambdacraft:textures/entities/turret.png"),
			SHOCKWAVE_PATH = src("lambdacraft:textures/entities/shockwave.png"),
			VORTIGAUNT_PATH = src("lambdacraft:textures/entities/vortigaunt.png"),
			GAUSS_ITEM_PATH = src("lambdacraft:textures/entities/gauss.png"),
			AMETHYST_PATH = src("lambdacraft:textures/blocks/amethyst_model.png"), 
			XENLIGHT_PATH = src("lambdacraft:textures/blocks/xenlight_model.png"), 
			HANDGUN_MDL_PATH = src("lambdacraft:textures/entities/9mmhandgun.png"),
			PYTHON_MDL_PATH = src("lambdacraft:textures/entities/357.png"),
			NMMAR_MDL_PATH = src("lambdacraft:textures/entities/9mmar.png"),
			URANIUM_MDL_PATH = src("lambdacraft:textures/entities/uranium.png"),
			EGON_HEAD_PATH = src("lambdacraft:textures/entities/egon_head.png"),
			EGON_BACKPACK = src("lambdacraft:textures/entities/egon_backpack.png"),
			EGON_MUZZLE = src("lambdacraft:textures/entities/egon_muz.png"), 
			XENPORTAL_PARTICLE_PATH[] = { src("lambdacraft:textures/entities/xin0.png"), src("lambdacraft:textures/entities/xin1.png") },
			EGON_BEAM_PATH = src("lambdacraft:textures/entities/egon_beam.png"),
			SS_SIDE_PATH[] = {
					src("lambdacraft:textures/blocks/ss_side0.png"),
					src("lambdacraft:textures/blocks/ss_side1.png") },
			SS_MAIN_PATH[] = { src("lambdacraft:textures/blocks/ss_0.png"),
					src("lambdacraft:textures/blocks/ss_1.png") },
			SPRY_PATH[] = { src("lambdacraft:textures/sprays/spry0.png"),
					src("lambdacraft:textures/sprays/spry1.png") },
			ITEM_SATCHEL_PATH[] = {
					src("lambdacraft:textures/items/weapon_satchel1.png"),
					src("lambdacraft:textures/items/weapon_satchel2.png") },
			EGON_BEAM_PATH1 = src("lambdacraft:textures/entities/egon_ray2.png"),
			RPG_TRAIL_PATH[] = {
					src("lambdacraft:textures/entities/rpg_trail.png"),
					src("lambdacraft:textures/entities/rpg_trail_tail.png") },
			VORTIGAUNT_RAY_PATH[] = {
					src("lambdacraft:textures/entities/ltn0.png"),
					src("lambdacraft:textures/entities/ltn1.png"),
					src("lambdacraft:textures/entities/ltn2.png") };
	
	public static final String HEV_ARMOR_PATH[] = {
		"lambdacraft:textures/armor/hev_1.png",
		"lambdacraft:textures/armor/hev_2.png"
	};
	
	public static final String SKYBOX_PATH = "lambdacraft:textures/sky/xen%s.png";

	public static final String xhair_path = "lambdacraft:crosshairs/", DEFAULT_XHAIR_PATH = xhair_path + "xhair1.png";
	public static final String spry_path = "lambdacraft:spray/";

	public static String PORTAL_PATH[] = new String[10];
	static {
		for(int i = 0; i < 10; i++) {
			PORTAL_PATH[i] = "lambdacraft:textures/blocks/xen_portal" + (i + 1) + ".png";
		}
	}
	
	public static IModelCustom MDL_GONARCH = AdvancedModelLoader.loadModel(
			new ResourceLocation("lambdacraft:models/bigmomoa.obj"));
	
	private static String mf = "lambdacraft:textures/muz/muz";
	public static final ResourceLocation MUZZLEFLASH[] = {
		src(mf + "1.png"), src(mf + "2.png"), src(mf + "3.png")
	}, 
	MUZZLEFLASH2[] = {
		src(mf + "4.png"), src(mf + "5.png"), src(mf + "6.png")
	}, 
	MUZZLEFLASH3[] = {
		src(mf + "1.png"), src(mf + "3.png"), src(mf + "7.png")
	};
	/**
	 * 获取随机的一个火光贴图。
	 * 
	 * @return 贴图路径
	 */
	public static String getRandomMuzzleFlash() {
		int random = (int) (RNG.nextFloat() * 7F) + 1;
		random = 1;
		String path = "lambdacraft:textures/muz/muz" + random + ".png";
		return path;
	}

	public static void loadProps(Configuration config) {
		LIGeneralRegistry.loadConfigurableClass(CBCMod.config, ClientProps.class);
		
		crosshairProps = new Properties();
		URL src = ClientProps.class.getResource("/assets/lambdacraft/crosshairs/crosshairs.properties");
		
		sprayProps = new Properties();
		URL src2 = ClientProps.class.getResource("/assets/lambdacraft/spray/sprays.properties");
		
		InputStream stream = null;
		
		try {
			stream = src.openStream();
			crosshairProps.load(new InputStreamReader(stream, Charsets.UTF_8));
			CBCMod.log.log(Level.FINE, "Successfully loaded crosshair props from file " + src);
		} catch (Exception e) {
			CBCMod.log.log(Level.SEVERE,"Unable to load crosshair props from file " + src);
		} finally {
			try {
			if(stream != null)
				stream.close();
			} catch(Exception e) {}
		}
		
		try {
			stream = src2.openStream();
			sprayProps.load(new InputStreamReader(stream, Charsets.UTF_8));
			CBCMod.log.log(Level.FINE, "Successfully loaded spray file from file " + src2);
		} catch (Exception e) {
			CBCMod.log.log(Level.SEVERE,"Unable to load spray props from file " + src2);
		} finally {
			try {
				if(stream != null)
					stream.close();
			} catch(Exception e) {}
		}
		
	}

	public static String getCrosshairPath(String wpnName) {
		try {
			String s = crosshairProps.getProperty(wpnName);
			if (s == null)
				return null;
			return xhair_path + s + ".png";
		} catch (NullPointerException e) {
		}
		return null;
	}
	
	public static String getSprayPath(int id) {
		try {
			String s = sprayProps.getProperty(String.valueOf(id));
			return spry_path + (s == null ? "nico.png" : s);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return spry_path + "nico.png";
	}
	
	public static void setSprayId(int id) {
		Property prop;
		sprayID = id;
		try {
			prop = CBCMod.config.get("graphics", "Spray_ID", "0");
			prop.set(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setSprayColor(int r, int g, int b, int a) {
		r = r > 255 ? 255 : (r < 0 ? 0 : r);
		g = g > 255 ? 255 : (g < 0 ? 0 : g);
		b = b > 255 ? 255 : (b < 0 ? 0 : b);
		a = a > 255 ? 255 : (a < 0 ? 0 : a);
		sprayR = r;
		sprayG = g;
		sprayB = b;
		sprayA = a;
		Property prop;
		try {
			prop = CBCMod.config.get("graphics", "Spray_R", "255");
			prop.set(r);
			
			prop = CBCMod.config.get("graphics", "Spray_G", "255");
			prop.set(g);
			
			prop = CBCMod.config.get("graphics", "Spray_B", "255");
			prop.set(b);
			
			prop = CBCMod.config.get("graphics", "Spray_A", "255");
			prop.set(a);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setCrosshairColor(int r, int g, int b) {
		r = r > 255 ? 255 : (r < 0 ? 0 : r);
		g = g > 255 ? 255 : (g < 0 ? 0 : g);
		b = b > 255 ? 255 : (b < 0 ? 0 : b);
		xHairR = r;
		xHairG = g;
		xHairB = b;
		Property prop;
		try {
			prop = CBCMod.config.get("graphics", "CrossHair_R", "255");
			prop.set(r);
			
			prop = CBCMod.config.get("graphics", "CrossHair_G", "255");
			prop.set(g);
			
			prop = CBCMod.config.get("graphics", "CrossHair_B", "255");
			prop.set(b);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getSprayId() {
		return sprayID < 0 ? 0 : sprayID;
	}
	
	private static ResourceLocation src(String s) {
		return new ResourceLocation(s);
	}

}

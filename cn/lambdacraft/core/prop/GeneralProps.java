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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.core.prop;

import net.minecraftforge.common.config.Configuration;
import cn.lambdacraft.core.CBCMod;
import cn.liutils.api.LIGeneralRegistry;
import cn.liutils.api.register.Configurable;

/**
 * Mod的一些固定信息。
 * 
 * @author WeAthFolD
 */
public class GeneralProps {

	@Configurable(key = "ignoreBlockDestroy", defValue = "false", comment = "Ignore all block destruction within the mod.")
	public static boolean ignoreBlockDestroy;

	@Configurable(key = "doWeaponuplift", defValue = "true", comment = "Enable weapon uplifting when shoot.")
	public static boolean doWeaponUplift;

	@Configurable(key = "doPlayerDamage", defValue = "true", comment = "Enable weapons' damage on player.")
	public static boolean doPlayerDamage;

	@Configurable(key = "updateRate", defValue = "4", comment = "Update rate of tile entities & entities tracking. Make this value higher if you server runs slow.")
	public static int updateRate;
	
	@Configurable(key = "KeyCode_UseBlocks", defValue = "33", comment = "Use Blocks Key.")
	public static int keyCodeUse ;
	
	@Configurable(key = "KeyCode_Reload", defValue = "19", comment = "Reload Key.")
	public static int keyCodeReload;

	

	public static final int 
		ENT_ID_GAUSS1 = 0,
		ENT_ID_EGON_RAY = 1,
		ENT_ID_TRAIL = 2, 
		ENT_ID_ARGRENADE = 3,
		ENT_ID_ARROW = 4,
		ENT_ID_HGRENADE = 5,
		ENT_ID_HORNET = 6,
		ENT_ID_ROCKET = 7,
		ENT_ID_DOT = 8,
		ENT_ID_SATCHEL = 9,
		ENT_ID_SNARK = 10,
		ENT_ID_BULLET = 11,
		ENT_ID_BOW = 12,
		ENT_ID_MEDKIT = 13,
		ENT_ID_BATTERY = 14,
		ENT_ID_GAUSS2 = 15,
		ENT_ID_GAUSS_PARTICLE = 16,
		ENT_ID_ART = 17,
		ENT_ID_HEADCRAB = 18,
		ENT_ID_BARNACLE = 19,
		ENT_ID_BOW_STILL = 20,
		ENT_ID_ZOMBIE = 21,
		ENT_ID_TURRET = 22,
		ENT_ID_HOUNDEYE = 23,
		ENT_ID_SHOCKWAVE = 24,
		ENT_ID_VORTIGAUNT = 25,
		ENT_ID_VOR_RAY = 26,
		ENT_ID_GONARCH = 27,
	    ENT_ID_MOB_BULL = 28,
	    ENT_ID_BULL_TOXIC = 29;
	
	public static final String NET_CHANNEL = "lcnet";

	public static final byte NET_ID_EXPLOSION = 0, NET_ID_DM = 1,
			NET_ID_CRAFTER_CL = 2, NET_ID_CHARGER_CL = 3, NET_ID_USE = 4,
			NET_ID_MEDFILLER_CL = 5, NET_ID_SENTRYSYNCER = 6, NET_ID_PRIMSHOOT = 7;

	public static final int GUI_ID_CRAFTER = 0, GUI_ID_CHARGER = 1,
			GUI_ID_HEALTH = 2, GUI_ID_MEDFILLER = 3, GUI_ID_GENFIRE = 4,
			GUI_ID_GENLAVA = 5, GUI_ID_GENSOLAR = 6, GUI_ID_BATBOX = 7,
			GUI_ID_EL_CRAFTER = 8;

	/**
	 * 方块和物品分类。
	 */
	public static final int CAT_MATERIAL = 0, CAT_WEAPON = 1, CAT_ELECTRIC = 2,
			CAT_EQUIPMENT = 3, CAT_MISC = 4;
	/**
	 * 方块分类。
	 */
	public static final int CAT_GENERIC = 0;

	

	public static void loadProps(Configuration config) {
		LIGeneralRegistry.loadConfigurableClass(config, GeneralProps.class);
	}

}

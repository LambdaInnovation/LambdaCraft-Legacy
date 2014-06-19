package cn.lambdacraft.deathmatch.register;

import net.minecraft.item.Item;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.deathmatch.item.ArmorHEV;
import cn.lambdacraft.deathmatch.item.ItemAttachment;
import cn.lambdacraft.deathmatch.item.ItemMedkit;
import cn.lambdacraft.deathmatch.item.weapon.ItemPhysicalCalibur;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_357;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_9mmAR;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_9mmhandgun;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Crossbow;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Crowbar;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Crowbar_Electrical;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Egon;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Gauss;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Hgrenade;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Hornet;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_RPG;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Satchel;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Shotgun;
import cn.liutils.core.register.Config;
import cn.liutils.core.register.ConfigHandler;

public class DMItems {

	public static Weapon_Crowbar weapon_crowbar, physCalibur;
	public static Weapon_Hgrenade weapon_hgrenade;

	public static Weapon_Gauss weapon_gauss;
	public static Weapon_Satchel weapon_satchel;
	public static Weapon_Egon weapon_egon;
	public static Weapon_9mmhandgun weapon_9mmhandgun;
	public static Weapon_9mmAR weapon_9mmAR;
	public static Weapon_357 weapon_357;
	public static Item weapon_shotgun;
	public static Weapon_Hornet weapon_hornet;
	public static Weapon_RPG weapon_RPG;
	public static Weapon_Crossbow weapon_crossbow;
	public static ItemMedkit medkit;
	public static Weapon_Crowbar_Electrical weapon_crowbar_el;
	
	public static ArmorHEV armorHEVBoot, armorHEVLeggings, armorHEVChestplate,
			armorHEVHelmet;
	public static ItemAttachment attach;

	public static void init(Config conf) {

		weapon_crowbar = new Weapon_Crowbar(ConfigHandler.getItemId(conf, 
				"weapon_crowbar", 1));
		
		weapon_shotgun = new Weapon_Shotgun(ConfigHandler.getItemId(conf, "weapon_shotgun", 1));
		weapon_hgrenade = new Weapon_Hgrenade(ConfigHandler.getItemId(conf, 
				"weapon_hgrenade", 1));
		weapon_9mmhandgun = new Weapon_9mmhandgun(ConfigHandler.getItemId(conf, 
				"weapon_nmmhandgun", 1));
		weapon_9mmAR = new Weapon_9mmAR(ConfigHandler.getItemId(conf, 
				"weapon_nmmAR", 1));
		weapon_357 = new Weapon_357(ConfigHandler.getItemId(conf, "weapon_357", 1));
		weapon_RPG = new Weapon_RPG(ConfigHandler.getItemId(conf, "weapon_RPG", 1));
		weapon_crossbow = new Weapon_Crossbow(ConfigHandler.getItemId(conf, 
				"weapon_crossbow", 1));
		weapon_satchel = new Weapon_Satchel(ConfigHandler.getItemId(conf, 
				"weapon_satchel", 1));

		weapon_gauss = new Weapon_Gauss(ConfigHandler.getItemId(conf, 
				"weapon_gauss", 1));
		weapon_egon = new Weapon_Egon(ConfigHandler.getItemId(conf, "weapon_egon",
				1));
		weapon_hornet = new Weapon_Hornet(ConfigHandler.getItemId(conf, 
				"weapon_hornet", 1));
		physCalibur = new ItemPhysicalCalibur(ConfigHandler.getItemId(conf, "physCalibur", 1));
		
		if(!CBCMod.ic2Installed) {
			armorHEVHelmet = new ArmorHEV(ConfigHandler.getItemId(conf, "hevHelmet",
				GeneralProps.CAT_EQUIPMENT), 0);
			armorHEVChestplate = new ArmorHEV(ConfigHandler.getItemId(conf, 
				"hevChestplate", 3), 1);
			armorHEVLeggings = new ArmorHEV(ConfigHandler.getItemId(conf, 
				"hevLeggings", 3), 2);
			armorHEVBoot = new ArmorHEV(ConfigHandler.getItemId(conf, "hevBoot", 3), 3);
			weapon_crowbar_el = new Weapon_Crowbar_Electrical(ConfigHandler.getItemId(conf, "weapon_crowbar_el", 1));
			
		}
		
		attach = new ItemAttachment(ConfigHandler.getItemId(conf, "attaches",
				3));
		medkit = new ItemMedkit(ConfigHandler.getItemId(conf, "medkit", 3));

	}
}

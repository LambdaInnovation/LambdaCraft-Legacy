package cn.lambdacraft.deathmatch.register;

import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
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

	public static void init(Configuration conf) {

		weapon_crowbar = new Weapon_Crowbar();
		
		weapon_shotgun = new Weapon_Shotgun();
		weapon_hgrenade = new Weapon_Hgrenade();
		weapon_9mmhandgun = new Weapon_9mmhandgun();
		weapon_9mmAR = new Weapon_9mmAR();
		weapon_357 = new Weapon_357();
		weapon_RPG = new Weapon_RPG();
		weapon_crossbow = new Weapon_Crossbow();
		weapon_satchel = new Weapon_Satchel();

		weapon_gauss = new Weapon_Gauss();
		weapon_egon = new Weapon_Egon();
		weapon_hornet = new Weapon_Hornet();
		physCalibur = new ItemPhysicalCalibur();
		
		armorHEVHelmet = new ArmorHEV(0);
			armorHEVChestplate = new ArmorHEV(1);
			armorHEVLeggings = new ArmorHEV(2);
			armorHEVBoot = new ArmorHEV(3);
			weapon_crowbar_el = new Weapon_Crowbar_Electrical();
		
		attach = new ItemAttachment();
		medkit = new ItemMedkit();

	}
}

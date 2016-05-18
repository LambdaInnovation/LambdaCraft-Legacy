package cn.lambdacraft.deathmatch.register;

import cpw.mods.fml.common.registry.GameRegistry;
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
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Displacer;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Egon;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Gauss;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Hgrenade;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Hornet;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_RPG;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Satchel;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Shotgun;

public class DMItems {

    public static Item 
        weapon_crowbar, 
        physCalibur,
        weapon_hgrenade,
        weapon_satchel,
        weapon_egon,
        weapon_9mmhandgun,
        weapon_9mmAR,
        weapon_357,
        weapon_shotgun,
        weapon_hornet,
        // weapon_displacer,
        medkit,
        weapon_crowbar_el;
    
    public static Weapon_RPG rpg;
    public static Weapon_Crossbow crossbow;
    public static Weapon_Gauss gauss;
    
    public static ArmorHEV 
        armorHEVBoot,
        armorHEVLeggings,
        armorHEVChestplate,
        armorHEVHelmet;
    
    public static ItemAttachment attach;

    public static void init(Configuration conf) {

        weapon_crowbar = reg(Weapon_Crowbar.class, "lc_crowbar");
        // weapon_displacer = reg(Weapon_Displacer.class, "lc_displacer");
        weapon_shotgun = reg(Weapon_Shotgun.class, "lc_shotgun");
        weapon_hgrenade = reg(Weapon_Hgrenade.class, "lc_hgren");
        weapon_9mmhandgun = reg(Weapon_9mmhandgun.class, "lc_9mmhg");
        weapon_9mmAR = reg(Weapon_9mmAR.class, "lc_9mmar");
        weapon_357 = reg(Weapon_357.class, "lc_357");
        rpg = (Weapon_RPG) reg(Weapon_RPG.class, "lc_rpg");
        crossbow = (Weapon_Crossbow) reg(Weapon_Crossbow.class, "lc_crossbow");
        weapon_satchel = reg(Weapon_Satchel.class, "lc_satchel");

        gauss = (Weapon_Gauss) reg(Weapon_Gauss.class, "lc_gauss");
        weapon_egon = reg(Weapon_Egon.class, "lc_egon");
        weapon_hornet = reg(Weapon_Hornet.class, "lc_hornet");
        physCalibur = reg(ItemPhysicalCalibur.class, "lc_pcalibur");
        
        Item[] hevs = reg(ArmorHEV.class, 4, "lc_hev");
        armorHEVHelmet = (ArmorHEV) hevs[0];
        armorHEVChestplate = (ArmorHEV) hevs[1];
        armorHEVLeggings = (ArmorHEV) hevs[2];
        armorHEVBoot = (ArmorHEV) hevs[3];
        weapon_crowbar_el = reg(Weapon_Crowbar_Electrical.class, "lc_elcrowbar");
        
        attach = (ItemAttachment) reg(ItemAttachment.class, "lc_attach");
        medkit = reg(ItemMedkit.class, "lc_medkit");

    }
    
    private static Item reg(Class<? extends Item> cl, String key) {
        try {
            Item it = cl.newInstance();
            GameRegistry.registerItem(it, key);
            return it;
        } catch(Exception e) {}
        return null;
    }
    
    private static Item[] reg(Class<? extends Item> cl, int n, String key) {
        Item[] arr = new Item[n];
        try {
        for(int i = 0; i < n; i++) {
            arr[i] = cl.getConstructor(Integer.TYPE).newInstance(i);
            GameRegistry.registerItem(arr[i], key + i);
        }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
}

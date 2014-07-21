package cn.lambdacraft.intergration.ic2;

import cpw.mods.fml.common.registry.GameRegistry;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.crafting.register.CBCBlocks;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.lambdacraft.intergration.ic2.item.ArmorHEVIC2;
import cn.lambdacraft.intergration.ic2.item.ItemBatteryIC2;
import cn.lambdacraft.intergration.ic2.item.Weapon_Crowbar_ElectricalIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockArmorChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockElCrafterIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockHealthChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockMedkitFillerIC2;
import cn.lambdacraft.intergration.ic2.tile.TileArmorChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.TileElCrafterIC2;
import cn.lambdacraft.intergration.ic2.tile.TileHealthChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.TileMedkitFillerIC2;
import cn.liutils.core.register.Config;
import cn.liutils.core.register.ConfigHandler;

public class IC2Registration {

	public static void registerBlocks() {
		Config conf = CBCMod.config;
		CBCBlocks.elCrafter = new BlockElCrafterIC2(ConfigHandler.getBlockId(conf, "elCrafter", 0));
		DMBlocks.armorCharger = new BlockArmorChargerIC2(ConfigHandler.getBlockId(conf, "armorCharger", 0));
		DMBlocks.healthCharger = new BlockHealthChargerIC2(ConfigHandler.getBlockId(conf, "healthCharger", 0));
		DMBlocks.medkitFiller = new BlockMedkitFillerIC2(ConfigHandler.getBlockId(conf, "medkitFiller", 0));
		
		GameRegistry.registerBlock(CBCBlocks.elCrafter, "electric_crafter");
		GameRegistry.registerBlock(DMBlocks.armorCharger, "armor_charger");
		GameRegistry.registerBlock(DMBlocks.healthCharger, "health_charger");
		GameRegistry.registerBlock(DMBlocks.medkitFiller, "medkit_filler");
		GameRegistry.registerTileEntity(TileArmorChargerIC2.class, "tile_entity_charger");
		GameRegistry.registerTileEntity(TileHealthChargerIC2.class, "tile_entity_hcharger");
		GameRegistry.registerTileEntity(TileMedkitFillerIC2.class, "tile_entity_medfiller");
		GameRegistry.registerTileEntity(TileElCrafterIC2.class, "tile_entity_elcrafter");
	}
	
	public static void registerItems() {
		Config conf = CBCMod.config;
		DMItems.armorHEVHelmet = new ArmorHEVIC2(ConfigHandler.getItemId(conf, "hevHelmet", GeneralProps.CAT_EQUIPMENT), 0);
		DMItems.armorHEVChestplate = new ArmorHEVIC2(ConfigHandler.getItemId(conf, "hevChestplate", 3), 1);
		DMItems.armorHEVLeggings = new ArmorHEVIC2(ConfigHandler.getItemId(conf, "hevLeggings", 3), 2);
		DMItems.armorHEVBoot = new ArmorHEVIC2(ConfigHandler.getItemId(conf, "hevBoot", 3), 3);
		DMItems.weapon_crowbar_el = new Weapon_Crowbar_ElectricalIC2(ConfigHandler.getItemId(conf, "weapon_crowbar_el", 1));
		CBCItems.battery = new ItemBatteryIC2(ConfigHandler.getItemId(conf, "battery", 3));
	}
}

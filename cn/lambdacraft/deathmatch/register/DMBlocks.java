package cn.lambdacraft.deathmatch.register;

import cpw.mods.fml.common.registry.GameRegistry;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.deathmatch.block.BlockArmorCharger;
import cn.lambdacraft.deathmatch.block.BlockHealthCharger;
import cn.lambdacraft.deathmatch.block.BlockMedkitFiller;
import cn.lambdacraft.deathmatch.block.BlockTripmine;
import cn.lambdacraft.deathmatch.block.TileArmorCharger;
import cn.lambdacraft.deathmatch.block.TileHealthCharger;
import cn.lambdacraft.deathmatch.block.TileMedkitFiller;
import cn.lambdacraft.deathmatch.block.TileTripmine;
import cn.liutils.core.register.Config;
import cn.liutils.core.register.ConfigHandler;
import net.minecraft.block.Block;

public class DMBlocks {

	public static Block blockTripmine, armorCharger, healthCharger,
			medkitFiller;

	public static void init(Config conf) {

		blockTripmine = new BlockTripmine(ConfigHandler.getBlockId(conf, 
				"tripmine", 0));
		GameRegistry.registerBlock(blockTripmine, "lc_blocktripmine");
		GameRegistry.registerTileEntity(TileTripmine.class, "tile_entity_tripmine");

		armorCharger = new BlockArmorCharger(ConfigHandler.getBlockId(conf, 
				"armorCharger", 0));
		healthCharger = new BlockHealthCharger(ConfigHandler.getBlockId(conf, 
				"healthCharger", 0));
		medkitFiller = new BlockMedkitFiller(ConfigHandler.getBlockId(conf, 
				"medkitFiller", 0));
		GameRegistry.registerBlock(armorCharger, "lc_armorcharger");
		GameRegistry.registerBlock(healthCharger, "lc_healthcharger");
		GameRegistry.registerBlock(medkitFiller, "lc_medfiller");

		GameRegistry.registerTileEntity(TileArmorCharger.class,
				"tile_entity_charger");
		GameRegistry.registerTileEntity(TileHealthCharger.class,
				"tile_entity_hcharger");
		GameRegistry.registerTileEntity(TileMedkitFiller.class,
				"tile_entity_medfiller");

	}
}

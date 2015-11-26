package cn.lambdacraft.deathmatch.register;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import cn.lambdacraft.deathmatch.block.BlockArmorCharger;
import cn.lambdacraft.deathmatch.block.BlockHealthCharger;
import cn.lambdacraft.deathmatch.block.BlockTripmine;
import cn.lambdacraft.deathmatch.block.TileArmorCharger;
import cn.lambdacraft.deathmatch.block.TileHealthCharger;
import cn.lambdacraft.deathmatch.block.TileTripmine;
import cpw.mods.fml.common.registry.GameRegistry;

public class DMBlocks {

	public static Block blockTripmine, armorCharger, healthCharger;

	public static void init(Configuration conf) {

		blockTripmine = new BlockTripmine();
		GameRegistry.registerBlock(blockTripmine, "lc_blocktripmine");
		GameRegistry.registerTileEntity(TileTripmine.class, "tile_entity_tripmine");

		armorCharger = new BlockArmorCharger();
		healthCharger = new BlockHealthCharger();
		GameRegistry.registerBlock(armorCharger, "lc_armorcharger");
		GameRegistry.registerBlock(healthCharger, "lc_healthcharger");

		GameRegistry.registerTileEntity(TileArmorCharger.class,
				"tile_entity_charger");
		GameRegistry.registerTileEntity(TileHealthCharger.class,
				"tile_entity_hcharger");

	}
}

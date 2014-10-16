package cn.lambdacraft.crafting.register;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.config.Configuration;
import cn.lambdacraft.core.block.BlockElectricalBase;
import cn.lambdacraft.crafting.block.BlockAdvWeaponCrafter;
import cn.lambdacraft.crafting.block.BlockBatBox;
import cn.lambdacraft.crafting.block.BlockCBCOres;
import cn.lambdacraft.crafting.block.BlockElectricCrafter;
import cn.lambdacraft.crafting.block.BlockGeneratorFire;
import cn.lambdacraft.crafting.block.BlockGeneratorLava;
import cn.lambdacraft.crafting.block.BlockGeneratorSolar;
import cn.lambdacraft.crafting.block.BlockRefined;
import cn.lambdacraft.crafting.block.BlockWeaponCrafter;
import cn.lambdacraft.crafting.block.BlockWire;
import cn.lambdacraft.crafting.block.tile.TileBatBox;
import cn.lambdacraft.crafting.block.tile.TileElCrafter;
import cn.lambdacraft.crafting.block.tile.TileGeneratorFire;
import cn.lambdacraft.crafting.block.tile.TileGeneratorLava;
import cn.lambdacraft.crafting.block.tile.TileGeneratorMugen;
import cn.lambdacraft.crafting.block.tile.TileGeneratorSolar;
import cn.lambdacraft.crafting.block.tile.TileWeaponCrafter;
import cn.lambdacraft.crafting.block.tile.TileWire;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * 方块注册类，包括了所有crafting包中的方块。
 * 
 * @author WeAthFolD
 */
public class CBCBlocks {

	public static Block weaponCrafter, blockRefined, uraniumOre, advCrafter,
			genFire, genLava, genSolar, genInfinite, wire, storageS, storageL;
	public static Block oreTin, oreCopper, elCrafter;

	/**
	 * 在这里进行实际的方块加载。请在Init中调用它。
	 * 
	 * @param conf
	 *            Mod内部通用Config
	 */
	public static void init(Configuration conf) {

		weaponCrafter = new BlockWeaponCrafter();
		blockRefined = new BlockRefined();
		uraniumOre = new BlockCBCOres(0);
		advCrafter = new BlockAdvWeaponCrafter();
		oreTin = new BlockCBCOres(1);
		oreCopper = new BlockCBCOres(2);
		genInfinite = new BlockElectricalBase(Material.rock).setTileType(TileGeneratorMugen.class)
				.setBlockTextureName("lambdacraft:infinite_gen").setBlockName("mugen");
		wire = new BlockWire();
		storageS = new BlockBatBox(0);
		storageL = new BlockBatBox(1);
		genSolar = new BlockGeneratorSolar();
		genLava = new BlockGeneratorLava();
		genFire = new BlockGeneratorFire();
		
		GameRegistry.registerBlock(weaponCrafter, "lc_weaponcrafter");
		GameRegistry.registerBlock(blockRefined, "lc_blockrefined");
		GameRegistry.registerBlock(uraniumOre, "lc_uranium");
		GameRegistry.registerBlock(advCrafter, "lc_advcrafter");
		GameRegistry.registerBlock(oreTin, "lc_oretin");
		GameRegistry.registerBlock(oreCopper, "lc_orecopper");
		
		elCrafter = new BlockElectricCrafter();
		GameRegistry.registerBlock(elCrafter, "lc_elcrafter");
		GameRegistry.registerTileEntity(TileElCrafter.class, "tile_entity_elcrafter");
		
		GameRegistry.registerBlock(wire, "lc_wire");
		GameRegistry.registerBlock(storageS, "lc_storages");
		GameRegistry.registerBlock(storageL, "lc_storagel");
		
		GameRegistry.registerBlock(genLava, "lc_genlava");
		GameRegistry.registerBlock(genSolar, "lc_gensolar");
		GameRegistry.registerBlock(genFire, "lc_genfire");
		GameRegistry.registerBlock(genInfinite, "lc_genmugen");
		
		GameRegistry.registerTileEntity(TileWeaponCrafter.class, "tile_entity_weapon_crafter");
		GameRegistry.registerTileEntity(TileGeneratorMugen.class, "tile_entity_mugen");
		GameRegistry.registerTileEntity(TileGeneratorSolar.class, "tile_entity_solar");
		GameRegistry.registerTileEntity(TileGeneratorFire.class, "tile_entity_genfire");
		GameRegistry.registerTileEntity(TileGeneratorLava.class, "tile_entity_genlava");
		GameRegistry.registerTileEntity(TileBatBox.TileBoxSmall.class, "tile_entity_batbox1");
		GameRegistry.registerTileEntity(TileBatBox.TileBoxLarge.class, "tile_entity_batbox2");
		GameRegistry.registerTileEntity(TileWire.class, "tile_entity_wire");
		
		return;

	}

}

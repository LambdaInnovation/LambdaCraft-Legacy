package cn.lambdacraft.crafting.register;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
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
import cn.liutils.core.register.Config;
import cn.liutils.core.register.ConfigHandler;

/**
 * 方块注册类，包括了所有crafting包中的方块。
 * 
 * @author WeAthFolD
 */
public class CBCBlocks {

	public static Block weaponCrafter, blockRefined, uraniumOre, advCrafter,
			genFire, genLava, genSolar, genMugen, wire, storageS, storageL;
	public static Block oreTin, oreCopper, elCrafter;

	/**
	 * 在这里进行实际的方块加载。请在Init中调用它。
	 * 
	 * @param conf
	 *            Mod内部通用Config
	 */
	public static void init(Config conf) {

		weaponCrafter = new BlockWeaponCrafter(ConfigHandler.getBlockId(conf,  "crafter", 0));
		blockRefined = new BlockRefined(ConfigHandler.getBlockId(conf, "refined", 0));
		uraniumOre = new BlockCBCOres(ConfigHandler.getBlockId(conf, "oreUranium",
				0), 0);
		advCrafter = new BlockAdvWeaponCrafter(ConfigHandler.getBlockId(conf, 
				"advCrafter", 0));

		oreTin = new BlockCBCOres(ConfigHandler.getBlockId(conf, "tinOre", 0), 1);
		oreCopper = new BlockCBCOres(
				ConfigHandler.getBlockId(conf, "cooperOre", 0), 2);
		genMugen = new BlockElectricalBase(ConfigHandler.getBlockId(conf, "mugen",
				0), Material.rock).setTileType(TileGeneratorMugen.class)
				.setIconName("genfire_side").setUnlocalizedName("mugen");
		wire = new BlockWire(ConfigHandler.getBlockId(conf, "wire", 0));
		storageS = new BlockBatBox(
				ConfigHandler.getBlockId(conf, "storagesmall", 0), 0);
		storageL = new BlockBatBox(
				ConfigHandler.getBlockId(conf, "storagelarge", 0), 1);
		genSolar = new BlockGeneratorSolar(ConfigHandler.getBlockId(conf, 
				"genSolar", 0));
		genLava = new BlockGeneratorLava(ConfigHandler.getBlockId(conf, "genLava",
				0));
		genFire = new BlockGeneratorFire(ConfigHandler.getBlockId(conf, "genFire",
				0));
		
		GameRegistry.registerBlock(weaponCrafter, "lc_weaponcrafter");
		GameRegistry.registerBlock(blockRefined, "lc_blockrefined");
		GameRegistry.registerBlock(uraniumOre, "lc_uranium");
		GameRegistry.registerBlock(advCrafter, "lc_advcrafter");
		GameRegistry.registerBlock(oreTin, "lc_oretin");
		GameRegistry.registerBlock(oreCopper, "lc_orecopper");
		
		elCrafter = new BlockElectricCrafter(ConfigHandler.getBlockId(conf,  "elCrafter", 0));
		GameRegistry.registerBlock(elCrafter, "lc_elcrafter");
		GameRegistry.registerTileEntity(TileElCrafter.class, "tile_entity_elcrafter");
		
		GameRegistry.registerBlock(wire, "lc_wire");
		GameRegistry.registerBlock(storageS, "lc_storages");
		GameRegistry.registerBlock(storageL, "lc_storagel");
		
		GameRegistry.registerBlock(genLava, "lc_genlava");
		GameRegistry.registerBlock(genSolar, "lc_gensolar");
		GameRegistry.registerBlock(genFire, "lc_genfire");
		GameRegistry.registerBlock(genMugen, "lc_genmugen");
		
		MinecraftForge.setBlockHarvestLevel(uraniumOre, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(blockRefined, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(weaponCrafter, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(advCrafter, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(oreTin, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(oreCopper, "pickaxe", 1);
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

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
package cn.lambdacraft.terrain.register;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import cn.lambdacraft.terrain.block.BlockBlackPillar;
import cn.lambdacraft.terrain.block.BlockSlimeFlow;
import cn.lambdacraft.terrain.block.BlockSlimeStill;
import cn.lambdacraft.terrain.block.BlockXenAmethyst;
import cn.lambdacraft.terrain.block.BlockXenCrystal;
import cn.lambdacraft.terrain.block.BlockXenDesertedDirt;
import cn.lambdacraft.terrain.block.BlockXenDirt;
import cn.lambdacraft.terrain.block.BlockXenGrass;
import cn.lambdacraft.terrain.block.BlockXenLight;
import cn.lambdacraft.terrain.block.BlockXenPortal;
import cn.lambdacraft.terrain.block.BlockXenSand;
import cn.lambdacraft.terrain.block.BlockXenStone;
import cn.lambdacraft.terrain.block.BlockXenWaterFlow;
import cn.lambdacraft.terrain.block.BlockXenWaterStill;
import cn.lambdacraft.terrain.tileentity.TileEntityXenAmethyst;
import cn.lambdacraft.terrain.tileentity.TileEntityXenLight;
import cn.lambdacraft.terrain.tileentity.TileEntityXenPortal;
import cn.liutils.core.register.Config;
import cn.liutils.core.register.ConfigHandler;

/**
 * 由于特殊原因，Xen方块暂时注册255以下的ID，不让GeneralRegistry自动分配。
 * @author F 
 * @author WeAthFolD
 *
 */
public class XenBlocks {
	
	public static Block[] blocks;
	public static BlockXenPortal xenPortal;
	public static BlockXenDesertedDirt desertedDirt;
	public static BlockXenCrystal crystal;
	public static BlockXenStone stone;
	public static BlockXenDirt dirt;
	public static BlockXenGrass grass;
	public static BlockXenLight light_on, light_off;
	public static BlockXenAmethyst amethyst;
	public static BlockBlackPillar pillar;
	public static BlockSlimeFlow slimeFlow;
	public static BlockSlimeStill slimeStill;
	public static BlockXenWaterFlow waterFlow;
	public static BlockXenWaterStill waterStill;
	public static BlockXenSand sand;
	
	public static void init(Config conf) {
		xenPortal = new BlockXenPortal(ConfigHandler.getBlockId(conf, "xenPortal", 2));
		desertedDirt = new BlockXenDesertedDirt(ConfigHandler.getFixedBlockId(conf, "xenDesertedDirt", 215, 255));
		crystal = new BlockXenCrystal(ConfigHandler.getFixedBlockId(conf, "xenCrystal", 216, 255));
		stone = new BlockXenStone(ConfigHandler.getFixedBlockId(conf, "xenStone", 217, 255));
		dirt = new BlockXenDirt(ConfigHandler.getFixedBlockId(conf, "xenDirt", 218, 255));
		grass = new BlockXenGrass(ConfigHandler.getFixedBlockId(conf, "xenGrass", 219, 255));
		pillar = new BlockBlackPillar(ConfigHandler.getFixedBlockId(conf, "xenPillar", 220, 255));
		slimeFlow = new BlockSlimeFlow(ConfigHandler.getFixedBlockId(conf, "xenSlimeFlow", 205, 255));
		slimeStill = new BlockSlimeStill(slimeFlow.blockID + 1);
		waterFlow = new BlockXenWaterFlow(ConfigHandler.getFixedBlockId(conf, "xenWater", 207, 255));
		waterStill = new BlockXenWaterStill(waterFlow.blockID + 1);
		light_on = new BlockXenLight(ConfigHandler.getFixedBlockId(conf, "xenLight_on", 209, 255), true);
		light_off = new BlockXenLight(ConfigHandler.getFixedBlockId(conf, "xenLight_off", 210, 255), false);
		amethyst = new BlockXenAmethyst(ConfigHandler.getFixedBlockId(conf, "xenAmythest", 211, 255));
		sand = new BlockXenSand(ConfigHandler.getFixedBlockId(conf, "xenSand", 212, 255));
		
		GameRegistry.registerBlock(xenPortal, "lc_xenportal");
		GameRegistry.registerBlock(desertedDirt, "lc_xendeserteddirt");
		GameRegistry.registerBlock(crystal, "lc_xencrystal");
		GameRegistry.registerBlock(stone, "lc_xenstone");
		GameRegistry.registerBlock(dirt, "lc_xendirt");
		GameRegistry.registerBlock(grass, "lc_xengrass");
		GameRegistry.registerBlock(pillar, "lc_xenpillar");
		GameRegistry.registerBlock(slimeFlow, "lc_xenslimeflow");
		GameRegistry.registerBlock(slimeStill, "lc_xenslimestill");
		GameRegistry.registerBlock(waterFlow, "lc_xenwaterflow");
		GameRegistry.registerBlock(waterStill, "lc_xenwaterstill");
		GameRegistry.registerBlock(light_on, "lc_xenlight_on");
		GameRegistry.registerBlock(light_off, "lc_xenlight_off");
		GameRegistry.registerBlock(amethyst, "lc_xenamethyst");
		GameRegistry.registerBlock(sand, "lc_xensand");
	
		GameRegistry.registerTileEntity(TileEntityXenPortal.class, "tile_entity_xen_portal");
		GameRegistry.registerTileEntity(TileEntityXenLight.class, "tile_entity_xen_light");
		GameRegistry.registerTileEntity(TileEntityXenAmethyst.class, "tile_entity_xen_amethyst");
		
		MinecraftForge.setBlockHarvestLevel(XenBlocks.crystal, "pickaxe", 1);
		
	}
	
}

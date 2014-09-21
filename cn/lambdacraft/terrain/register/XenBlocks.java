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
import net.minecraftforge.common.config.Configuration;
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
import cn.lambdacraft.terrain.tileentity.TileEntityXenAmethyst;
import cn.lambdacraft.terrain.tileentity.TileEntityXenLight;
import cn.lambdacraft.terrain.tileentity.TileEntityXenPortal;
import cn.liutils.core.register.ConfigHandler;

/**
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
	public static BlockXenSand sand;
	
	public static void init(Configuration conf) {
		
		GameRegistry.registerBlock(xenPortal, "lc_xenportal");
		GameRegistry.registerBlock(desertedDirt, "lc_xendeserteddirt");
		GameRegistry.registerBlock(crystal, "lc_xencrystal");
		GameRegistry.registerBlock(stone, "lc_xenstone");
		GameRegistry.registerBlock(dirt, "lc_xendirt");
		GameRegistry.registerBlock(grass, "lc_xengrass");
		GameRegistry.registerBlock(pillar, "lc_xenpillar");
		GameRegistry.registerBlock(slimeFlow, "lc_xenslimeflow");
		GameRegistry.registerBlock(slimeStill, "lc_xenslimestill");
		GameRegistry.registerBlock(light_on, "lc_xenlight_on");
		GameRegistry.registerBlock(light_off, "lc_xenlight_off");
		GameRegistry.registerBlock(amethyst, "lc_xenamethyst");
		GameRegistry.registerBlock(sand, "lc_xensand");
	
		GameRegistry.registerTileEntity(TileEntityXenPortal.class, "tile_entity_xen_portal");
		GameRegistry.registerTileEntity(TileEntityXenLight.class, "tile_entity_xen_light");
		GameRegistry.registerTileEntity(TileEntityXenAmethyst.class, "tile_entity_xen_amethyst");
	}
	
}

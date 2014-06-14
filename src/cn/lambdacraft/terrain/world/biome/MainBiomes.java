package cn.lambdacraft.terrain.world.biome;

import cn.lambdacraft.terrain.ModuleTerrain;

/**
 * 用于实例化各个biome类 
 * @author F
 *
 */
public class MainBiomes {
	
	public static final BiomeGenHill xenHill = new BiomeGenHill(ModuleTerrain.xenHillBiomeId);
	public static final BiomeGenPlain xenPlain = new BiomeGenPlain(ModuleTerrain.xenPlainBiomeId);
	public static final BiomeGenVoid xenVoid = new BiomeGenVoid(ModuleTerrain.xenVoidBiomeId);
	public static final BiomeGenBroken xenBroken = new BiomeGenBroken(ModuleTerrain.xenBrokenBiomeId);

}

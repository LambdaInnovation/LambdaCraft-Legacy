package cn.lambdacraft.terrain.world.biome;

import net.minecraft.world.biome.BiomeGenBase;
import cn.lambdacraft.terrain.ModuleTerrain;

/**
 * 用于实例化各个biome类 
 * @author F
 *
 */
public class MainBiomes {
	
	public static final BiomeGenBase.Height height_xen_hill = new BiomeGenBase.Height(-0.5F, 0.5F);
	public static final BiomeGenBase.Height height_xen_plain = new BiomeGenBase.Height(-1.0F, 0.2F);
	
	public static final BiomeGenHill xenHill = new BiomeGenHill(ModuleTerrain.xenHillBiomeId);
	public static final BiomeGenPlain xenPlain = new BiomeGenPlain(ModuleTerrain.xenPlainBiomeId);
	public static final BiomeGenVoid xenVoid = new BiomeGenVoid(ModuleTerrain.xenVoidBiomeId);
	public static final BiomeGenBroken xenBroken = new BiomeGenBroken(ModuleTerrain.xenBrokenBiomeId);
	public static final BiomeGenStonePlain xenStonePlain = new BiomeGenStonePlain(ModuleTerrain.xenStonePlainBiomeId);
	public static final BiomeGenXenPlainInDefaultWorld xenPlainInDefaultWorld = new BiomeGenXenPlainInDefaultWorld(ModuleTerrain.xenPlainInDefaultWorldBiomeId);

}

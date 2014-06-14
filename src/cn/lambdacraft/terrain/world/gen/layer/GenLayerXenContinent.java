package cn.lambdacraft.terrain.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
/**
 * 复制自MC原本的类
 * @author F
 *
 */
public class GenLayerXenContinent extends GenLayer {

	public GenLayerXenContinent(long par1) 
	{
		super(par1);
	}
	
	public static GenLayer[] makeTheWorld(long seed) 
	{

		GenLayer biomes = new GenLayerBiomesXenContinent(1L);

		// more GenLayerZoom = bigger biomes
		biomes = new GenLayerZoom(1000L, biomes);
		biomes = new GenLayerZoom(1001L, biomes);
		biomes = new GenLayerZoom(1002L, biomes);

		GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);

		biomes.initWorldGenSeed(seed);
		genlayervoronoizoom.initWorldGenSeed(seed);

		return new GenLayer[] {biomes, genlayervoronoizoom};
	}


	@Override
	public int[] getInts(int i, int j, int k, int l)
	{
		return null;
	}
}

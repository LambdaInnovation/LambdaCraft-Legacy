package cn.lambdacraft.xen.world.gen.layer;

import cn.lambdacraft.xen.world.biome.MainBiomes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
/**
 * 复制自MC原本的类
 * @author F
 *
 */
public class GenLayerBiomesXen extends GenLayer {

	protected BiomeGenBase[] allowedBiomes = {MainBiomes.xenVoid, MainBiomes.xenHill, MainBiomes.xenPlain};

	public GenLayerBiomesXen(long seed, GenLayer genlayer) {
		super(seed);
		this.parent = genlayer;
	}

	public GenLayerBiomesXen(long seed) {
		super(seed);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		int[] dest = IntCache.getIntCache(width*depth);

		for (int dz=0; dz<depth; dz++){
			
			for (int dx=0; dx<width; dx++){
				
				this.initChunkSeed(dx+x, dz+z);
				dest[(dx+dz*width)] = this.allowedBiomes[nextInt(this.allowedBiomes.length)].biomeID;
			}
		}
		return dest;
	}
}

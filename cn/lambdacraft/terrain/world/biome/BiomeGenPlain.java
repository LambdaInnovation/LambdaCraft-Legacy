package cn.lambdacraft.terrain.world.biome;

import cn.lambdacraft.terrain.register.XenBlocks;
import net.minecraft.world.biome.BiomeGenBase;
/**
 * Xen平原生态地貌
 * @author F
 *
 */
public class BiomeGenPlain extends BiomeGenBase{

	public BiomeGenPlain(int par1) {
		super(par1);
		this.minHeight = -1.0F;
		this.maxHeight = 0.2F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.topBlock = ((byte)XenBlocks.grass.blockID);
		this.fillerBlock = ((byte)XenBlocks.dirt.blockID);
		this.setBiomeName("Xen Plain");
		this.theBiomeDecorator = new BiomeDecoratorXen(this);
	}
}

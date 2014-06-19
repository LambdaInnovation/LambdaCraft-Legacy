package cn.lambdacraft.terrain.world.biome;

import cn.lambdacraft.terrain.register.XenBlocks;
import net.minecraft.world.biome.BiomeGenBase;
/**
 * Xen平原生态地貌
 * @author F
 *
 */
public class BiomeGenBroken extends BiomeGenBase{

	public BiomeGenBroken(int par1) {
		super(par1);
		this.minHeight = -0.5F;
		this.maxHeight = 0.5F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.topBlock = ((byte)XenBlocks.grass.blockID);
		this.fillerBlock = ((byte)XenBlocks.dirt.blockID);
		this.setBiomeName("Xen Broken");
		this.theBiomeDecorator = new BiomeDecoratorXen(this);
	}
}

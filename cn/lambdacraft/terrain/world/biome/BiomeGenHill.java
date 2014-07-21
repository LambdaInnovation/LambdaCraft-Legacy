package cn.lambdacraft.terrain.world.biome;

import cn.lambdacraft.terrain.register.XenBlocks;
import net.minecraft.world.biome.BiomeGenBase;
/**
 * Xen高山生态地貌
 * @author F
 *
 */
public class BiomeGenHill extends BiomeGenBase{

	public BiomeGenHill(int par1) {
		super(par1);
		this.minHeight = -0.5F;
		this.maxHeight = 0.5F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.topBlock = ((byte)XenBlocks.grass.blockID);
		this.fillerBlock = ((byte)XenBlocks.dirt.blockID);
		this.setBiomeName("Xen Hill");
		this.theBiomeDecorator = new BiomeDecoratorXen(this);
	}
}

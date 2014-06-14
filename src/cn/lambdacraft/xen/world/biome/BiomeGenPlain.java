package cn.lambdacraft.xen.world.biome;

import cn.lambdacraft.xen.register.XENBlocks;
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
		this.topBlock = ((byte)XENBlocks.grass.blockID);
		this.fillerBlock = ((byte)XENBlocks.dirt.blockID);
		this.setBiomeName("Xen Plain");
		this.theBiomeDecorator = new BiomeDecoratorXen(this);
	}
}

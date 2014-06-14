package cn.lambdacraft.xen.world.biome;

import cn.lambdacraft.xen.register.XENBlocks;
import net.minecraft.world.biome.BiomeGenBase;
/**
 * Xen虚空生态地貌
 * @author F
 *
 */
public class BiomeGenVoid extends BiomeGenBase{

	public BiomeGenVoid(int par1) {
		super(par1);
		this.minHeight = -1.95F;
		this.maxHeight = 0.5F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.topBlock = ((byte)XENBlocks.grass.blockID);
		this.fillerBlock = ((byte)XENBlocks.dirt.blockID);
		this.setBiomeName("Xen Void");
		this.theBiomeDecorator = new BiomeDecoratorXen(this);
	}
}

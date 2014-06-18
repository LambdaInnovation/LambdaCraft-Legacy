package cn.lambdacraft.terrain.world.biome;

import net.minecraft.world.biome.BiomeGenBase;
import cn.lambdacraft.terrain.register.XenBlocks;

public class BiomeGenStonePlain extends BiomeGenBase{

	public BiomeGenStonePlain(int par1) {
		super(par1);
		this.minHeight = -1.0F;
		this.maxHeight = 0.2F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.topBlock = ((byte)XenBlocks.stone.blockID);
		this.fillerBlock = ((byte)XenBlocks.stone.blockID);
		this.setBiomeName("Xen Plain");
		this.theBiomeDecorator = new BiomeDecoratorXen(this);
	}
}

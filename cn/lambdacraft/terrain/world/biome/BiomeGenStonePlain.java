package cn.lambdacraft.terrain.world.biome;

import net.minecraft.world.biome.BiomeGenBase;
import cn.lambdacraft.terrain.register.XenBlocks;

public class BiomeGenStonePlain extends BiomeGenBase{

	public BiomeGenStonePlain(int par1) {
		super(par1);
		this.setHeight(MainBiomes.height_xen_plain);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.topBlock = XenBlocks.stone;
		this.fillerBlock = XenBlocks.stone;
		this.setBiomeName("Xen Plain");
		this.theBiomeDecorator = new BiomeDecoratorXen();
	}
}

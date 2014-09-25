package cn.lambdacraft.terrain.world.biome;

import java.util.Random;

import cn.lambdacraft.terrain.register.XenBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
/**
 * Xen平原生态地貌
 * @author F
 *
 */
public class BiomeGenBroken extends BiomeGenBase{

	public BiomeGenBroken(int par1) {
		super(par1);
		this.setHeight(MainBiomes.height_xen_hill);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.topBlock = XenBlocks.grass;
		this.fillerBlock = XenBlocks.dirt;
		this.setBiomeName("Xen Broken");
		this.theBiomeDecorator = new BiomeDecoratorXen();
		this.waterColorMultiplier = 13458524;
	}
	
}

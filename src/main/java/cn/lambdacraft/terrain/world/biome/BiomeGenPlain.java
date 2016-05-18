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
        this.setHeight(MainBiomes.height_xen_plain);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.topBlock = XenBlocks.grass;
        this.fillerBlock = XenBlocks.dirt;
        this.setBiomeName("Xen Plain");
        this.theBiomeDecorator = new BiomeDecoratorXen();
        this.waterColorMultiplier = 13458524;
    }
}

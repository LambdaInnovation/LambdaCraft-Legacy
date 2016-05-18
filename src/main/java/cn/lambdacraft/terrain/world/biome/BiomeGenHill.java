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
        this.setHeight(MainBiomes.height_xen_hill);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.topBlock = XenBlocks.grass;
        this.fillerBlock = XenBlocks.dirt;
        this.setBiomeName("Xen Hill");
        this.theBiomeDecorator = new BiomeDecoratorXen();
        this.waterColorMultiplier = 13458524;
    }
}

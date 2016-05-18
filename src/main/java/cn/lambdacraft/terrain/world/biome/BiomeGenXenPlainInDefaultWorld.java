package cn.lambdacraft.terrain.world.biome;

import cn.lambdacraft.terrain.register.XenBlocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenXenPlainInDefaultWorld extends BiomeGenBase
{

    public BiomeGenXenPlainInDefaultWorld(int par1) 
    {
        super(par1);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.waterColorMultiplier = 13458524;
        this.topBlock = (XenBlocks.grass);
        this.fillerBlock = (XenBlocks.dirt);
        this.setBiomeName("Xen Plain(Default World)");
    }

}

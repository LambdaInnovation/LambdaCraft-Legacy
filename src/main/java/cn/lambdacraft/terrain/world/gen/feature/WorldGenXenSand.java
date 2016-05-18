package cn.lambdacraft.terrain.world.gen.feature;

import java.util.Random;

import cn.lambdacraft.terrain.register.XenBlocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenXenSand extends WorldGenerator
{
    private int radius;

    public WorldGenXenSand(int par1)
    {
        this.radius = par1;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int l = par2Random.nextInt(this.radius - 2) + 2;
        byte b0 = 2;

        for (int i1 = par3 - l; i1 <= par3 + l; ++i1)
        {
            for (int j1 = par5 - l; j1 <= par5 + l; ++j1)
            {
                int k1 = i1 - par3;
                int l1 = j1 - par5;

                if (k1 * k1 + l1 * l1 <= l * l)
                {
                    for (int i2 = par4 - b0; i2 <= par4 + b0; ++i2)
                    {
                        Block j2 = par1World.getBlock(i1, i2, j1);

                        if (j2 == XenBlocks.dirt || j2 == XenBlocks.grass)
                        {
                            par1World.setBlock(i1, i2, j1, XenBlocks.sand, 0, 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}

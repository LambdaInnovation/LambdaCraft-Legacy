package cn.lambdacraft.terrain.world.gen.feature;

import java.util.Random;

import cn.lambdacraft.terrain.register.XenBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLights extends WorldGenerator
{
    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int l = 0; l < 64; ++l)
        {
            int i1 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int j1 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int k1 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isAirBlock(i1, j1, k1) && canLightStay(par1World, i1, j1, k1))
            {
                par1World.setBlock(i1, j1, k1, XenBlocks.light_off, 0, 2);
            }
        }

        return true;
    }
    
    private boolean canLightStay(World world, int par3, int par4, int par5)
    {
        Block block = world.getBlock(par3, par4 - 1, par5);
        if(block == XenBlocks.dirt || block == XenBlocks.stone || block == XenBlocks.grass)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
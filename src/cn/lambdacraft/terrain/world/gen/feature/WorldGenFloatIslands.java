package cn.lambdacraft.terrain.world.gen.feature;

import java.util.Random;

import cn.lambdacraft.terrain.register.XenBlocks;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFloatIslands extends WorldGenerator{
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		
		boolean noGrass = false;
		int height = random.nextInt(25) + 25;
		int width = random.nextInt(3) * 10 + 11;
		float[] noiseArray = new float[961];
		for (int i = 0; i < noiseArray.length; i++) {
			noiseArray[i] = 1.0F;
		}
		int x1;
		int z1;
		int y1;
		
		if(random.nextInt(4) == 0)
		{
			noGrass = true;
		}
		
		for (x1 = 0; x1 < (width - 1)/5; x1++) 
		{
			for (z1 = 0; z1 < (width - 1)/5; z1++)
			{
				float halfWidth = ((float)width - 6)/10;
				noiseArray[x1 * 5 + z1 * 155] = (float) ((random.nextFloat() + Math.sqrt(Math.abs(x1 - halfWidth) * Math.abs(x1 - halfWidth) +  Math.abs(z1 - halfWidth) * Math.abs(z1 - halfWidth)))/((width - 1)/5));
			}
		}
		
		for (x1 = 0; x1 < width; x1++)
		{
			for (z1 = 0; z1 < width; z1++)
			{
				if(!(z1 == width - 1  && x1 == width - 1))
				{
					float xFloat =  x1/5.0F;
					float zFloat =  z1/5.0F;
					int xInt = (int)(x1/5.0F);
					int zInt = (int)(z1/5.0F);
					
					if(z1 == width - 1)
					{
						float tx = xFloat - xInt;
						float noise1 = noiseArray[xInt * 5 + zInt * 155];
						float noise2 = noiseArray[(xInt + 1)* 5 + zInt * 155];
						float noisetx = noise1 * (1 - tx * tx * (3 - 2 * tx)) + noise2 * tx * tx * (3 - 2 * tx);
						noiseArray[x1 + z1 * 31] = noisetx;
					} 
					else if(x1 == width - 1)
					{
						float tz = zFloat - zInt;
						float noise1 = noiseArray[(xInt) * 5 + (zInt) * 155];
						float noise3 = noiseArray[(xInt) * 5 + ((zInt) + 1) * 155];
						float noisetz = noise1 * (1 - tz * tz * (3 - 2 * tz)) + noise3 * tz * tz * (3 - 2 * tz);
						noiseArray[x1 + z1 * 31] = noisetz;
					}
					else
					{
						float tx = xFloat - xInt;
						float tz = zFloat - zInt;
						float noise1 = noiseArray[(xInt) * 5 + (zInt) * 155];
						float noise2 = noiseArray[((xInt) + 1)* 5 + (zInt) * 155];
						float noise3 = noiseArray[(xInt) * 5 + ((zInt) + 1) * 155];
						float noise4 = noiseArray[((xInt) + 1)* 5 + ((zInt) + 1) * 155];
						float noisetx1 = noise1 * (1 - tx * tx * (3 - 2 * tx)) + noise2 * tx * tx * (3 - 2 * tx);
						float noisetx2 = noise3 * (1 - tx * tx * (3 - 2 * tx)) + noise4 * tx * tx * (3 - 2 * tx);
						float noisetz = noisetx1 * (1 - tz * tz * (3 - 2 * tz)) + noisetx2 * tz * tz * (3 - 2 * tz);
						noiseArray[x1 + z1 * 31] = noisetz;
					}
				}
			}
		}
		
		
		for (x1 = 0; x1 < width; x1++)
		{
			for (z1 = 0; z1 < width; z1++)
			{
				for (y1 = 0; y1 < height * 0.5F; y1++)
				{
					if(noiseArray[x1 + z1 * 31] <= 0.5F && y1 > noiseArray[x1 + z1 * 31] * height)
					{
						if(y1 == height * 0.5F - 1 && !noGrass)
						{
							world.setBlock(x + x1, y + y1, z + z1, XenBlocks.grass.blockID, 0, 2);
						}
						else if((y1 == height * 0.5F - 2 || y1 == height * 0.5F - 3)&& !noGrass)
						{
							world.setBlock(x + x1, y + y1, z + z1, XenBlocks.dirt.blockID, 0, 2);
						}
						else
						{
							world.setBlock(x + x1, y + y1, z + z1, XenBlocks.stone.blockID, 0, 2);
						}
					}
				}
			}
		}
		return true;
	}
}

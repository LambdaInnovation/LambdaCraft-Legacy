package cn.lambdacraft.terrain.world.gen;

import cn.lambdacraft.terrain.register.XenBlocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;

public class MapGenFloatIsland extends MapGenBase{

    protected void generateFloatIsland(World world, int genX, int genY, int genZ, byte[] par5ArrayOfByte, int thisChunkX, int thisChunkZ)
    {
    	
		int height = this.rand.nextInt(25) + 25;
		int width = this.rand.nextInt(3) * 10 + 11;
		float[] noiseArray = new float[961];
		float[] noiseArray2 = new float[961];
		for (int i = 0; i < noiseArray.length; i++) {
			noiseArray[i] = 1.0F;
		}
		
		int x1;
		int z1;
		
		for (x1 = 0; x1 < (width - 1)/5; x1++) 
		{
			for (z1 = 0; z1 < (width - 1)/5; z1++)
			{
				float halfWidth = ((float)width - 6)/10;
				noiseArray[x1 * 5 + z1 * 155] = (float) ((this.rand.nextFloat() + Math.sqrt(Math.abs(x1 - halfWidth) * Math.abs(x1 - halfWidth) +  Math.abs(z1 - halfWidth) * Math.abs(z1 - halfWidth)))/((width - 1)/5));
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
		
		int xHelper = this.rand.nextInt((width - 1)/5 - 1);
		int zHelper = this.rand.nextInt((width - 1)/5 - 1);
		for (x1 = 0; x1 < (width - 1)/5; x1++) 
		{
			for (z1 = 0; z1 < (width - 1)/5; z1++)
			{
				if(x1 == xHelper && z1 == zHelper)
				{
					noiseArray2[x1 * 5 + z1 * 155] = 0.3F;
				}
				else if(x1 == 0 || z1 == 0 || x1 == (width - 1)/5 - 1 || z1 == (width - 1)/5 - 1)
				{
					noiseArray2[x1 * 5 + z1 * 155] = 0.0F;
				}
				else
				{
					noiseArray2[x1 * 5 + z1 * 155] = this.rand.nextFloat()/5;
				}
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
						float noise1 = noiseArray2[xInt * 5 + zInt * 155];
						float noise2 = noiseArray2[(xInt + 1)* 5 + zInt * 155];
						float noisetx = noise1 * (1 - tx * tx * (3 - 2 * tx)) + noise2 * tx * tx * (3 - 2 * tx);
						noiseArray2[x1 + z1 * 31] = noisetx;
					} 
					else if(x1 == width - 1)
					{
						float tz = zFloat - zInt;
						float noise1 = noiseArray2[(xInt) * 5 + (zInt) * 155];
						float noise3 = noiseArray2[(xInt) * 5 + ((zInt) + 1) * 155];
						float noisetz = noise1 * (1 - tz * tz * (3 - 2 * tz)) + noise3 * tz * tz * (3 - 2 * tz);
						noiseArray2[x1 + z1 * 31] = noisetz;
					}
					else
					{
						float tx = xFloat - xInt;
						float tz = zFloat - zInt;
						float noise1 = noiseArray2[(xInt) * 5 + (zInt) * 155];
						float noise2 = noiseArray2[((xInt) + 1)* 5 + (zInt) * 155];
						float noise3 = noiseArray2[(xInt) * 5 + ((zInt) + 1) * 155];
						float noise4 = noiseArray2[((xInt) + 1)* 5 + ((zInt) + 1) * 155];
						float noisetx1 = noise1 * (1 - tx * tx * (3 - 2 * tx)) + noise2 * tx * tx * (3 - 2 * tx);
						float noisetx2 = noise3 * (1 - tx * tx * (3 - 2 * tx)) + noise4 * tx * tx * (3 - 2 * tx);
						float noisetz = noisetx1 * (1 - tz * tz * (3 - 2 * tz)) + noisetx2 * tz * tz * (3 - 2 * tz);
						noiseArray2[x1 + z1 * 31] = noisetz;
					}
				}
			}
		}
		
		int genStartX = genX - thisChunkX * 16;
		int genEndX = genX + width - thisChunkX * 16;
		int genStartZ = genZ - thisChunkZ * 16;
		int genEndZ = genZ + width - thisChunkZ * 16;
		int genEndY = genY + height / 2;
		int x2;
		int y2;
		int z2;
		
		if(genStartX < 0 )
		{
			genStartX = 0;
		}
		
		if(genEndX > 16 )
		{
			genEndX = 16;
		}
		
		if(genStartZ < 0 )
		{
			genStartZ = 0;
		}
		
		if(genEndZ > 16 )
		{
			genEndZ = 16;
		}
		
		if(genEndY > 128 )
		{
			genEndY = 128;
		}
		
		for (x2 = genStartX; x2 < genEndX; x2++)
		{
			for (z2 = genStartZ; z2 < genEndZ; z2++)
			{
				for (y2 = genY; y2 <= genEndY; y2++)
				{
					if(noiseArray[x2 + thisChunkX * 16 - genX + (z2 + thisChunkZ * 16 - genZ) * 31] <= 0.5F && y2 > noiseArray[x2 + thisChunkX * 16 - genX + (z2 + thisChunkZ * 16 - genZ) * 31] * height + genY)
					{
							par5ArrayOfByte[(x2 * 16 + z2) * 128 + y2] = (byte)XenBlocks.stone.blockID;
					}
				}
			}
		}
		
		boolean[] booleanArray = new boolean[961];
		for (int i = 0; i < booleanArray.length; i++) {
			booleanArray[i] = false;
		}
		
		for (x2 = genStartX; x2 < genEndX; x2++)
		{
			for (z2 = genStartZ; z2 < genEndZ; z2++)
			{
				if(noiseArray[x2 + thisChunkX * 16 - genX + (z2 + thisChunkZ * 16 - genZ) * 31] <= 0.5F)
				{
					booleanArray[x2 + thisChunkX * 16 - genX + (z2 + thisChunkZ * 16 - genZ) * 31] = true;
				}
			}
		}
		
		if(!(genEndY == 128))
		{
			height = height/2;
			genY = (int)genEndY + 1;
			genEndY = genEndY + rand.nextInt(height) + 5;
			
			if(genEndY > 128 )
			{
				genEndY = 128;
			}
			
			for (x2 = genStartX; x2 < genEndX; x2++)
			{
				for (z2 = genStartZ; z2 < genEndZ; z2++)
				{
					for (y2 = genY; y2 <= genEndY; y2++)
					{
						if(y2 < noiseArray2[x2 + thisChunkX * 16 - genX + (z2 + thisChunkZ * 16 - genZ) * 31] * height + genY
						&& booleanArray[x2 + thisChunkX * 16 - genX + (z2 + thisChunkZ * 16 - genZ) * 31])
						{
								par5ArrayOfByte[(x2 * 16 + z2) * 128 + y2] = (byte)XenBlocks.stone.blockID;
						}
					}
				}
			}
		}
		
    }

    @Override
	protected void recursiveGenerate(World world, int genChunkX, int genChunkZ, int thisChunkX, int thisChunkZ, byte[] par6ArrayOfByte)
    {
        if (this.rand.nextInt(30) == 0)
        {
        	int genX = genChunkX * 16 + this.rand.nextInt(16);
            int genY = this.rand.nextInt(90);
            int genZ = genChunkZ * 16 + this.rand.nextInt(16);
            
            this.generateFloatIsland(world, genX, genY, genZ, par6ArrayOfByte, thisChunkX, thisChunkZ);
        }
    }
}


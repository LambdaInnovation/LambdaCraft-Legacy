package cn.lambdacraft.terrain.world.gen;

import cn.lambdacraft.terrain.register.XenBlocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;

/**
 *   @author F
 *   关于浮岛群的生成……
 */
public class MapGenFloatIslandGroup extends MapGenBase{

	public MapGenFloatIslandGroup() {
		super();
		this.range = 25;
	}

	/** 这部分内容基本同单个浮岛生成 */
	private void generateFloatIsland(World world, int genX, int genY, int genZ, byte[] par5ArrayOfByte, 
			                           int thisChunkX, int thisChunkZ, int height, int width)
    {
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

	/** 
	 * 在给定的两点间造出连接用的桥梁
	 * @param world 当前的world实例
	 * @param firstGenX 第一个点的X坐标 以下类推
	 * @param firstGenY
	 * @param firstGenZ
	 * @param secondGenX
	 * @param secondGenY
	 * @param secondGenZ
	 * @param par5ArrayOfByte 包含该chunk所有blockID信息的一个byte数组
	 * @param thisChunkX 当前chunk的X坐标
	 * @param thisChunkZ 当前chunk的Z坐标
	 */
	private void generateBridge(World world, int firstGenX, int firstGenY, int firstGenZ, int secondGenX, 
			int secondGenY, int secondGenZ, byte[] par5ArrayOfByte, int thisChunkX, int thisChunkZ)
	{
		if(!(Math.max(firstGenY, secondGenY) > 128))
		{
			int littleZ = Math.min(firstGenZ, secondGenZ);
			
			// 决定相对于该chunk的坐标 
			int genStartX = Math.min(firstGenX, secondGenX) - thisChunkX * 16;
			int genEndX = Math.max(firstGenX, secondGenX) - thisChunkX * 16;
			int genStartZ = littleZ - thisChunkZ * 16;
			int genEndZ = Math.max(firstGenZ, secondGenZ) - thisChunkZ * 16;
			
			// 起点坐标与终点坐标的高度差及宽度
			int height = Math.abs(firstGenY - secondGenY);
			int lengthOfX = Math.abs(firstGenX - secondGenX);
			int lengthOfZ = Math.abs(firstGenZ - secondGenZ);
			
			//桥梁本身的高度和宽度相关参数
			int widthOfBriage = ((lengthOfZ + 1)/(lengthOfX + 1) + 1) / 2 + 1;
			int heightOfBriage = 1;
			
			int upY = Math.max(firstGenY, secondGenY);
			int downY = Math.min(firstGenY, secondGenY);
			
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
			
			/** 判断相对位置 使桥的方向正确 */
			if((secondGenX >= firstGenX) && (secondGenZ >= firstGenZ))
			{
				for (int x = genStartX; x < genEndX; x++)
				{
					for (int z = genStartZ; z < genEndZ; z++)
					{
						for (int y = downY; y <= upY; y++)
						{
							/** 相似三角型的相似比 */
							float t = ((float)(x + thisChunkX * 16 - Math.min(firstGenX, secondGenX)))/((float)(Math.abs(firstGenX - secondGenX)));
							if((firstGenY >= secondGenY) 
								&& (y <= (int)(downY + (1.0F - t) * height) + heightOfBriage) && (y >= (int)(downY + (1.0F - t) * height) - heightOfBriage)
								&& ((z + thisChunkZ * 16) <= (int)(littleZ + t * lengthOfZ) + widthOfBriage)
								&& ((z + thisChunkZ * 16) >= (int)(littleZ + t * lengthOfZ) - widthOfBriage))
							{
									par5ArrayOfByte[(x * 16 + z) * 128 + y] = (byte)XenBlocks.stone.blockID;
							}
							if((firstGenY < secondGenY) 
								&& (y <= (int)(downY + t * height) + heightOfBriage) && (y >= (int)(downY + t * height) - heightOfBriage)
								&& ((z + thisChunkZ * 16) <= (int)(littleZ + t * lengthOfZ) + widthOfBriage)
								&& ((z + thisChunkZ * 16) >= (int)(littleZ + t * lengthOfZ) - widthOfBriage))
							{
									par5ArrayOfByte[(x * 16 + z) * 128 + y] = (byte)XenBlocks.stone.blockID;
							}
						}
					}
				}
			}
			if((secondGenX <= firstGenX) && (secondGenZ <= firstGenZ))
			{
				for (int x = genStartX; x < genEndX; x++)
				{
					for (int z = genStartZ; z < genEndZ; z++)
					{
						for (int y = downY; y <= upY; y++)
						{
							/** 相似三角型的相似比 */
							float t = ((float)(x + thisChunkX * 16 - Math.min(firstGenX, secondGenX)))/((float)(Math.abs(firstGenX - secondGenX)));
							if((firstGenY >= secondGenY) 
								&& (y <= (int)(downY + t * height) + heightOfBriage) && (y >= (int)(downY + t * height) - heightOfBriage)
								&& ((z + thisChunkZ * 16) <= (int)(littleZ + t * lengthOfZ) + widthOfBriage)
								&& ((z + thisChunkZ * 16) >= (int)(littleZ + t * lengthOfZ) - widthOfBriage))
							{
									par5ArrayOfByte[(x * 16 + z) * 128 + y] = (byte)XenBlocks.stone.blockID;
							}
							if((firstGenY < secondGenY) 
								&& (y <= (int)(downY + (1.0F - t) * height) + heightOfBriage) && (y >= (int)(downY + (1.0F - t) * height) - heightOfBriage)
								&& ((z + thisChunkZ * 16) <= (int)(littleZ + t * lengthOfZ) + widthOfBriage)
								&& ((z + thisChunkZ * 16) >= (int)(littleZ + t * lengthOfZ) - widthOfBriage))
							{
									par5ArrayOfByte[(x * 16 + z) * 128 + y] = (byte)XenBlocks.stone.blockID;
							}
						}
					}
				}
			}
			if((secondGenX < firstGenX) && (secondGenZ > firstGenZ))
			{
				for (int x = genStartX; x < genEndX; x++)
				{
					for (int z = genStartZ; z < genEndZ; z++)
					{
						for (int y = downY; y <= upY; y++)
						{
							/** 相似三角型的相似比 */
							float t = ((float)(x + thisChunkX * 16 - Math.min(firstGenX, secondGenX)))/((float)(Math.abs(firstGenX - secondGenX)));
							if((firstGenY >= secondGenY) 
								&& (y <= (int)(downY + t * height) + heightOfBriage) && (y >= (int)(downY + t * height) - heightOfBriage)
								&& ((z + thisChunkZ * 16) <= (int)(littleZ + (1.0F - t) * lengthOfZ) + widthOfBriage)
								&& ((z + thisChunkZ * 16) >= (int)(littleZ + (1.0F - t) * lengthOfZ) - widthOfBriage))
							{
									par5ArrayOfByte[(x * 16 + z) * 128 + y] = (byte)XenBlocks.stone.blockID;
							}
							if((firstGenY < secondGenY) 
								&& (y <= (int)(downY + (1.0F - t) * height) + heightOfBriage) && (y >= (int)(downY + (1.0F - t) * height) - heightOfBriage)
								&& ((z + thisChunkZ * 16) <= (int)(littleZ + (1.0F - t) * lengthOfZ) + widthOfBriage)
								&& ((z + thisChunkZ * 16) >= (int)(littleZ + (1.0F - t) * lengthOfZ) - widthOfBriage))
							{
									par5ArrayOfByte[(x * 16 + z) * 128 + y] = (byte)XenBlocks.stone.blockID;
							}
						}
					}
				}
			}
			if((secondGenX > firstGenX) && (secondGenZ < firstGenZ))
			{
				for (int x = genStartX; x < genEndX; x++)
				{
					for (int z = genStartZ; z < genEndZ; z++)
					{
						for (int y = downY; y <= upY; y++)
						{
							
							/** 相似三角型的相似比 */
							float t = ((float)(x + thisChunkX * 16 - Math.min(firstGenX, secondGenX)))/((float)(Math.abs(firstGenX - secondGenX)));
							if((firstGenY >= secondGenY) 
								&& (y <= (int)(downY + (1.0F - t) * height) + heightOfBriage) && (y >= (int)(downY + (1.0F - t) * height) - heightOfBriage)
								&& ((z + thisChunkZ * 16) <= (int)(littleZ + (1.0F - t) * lengthOfZ) + widthOfBriage)
								&& ((z + thisChunkZ * 16) >= (int)(littleZ + (1.0F - t) * lengthOfZ) - widthOfBriage))
							{
									par5ArrayOfByte[(x * 16 + z) * 128 + y] = (byte)XenBlocks.stone.blockID;
							}
							if((firstGenY < secondGenY) 
								&& (y <= (int)(downY + t * height) + heightOfBriage) && (y >= (int)(downY + t * height) - heightOfBriage)
								&& ((z + thisChunkZ * 16) <= (int)(littleZ + (1.0F - t) * lengthOfZ) + widthOfBriage)
								&& ((z + thisChunkZ * 16) >= (int)(littleZ + (1.0F - t) * lengthOfZ) - widthOfBriage))
							{
									par5ArrayOfByte[(x * 16 + z) * 128 + y] = (byte)XenBlocks.stone.blockID;
							}
						}
					}
				}
			}
			
		}
	}
    @Override
	protected void recursiveGenerate(World world, int genChunkX, int genChunkZ, int thisChunkX, int thisChunkZ, byte[] par6ArrayOfByte)
    {
        if (this.rand.nextInt(300) == 0)
        {
        	int genX = genChunkX * 16 + this.rand.nextInt(16);
            int genY = this.rand.nextInt(40) + 50;
            int genZ = genChunkZ * 16 + this.rand.nextInt(16);
            
            /** 储存每个浮岛的高度和宽度大小 */
            int[] heightGroup = new int[16];
            int[] widthGroup = new int[16];
            
            /** 储存每个浮岛的X Z Y坐标 */
            int[] xGroup = new int[16];
            int[] zGroup = new int[16];
            int[] yGroup = new int[16];
            
            /** 代表每个空岛是否存在  */
            boolean[] island = new boolean[16];
            
            /** 浮岛间的最小X Z间距和允许随机的间距 */
            int minDistance = 5;
            int randomDistance = 20;
            
            for (int i = 0; i < 16; i++) 
            {
            	heightGroup[i] = this.rand.nextInt(25) + 25;
            	widthGroup[i] = this.rand.nextInt(2) * 10 + 21;
            	island[i] = false;
            	
            	if(i == 0)
            	{
            		xGroup[i] = genX;
            		zGroup[i] = genZ;
            		yGroup[i] = genY;
            	}
            	if(i == 1 || i == 2 || i == 3)
            	{
            		xGroup[i] = genX + addOrSubtract(this.rand.nextInt(randomDistance) + minDistance, widthGroup[0], widthGroup[i]);
            		zGroup[i] = genZ + addOrSubtract(this.rand.nextInt(randomDistance) + minDistance, widthGroup[0], widthGroup[i]);
            		yGroup[i] = genY + addOrSubtract() * (this.rand.nextInt(10));
            	}
            	if(i == 4 || i == 5)
            	{
            		xGroup[i] = xGroup[1] + addOrSubtract(this.rand.nextInt(randomDistance) + minDistance, widthGroup[1], widthGroup[i]);
            		zGroup[i] = zGroup[1] + addOrSubtract(this.rand.nextInt(randomDistance) + minDistance, widthGroup[1], widthGroup[i]);
            		yGroup[i] = yGroup[1] + addOrSubtract() * (this.rand.nextInt(10));
            	}
            	if(i == 6 || i == 7)
            	{
            		xGroup[i] = xGroup[2] + addOrSubtract(this.rand.nextInt(randomDistance) + minDistance, widthGroup[2], widthGroup[i]);
            		zGroup[i] = zGroup[2] + addOrSubtract(this.rand.nextInt(randomDistance) + minDistance, widthGroup[2], widthGroup[i]);
            		yGroup[i] = yGroup[2] + addOrSubtract() * (this.rand.nextInt(10));
            	}
            	if(i == 8 || i == 9)
            	{
            		xGroup[i] = xGroup[3] + addOrSubtract(this.rand.nextInt(randomDistance) + minDistance, widthGroup[3], widthGroup[i]);
            		zGroup[i] = zGroup[3] + addOrSubtract(this.rand.nextInt(randomDistance) + minDistance, widthGroup[3], widthGroup[i]);
            		yGroup[i] = yGroup[3] + addOrSubtract() * (this.rand.nextInt(10));
            	}
            	if(i >= 10)
            	{
            		xGroup[i] = xGroup[i - 6] + addOrSubtract(this.rand.nextInt(randomDistance) + minDistance, widthGroup[i - 6], widthGroup[i]);
            		zGroup[i] = zGroup[i - 6] + addOrSubtract(this.rand.nextInt(randomDistance) + minDistance, widthGroup[i - 6], widthGroup[i]);
            		yGroup[i] = yGroup[i - 6] + addOrSubtract() * (this.rand.nextInt(10));
            	}
			}
            
            for (int i = 0; i < 16; i++) 
            {
				if(i == 0 || i == 1 || i == 4)
				{
					island[i] = true;
				}
				if(i == 2 || i == 3 || i == 5)
				{
					island[i] = this.rand.nextBoolean();
				}
				if((i == 6 || i == 7) && island[2])
				{
					island[i] = this.rand.nextBoolean();
				}
				if((i == 8 || i == 9) && island[3])
				{
					island[i] = this.rand.nextBoolean();
				}
				if(i >= 10 && island[i - 6])
				{
					island[i] = this.rand.nextBoolean();
				}
			}     
            
            for (int i = 0; i < 16; i++) 
            {
				if(island[i])
				{
					this.generateFloatIsland(world, xGroup[i], yGroup[i], zGroup[i], par6ArrayOfByte, thisChunkX, thisChunkZ, heightGroup[i], widthGroup[i]);
				}
			}
            
            for (int start = 0; start < 10; start++) 
            {
				for (int end = 0; end < 16; end++) 
				{
					if((start == 0) && (end == 1 || end == 2 || end == 3) && island[end]) 
					{
						this.generateBridge(world, xGroup[start] + widthGroup[start]/2, 
						yGroup[start] + heightGroup[start]/2, zGroup[start] + widthGroup[start]/2,xGroup[end] + widthGroup[end]/2, 
						yGroup[end] + heightGroup[end]/2, zGroup[end] + widthGroup[end]/2, par6ArrayOfByte, thisChunkX, thisChunkZ);
					}
					if(((start == 1) && (end == 4 || end == 5) && island[end])
							|| ((start == 2) && (end == 6 || end == 7) && island[end])
							|| ((start == 3) && (end == 8 || end == 9) && island[end]))
					{
						this.generateBridge(world, xGroup[start] + widthGroup[start]/2, 
						yGroup[start] + heightGroup[start]/2, zGroup[start] + widthGroup[start]/2,xGroup[end] + widthGroup[end]/2, 
						yGroup[end] + heightGroup[end]/2, zGroup[end] + widthGroup[end]/2, par6ArrayOfByte, thisChunkX, thisChunkZ);
					}
					if ((start == (end - 6)) && (end >= 10) && island[end])
					{
						this.generateBridge(world, xGroup[start] + widthGroup[start]/2, 
						yGroup[start] + heightGroup[start]/2, zGroup[start] + widthGroup[start]/2,xGroup[end] + widthGroup[end]/2, 
						yGroup[end] + heightGroup[end]/2, zGroup[end] + widthGroup[end]/2, par6ArrayOfByte, thisChunkX, thisChunkZ);
					}
				}
			}
        }
    }
    
    /** 
     * 用于随机决定坐标是加或者减，同时考虑方向上的问题
     * @param distance 两浮岛的间距
     * @param widthStart 坐标已经决定的浮岛的宽度
     * @param widthEnd 坐标尚未决定的浮岛的宽度
     * @return
     */
    private int addOrSubtract(int distance, int widthStart, int widthEnd)
    {
    	if(this.rand.nextBoolean())
    	{
    		return distance + widthStart;
    	}
    	else
    	{
    		return -(distance + widthEnd);
    	}
    }
    
    private int addOrSubtract()
    {
    	if(this.rand.nextBoolean())
    	{
    		return 1;
    	}
    	else
    	{
    		return -1;
    	}
    }
}

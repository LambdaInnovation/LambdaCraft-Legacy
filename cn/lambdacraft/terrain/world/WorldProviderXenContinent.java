/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组＄1�7 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从�1�7�LambdaCraft弄1�7源协议�1�7��1�7�你允许阅读，修改以及调试运衄1�7
 * 源代码， 然�1�7�你不允许将源代码以另外任何的方式发布，除非你得到了版权扄1�7有�1�7�的许可〄1�7
 */
package cn.lambdacraft.terrain.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cn.lambdacraft.terrain.ModuleTerrain;
import cn.lambdacraft.terrain.client.renderer.RenderXenSky;
import cn.lambdacraft.terrain.world.chunk.WorldChunkManagerXenContinent;
import cn.lambdacraft.terrain.world.gen.ChunkProviderXenContinent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.DimensionManager;

/**
 * @author F
 *
 */
public class WorldProviderXenContinent extends WorldProvider {
	
	@Override
	public String getDimensionName() {
		return "XenContinent";
	}

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerXenContinent(worldObj.getSeed(), terrainType);
		this.dimensionId = ModuleTerrain.xenContinentDimensionID;
	}
	
    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    @Override
	public boolean canRespawnHere()
    {
        return false;
    }
	
    @Override
    public IChunkProvider createChunkGenerator()
    {
    	return new ChunkProviderXenContinent(worldObj, worldObj.getSeed(), false);
    }

	@Override
	public String getSaveFolder() 
	{
		return "DIM_XEN_CONTINENT";
	}

	@Override
	public String getWelcomeMessage()
	{
		return "Welcome to the borderworld, Xen";
	}

	@Override
	public String getDepartMessage() 
	{
			return "See you next time";
	}
	
	@Override
    public long getWorldTime()
    {
        return 12000L;
    }
	
    @Override
	public void toggleRain(){}
    
    @Override
	public void updateWeather(){}
    
    @Override
	public boolean canDoLightning(Chunk chunk)
    {
        return false;
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * Return Vec3D with biome specific fog color
     */
    public Vec3 getFogColor(float par1, float par2)
    {
        float f2 = MathHelper.cos(par1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        float f3 = 0.7529412F;
        float f4 = 0.84705883F;
        float f5 = 1.0F;
        f3 *= f2 * 0.306F + 0.06F;
        f4 *= f2 * 0.788F + 0.06F;
        f5 *= f2 * 0.2275F + 0.09F;
        return this.worldObj.getWorldVec3Pool().getVecFromPool(f3, f4, f5);
    }

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) 
	{
		return false;
	}
	
	@Override
    public boolean isSurfaceWorld()
    {
        return false;
    }
    
	@Override
    public boolean isDaytime()
    {
    	return false;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
        return new RenderXenSky();
    }
}

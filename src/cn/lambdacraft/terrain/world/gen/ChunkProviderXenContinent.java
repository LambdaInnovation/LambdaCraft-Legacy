package cn.lambdacraft.terrain.world.gen;

import java.util.List;
import java.util.Random;

import cn.lambdacraft.terrain.register.XenBlocks;
import cn.lambdacraft.terrain.world.biome.MainBiomes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.event.terraingen.*;
/**
 * 
 * @author F
 * Xen世界生成法则核心部分
 * Noise生成依赖于Minecraft原版生成算法
 *
 */
public class ChunkProviderXenContinent implements IChunkProvider
{
    /** RNG. */
    private Random rand;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    
    /** 第二次Noise所用 */
    private NoiseGeneratorOctaves noiseGenXen1;
    private NoiseGeneratorOctaves noiseGenXen2;
    private NoiseGeneratorOctaves noiseGenXen3;
    private NoiseGeneratorOctaves noiseGenXen4;
    public NoiseGeneratorOctaves noiseGenXen5;
    public NoiseGeneratorOctaves noiseGenXen6;
    public NoiseGeneratorOctaves mobSpawnerNoiseXen;


    /** Reference to the World object. */
    private World worldObj;

    /** are map structures going to be generated (e.g. strongholds) */
    private final boolean mapFeaturesEnabled;

    /** Holds the overall noise array used in chunk generation */
    private double[] noiseArray;
    private double[] stoneNoise = new double[256];
    
    /** 第二次Noise所用 */
    private double[] noiseArrayXen;

    /** The biomes that are used to generate the chunk */
    private BiomeGenBase[] biomesForGeneration;

    /** A double array that hold terrain noise from noiseGen3 */
    double[] noise3;
    double[] noise1;
    double[] noise2;
    double[] noise5;
    double[] noise6;
    
    /** 第二次Noise所用 */
    double[] noiseXen3;
    double[] noiseXen1;
    double[] noiseXen2;
    double[] noiseXen5;
    double[] noiseXen6;
    
    /**
     * Used to store the 5x5 parabolic field that is used during terrain generation.
     */
    float[] parabolicField;
    
    /** 第二次Noise所用 */
    float[] parabolicFieldXen;
    int[][] field_73219_j = new int[32][32];
    
    private MapGenBase ravineGenerator = new MapGenRavine();
    private MapGenBase caveGenerator = new MapGenCaves();
    
    /** 生成小型浮空岛或者浮空岛群组   */
    private MapGenFloatIsland floatIslandGenerator = new MapGenFloatIsland();
    private MapGenFloatIslandGroup floatIslandGroupGenerator = new MapGenFloatIslandGroup();

    public ChunkProviderXenContinent(World par1World, long par2, boolean par4)
    {
        this.worldObj = par1World;
        this.mapFeaturesEnabled = par4;
        this.rand = new Random(par2);
        
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);

        NoiseGeneratorOctaves[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6, mobSpawnerNoise};
        noiseGens = TerrainGen.getModdedNoiseGenerators(par1World, this.rand, noiseGens);
        this.noiseGen1 = noiseGens[0];
        this.noiseGen2 = noiseGens[1];
        this.noiseGen3 = noiseGens[2];
        this.noiseGen4 = noiseGens[3];
        this.noiseGen5 = noiseGens[4];
        this.noiseGen6 = noiseGens[5];
        this.mobSpawnerNoise = noiseGens[6];
        
        this.noiseGenXen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGenXen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGenXen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGenXen4 = new NoiseGeneratorOctaves(this.rand, 4);
        this.noiseGenXen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGenXen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoiseXen = new NoiseGeneratorOctaves(this.rand, 8);
        
        NoiseGeneratorOctaves[] noiseGensXen = {noiseGenXen1, noiseGenXen2, noiseGenXen3, noiseGenXen4, noiseGenXen5, noiseGenXen6, mobSpawnerNoiseXen};
        noiseGensXen = TerrainGen.getModdedNoiseGenerators(par1World, this.rand, noiseGensXen);
        this.noiseGenXen1 = noiseGensXen[0];
        this.noiseGenXen2 = noiseGensXen[1];
        this.noiseGenXen3 = noiseGensXen[2];
        this.noiseGenXen4 = noiseGensXen[3];
        this.noiseGenXen5 = noiseGensXen[4];
        this.noiseGenXen6 = noiseGensXen[5];
        this.mobSpawnerNoiseXen = noiseGensXen[6];
    }

    /**
     * Generates the shape of the terrain for the chunk though its all stone though the water is frozen if the
     * temperature is low enough
     */
    public void generateTerrain(int par1, int par2, byte[] par3ArrayOfByte)
    {
        byte b0 = 4;
        byte b1 = 16;
        byte b2 = 63;
        int k = b0 + 1;
        byte b3 = 17;
        int l = b0 + 1;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, k + 5, l + 5);
        this.noiseArray = this.initializeNoiseField(this.noiseArray, par1 * b0, 0, par2 * b0, k, b3, l);
        byte blockID;

        for (int i1 = 0; i1 < b0; ++i1)
        {
            for (int j1 = 0; j1 < b0; ++j1)
            {
                for (int k1 = 0; k1 < b1; ++k1)
                {
                    double d0 = 0.125D;
                    double d1 = this.noiseArray[((i1 + 0) * l + j1 + 0) * b3 + k1 + 0];
                    double d2 = this.noiseArray[((i1 + 0) * l + j1 + 1) * b3 + k1 + 0];
                    double d3 = this.noiseArray[((i1 + 1) * l + j1 + 0) * b3 + k1 + 0];
                    double d4 = this.noiseArray[((i1 + 1) * l + j1 + 1) * b3 + k1 + 0];
                    double d5 = (this.noiseArray[((i1 + 0) * l + j1 + 0) * b3 + k1 + 1] - d1) * d0;
                    double d6 = (this.noiseArray[((i1 + 0) * l + j1 + 1) * b3 + k1 + 1] - d2) * d0;
                    double d7 = (this.noiseArray[((i1 + 1) * l + j1 + 0) * b3 + k1 + 1] - d3) * d0;
                    double d8 = (this.noiseArray[((i1 + 1) * l + j1 + 1) * b3 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; ++l1)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2)
                        {
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            short short1 = 128;
                            j2 -= short1;
                            double d14 = 0.25D;
                            double d15 = (d11 - d10) * d14;
                            double d16 = d10 - d15;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                if ((d16 += d15) > 0.0D)
                                {
                                	par3ArrayOfByte[j2 += short1] = (byte)XenBlocks.stone.blockID;
                                }
                                else if (k1 * 8 + l1 < b2)
                                {
                                	par3ArrayOfByte[j2 += short1] = 0;
                                }
                                else
                                {
                                	par3ArrayOfByte[j2 += short1] = 0;
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }
    
    /**
     * 挖出空岛
     * 
     * @author F
     * @param par1
     * @param par2
     * @param par3ArrayOfByte
     */
    public void generateTerrainXen(int par1, int par2, byte[] par3ArrayOfByte){
    	
    	byte b0 = 4;
        byte b1 = 16;
        byte b2 = 63;
        int k = b0 + 1;
        byte b3 = 17;
        int l = b0 + 1;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, k + 5, l + 5);
        this.noiseArrayXen = this.initializeNoiseFieldXen(this.noiseArrayXen, par1 * b0, 0, par2 * b0, k, b3, l);
    	
    	for (int i1 = 0; i1 < b0; ++i1)
        {
            for (int j1 = 0; j1 < b0; ++j1)
            {
                for (int k1 = 0; k1 < b1; ++k1)
                {
                    double d0 = 0.125D;
                    double d1 = this.noiseArrayXen[((i1 + 0) * l + j1 + 0) * b3 + k1 + 0];
                    double d2 = this.noiseArrayXen[((i1 + 0) * l + j1 + 1) * b3 + k1 + 0];
                    double d3 = this.noiseArrayXen[((i1 + 1) * l + j1 + 0) * b3 + k1 + 0];
                    double d4 = this.noiseArrayXen[((i1 + 1) * l + j1 + 1) * b3 + k1 + 0];
                    double d5 = (this.noiseArrayXen[((i1 + 0) * l + j1 + 0) * b3 + k1 + 1] - d1) * d0;
                    double d6 = (this.noiseArrayXen[((i1 + 0) * l + j1 + 1) * b3 + k1 + 1] - d2) * d0;
                    double d7 = (this.noiseArrayXen[((i1 + 1) * l + j1 + 0) * b3 + k1 + 1] - d3) * d0;
                    double d8 = (this.noiseArrayXen[((i1 + 1) * l + j1 + 1) * b3 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; ++l1)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2)
                        {
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            short short1 = 128;
                            j2 -= short1;
                            double d14 = 0.25D;
                            double d15 = (d11 - d10) * d14;
                            double d16 = d10 - d15;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                if ((d16 += d15) > 0.0D)
                                {
                                	par3ArrayOfByte[j2 += short1] = 0;
                                }
                                else if (k1 * 8 + l1 < b2)
                                {
                                	par3ArrayOfByte[j2 += short1] = par3ArrayOfByte[j2];
                                }
                                else
                                {
                                	par3ArrayOfByte[j2 += short1] = par3ArrayOfByte[j2];
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }
    
    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
    {

        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        if (this.parabolicField == null)
        {
            this.parabolicField = new float[25];

            for (int k1 = -2; k1 <= 2; ++k1)
            {
                for (int l1 = -2; l1 <= 2; ++l1)
                {
                    float f = 10.0F / MathHelper.sqrt_float(k1 * k1 + l1 * l1 + 0.2F);
                    this.parabolicField[k1 + 2 + (l1 + 2) * 5] = f;
                }
            }
        }

        double d0 = 684.412D;
        double d1 = 684.412D;
        this.noise5 = this.noiseGen5.generateNoiseOctaves(this.noise5, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
        this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
        this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
        this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        boolean flag = false;
        boolean flag1 = false;
        int i2 = 0;
        int j2 = 0;

        for (int k2 = 0; k2 < par5; ++k2)
        {
            for (int l2 = 0; l2 < par7; ++l2)
            {
                float f1 = 0.0F;
                float f2 = 0.0F;
                float f3 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[k2 + 2 + (l2 + 2) * (par5 + 5)];

                for (int i3 = -b0; i3 <= b0; ++i3)
                {
                    for (int j3 = -b0; j3 <= b0; ++j3)
                    {
                        BiomeGenBase biomegenbase1 = this.biomesForGeneration[k2 + i3 + 2 + (l2 + j3 + 2) * (par5 + 5)];
                        float f4 = this.parabolicField[i3 + 2 + (j3 + 2) * 5] / (biomegenbase1.minHeight + 2.0F);//min

                        if (biomegenbase1.minHeight > biomegenbase.minHeight)//min
                        {
                            f4 /= 2.0F;
                        }

                        f1 += biomegenbase1.maxHeight * f4;//max
                        f2 += biomegenbase1.minHeight * f4;//min
                        f3 += f4;
                        
                    }
                }

                f1 /= f3;
                f2 /= f3;
                f1 = f1 * 0.9F + 0.1F;
                f2 = (f2 * 4.0F - 1.0F) / 8.0F;
                double d2 = this.noise6[j2] / 8000.0D;

                if (d2 < 0.0D)
                {
                    d2 = -d2 * 0.3D;
                }

                d2 = d2 * 3.0D - 2.0D;

                if (d2 < 0.0D)
                {
                    d2 /= 2.0D;

                    if (d2 < -1.0D)
                    {
                        d2 = -1.0D;
                    }

                    d2 /= 1.4D;
                    d2 /= 2.0D;
                }
                else
                {
                    if (d2 > 1.0D)
                    {
                        d2 = 1.0D;
                    }

                    d2 /= 8.0D;
                }

                ++j2;

                for (int k3 = 0; k3 < par6; ++k3)
                {
                    double d3 = f2;
                    double d4 = f1;
                    d3 += d2 * 0.2D;
                    d3 = d3 * par6 / 16.0D;
                    double d5 = par6 / 2.0D + d3 * 4.0D;
                    double d6 = 0.0D;
                    double d7 = (k3 - d5) * 12.0D * 128.0D / 128.0D / d4;

                    if (d7 < 0.0D)
                    {
                        d7 *= 4.0D;
                    }

                    double d8 = this.noise1[i2] / 512.0D;
                    double d9 = this.noise2[i2] / 512.0D;
                    double d10 = (this.noise3[i2] / 10.0D + 1.0D) / 2.0D;

                    if (d10 < 0.0D)
                    {
                        d6 = d8;
                    }
                    else if (d10 > 1.0D)
                    {
                        d6 = d9;
                    }
                    else
                    {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;

                    if (k3 > par6 - 4)
                    {
                        double d11 = (k3 - (par6 - 4)) / 3.0F;
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    par1ArrayOfDouble[i2] = d6;
                    ++i2;
                }
            }
        }

        return par1ArrayOfDouble;
    }
    /**
     * 
     * @author F
     * @param par1ArrayOfDouble
     * @param par2
     * @param par3
     * @param par4
     * @param par5
     * @param par6
     * @param par7
     * @return
     */
    private double[] initializeNoiseFieldXen(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
    {

        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        if (this.parabolicFieldXen == null)
        {
            this.parabolicFieldXen = new float[25];

            for (int k1 = -2; k1 <= 2; ++k1)
            {
                for (int l1 = -2; l1 <= 2; ++l1)
                {
                    float f = 10.0F / MathHelper.sqrt_float(k1 * k1 + l1 * l1 + 0.2F);
                    this.parabolicFieldXen[k1 + 2 + (l1 + 2) * 5] = f;
                }
            }
        }

        double d0 = 684.412D;
        double d1 = 684.412D;
        this.noiseXen5 = this.noiseGenXen5.generateNoiseOctaves(this.noiseXen5, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
        this.noiseXen6 = this.noiseGenXen6.generateNoiseOctaves(this.noiseXen6, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
        this.noiseXen3 = this.noiseGenXen3.generateNoiseOctaves(this.noiseXen3, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
        this.noiseXen1 = this.noiseGenXen1.generateNoiseOctaves(this.noiseXen1, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        this.noiseXen2 = this.noiseGenXen2.generateNoiseOctaves(this.noiseXen2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        boolean flag = false;
        boolean flag1 = false;
        int i2 = 0;
        int j2 = 0;
        float minHeight = -1.3F;
        float maxHeight = 1.0F;

        for (int k2 = 0; k2 < par5; ++k2)
        {
            for (int l2 = 0; l2 < par7; ++l2)
            {
                float f1 = 0.0F;
                float f2 = 0.0F;
                float f3 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[k2 + 2 + (l2 + 2) * (par5 + 5)];

                for (int i3 = -b0; i3 <= b0; ++i3)
                {
                    for (int j3 = -b0; j3 <= b0; ++j3)
                    {
                        BiomeGenBase biomegenbase1 = this.biomesForGeneration[k2 + i3 + 2 + (l2 + j3 + 2) * (par5 + 5)];
                        float f4 = this.parabolicField[i3 + 2 + (j3 + 2) * 5] / (minHeight + 2.0F);

                        if (minHeight > biomegenbase.minHeight)
                        {
                            f4 /= 2.0F;
                        }

                        f1 += maxHeight * f4;
                        f2 += minHeight * f4;
                        f3 += f4;
                    }
                }

                f1 /= f3;
                f2 /= f3;
                f1 = f1 * 0.9F + 0.1F;
                f2 = (f2 * 4.0F - 1.0F) / 8.0F;
                double d2 = this.noiseXen6[j2] / 8000.0D;

                if (d2 < 0.0D)
                {
                    d2 = -d2 * 0.3D;
                }

                d2 = d2 * 3.0D - 2.0D;

                if (d2 < 0.0D)
                {
                    d2 /= 2.0D;

                    if (d2 < -1.0D)
                    {
                        d2 = -1.0D;
                    }

                    d2 /= 1.4D;
                    d2 /= 2.0D;
                }
                else
                {
                    if (d2 > 1.0D)
                    {
                        d2 = 1.0D;
                    }

                    d2 /= 8.0D;
                }

                ++j2;

                for (int k3 = 0; k3 < par6; ++k3)
                {
                    double d3 = f2;
                    double d4 = f1;
                    d3 += d2 * 0.2D;
                    d3 = d3 * par6 / 16.0D;
                    double d5 = par6 / 2.0D + d3 * 4.0D;
                    double d6 = 0.0D;
                    double d7 = (k3 - d5) * 12.0D * 128.0D / 128.0D / d4;

                    if (d7 < 0.0D)
                    {
                        d7 *= 4.0D;
                    }

                    double d8 = this.noiseXen1[i2] / 512.0D;
                    double d9 = this.noiseXen2[i2] / 512.0D;
                    double d10 = (this.noiseXen3[i2] / 10.0D + 1.0D) / 2.0D;

                    if (d10 < 0.0D)
                    {
                        d6 = d8;
                    }
                    else if (d10 > 1.0D)
                    {
                        d6 = d9;
                    }
                    else
                    {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;

                    if (k3 > par6 - 4)
                    {
                        double d11 = (k3 - (par6 - 4)) / 3.0F;
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    par1ArrayOfDouble[i2] = d6;
                    ++i2;
                }
            }
        }

        return par1ArrayOfDouble;
    }
    
    /**
     * loads or generates the chunk at the chunk location specified
     */
    @Override
	public Chunk loadChunk(int par1, int par2)
    {
        return this.provideChunk(par1, par2);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    @Override
	public Chunk provideChunk(int par1, int par2)
    {
        this.rand.setSeed(par1 * 341873128712L + par2 * 132897987541L);
        byte[] abyte = new byte[32768];
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        this.generateTerrain(par1, par2, abyte);
        this.floatIslandGenerator.generate(this, this.worldObj, par1, par2, abyte);
        this.floatIslandGroupGenerator.generate(this, this.worldObj, par1, par2, abyte);
        this.replaceBlocksForBiome(par1, par2, abyte, this.biomesForGeneration);
        this.generateTerrainXen(par1, par2, abyte);
        this.caveGenerator.generate(this, this.worldObj, par1, par2, abyte);
        this.ravineGenerator.generate(this, this.worldObj, par1, par2, abyte);

        if (this.mapFeaturesEnabled){}

        Chunk chunk = new Chunk(this.worldObj, abyte, par1, par2);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
        {
            abyte1[k] = (byte)this.biomesForGeneration[k].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }
    
    /**
     * Replaces the stone that was placed in with blocks that match the biome
     */
    public void replaceBlocksForBiome(int par1, int par2, byte[] par3ArrayOfByte, BiomeGenBase[] par4ArrayOfBiomeGenBase)
    {

    	/**    海平面数值 在主世界中海的水方块所在Y轴最大值为该值减一     */
        byte seaLevel = 35;
        double d0 = 0.03125D;
        this.stoneNoise = this.noiseGen4.generateNoiseOctaves(this.stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[z + x * 16];
                float temperature = biomegenbase.getFloatTemperature();
                
                /**  
                 *   此处（当前X、Z值所对应的）的噪点值。范围为1-4，事实上它决定了会有几层fillerblock  
                 *   但从下面代码来看貌似还可以小于或等于0？猜测为保护性代码
                 */
                int noise = (int)(this.stoneNoise[x + z * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                
                int j1 = -1;
                byte topBlock = biomegenbase.topBlock;
                byte fillerBlock = biomegenbase.fillerBlock;
                
                for (int y = 127; y >= 0; --y)
                {
                	/**  当前所检索到的方块在ArrayOfByte中所对应的序号  */
                    int currentBlockInArrayOfByte = (z * 16 + x) * 128 + y;
                    
                    byte currentBlockId = par3ArrayOfByte[currentBlockInArrayOfByte];

                    if(biomegenbase == MainBiomes.xenBroken)
                    {
                    	if(rand.nextInt(50) != 0)
                    	{
                    		par3ArrayOfByte[currentBlockInArrayOfByte] = 0;
                    	}
                    }
                    else
                    {
                    	if (topBlock == 0)
                        {
                            par3ArrayOfByte[currentBlockInArrayOfByte] = 0;
                        }
                        
                        if (currentBlockId == 0)
                        {
                            j1 = -1;
                        }
                        else if (currentBlockId == (byte)XenBlocks.stone.blockID)
                        {
                            if (j1 == -1)
                            {
                                if (noise <= 0)
                                {
                                	topBlock = 0;
                                	fillerBlock = (byte)XenBlocks.stone.blockID;
                                }
                                else if (y >= seaLevel - 4 && y <= seaLevel + 1)
                                {
                                	topBlock = biomegenbase.topBlock;
                                	fillerBlock = biomegenbase.fillerBlock;
                                }

                                if (y < seaLevel && topBlock == 0)
                                {
                                    if (temperature < 0.15F)
                                    {
                                    	topBlock = (byte)Block.ice.blockID;
                                    }
                                    else
                                    {
                                    	topBlock = (byte)Block.waterStill.blockID;
                                    }
                                }

                                j1 = noise;

                                if (y >= seaLevel - 1)
                                {
                                    par3ArrayOfByte[currentBlockInArrayOfByte] = topBlock;
                                }
                                else
                                {
                                    par3ArrayOfByte[currentBlockInArrayOfByte] = fillerBlock;
                                }
                            }
                            else if (j1 > 0)
                            {
                                --j1;
                                par3ArrayOfByte[currentBlockInArrayOfByte] = fillerBlock;

                                if (j1 == 0 && fillerBlock == Block.sand.blockID)
                                {
                                    j1 = this.rand.nextInt(4);
                                    fillerBlock = (byte)Block.sandStone.blockID;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    @Override
	public boolean chunkExists(int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    @Override
	public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        BlockSand.fallInstantly = true;
        int k = par2 * 16;
        int l = par3 * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(par2 * i1 + par3 * j1 ^ this.worldObj.getSeed());
        boolean flag = false;
        
        int k1;
        int l1;
        int i2;
        
        if (!flag && this.rand.nextInt(4) == 0)
        {
            k1 = k + this.rand.nextInt(16) + 8;
            l1 = this.rand.nextInt(80) + 30;
            i2 = l + this.rand.nextInt(16) + 8;
            (new WorldGenLakes(XenBlocks.waterStill.blockID)).generate(this.worldObj, this.rand, k1, l1, i2);
        }

        if (!flag && this.rand.nextInt(32) == 0)
        {
            k1 = k + this.rand.nextInt(16) + 8;
            l1 = this.rand.nextInt(80) + 30;
            i2 = l + this.rand.nextInt(16) + 8;
            (new WorldGenLakes(XenBlocks.slimeStill.blockID)).generate(this.worldObj, this.rand, k1, l1, i2);
        }

        biomegenbase.decorate(this.worldObj, this.rand, k, l);
        BlockSand.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    @Override
	public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    @Override
	public void saveExtraData() {}

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    @Override
	public boolean unloadQueuedChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    @Override
	public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    @Override
	public String makeString()
    {
        return "RandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    @Override
	public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(par2, par4);
        return biomegenbase.getSpawnableList(par1EnumCreatureType);
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    @Override
	public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
    {
        return null;
    }

    @Override
	public int getLoadedChunkCount()
    {
        return 0;
    }

    @Override
	public void recreateStructures(int par1, int par2)
    {
        if (this.mapFeaturesEnabled){}
    }

}

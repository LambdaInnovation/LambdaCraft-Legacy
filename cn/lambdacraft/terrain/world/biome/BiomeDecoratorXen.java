package cn.lambdacraft.terrain.world.biome;

import java.util.Random;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.terrain.register.XenBlocks;
import cn.lambdacraft.terrain.world.gen.feature.WorldGenAmethyst;
import cn.lambdacraft.terrain.world.gen.feature.WorldGenLights;
import cn.lambdacraft.terrain.world.gen.feature.WorldGenMinableXen;
import cn.lambdacraft.terrain.world.gen.feature.WorldGenPillar;
import cn.lambdacraft.terrain.world.gen.feature.WorldGenXenSand;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
/**
 * 创建矿石
 * @author F
 *
 */
public class BiomeDecoratorXen extends BiomeDecorator {

	/** The world the BiomeDecorator is currently decorating */
    public World currentWorld;

    /** The Biome Decorator's random number generator. */
    public Random randomGenerator;

    /** The X-coordinate of the chunk currently being decorated */
    public int chunk_X;

    /** The Z-coordinate of the chunk currently being decorated */
    public int chunk_Z;

    /** The biome generator object. */
    public BiomeGenBase biome;

    public WorldGenerator crystalGen;
    public WorldGenerator lightGen;
    public WorldGenerator amethystGen;
    public WorldGenerator slimeGen;
    public WorldGenerator xenSandGen;

    public BiomeDecoratorXen()
    {
    	super();
        this.crystalGen = new WorldGenMinableXen(XenBlocks.crystal, 8);
        this.lightGen = new WorldGenLights();
        this.amethystGen = new WorldGenAmethyst();
        this.xenSandGen = new WorldGenXenSand(7);
    }

    /**
     * Decorates the world. Calls code that was formerly (pre-1.8) in ChunkProviderGenerate.populate
     */
    @Override
	public void decorateChunk(World par1World, Random par2Random, BiomeGenBase biomeGenBase, int par3, int par4)
    {
        if (this.currentWorld != null)
        {
           // throw new RuntimeException("Already decorating!!");
        	CBCMod.log.severe("XenDecorator Already Decorating!");
        }
        else
        {
            this.currentWorld = par1World;
            this.randomGenerator = par2Random;
            this.chunk_X = par3;
            this.chunk_Z = par4;
            this.genDecorations(biomeGenBase);
            this.currentWorld = null;
            this.randomGenerator = null;
            
        }
    }

    /**
     * The method that does the work of actually decorating chunks
     */
    @Override
	protected void genDecorations(BiomeGenBase biomeGenBase)
    {
       this.generateOres();
       
       int i;
       int l;
       int k;
       int j;
       
       if(this.randomGenerator.nextInt(20) == 0)
       {
    	   k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
           l = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
           WorldGenerator worldgenerator = new WorldGenPillar();
           worldgenerator.generate(this.currentWorld, this.randomGenerator, k, this.currentWorld.getHeightValue(k, l), l);
       }
       
       for (j = 0; j < 2; ++j)
       {
           k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
           l = this.randomGenerator.nextInt(80) + 30;
           i = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
           this.lightGen.generate(this.currentWorld, this.randomGenerator, k, l, i);
       }
       
       for (j = 0; j < 2; ++j)
       {
           k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
           l = this.randomGenerator.nextInt(128);
           i = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
           this.amethystGen.generate(this.currentWorld, this.randomGenerator, k, l, i);
       }
       
       if(this.randomGenerator.nextInt(8) == 0)
       {
    	   j = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
           k = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
           this.xenSandGen.generate(this.currentWorld, this.randomGenerator, j, this.currentWorld.getTopSolidOrLiquidBlock(j, k), k);
       }
       
       
    }

    /**
     * Standard ore generation helper. Generates most ores.
     */
    @Override
	protected void genStandardOre1(int par1, WorldGenerator par2WorldGenerator, int par3, int par4)
    {
        for (int l = 0; l < par1; ++l)
        {
            int i1 = this.chunk_X + this.randomGenerator.nextInt(16);
            int j1 = this.randomGenerator.nextInt(par4 - par3) + par3;
            int k1 = this.chunk_Z + this.randomGenerator.nextInt(16);
            par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, i1, j1, k1);
        }
    }

    /**
     * Standard ore generation helper. Generates Lapis Lazuli.
     */
    @Override
	protected void genStandardOre2(int par1, WorldGenerator par2WorldGenerator, int par3, int par4)
    {
        for (int l = 0; l < par1; ++l)
        {
            int i1 = this.chunk_X + this.randomGenerator.nextInt(16);
            int j1 = this.randomGenerator.nextInt(par4) + this.randomGenerator.nextInt(par4) + (par3 - par4);
            int k1 = this.chunk_Z + this.randomGenerator.nextInt(16);
            par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, i1, j1, k1);
        }
    }

    /**
     * Generates ores in the current chunk
     */
    @Override
	protected void generateOres()
    {
        this.genStandardOre1(2, this.crystalGen, 30, 100);
    }
}


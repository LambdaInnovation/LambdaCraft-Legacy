/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.terrain.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.core.util.LCParticles;
import cn.lambdacraft.terrain.ModuleTerrain;
import cn.lambdacraft.terrain.register.XenBlocks;
import cn.lambdacraft.terrain.tileentity.TileEntityXenPortal;
import cn.lambdacraft.terrain.world.TeleporterXen;

/**
 * Xen传送门
 * @author F
 *
 */
public class BlockXenPortal extends BlockContainer {
	
	private String iconName;
	private final float HALF_WIDTH = 0.4F;

	public BlockXenPortal() {
		super(Material.portal);
		this.setCreativeTab(CBCMod.cct);
		this.setStepSound(soundTypeGlass);
		this.setLightLevel(0.75F);
		this.setBlockName("xenPortal");
		this.setIconName("xen_portal1");
		this.setHardness(-1.0F);
		this.setResistance(2000.0F);
	}

	public void setIconName(String path) {
		iconName = path;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("lambdacraft:"
				+ iconName);
	}
	
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (par1World.provider.isSurfaceWorld() && par1World.getGameRules().getGameRuleBooleanValue("doMobSpawning") && par5Random.nextInt(2000) < par1World.difficultySetting.getDifficultyId())
        {
            int l;

            for (l = par3; !par1World.doesBlockHaveSolidTopSurface(par1World, par2, l, par4) && l > 0; --l)
            {
                ;
            }

            if (l > 0 && !par1World.getBlock(par2, l + 1, par4).isNormalCube())
            {
                Entity entity = ItemMonsterPlacer.spawnCreature(par1World, 57, par2 + 0.5D, l + 1.1D, par4 + 0.5D);

                if (entity != null)
                {
                    entity.timeUntilPortal = entity.getPortalCooldown();
                }
            }
        }
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.setBlockBounds(0.5F - HALF_WIDTH, 0.5F - HALF_WIDTH, 0.5F - HALF_WIDTH, 0.5F + HALF_WIDTH, 0.5F + HALF_WIDTH, 0.5F + HALF_WIDTH);
    }
	
    @Override
	public int getRenderType()
    {
        return ClientProps.RENDER_ID_XENPORTAL;
    }

    public boolean tryToCreatePortal(World par1World, int par2, int par3, int par4)
    {
        byte b0 = 0;
        byte b1 = 0;

        if (par1World.getBlock(par2 - 1, par3, par4) == Blocks.bedrock || par1World.getBlock(par2 + 1, par3, par4) == Blocks.bedrock)
        {
            b0 = 1;
        }

        if (par1World.getBlock(par2, par3, par4 - 1) == Blocks.bedrock || par1World.getBlock(par2, par3, par4 + 1) == Blocks.bedrock)
        {
            b1 = 1;
        }

        if (b0 == b1)
        {
            return false;
        }
        else
        {
            if (par1World.getBlock(par2 - b0, par3, par4 - b1) == Blocks.air)
            {
                par2 -= b0;
                par4 -= b1;
            }

            int l;
            int i1;

            for (l = -1; l <= 2; ++l)
            {
                for (i1 = -1; i1 <= 3; ++i1)
                {
                    boolean flag = l == -1 || l == 2 || i1 == -1 || i1 == 3;

                    if (l != -1 && l != 2 || i1 != -1 && i1 != 3)
                    {
                        Block j1 = par1World.getBlock(par2 + b0 * l, par3 + i1, par4 + b1 * l);

                        if (flag)
                        {
                            if (j1 != Blocks.bedrock)
                            {
                                return false;
                            }
                        }
                        else if (j1 != Blocks.air && j1 != Blocks.fire)
                        {
                            return false;
                        }
                    }
                }
            }

            for (l = 0; l < 2; ++l)
            {
                for (i1 = 0; i1 < 3; ++i1)
                {
                    par1World.setBlock(par2 + b0 * l, par3 + i1, par4 + b1 * l, XenBlocks.xenPortal , 0, 2);
                }
            }

            return true;
        }
    }
    
    /**
     *    
     * 传送部分代码
     * 
     */
    @Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null && par5Entity instanceof EntityPlayerMP)
        {
        	EntityPlayerMP thePlayer = (EntityPlayerMP)par5Entity;
            if (thePlayer.timeUntilPortal > 0)
            {
                    thePlayer.timeUntilPortal = 10;
            }
            else if (thePlayer.dimension != ModuleTerrain.xenContinentDimensionID)
            {
                    thePlayer.timeUntilPortal = 10;
                    thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, ModuleTerrain.xenContinentDimensionID, new TeleporterXen(thePlayer.mcServer.worldServerForDimension(ModuleTerrain.xenContinentDimensionID)));
            }
            else {
                    thePlayer.timeUntilPortal = 10;
                    thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, 0, new TeleporterXen(thePlayer.mcServer.worldServerForDimension(0)));
            }
        }
    }

	@Override
	public int quantityDropped(Random par1Random)
    {
        return 0;
    }
	
	@Override
	@SideOnly(Side.CLIENT)

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
	@SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par5Random.nextInt(100) == 0)
        {
            par1World.playSound(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "portal.portal", 0.5F, par5Random.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int l = 0; l < 4; ++l)
        {
            double d0 = par2 + par5Random.nextFloat();
            double d1 = par3 + par5Random.nextFloat();
            double d2 = par4 + par5Random.nextFloat();
            double d3 = 0.0D;
            double d4 = 0.0D;
            double d5 = 0.0D;
            int i1 = par5Random.nextInt(2) * 2 - 1;
            d3 = (par5Random.nextFloat() - 0.5D) * 0.5D;
            d4 = (par5Random.nextFloat() - 0.5D) * 0.5D;
            d5 = (par5Random.nextFloat() - 0.5D) * 0.5D;

            if (par1World.getBlock(par2 - 1, par3, par4) != this && par1World.getBlock(par2 + 1, par3, par4) != this)
            {
                d0 = par2 + 0.5D + 0.25D * i1;
                d3 = par5Random.nextFloat() * 2.0F * i1;
            }
            else
            {
                d2 = par4 + 0.5D + 0.25D * i1;
                d5 = par5Random.nextFloat() * 2.0F * i1;
            }

            LCParticles.spawnParticle(par1World, "xenportal", d0, d1, d2, d3, d4, d5);
        }
    }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityXenPortal();
	}

}

package cn.lambdacraft.terrain.block;

import cn.lambdacraft.core.CBCMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Xen黑柱
 * @author F
 *
 */
public class BlockBlackPillar extends Block{

	@SideOnly(Side.CLIENT)
    private IIcon iconPillar;
    @SideOnly(Side.CLIENT)
    private IIcon iconPillarSide;
	
	public BlockBlackPillar() 
	{
		super(Material.rock);
		this.setBlockName("xenblackpillar");
		this.setBlockTextureName("xen_black_pillar");
		this.setBlockUnbreakable();
		this.setResistance(2000.0F);
		this.setStepSound(soundTypeStone);
		setCreativeTab(CBCMod.cct);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
	    this.iconPillar = p_149651_1_.registerIcon("lambdacraft:" + this.getTextureName());
	    this.iconPillarSide = p_149651_1_.registerIcon("lambdacraft:" + this.getTextureName() + "_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess par1, int x, int y, int z, int side)
    {
        return this.getIcon(side, par1.getBlockMetadata(x, y, z));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
	     if(side == 1 || side == 0){
	    	 return iconPillar;
	     }else{
	    	 return iconPillarSide;
	     }
	}
	
}
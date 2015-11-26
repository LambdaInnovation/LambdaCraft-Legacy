package cn.lambdacraft.terrain.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.terrain.register.XenBlocks;
/**
 * Xen草方块
 * @author F
 *
 */
public class BlockXenGrass extends Block{

	@SideOnly(Side.CLIENT)
    private IIcon iconGrassTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconGrassSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconGrassBottom;
	
	public BlockXenGrass() 
	{
		super(Material.grass);
		this.setBlockName("xengrass");
		this.setBlockTextureName("xen_grass");
		this.setHardness(0.6F);
		this.setStepSound(soundTypeGrass);
		setCreativeTab(CBCMod.cct);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.iconGrassTop : (par1 == 0 ? this.iconGrassBottom : this.iconGrassSide);
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.iconGrassTop = par1IconRegister.registerIcon("lambdacraft:" + this.getTextureName() + "_top");
		this.iconGrassSide = par1IconRegister.registerIcon("lambdacraft:" + this.getTextureName() + "_side");
		this.iconGrassBottom = par1IconRegister.registerIcon("lambdacraft:" + "xen_dirt");
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(XenBlocks.dirt);
    }
}

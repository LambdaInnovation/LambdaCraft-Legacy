package cn.lambdacraft.terrain.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cn.lambdacraft.core.block.CBCBlock;
import cn.lambdacraft.terrain.register.XenBlocks;
/**
 * Xen草方块
 * @author F
 *
 */
public class BlockXenGrass extends CBCBlock{

	@SideOnly(Side.CLIENT)
    private Icon iconGrassTop;
    @SideOnly(Side.CLIENT)
    private Icon iconGrassSide;
    @SideOnly(Side.CLIENT)
    private Icon iconGrassBottom;
	
	public BlockXenGrass(int par1) 
	{
		super(Material.rock);
		this.setUnlocalizedName("xengrass");
		this.setIconName("xen_grass");
		this.setHardness(0.6F);
		this.setStepSound(soundGrassFootstep);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.iconGrassTop : (par1 == 0 ? this.iconGrassBottom : this.iconGrassSide);
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.iconGrassTop = par1IconRegister.registerIcon("lambdacraft:" + "xen_grass_top");
		this.iconGrassSide = par1IconRegister.registerIcon("lambdacraft:" + "xen_grass_side");
		this.iconGrassBottom = par1IconRegister.registerIcon("lambdacraft:" + "xen_dirt");
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return XenBlocks.dirt.blockID;
	}
}

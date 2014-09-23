package cn.lambdacraft.terrain.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

/**
 * Xen沙
 * @author F
 *
 */
public class BlockXenSand extends Block {

	public BlockXenSand(int par1)
	{
		super( Material.sand);
		this.setBlockName("xensand");
		this.setBlockTextureName("xen_sand");
		this.setHardness(0.5F);
		this.setStepSound(soundTypeSand);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
	    this.blockIcon = p_149651_1_.registerIcon("lambdacraft:" + this.getTextureName());
	}
}

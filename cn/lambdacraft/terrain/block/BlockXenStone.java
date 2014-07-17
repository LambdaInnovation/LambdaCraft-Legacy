package cn.lambdacraft.terrain.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

/**
 * Xen石
 * @author F
 *
 */
public class BlockXenStone extends Block {

	public BlockXenStone() 
	{
		super(Material.rock);
		this.setBlockName("xenstone");
		this.setBlockTextureName("lambdacraft:xen_stone");
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		this.setStepSound(soundTypeStone);
	}
}
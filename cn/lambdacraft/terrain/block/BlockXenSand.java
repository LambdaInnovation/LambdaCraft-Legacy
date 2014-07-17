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

	public BlockXenSand()
	{
		super(Material.sand);
		this.setBlockName("xensand");
		this.setBlockTextureName("lambdacraft:xen_sand");
		this.setHardness(0.5F);
		this.setStepSound(soundTypeSand);
	}
	
}

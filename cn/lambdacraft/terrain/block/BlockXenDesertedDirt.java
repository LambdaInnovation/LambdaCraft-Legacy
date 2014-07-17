package cn.lambdacraft.terrain.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

/**
 * Xen荒芜土 主要是用于传送时创建类似门东西
 * @author F
 *
 */
public class BlockXenDesertedDirt extends Block {

	public BlockXenDesertedDirt() 
	{
		super(Material.ground);
		this.setBlockName("xendeserteddirt");
		this.setBlockTextureName("lambdacraft:xen_deserted_dirt");
		this.setHardness(0.8F);
		this.setStepSound(soundTypeGravel);
	}
}
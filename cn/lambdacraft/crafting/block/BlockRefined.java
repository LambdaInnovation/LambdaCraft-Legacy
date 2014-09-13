package cn.lambdacraft.crafting.block;

import cn.lambdacraft.core.CBCMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockRefined extends Block {

	public BlockRefined() {
		super(Material.iron);
		setCreativeTab(CBCMod.cct);
		setHardness(2.0F);
		setBlockName("refined");
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon("lambdacraft:refined");
	}

}

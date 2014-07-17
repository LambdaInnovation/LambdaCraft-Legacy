package cn.lambdacraft.crafting.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import cn.lambdacraft.core.LCMod;

public class BlockRefined extends Block {

	public BlockRefined() {
		super(Material.iron);
		setCreativeTab(LCMod.cctMisc);
		setHardness(2.0F);
		setBlockName("refined");
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon("lambdacraft:refined");
	}

}

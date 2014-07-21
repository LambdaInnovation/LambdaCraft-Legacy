package cn.lambdacraft.crafting.block;

import cn.lambdacraft.core.CBCMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockRefined extends Block {

	public BlockRefined(int par1) {
		super(par1, Material.iron);
		setCreativeTab(CBCMod.cctMisc);
		setHardness(2.0F);
		setUnlocalizedName("refined");
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon("lambdacraft:refined");
	}

}

package cn.lambdacraft.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cn.lambdacraft.core.CBCMod;

public class CBCBlock extends Block {

	public CBCBlock(Material par2Material) {
		super(par2Material);
		setCreativeTab(CBCMod.cct);
	}

}

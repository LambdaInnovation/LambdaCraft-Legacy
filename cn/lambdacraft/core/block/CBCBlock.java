package cn.lambdacraft.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import cn.lambdacraft.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CBCBlock extends Block {

	public CBCBlock(Material par2Material) {
		super(par2Material);
		setCreativeTab(CBCMod.cct);
	}

}

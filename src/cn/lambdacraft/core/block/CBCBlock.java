package cn.lambdacraft.core.block;

import cn.lambdacraft.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class CBCBlock extends Block {

	private String iconName;

	public CBCBlock(int par1, Material par2Material) {
		super(par1, par2Material);
		setCreativeTab(CBCMod.cct);
	}

	public void setIconName(String path) {
		iconName = path;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("lambdacraft:"
				+ iconName);
	}

}

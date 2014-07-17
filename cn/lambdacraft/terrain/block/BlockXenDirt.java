package cn.lambdacraft.terrain.block;

import java.util.Random;

import cn.lambdacraft.terrain.register.XenBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

/**
 * Xen土
 * @author F
 *
 */
public class BlockXenDirt extends Block {

	public BlockXenDirt()
	{
		super(Material.rock);
		this.setBlockName("xendirt");
		this.setBlockTextureName("lambdacraft:xen_dirt");
		this.setHardness(0.5F);
		this.setStepSound(soundTypeGravel);
	}
}
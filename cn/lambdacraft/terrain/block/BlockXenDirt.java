package cn.lambdacraft.terrain.block;

import net.minecraft.block.material.Material;
import cn.lambdacraft.core.block.CBCBlock;

/**
 * Xen土
 * @author F
 *
 */
public class BlockXenDirt extends CBCBlock {

	public BlockXenDirt(int par1)
	{
		super(par1, Material.rock);
		this.setUnlocalizedName("xendirt");
		this.setIconName("xen_dirt");
		this.setHardness(0.5F);
		this.setStepSound(soundGravelFootstep);
	}
}
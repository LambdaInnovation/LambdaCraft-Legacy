package cn.lambdacraft.terrain.block;

import net.minecraft.block.material.Material;
import cn.lambdacraft.core.block.CBCBlock;

/**
 * Xen石
 * @author F
 *
 */
public class BlockXenStone extends CBCBlock {

	public BlockXenStone(int par1) 
	{
		super(par1, Material.rock);
		this.setUnlocalizedName("xenstone");
		this.setIconName("xen_stone");
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		this.setStepSound(soundStoneFootstep);
	}
}
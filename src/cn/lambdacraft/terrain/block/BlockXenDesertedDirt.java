package cn.lambdacraft.terrain.block;

import net.minecraft.block.material.Material;
import cn.lambdacraft.core.block.CBCBlock;

/**
 * Xen荒芜土 主要是用于传送时创建类似门东西
 * @author F
 *
 */
public class BlockXenDesertedDirt extends CBCBlock {

	public BlockXenDesertedDirt(int par1) {
		super(par1, Material.ground);
		this.setUnlocalizedName("xendeserteddirt");
		this.setIconName("xen_deserted_dirt");
		this.setHardness(0.8F);
		this.setStepSound(soundGravelFootstep);
	}

}
package cn.lambdacraft.terrain.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import cn.lambdacraft.core.block.CBCBlock;
/**
 * Xen水晶方块 掉落水晶材料
 * @author F
 *
 */
public class BlockXenCrystal extends CBCBlock {

	public BlockXenCrystal(int par1) {
		super(Material.rock);
		this.setUnlocalizedName("xencrystal");
		this.setIconName("xen_crystal");
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		this.setStepSound(soundStoneFootstep);
		this.setLightValue(0.5F);
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		//TODO 要掉落啥东西呢
		return this.blockID;
	}

}

package cn.lambdacraft.terrain.block;

import cn.lambdacraft.core.CBCMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
/**
 * Xen水晶方块 掉落水晶材料
 * @author F
 *
 */
public class BlockXenCrystal extends Block {

	public BlockXenCrystal() {
		super(Material.rock);
		this.setBlockName("xencrystal");
		this.setBlockTextureName("xen_crystal");
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		this.setStepSound(soundTypeStone);
		this.setLightLevel(0.5F);
		this.setHarvestLevel("pickaxe", 1);
		setCreativeTab(CBCMod.cct);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
	    this.blockIcon = p_149651_1_.registerIcon("lambdacraft:" + this.getTextureName());
	}

}

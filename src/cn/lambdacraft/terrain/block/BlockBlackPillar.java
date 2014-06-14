package cn.lambdacraft.terrain.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cn.lambdacraft.core.block.CBCBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Xen黑柱
 * @author F
 *
 */
public class BlockBlackPillar extends CBCBlock{

	@SideOnly(Side.CLIENT)
    private Icon iconPillar;
    @SideOnly(Side.CLIENT)
    private Icon iconPillarSide;
	
	public BlockBlackPillar(int par1) 
	{
		super(par1, Material.rock);
		this.setUnlocalizedName("xenblackpillar");
		this.setIconName("xen_black_pillar");
		this.setHardness(-1.0F);
		this.setResistance(2000.0F);
		this.setStepSound(soundStoneFootstep);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.iconPillar : (par1 == 0 ? this.iconPillar : this.iconPillarSide);
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.iconPillar = par1IconRegister.registerIcon("lambdacraft:" + "xen_black_pillar");
		this.iconPillarSide = par1IconRegister.registerIcon("lambdacraft:" + "xen_black_pillar_side");
	}
}
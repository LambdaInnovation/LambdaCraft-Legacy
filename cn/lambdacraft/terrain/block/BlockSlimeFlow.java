package cn.lambdacraft.terrain.block;

import cn.lambdacraft.core.LCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class BlockSlimeFlow extends BlockDynamicLiquid 
{
	
	public BlockSlimeFlow()
	{
        super(Material.water);
        this.setCreativeTab(LCMod.cct);
        this.setHardness(100.0F);
        this.setLightOpacity(3);
        this.setBlockName("slimeflow");
        this.setBlockTextureName("lambdacraft:slime_flow");
        this.disableStats();
        this.setCreativeTab(LCMod.cct);
	}
}
